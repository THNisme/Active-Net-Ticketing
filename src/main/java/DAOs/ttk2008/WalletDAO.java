package DAOs.ttk2008;

import Models.ttk2008.Wallet;
import Utils.singleton.DBContext;
import java.sql.*;

public class WalletDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    public Wallet getWalletByUserId(int userId) {
        Wallet wallet = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT WalletID, UserID, Balance, LastUpdated, StatusID "
                    + "FROM Wallet WHERE UserID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                wallet = new Wallet();
                wallet.setWalletID(rs.getInt("WalletID"));
                wallet.setUserID(rs.getInt("UserID"));
                wallet.setBalance(rs.getBigDecimal("Balance"));
                wallet.setLastUpdated(rs.getTimestamp("LastUpdated"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wallet;
    }

    public boolean updateBalance(int userId, long amount, Timestamp lastUpdated) {
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE Wallet SET Balance = Balance + ?, LastUpdated = ? WHERE UserID = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, amount);
            ps.setTimestamp(2, lastUpdated);
            ps.setInt(3, userId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                TransactionLoggerDAO.log(conn, userId, "Naptien", amount, "người dùng nạp tiền vnpay", "thành công");

                conn.commit();
                success = true;
            } else {
                conn.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.setAutoCommit(true); // trả về chế độ bình thường
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static void main(String[] args) {
        WalletDAO dao = new WalletDAO();

        int userID = 1;
        long amountToAdd = 500000; // nạp 500,000 VND
        Timestamp now = new Timestamp(System.currentTimeMillis());

        boolean success = dao.updateBalance(userID, amountToAdd, now);

        if (success) {
            System.out.println("Cập nhật số dư thành công cho userID = " + userID);
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }
}
