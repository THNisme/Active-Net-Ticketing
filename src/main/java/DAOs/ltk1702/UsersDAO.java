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
                u.setContactFullname(rs.getString("ContactFullname"));
                u.setContactEmail(rs.getString("ContactEmail"));
                u.setContactPhone(rs.getString("ContactPhone"));

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

                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("PasswordHash"));
                u.setRole(rs.getInt("Role"));
                u.setCreatedAt(rs.getDate("CreatedAt"));
                u.setStatusID(rs.getInt("StatusID"));

                u.setContactFullname(rs.getString("ContactFullname"));
                u.setContactEmail(rs.getString("ContactEmail"));
                u.setContactPhone(rs.getString("ContactPhone"));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, CreatedAt, StatusID, ContactFullname, ContactEmail, ContactPhone) VALUES (?, ?, ?, GETDATE(), ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setInt(4, STATUS_ACTIVE);
            ps.setString(5, user.getContactFullname());
            ps.setString(6, user.getContactEmail());
            ps.setString(7, user.getContactPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE Users SET Username = ?, PasswordHash = ?, Role = ?, ContactFullname = ?, ContactEmail = ?, ContactPhone = ? WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getContactFullname());
            ps.setString(5, user.getContactEmail());
            ps.setString(6, user.getContactPhone());
            ps.setInt(7, user.getUserID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        UsersDAO dao = new UsersDAO();
        List<User> users = dao.getAllUsers();

        if (users == null || users.isEmpty()) {
            System.out.println("Không có người dùng nào.");
            return;
        }

        System.out.println("====== DANH SÁCH NGƯỜI DÙNG ======");
        for (User u : users) {
            System.out.println("---------------------------------------");
            System.out.println("UserID        : " + u.getUserID());
            System.out.println("Username      : " + u.getUsername());
            System.out.println("Password Hash : " + u.getPassword());
            System.out.println("Role          : " + (u.getRole() == 1 ? "Admin" : "User"));
            System.out.println("Created At    : " + u.getCreatedAt());
            System.out.println("Status ID     : " + u.getStatusID());
            // System.out.println("Full Name     : " + u.getContactFullname());
            System.out.println("Email         : " + u.getContactEmail());
            // System.out.println("Phone         : " + u.getContactPhone());
        }
        System.out.println("=======================================");
    }
}
