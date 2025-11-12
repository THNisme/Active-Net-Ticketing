/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Wallet;
import Utils.nvd2603.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NguyenDuc
 */
public class WalletDao extends DBContext {

    public WalletDao() {
        super();
    }

    public Wallet getWalletByUserId(int userId) throws SQLException {
        String sql = "SELECT WalletID, UserID, Remain FROM Wallet WHERE UserID = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, userId);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            Wallet w = new Wallet();
            w.setWalletId(rs.getInt("WalletID"));
            w.setUserId(rs.getInt("UserID"));
            w.setRemain(rs.getDouble("Remain"));
            return w;
        }
        return null;
    }

    public void updateWalletBalance(int userId, double newBalance) throws SQLException {
        String sql = "UPDATE Wallet SET Remain = ? WHERE UserID = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setDouble(1, newBalance);
        stm.setInt(2, userId);
        stm.executeUpdate();
    }
}
