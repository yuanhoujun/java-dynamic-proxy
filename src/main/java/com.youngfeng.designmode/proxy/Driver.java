package com.youngfeng.designmode.proxy;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-11-30 17:45
 */
public class Driver implements Drivable {

    public void drive() throws InterruptedException {
        System.out.println("I'm driving...");
        Thread.sleep(1000);
        System.out.println("Drive finished...");
    }
}
