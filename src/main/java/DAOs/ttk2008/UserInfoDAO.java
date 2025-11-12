package DAOs.ttk2008;

import Models.ttk2008.UserInfo;
import Utils.singleton.DBContext;
import java.sql.*;

public class UserInfoDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    public UserInfo getUserById(int userId) {
        try {
            String sql = "SELECT UserID, Username, PasswordHash, Role, CreatedAt, StatusID, "
                    + "ContactFullname, ContactEmail, ContactPhone "
                    + "FROM Users WHERE UserID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserInfo(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("PasswordHash"),
                        rs.getInt("Role"),
                        rs.getString("CreatedAt"),
                        rs.getInt("StatusID"),
                        rs.getString("ContactFullname"),
                        rs.getString("ContactEmail"),
                        rs.getString("ContactPhone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(UserInfo user) {
        try {
            String sql = "UPDATE Users SET Username=?, PasswordHash=?, "
                    + "ContactFullname=?, ContactEmail=?, ContactPhone=? WHERE UserID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getContactFullname());
            ps.setString(4, user.getContactEmail());
            ps.setString(5, user.getContactPhone());
            ps.setInt(6, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameTaken(String username, int excludeUserId) {
        try {
            String sql = "SELECT COUNT(*) FROM Users WHERE Username = ? AND UserID <> ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, excludeUserId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
