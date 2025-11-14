/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.ltk1702;

import Utils.singleton.DBContext;
import Models.ltk1702.EventCategories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class EventCategoriesDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_INACTIVE = 2;
    private static final int STATUS_DELETED = 3;

    public List<EventCategories> getAllCategories() {
        List<EventCategories> list = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName, Description, StatusID FROM EventCategories WHERE StatusID <> ? ORDER BY CategoryName";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventCategories c = new EventCategories();
                c.setCategoryID(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setDescription(rs.getString("Description"));
                c.setStatusID(rs.getInt("StatusID"));
                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public EventCategories getCategoryById(int id) {
        String sql = "SELECT CategoryID, CategoryName, Description, StatusID FROM EventCategories WHERE CategoryID = ? AND StatusID <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new EventCategories(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getString("Description"),
                        rs.getInt("StatusID")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean checkNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM EventCategories WHERE CategoryName = ? AND StatusID <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean addCategory(EventCategories cat) {
        String sql = "INSERT INTO EventCategories (CategoryName, Description, StatusID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getCategoryName());
            ps.setString(2, cat.getDescription());
            ps.setInt(3, STATUS_ACTIVE);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(EventCategories cat) {
        String sql = "UPDATE EventCategories SET CategoryName = ?, Description = ?, StatusID = ? WHERE CategoryID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getCategoryName());
            ps.setString(2, cat.getDescription());
            ps.setInt(3, cat.getStatusID());
            ps.setInt(4, cat.getCategoryID());
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean softDeleteCategory(int id) {
        String sql = "UPDATE EventCategories SET StatusID = ? WHERE CategoryID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, STATUS_DELETED);
            ps.setInt(2, id);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
public static void main(String[] args) {
    EventCategoriesDAO dao = new EventCategoriesDAO();
    List<EventCategories> categories = dao.getAllCategories();

    if (categories == null || categories.isEmpty()) {
        System.out.println("⚠️ Không có danh mục sự kiện nào trong cơ sở dữ liệu.");
        return;
    }

    System.out.println("====== DANH SÁCH DANH MỤC SỰ KIỆN ======");
    for (EventCategories c : categories) {
        System.out.println("--------------------------------------");
        System.out.println("ID           : " + c.getCategoryID());
        System.out.println("Tên danh mục : " + c.getCategoryName());
        System.out.println("Mô tả        : " + (c.getDescription() != null ? c.getDescription() : "(Không có mô tả)"));
        System.out.println("Trạng thái   : " + 
            (c.getStatusID() == 1 ? "Hoạt động" : 
            (c.getStatusID() == 2 ? "Ngừng hoạt động" : "Đã xóa")));
    }
    System.out.println("=======================================");
}

}
