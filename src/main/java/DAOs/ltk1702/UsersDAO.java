/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.ltk1702;

import Utils.singleton.DBContext;
import java.util.ArrayList;
import java.util.List;
import Models.ltk1702.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class UsersDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_INACTIVE = 2;
    private static final int STATUS_DELETED = 3;

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE StatusID <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("PasswordHash"));
                u.setRole(rs.getInt("Role"));
                u.setCreatedAt(rs.getDate("CreatedAt"));
                u.setStatusID(rs.getInt("StatusID"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE UserID = ? AND StatusID <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("PasswordHash"),
                        rs.getInt("Role"),
                        rs.getDate("CreatedAt"),
                        rs.getInt("StatusID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ? AND StatusID <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, CreatedAt, StatusID) VALUES (?, ?, ?, GETDATE(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setInt(4, STATUS_ACTIVE);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE Users SET Username = ?, PasswordHash = ?, Role = ? WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setInt(4, user.getUserID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void softDeleteUser(int id) {
        String sql = "UPDATE Users SET StatusID = ? WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, STATUS_DELETED);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
