package com.youngfeng.proxy;

import java.util.Random;

/**
 * Bird
 *
 * @author Scott Smith 2018-02-28 15:42
 */
public class Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("Bird is flying...");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
