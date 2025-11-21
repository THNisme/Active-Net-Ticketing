package DAOs.ttk2008;

import Utils.singleton.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Models.ttk2008.Transaction;

public class TransactionDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    /**
     * Get all transactions by user ID
     */
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> list = new ArrayList<>();
        try {
            String sql = "SELECT t.TransactionID, t.WalletID, t.OrderID, t.TransactionTypeID, "
                    + "t.Amount, t.Remain, t.CreatedAt, "
                    + "t.PromotionAmount, t.PromotionID "
                    + "FROM Transactions t "
                    + "JOIN Wallet w ON t.WalletID = w.WalletID "
                    + "WHERE w.UserID = ? "
                    + "ORDER BY t.CreatedAt DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction tr = new Transaction();
                tr.setTransactionID(rs.getInt("TransactionID"));
                tr.setWalletID(rs.getInt("WalletID"));
                tr.setOrderID(rs.getInt("OrderID"));
                tr.setTransactionTypeID(rs.getInt("TransactionTypeID"));
                tr.setAmount(rs.getLong("Amount"));
                tr.setRemain(rs.getLong("Remain"));
                tr.setCreatedAt(rs.getTimestamp("CreatedAt"));
                tr.setPromotionAmount(rs.getLong("PromotionAmount"));
                tr.setPromotionID(rs.getInt("PromotionID"));

                list.add(tr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean insertTransaction(Transaction tr) {
        boolean success = false;
        String sql = "INSERT INTO Transactions (WalletID, OrderID, TransactionTypeID, Amount, Remain, CreatedAt, PromotionAmount, PromotionID) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tr.getWalletID());
            ps.setObject(2, tr.getOrderID() != 0 ? tr.getOrderID() : null); // OrderID có thể null
            ps.setInt(3, tr.getTransactionTypeID());
            ps.setLong(4, tr.getAmount());
            ps.setLong(5, tr.getRemain());
            ps.setTimestamp(6, tr.getCreatedAt());
            ps.setLong(7, tr.getPromotionAmount());
            ps.setObject(8, tr.getPromotionID() != 0 ? tr.getPromotionID() : null); // PromotionID có thể null

            int rows = ps.executeUpdate();
            success = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
