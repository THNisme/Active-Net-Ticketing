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
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        String sql = "SELECT * FROM Events WHERE StatusID = 1";
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
        String sql = "SELECT * FROM Events WHERE EventID = ? AND StatusID = 1";
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
        String sql = "INSERT INTO Events (CategoryID, EventName, Description, ImageURL, StartDate, EndDate, PlaceID) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, e.getCategoryID());
            st.setString(2, e.getEventName());
            st.setString(3, e.getDescription());
            st.setString(4, e.getImageURL());
            st.setTimestamp(5, new Timestamp(e.getStartDate().getTime()));
            st.setTimestamp(6, new Timestamp(e.getEndDate().getTime()));
            st.setInt(7, e.getPlaceID());

            int rows = st.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        e.setEventID(rs.getInt(1)); // Gán ID vừa tạo cho object
                    }
                }
                return true;
            }
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

//    SOFT DELETE: EVENT BE SET STATUS DELELTE AND THROW IN TRASH BIN
    public boolean deleteCascade(int eventId) {
        String sqlTickets = """
        UPDATE Tickets
        SET StatusID = (SELECT StatusID FROM Status WHERE Entity='COMMON' AND Code='DELETED')
        WHERE TicketTypeID IN (SELECT TicketTypeID FROM TicketTypes WHERE EventID = ?)
    """;

        String sqlTicketTypes = """
        UPDATE TicketTypes
        SET StatusID = (SELECT StatusID FROM Status WHERE Entity='COMMON' AND Code='DELETED')
        WHERE EventID = ?
    """;

        String sqlEvent = """
        UPDATE Events
        SET StatusID = (SELECT StatusID FROM Status WHERE Entity='COMMON' AND Code='DELETED')
        WHERE EventID = ?
    """;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement st1 = conn.prepareStatement(sqlTickets)) {
                st1.setInt(1, eventId);
                st1.executeUpdate();
            }

            try (PreparedStatement st2 = conn.prepareStatement(sqlTicketTypes)) {
                st2.setInt(1, eventId);
                st2.executeUpdate();
            }

            try (PreparedStatement st3 = conn.prepareStatement(sqlEvent)) {
                st3.setInt(1, eventId);
                st3.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ignored) {
        }
        }
        return false;
    }

    // GET ALL EVENT BE SOFT DELETED
    public List<Event> getAllSoftDelete() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE StatusID = 3";
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

    public boolean hasOverlappingEvent(int placeID, Date startDate, Date endDate, Integer excludeEventID) {
        String sql = "SELECT COUNT(*) FROM Events "
                + "WHERE PlaceID = ? "
                + "AND StatusID != 3 "
                + // tránh đếm các sự kiện đã bị xóa (DELETED)
                "AND ((StartDate <= ?) AND (EndDate >= ?))";

        // Nếu là update, bỏ qua chính event đang check
        if (excludeEventID != null) {
            sql += " AND EventID <> ?";
        }

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, placeID);
            st.setTimestamp(2, new Timestamp(endDate.getTime()));
            st.setTimestamp(3, new Timestamp(startDate.getTime()));
            if (excludeEventID != null) {
                st.setInt(4, excludeEventID);
            }

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Có sự kiện trùng
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Event> getEventsByPlaceID(int placeID) {
        List<Event> events = new ArrayList<>();

        String sql = """
        SELECT 
            e.EventID,
            e.EventName,
            e.Description,
            e.ImageURL,
            e.StartDate,
            e.EndDate,
            e.PlaceID,
            p.PlaceName,
            e.CategoryID,
            c.CategoryName,
            e.StatusID,
            s.Code AS StatusCode,
            s.Description AS StatusDescription
        FROM Events e
        JOIN Places p ON e.PlaceID = p.PlaceID
        LEFT JOIN EventCategories c ON e.CategoryID = c.CategoryID
        JOIN Status s ON e.StatusID = s.StatusID
        WHERE e.PlaceID = ? AND e.StatusID != 3
        ORDER BY e.StartDate ASC
    """;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, placeID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event();
                    e.setEventID(rs.getInt("EventID"));
                    e.setEventName(rs.getString("EventName"));
                    e.setDescription(rs.getString("Description"));
                    e.setImageURL(rs.getString("ImageURL"));
                    e.setStartDate(rs.getTimestamp("StartDate"));
                    e.setEndDate(rs.getTimestamp("EndDate"));
                    e.setPlaceID(rs.getInt("PlaceID"));
                    e.setCategoryID(rs.getInt("CategoryID"));
                    e.setStatusID(rs.getInt("StatusID"));

                    // Nếu bạn có các trường phụ như tên category, place thì có thể set thêm:
//                    e.setPlaceName(rs.getString("PlaceName"));
//                    e.setCategoryName(rs.getString("CategoryName"));
//                    e.setStatusCode(rs.getString("StatusCode"));
//                    e.setStatusDescription(rs.getString("StatusDescription"));

                    events.add(e);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return events;
    }

    public static void main(String[] args) {
        EventDAO dao = new EventDAO();
        List<Event> list = dao.getEventsByPlaceID(1);
//
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

//        Event e = dao.getById(3);
//
//        System.out.println("ID: " + e.getEventID());
//        System.out.println("CateID: " + e.getCategoryID());
//        System.out.println("Name: " + e.getEventName());
//        System.out.println("Description: " + e.getDescription());
//        System.out.println("ImgURL: " + e.getImageURL());
//        System.out.println("Start: " + e.getStartDate());
//        System.out.println("End: " + e.getEndDate());
//        System.out.println("Place: " + e.getPlaceID());
//        System.out.println("Status: " + e.getStatusID());
//          Event newEvent = new Event();
//          
//          newEvent.setEventName("Test");
//          newEvent.setCategoryID(1);
//          newEvent.setPlaceID(1);
//          newEvent.setImageURL("imgtest");
//          newEvent.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
//          newEvent.setEndDate(Timestamp.valueOf(LocalDateTime.now()));
//          newEvent.setDescription("desdasdasd");
//          
//          dao.create(newEvent);
//        Event updateEvent = new Event();
//
//        updateEvent.setEventID(5);
//        updateEvent.setEventName("Test-Update");
//        updateEvent.setCategoryID(1);
//        updateEvent.setPlaceID(2);
//        updateEvent.setImageURL("imgtest-Update");
//        updateEvent.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
//        updateEvent.setEndDate(Timestamp.valueOf(LocalDateTime.now()));
//        updateEvent.setDescription("desdasdasd");
//        updateEvent.setStatusID(1);
//
//        dao.update(updateEvent);
//
////        dao.softDelete(4);
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
        }
    }
