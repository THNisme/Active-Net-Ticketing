/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.EventCategory;
import Utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class EventCategoryDAO extends DBContext {

    //GET ALL EVENTCATEGORY
    public List<EventCategory> getAll() {
        List<EventCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM EventCategories";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new EventCategory(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET BY ID
    public EventCategory getById(int id) {
        String sql = "SELECT * FROM EventCategories WHERE CategoryID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new EventCategory(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        EventCategoryDAO dao = new EventCategoryDAO();
//        List<EventCategory> list = dao.getAll();
//
//        for (EventCategory c : list) {
//            System.out.println("ID: " + c.getCategoryID());
//            System.out.println("CateName: " + c.getCategoryName());
//        }

        EventCategory c = dao.getById(3);
        System.out.println("ID: " + c.getCategoryID());
        System.out.println("CateName: " + c.getCategoryName());
    }
}
