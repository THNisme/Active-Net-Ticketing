package Models.ttk2008;

import java.sql.Timestamp;

public class Transaction {

    private int transactionID;
    private int walletID;
    private Integer orderID;
    private int transactionTypeID;
    private long amount;
    private long remain;
    private Timestamp createdAt;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public int getTransactionTypeID() {
        return transactionTypeID;
    }

    public void setTransactionTypeID(int transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getRemain() {
        return remain;
    }

    public void setRemain(long remain) {
        this.remain = remain;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
