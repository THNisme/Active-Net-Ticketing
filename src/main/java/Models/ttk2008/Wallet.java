package Models.ttk2008;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Wallet {
    private int walletID;
    private int userID;
    private BigDecimal balance;
    private Timestamp lastUpdated;

    // Getters & Setters
    public int getWalletID() { return walletID; }
    public void setWalletID(int walletID) { this.walletID = walletID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }
}
