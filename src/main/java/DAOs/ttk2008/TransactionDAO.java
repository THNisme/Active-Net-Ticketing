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
}
