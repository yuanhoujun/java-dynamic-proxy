package com.youngfeng.proxy;

/**
 * Bird2
 *
 * @author Scott Smith 2018-03-01 10:48
 */
public class Bird2 extends Bird {

    @Override
    public void fly() {
        long start = System.currentTimeMillis();

        super.fly();

        long end = System.currentTimeMillis();
        System.out.println("Fly time = " + (end - start));
    }
}
