package DAOs.nvd2306;

import Models.nvd2306.User;
import Utils.singleton.DBContext;
import MD5.HashPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
    public int register(User user) {
        String sqlInsertUser
                = "INSERT INTO Users (Username, PasswordHash, Role, CreatedAt, StatusID, ContactEmail, ContactFullname, ContactPhone) "
                + "VALUES (?, ?, ?, GETDATE(), 1, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsertUser, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getContactEmail());
            ps.setString(5, user.getContactFullname());
            ps.setString(6, user.getContactPhone());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return -1;
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newID = rs.getInt(1);

                // tạo wallet
                PreparedStatement psWallet = conn.prepareStatement(
                        "INSERT INTO Wallet (UserID, Balance, LastUpdated, StatusID) VALUES (?, 0, GETDATE(), 1)"
                );
                psWallet.setInt(1, newID);
                psWallet.executeUpdate();

                return newID;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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

    public void updateOTP(int userID, String otp, Timestamp expiry) {
        String sql = "UPDATE Users SET OTPCode=?, OTPExpiredAt=? WHERE UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, otp);
            ps.setTimestamp(2, expiry);
            ps.setInt(3, userID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE contactEmail=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                User u = new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("passwordHash"),
                        rs.getInt("role"),
                        rs.getDate("createdAt"),
                        rs.getString("contactEmail")
                );

                // ⭐⭐ THÊM 2 DÒNG NÀY ⭐⭐
                u.setOTPCode(rs.getString("OTPCode"));
                u.setOTPExpiredAt(rs.getTimestamp("OTPExpiredAt"));

                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByID(int userID) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("passwordHash"));
                u.setRole(rs.getInt("role"));
                u.setCreatedAt(rs.getDate("createdAt"));
                u.setContactEmail(rs.getString("contactEmail"));

                u.setOTPCode(rs.getString("OTPCode"));
                u.setOTPExpiredAt(rs.getTimestamp("OTPExpiredAt"));

                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void markVerified(int userID) {
        String sql = "UPDATE Users SET isVerified = 1, OTPCode = NULL, OTPExpiredAt = NULL WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
