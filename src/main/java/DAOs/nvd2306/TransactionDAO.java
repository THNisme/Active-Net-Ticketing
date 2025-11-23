/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Transaction;
import Utils.singleton.DBContext;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author NguyenDuc
 */
public class TransactionDAO {

    private final Connection conn;

    public TransactionDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    /**
     * Ghi giao dịch thanh toán (PAYMENT) trong cùng transaction
     */
    public void insertPayment(Connection conn, int walletId, Integer orderId,
            BigDecimal amount, BigDecimal remain) throws SQLException {
        String sql = """
            INSERT INTO Transactions (WalletID, OrderID, TransactionTypeID, Amount, Remain, CreatedAt, StatusID)
            VALUES (?, ?, 
                (SELECT TOP 1 TransactionTypeID FROM TransactionTypes WHERE Code = 'PAYMENT'),
                ?, ?, GETDATE(), 1);
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, walletId);
            if (orderId == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, orderId);
            }
            ps.setBigDecimal(3, amount);   // Ví dụ: số âm khi trừ tiền
            ps.setBigDecimal(4, remain);   // Số dư còn lại sau thanh toán
            ps.executeUpdate();
        }
    }

    /**
     * (Tuỳ chọn) Ghi giao dịch nạp tiền (DEPOSIT)
     */
    public void insertDeposit(Connection conn, int walletId,
            BigDecimal amount, BigDecimal remain) throws SQLException {
        String sql = """
            INSERT INTO Transactions (WalletID, TransactionTypeID, Amount, Remain, CreatedAt, StatusID)
            VALUES (?, 
                (SELECT TOP 1 TransactionTypeID FROM TransactionTypes WHERE Code = 'DEPOSIT'),
                ?, ?, GETDATE(), 1);
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, walletId);
            ps.setBigDecimal(2, amount);
            ps.setBigDecimal(3, remain);
            ps.executeUpdate();
        }
    }

    public boolean insertPaymentTransaction(int userId, int orderId, BigDecimal amount) {

        String sql = """
        INSERT INTO Transactions(WalletID, OrderID, TransactionTypeID, Amount, Remain)
        SELECT WalletID, ?, 
               (SELECT TransactionTypeID FROM TransactionTypes WHERE Code = 'PAYMENT'),
               ?, Balance - ?
        FROM Wallet
        WHERE UserID = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setBigDecimal(2, amount);
            ps.setBigDecimal(3, amount);
            ps.setInt(4, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void insert(Transaction t) {

        String sql = "INSERT INTO Transactions "
                + "(WalletID, OrderID, TransactionTypeID, Amount, Remain, Description, CreatedAt, StatusID) "
                + "VALUES (?, ?, ?, ?, ?, ?, GETDATE(), 1)";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getWalletID());
            ps.setObject(2, t.getOrderID());          // Integer → Object
            ps.setInt(3, t.getTransactionTypeID());
            ps.setBigDecimal(4, t.getAmount());
            ps.setBigDecimal(5, t.getRemain());
            ps.setString(6, t.getDescription());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Lỗi insert transaction");
        }
    }
}
