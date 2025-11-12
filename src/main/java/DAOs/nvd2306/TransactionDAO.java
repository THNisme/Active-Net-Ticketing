/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Transaction;
import Utils.nvd2603.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author NguyenDuc
 */
public class TransactionDAO extends DBContext {

    public void insertTransaction(Transaction t) throws SQLException {
        String sql = """
            INSERT INTO Transactions(UserID, TransactionTypeID, Amount, CreatedAt, StatusID)
            VALUES (?, ?, ?, GETDATE(), 1);
        """;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, t.getUserId());
        stm.setInt(2, t.getTransactionTypeId());
        stm.setDouble(3, t.getAmount());
        stm.executeUpdate();
    }
}
