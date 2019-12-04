package com.youngfeng.designmode.proxy;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-11-30 17:50
 */
public class Client {

    public static void main(String[] args) {
        BuilderCreator builderCreator = new BuilderCreator();

        MyInvocationHandler handler = new MyInvocationHandler(builderCreator);
        Object proxy = Proxy.newProxyInstance(handler, ICreator.class);

        ICreator creator = (ICreator) proxy;
        Person person = creator.create("Scott").age(18).build();
        System.out.println(person.getAge());

        creator.foo(1, 2);
    }
}
