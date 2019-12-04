package com.youngfeng.designmode.proxy;

import java.lang.reflect.Method;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-01 16:53
 */
public class MyInvocationHandler implements InvocationHandler {
    private BuilderCreator target;

    public MyInvocationHandler(BuilderCreator target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            System.out.println(method.getName() + " invoke start...");
            Object result = method.invoke(target, args);
            System.out.println(method.getName() + " invoke end...");

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}