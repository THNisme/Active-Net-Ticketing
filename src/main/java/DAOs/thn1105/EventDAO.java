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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class EventDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    // GET ALL EVENT - DONT CARE ABOUT STATUS
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

    public Event getById(int id) {
        String sql = "SELECT * FROM Events WHERE EventID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Event(
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean create(Event e) {
        String sql = "INSERT INTO Events (CategoryID, EventName, Description, ImageURL, StartDate, EndDate, PlaceID, StatusID) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, e.getCategoryID());
            st.setString(2, e.getEventName());
            st.setString(3, e.getDescription());
            st.setString(4, e.getImageURL());
            st.setTimestamp(5, new Timestamp(e.getStartDate().getTime()));
            st.setTimestamp(6, new Timestamp(e.getEndDate().getTime()));
            st.setInt(7, e.getPlaceID());
            st.setInt(8, e.getStatusID());
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(Event e) {
        String sql = "UPDATE Events SET CategoryID=?, EventName=?, Description=?, ImageURL=?, StartDate=?, EndDate=?, PlaceID=?, StatusID=? WHERE EventID=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, e.getCategoryID());
            st.setString(2, e.getEventName());
            st.setString(3, e.getDescription());
            st.setString(4, e.getImageURL());
            st.setTimestamp(5, new Timestamp(e.getStartDate().getTime()));
            st.setTimestamp(6, new Timestamp(e.getEndDate().getTime()));
            st.setInt(7, e.getPlaceID());
            st.setInt(8, e.getStatusID());
            st.setInt(9, e.getEventID());
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // HARD DELETE: EVENT BE DELETE PERMANNENTLY
    public boolean hardDelete(int id) {
        String deleteTicketTypes = "DELETE FROM TicketTypes WHERE EventID = ?";
        String deleteEvent = "DELETE FROM Events WHERE EventID = ?";
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement st1 = conn.prepareStatement(deleteTicketTypes); PreparedStatement st2 = conn.prepareStatement(deleteEvent)) {

                st1.setInt(1, id);
                st1.executeUpdate();

                st2.setInt(1, id);
                int rows = st2.executeUpdate();

                conn.commit();
                return rows > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
        return false;
    }

//    SOFT DELETE: EVENT BE SET STATUS DELELTE AND THROW IN TRASH BIN
    public boolean softDelete(int id) {
        String sql = "UPDATE Events SET StatusID=2 WHERE EventID=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // GET ALL EVENT BE SOFT DELETED
    public List<Event> getAllSoftDelete() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE StatusID = 2";
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
        //        List<Event> list = dao.getAll();
        //
        //        for (Event e : list) {
        //            System.out.println("ID: " + e.getEventID());
        //            System.out.println("CateID: " + e.getCategoryID());
        //            System.out.println("Name: " + e.getEventName());
        //            System.out.println("Description: " + e.getDescription());
        //            System.out.println("ImgURL: " + e.getImageURL());
        //            System.out.println("Start: " + e.getStartDate());
        //            System.out.println("End: " + e.getEndDate());
        //            System.out.println("Place: " + e.getPlaceID());
        //            System.out.println("Status: " + e.getStatusID());
        //            System.out.println("");
        //        }

        dao.hardDelete(7);
        List<Event> list = dao.getAllSoftDelete();

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
