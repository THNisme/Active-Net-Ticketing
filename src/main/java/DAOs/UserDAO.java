/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.User;
import Utils.DBContext;
import Utils.PasswordUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NguyenDuc
 */
public class UserDAO extends DBContext {

    public User login(String username, String password) {
        String sql = "SELECT * FROM [Users] WHERE username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("passwordHash");
                if (PasswordUtils.checkPassword(password, hashedPassword)) {
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
            e.printStackTrace();
        }

        // ✅ luôn trả về user rỗng, tránh null
        User u = new User();
        u.setUserID(-1);
        return u;
    }

    // Đăng ký
    public boolean register(User user) {
        String sql = "INSERT INTO [Users](username, passwordHash, role, createdAt) VALUES (?, ?, ?, GETDATE())";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setInt(3, user.getRole());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check username trùng
    public boolean checkUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
