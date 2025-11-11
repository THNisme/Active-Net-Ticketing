/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Event;
import Models.nvd2306.TicketType;
import Utils.nvd2603.DBContext;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    public List<Event> filterEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Event> events = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT e.EventID, e.CategoryID, e.EventName, e.Description, ")
                .append("e.ImageURL, e.StartDate, e.EndDate, e.PlaceID, e.StatusID ")
                .append("FROM Events e WHERE e.StatusID = 1 ");

        if (startDate != null && endDate != null) {
            sql.append("AND (")
                    .append("(CAST(e.StartDate AS DATE) >= ? AND CAST(e.StartDate AS DATE) <= ?) ")
                    .append("OR (CAST(e.EndDate AS DATE) >= ? AND CAST(e.EndDate AS DATE) <= ?) ")
                    .append("OR (CAST(e.StartDate AS DATE) <= ? AND CAST(e.EndDate AS DATE) >= ?) ")
                    .append(") ");
        } else if (startDate != null) {
            sql.append("AND CAST(e.StartDate AS DATE) >= ? ");
        } else if (endDate != null) {
            sql.append("AND CAST(e.EndDate AS DATE) <= ? ");
        }

        sql.append("ORDER BY e.StartDate ASC");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null && endDate != null) {
                ps.setDate(paramIndex++, Date.valueOf(startDate));
                ps.setDate(paramIndex++, Date.valueOf(endDate));
                ps.setDate(paramIndex++, Date.valueOf(startDate));
                ps.setDate(paramIndex++, Date.valueOf(endDate));
                ps.setDate(paramIndex++, Date.valueOf(startDate));
                ps.setDate(paramIndex++, Date.valueOf(endDate));
            } else if (startDate != null) {
                ps.setDate(paramIndex++, Date.valueOf(startDate));
            } else if (endDate != null) {
                ps.setDate(paramIndex++, Date.valueOf(endDate));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEventID(rs.getInt("EventID"));
                    event.setCategoryID(rs.getInt("CategoryID"));
                    event.setEventName(rs.getString("EventName"));
                    event.setDescription(rs.getString("Description"));
                    event.setImageURL(rs.getString("ImageURL"));
                    event.setStartDate(rs.getTimestamp("StartDate"));
                    event.setEndDate(rs.getTimestamp("EndDate"));
                    event.setPlaceID(rs.getInt("PlaceID"));
                    event.setStatusID(rs.getInt("StatusID"));
                    events.add(event);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi filterEventsByDateRange: " + e.getMessage());
            e.printStackTrace();
        }

        return events;
    }

    public BigDecimal getLowestTicketPrice(int eventId) {
        String sql = "SELECT MIN(Price) AS LowestPrice FROM TicketTypes WHERE EventID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal p = rs.getBigDecimal("LowestPrice");
                    return p; // có thể null nếu chưa có TicketTypes
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi getLowestTicketPrice: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // hoặc BigDecimal.ZERO;
    }

    // ✅ Đếm số vé còn lại
    public int getAvailableTicketCount(int ticketTypeId) {
        String sql = """
            SELECT COUNT(*) AS Available
            FROM Tickets
            WHERE TicketTypeID = ? AND StatusID = 1
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Available");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi getAvailableTicketCount: " + e.getMessage());
        }
        return 0;
    }

    // ✅ Lấy danh sách TicketType kèm số vé còn lại
    public List<TicketType> getTicketTypesByEventId(int eventId) {
        List<TicketType> list = new ArrayList<>();
        String sql = """
            SELECT TicketTypeID, EventID, ZoneID, TypeName, Price, StatusID
            FROM TicketTypes
            WHERE EventID = ? AND StatusID = 1
            ORDER BY Price ASC
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TicketType t = new TicketType();
                    t.setTicketTypeID(rs.getInt("TicketTypeID"));
                    t.setEventID(rs.getInt("EventID"));
                    t.setZoneID(rs.getInt("ZoneID"));
                    t.setTypeName(rs.getString("TypeName"));
                    t.setPrice(rs.getBigDecimal("Price"));
                    t.setStatusID(rs.getInt("StatusID"));

                    // lấy số vé còn lại
                    int available = getAvailableTicketCount(t.getTicketTypeID());
                    t.setAvailableCount(available);

                    list.add(t);
                }
            }
        } catch (SQLException ex) {
            System.out.println("❌ getTicketTypesByEventId: " + ex.getMessage());
        }
        return list;
    }

    // ✅ Lấy thông tin chi tiết sự kiện (đã có ở bạn)
    public Event getEventDetailById(int eventId) {
        Event event = null;
        String sql = """
        SELECT 
            e.EventID, e.CategoryID, e.EventName, e.Description, e.ImageURL,
            e.StartDate, e.EndDate, e.PlaceID, e.StatusID,
            p.PlaceName, p.Address,
            MIN(tt.Price) AS LowestPrice
        FROM Events e
        JOIN Places p ON e.PlaceID = p.PlaceID
        LEFT JOIN TicketTypes tt ON e.EventID = tt.EventID
        WHERE e.EventID = ?
        GROUP BY e.EventID, e.CategoryID, e.EventName, e.Description, e.ImageURL,
                 e.StartDate, e.EndDate, e.PlaceID, e.StatusID, p.PlaceName, p.Address
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                event = new Event();
                event.setEventID(rs.getInt("EventID"));
                event.setCategoryID(rs.getInt("CategoryID"));
                event.setEventName(rs.getString("EventName"));
                event.setDescription(rs.getString("Description"));
                event.setImageURL(rs.getString("ImageURL"));
                event.setStartDate(rs.getTimestamp("StartDate"));
                event.setEndDate(rs.getTimestamp("EndDate"));
                event.setPlaceID(rs.getInt("PlaceID"));
                event.setStatusID(rs.getInt("StatusID"));
                event.setPlaceName(rs.getString("PlaceName"));
                event.setAddress(rs.getString("Address"));
                event.setLowestPrice(rs.getBigDecimal("LowestPrice"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi getEventDetailById: " + e.getMessage());
        }
        return event;
    }
}
