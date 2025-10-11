/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.Event;
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
public class EventDAO {

     private Connection conn = DBContext.getInstance().getConnection();
    // GET ALL EVENT
    public List<Event> getAll() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events";
        try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Event e = new Event(
                        rs.getInt("EventID"),
                        rs.getInt("CategoryID"),
                        rs.getString("EventName"),
                        rs.getString("Description"),
                        rs.getString("ImageURL"),
                        rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),
                        rs.getInt("PlaceID"),
                        rs.getInt("StatusID")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        EventDAO dao = new EventDAO();

        List<Event> list = dao.getAll();

        for (Event e : list) {
            System.out.println("ID: " + e.getEventID());
            System.out.println("CateID: " + e.getCategoryID());
            System.out.println("Name: " + e.getEventName());
            System.out.println("Description: " + e.getDescription());
            System.out.println("ImgURL: " + e.getImageURL());
            System.out.println("Start: " + e.getStartDate());
            System.out.println("End: " + e.getEndDate());
            System.out.println("Place: " + e.getPlaceID());
            System.out.println("Status: " + e.getStatusID());
            System.out.println("");
        }

    }
}
