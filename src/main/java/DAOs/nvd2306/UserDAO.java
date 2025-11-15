package DAOs.nvd2306;

import Models.nvd2306.User;
import Utils.singleton.DBContext;
import MD5.HashPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final Connection conn;

    public UserDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM [Users] WHERE username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("passwordHash");
                // So sánh MD5
                if (HashPassword.hashMD5(password).equalsIgnoreCase(hashedPassword)) {
                    User u = new User();
                    u.setUserID(rs.getInt("userID"));
                    u.setUsername(rs.getString("username"));
                    u.setPasswordHash(rs.getString("passwordHash"));
                    u.setRole(rs.getInt("role"));
                    u.setCreatedAt(rs.getDate("createdAt"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đăng kí " + e.getMessage());
            e.printStackTrace();
        }

        // Trả về user rỗng nếu sai
        User u = new User();
        u.setUserID(-1);
        return u;
    }

    // =========================================================
    //                 REGISTER + AUTO CREATE WALLET
    // =========================================================
    public boolean register(User user) {
        String sqlInsertUser
                = "INSERT INTO Users (Username, PasswordHash, Role, CreatedAt, StatusID, ContactEmail) "
                + "VALUES (?, ?, ?, GETDATE(), 1, ?)";

        try {
            // 1️⃣ Insert vào Users
            PreparedStatement ps = conn.prepareStatement(sqlInsertUser, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getContactEmail());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }

            // 2️⃣ Lấy UserID vừa tạo
            ResultSet rs = ps.getGeneratedKeys();
            int newUserId = -1;
            if (rs.next()) {
                newUserId = rs.getInt(1);
            }

            if (newUserId <= 0) {
                return false;
            }

            // 3️⃣ Insert vào Wallet với Balance mặc định = 0
            String sqlInsertWallet
                    = "INSERT INTO Wallet (UserID, Balance, LastUpdated, StatusID) "
                    + "VALUES (?, 0, GETDATE(), 1)";

            PreparedStatement psWallet = conn.prepareStatement(sqlInsertWallet);
            psWallet.setInt(1, newUserId);

            psWallet.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi register (User + Wallet): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Lỗi checkUsernameExists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT 1 FROM Users WHERE ContactEmail = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi checkEmailExists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
}
