/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.EventCategory;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class EventCategoryDAO {

    private Connection conn = DBContext.getInstance().getConnection();

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
                        rs.getString("Description"),
                        rs.getInt("StatusID")
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
                        rs.getString("Description"),
                        rs.getInt("StatusID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE NEW EVENT CATEGORY
    public boolean create(EventCategory c) {
        String sql = "INSERT INTO EventCategories (CategoryName, Description) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE CATEGORY
    public boolean update(EventCategory c) {
        String sql = "UPDATE EventCategories SET CategoryName=?, Description=?, StatusID=? WHERE CategoryID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getStatusID());
            ps.setInt(4, c.getCategoryID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // SOFT DELETE A CATEGORY
    public boolean softDelete(int id) {
        String sql = "UPDATE EventCategories SET StatusID=3 WHERE CategoryID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        EventCategoryDAO dao = new EventCategoryDAO();
//        List<EventCategory> list = dao.getAll();

//        for (EventCategory c : list) {
//            System.out.println("ID: " + c.getCategoryID());
//            System.out.println("CateName: " + c.getCategoryName());
//        }
//        EventCategory c = dao.getById(3);
//        System.out.println("ID: " + c.getCategoryID());
//        System.out.println("CateName: " + c.getCategoryName());
//        EventCategory newCate = new EventCategory();
//
//        newCate.setCategoryName("F-Active");
//        newCate.setDescription("FACTIVE EVENT CLUB");
//
//        dao.create(newCate);
//
//        List<EventCategory> list = dao.getAll();
//
//        for (EventCategory c : list) {
//            System.out.println("ID: " + c.getCategoryID());
//            System.out.println("CateName: " + c.getCategoryName());
//            System.out.println("CateDescription: " + c.getDescription());
//        }
//        EventCategory updateCate = new EventCategory();
//
//        updateCate.setCategoryID(10);
//
//        updateCate.setCategoryName("Tzy");
//        updateCate.setDescription("My baeee <3");
//        updateCate.setStatusID(1);
//        
//        dao.update(updateCate);
//
//        List<EventCategory> list = dao.getAll();
//
//        for (EventCategory c : list) {
//            System.out.println("ID: " + c.getCategoryID());
//            System.out.println("CateName: " + c.getCategoryName());
//            System.out.println("CateDescription: " + c.getDescription());
//        }
//        dao.softDelete(9);
//
//        List<EventCategory> list = dao.getAll();
//
//        for (EventCategory c : list) {
//            System.out.println("ID: " + c.getCategoryID());
//            System.out.println("CateName: " + c.getCategoryName());
//            System.out.println("CateDescription: " + c.getDescription());
//            System.out.println("Status: " + c.getStatusID());
//        }

    }
}
