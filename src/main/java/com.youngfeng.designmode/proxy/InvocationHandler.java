package com.youngfeng.designmode.proxy;

import java.lang.reflect.Method;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-01 15:59
 */
public interface InvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args);
}
