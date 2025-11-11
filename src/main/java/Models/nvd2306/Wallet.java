/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nvd2306;

/**
 *
 * @author NguyenDuc
 */
public class Wallet {

    private int walletID;
    private int userID;
    private double balance;

    public Wallet() {
    }

    public Wallet(int walletID, int userID, double balance) {
        this.walletID = walletID;
        this.userID = userID;
        this.balance = balance;
    }

    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
