/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Wallet;
import Utils.singleton.DBContext;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NguyenDuc
 */
public class WalletDao {

    public Wallet getWalletByUserId(int userId) throws SQLException {
        String sql = "SELECT WalletID, UserID, Balance, LastUpdated, StatusID FROM Wallet WHERE UserID = ?";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, userId);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Wallet w = new Wallet();
                    w.setWalletID(rs.getInt("WalletID"));
                    w.setUserID(rs.getInt("UserID"));
                    w.setBalance(rs.getBigDecimal("Balance"));
                    w.setLastUpdated(rs.getTimestamp("LastUpdated"));
                    w.setStatusID(rs.getInt("StatusID"));
                    return w;
                }
            }
        }

        return null;
    }

    // updateBalance dùng connection từ PaymentServlet (trong transaction)
    public void updateBalance(Connection conn, int walletID, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE Wallet SET Balance = ?, LastUpdated = GETDATE() WHERE WalletID = ?";

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setBigDecimal(1, newBalance);
            stm.setInt(2, walletID);
            stm.executeUpdate();
        }
    }
}
