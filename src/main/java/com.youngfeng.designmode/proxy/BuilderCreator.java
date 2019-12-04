package com.youngfeng.designmode.proxy;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-01 17:31
 */
public class BuilderCreator implements ICreator {

    @Override
    public Person.Builder create(String name) {
        return new Person.Builder(name);
    }

    @Override
    public int foo(int x, int y) {
        return 0;
    }
}
