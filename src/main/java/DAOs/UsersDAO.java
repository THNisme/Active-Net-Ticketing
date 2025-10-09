/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Utils.DBContext;
import java.util.ArrayList;
import java.util.List;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class UsersDAO extends DBContext {
    
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY UserID DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("UserID"),
                    rs.getString("Username"),
                    rs.getString("PasswordHash"),
                    rs.getInt("Role"),
                    rs.getDate("CreatedAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

       public void addUser(User user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role, CreatedAt) VALUES (?, ?, ?, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

       public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("UserID"),
                    rs.getString("Username"),
                    rs.getString("PasswordHash"),
                    rs.getInt("Role"),
                    rs.getDate("CreatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public void updateUser(User user) {
        String sql = "UPDATE Users SET Username=?, PasswordHash=?, Role=? WHERE UserID=?";
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

       public void deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
}

