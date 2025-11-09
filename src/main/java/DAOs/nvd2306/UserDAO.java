package DAOs.nvd2306;

import Models.nvd2306.User;
import Utils.nvd2603.DBContext;
import MD5.HashPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DBContext {

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

    public boolean register(User user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, CreatedAt, StatusID, ContactEmail) "
                + "VALUES (?, ?, ?, GETDATE(), 1, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getContactEmail()); // <-- NEW
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi register: " + e.getMessage());
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
