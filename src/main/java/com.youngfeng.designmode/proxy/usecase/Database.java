package com.youngfeng.designmode.proxy.usecase;

/**
 * This is a short description.
 *
 * @author Scott Smith 2019-12-02 13:57
 */
public class Database implements TransactionConstr {

    public static Database getInstance() {
        return new Database();
    }

    @Override
    public void commit(int x) {
        if (x < 0) throw new IllegalArgumentException();
        System.out.println("Transaction commit success...");
    }

    public void rollback() {
        System.out.println("Transaction rollback...");
    }
}
