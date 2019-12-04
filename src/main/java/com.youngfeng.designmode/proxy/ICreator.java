package com.youngfeng.designmode.proxy;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-01 16:51
 */
public interface ICreator {
    Person.Builder create(String name);

    int foo(int x, int y);
}
