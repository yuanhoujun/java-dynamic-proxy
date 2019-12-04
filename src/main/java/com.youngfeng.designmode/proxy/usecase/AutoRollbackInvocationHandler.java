package com.youngfeng.designmode.proxy.usecase;

import com.youngfeng.designmode.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-02 14:13
 */
public class AutoRollbackInvocationHandler implements InvocationHandler {
    private Database target;

    public AutoRollbackInvocationHandler(Database target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            method.invoke(target, args);
        } catch (Exception e) {
            target.rollback();
        }
        return null;
    }
}
