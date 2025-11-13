package DAOs.ttk2008;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionLoggerDAO {

    public static void log(Connection conn, int userId, String type, long amount, String description, String status) throws SQLException {
        int walletId = getWalletIdByUser(conn, userId);
        if (walletId == -1) {
            System.out.println("Không tìm thấy ví cho userID: " + userId);
            return;
        }

        long remain = getCurrentBalance(conn, walletId);

        int transactionTypeId = type.equalsIgnoreCase("TOP_UP") ? 1 : 1; 

        String sql = "INSERT INTO Transactions (WalletID, TransactionTypeID, Amount, Remain, CreatedAt) " +
                     "VALUES (?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, walletId);
            ps.setInt(2, transactionTypeId);
            ps.setLong(3, amount);
            ps.setLong(4, remain);
            ps.executeUpdate();
        }

        System.out.println("Đã ghi transaction log cho ví ID = " + walletId + ", số tiền = " + amount);
    }

    private static int getWalletIdByUser(Connection conn, int userId) throws SQLException {
        String sql = "SELECT WalletID FROM Wallet WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("WalletID");
        }
        return -1;
    }

    private static long getCurrentBalance(Connection conn, int walletId) throws SQLException {
        String sql = "SELECT Balance FROM Wallet WHERE WalletID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, walletId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("Balance");
        }
        return 0;
    }
}
