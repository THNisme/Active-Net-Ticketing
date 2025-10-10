/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Event;
import Utils.nvd2603.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class EventDao extends DBContext {

    public List<Event> searchEvents(String keyword) {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE EventName LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                e.setDescription(rs.getString("Description"));
                e.setImageURL(rs.getString("ImageURL"));
                e.setStartDate(rs.getTimestamp("StartDate"));
                e.setEndDate(rs.getTimestamp("EndDate"));
                e.setPlaceID(rs.getInt("PlaceID"));
                e.setStatusID(rs.getInt("StatusID"));
                list.add(e);
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi searchEvents: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setCategoryID(rs.getInt("CategoryID"));
                e.setEventName(rs.getString("EventName"));
                e.setDescription(rs.getString("Description"));
                e.setImageURL(rs.getString("ImageURL"));
                e.setStartDate(rs.getTimestamp("StartDate"));
                e.setEndDate(rs.getTimestamp("EndDate"));
                e.setPlaceID(rs.getInt("PlaceID"));
                e.setStatusID(rs.getInt("StatusID"));
                list.add(e);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi getAllEvents: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
