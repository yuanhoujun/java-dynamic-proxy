package com.youngfeng.designmode.proxy.usecase;

import com.youngfeng.designmode.proxy.Proxy;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-02 14:13
 */
public class Test {

    public static void main(String[] args) {
        Database db = Database.getInstance();
        AutoRollbackInvocationHandler invocationHandler = new AutoRollbackInvocationHandler(db);
        Object proxy = Proxy.newProxyInstance(invocationHandler, TransactionConstr.class);
        ((TransactionConstr)proxy).commit(-1);

        // 通过try catch的方式手动回滚数据
        try {
            db.commit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        }

        System.out.println(0xffffffffL);
    }
}
