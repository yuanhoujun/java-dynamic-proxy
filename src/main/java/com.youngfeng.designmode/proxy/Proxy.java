package com.youngfeng.designmode.proxy;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-01 11:40
 */
public class Proxy {
    // 这里需要修改为你自己期望的源码生成路径
    private static String PATH = "/Users/ouyangfeng/Desktop/";

    public static Object newProxyInstance(InvocationHandler handler, Class ints) {
        try {
            FieldSpec fieldSpec = FieldSpec.builder(InvocationHandler.class, "invocationHandler")
                    .addModifiers(Modifier.PRIVATE)
                    .build();

            MethodSpec constructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(InvocationHandler.class, "invocationHandler")
                    .addStatement("this.$N = $N", "invocationHandler", "invocationHandler")
                    .build();

            String className = "Proxy$0";

            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ints)
                    .addMethod(constructor)
                    .addField(fieldSpec);

            // 为了保证灵活性，这里需要遍历接口中的方法，逐一实现
            Method[] methods = ints.getMethods();

            for (Method method: methods) {
                MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(method.getName())
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class);

                Class[] params = method.getParameterTypes();
                String args = params.length <= 0 ? null : "new Object[] {";
                String argTypes = params.length <= 0 ? null : "new Class[] {";

                for (int i = 0; i < params.length; i++) {
                    Class param = params[i];
                    methodSpecBuilder.addParameter(ParameterSpec.builder(param, "p" + i).build());
                    args += "p" + i + ",";
                    argTypes += TypeName.get(param).toString() + ".class,";
                }

                args = args == null ? args : args.substring(0, args.length() - 1) + "}";
                argTypes = argTypes == null ? argTypes : argTypes.substring(0, argTypes.length() - 1) + "}";

                Class[] exceptions = method.getExceptionTypes();
                for (int i = 0; i < exceptions.length; i++) {
                    methodSpecBuilder.addException(exceptions[i]);
                }

                Class returnType = method.getReturnType();
                methodSpecBuilder.returns(returnType);

                methodSpecBuilder.beginControlFlow("try");
                methodSpecBuilder.addStatement("$T method = $N.class.getMethod($S, $N)", Method.class, ints.getName(), method.getName(), argTypes);
                // 方法没有返回值的情况下，这里需要额外处理
                if (returnType.getName().equals("void")) {
                    methodSpecBuilder.addStatement("this.invocationHandler.invoke(this, method, " + args + ")");
                } else {
                    // 为了保证逻辑的严谨性，这里需要判断实际返回数据类型是否是接口中方法的返回值类型
                    methodSpecBuilder.addStatement("Object result = this.invocationHandler.invoke(this, method, " + args + ")")
                            .addCode("if (result instanceof $T) {\n", TypeName.get(method.getReturnType()).box())
                            .addStatement("\treturn ($T) result", TypeName.get(returnType).box())
                            .addCode("} else {\n")
                            .addStatement("\treturn ($T) null", TypeName.get(returnType).box())
                            .addCode("}\n");
                }

                methodSpecBuilder.nextControlFlow("catch ($T e)", Exception.class)
                        .addStatement("e.printStackTrace()");

                if (returnType.getName().equals("void")) {
                    methodSpecBuilder.endControlFlow();
                } else {
                    methodSpecBuilder.addStatement("return ($T) null", TypeName.get(returnType).box())
                            .endControlFlow();
                }

                typeSpecBuilder.addMethod(methodSpecBuilder.build());
            }


            JavaFile javaFile = JavaFile.builder("com.youngfeng.designmode.proxy", typeSpecBuilder.build()).build();

            File sourceFile = new File(PATH);
            javaFile.writeTo(sourceFile);

            // 编译生成的Java源码
            javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
            Iterable iterable = fileManager.getJavaFileObjects(PATH + "com/youngfeng/designmode/proxy/" + className + ".java");
            javax.tools.JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, null, null, null, iterable);
            task.call();
            fileManager.close();

            // 通过反射将源码加载到内存中
            URL[] urls = new URL[] {new URL("file:" + PATH)};
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class cls = classLoader.loadClass("com.youngfeng.designmode.proxy." + className);
            Constructor constr = cls.getConstructor(InvocationHandler.class);
            Object obj = constr.newInstance(handler);

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
