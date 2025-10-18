package DAOs;

import Models.MyTicket;
import Utils.singleton.DBContext;
import java.sql.*;
import java.util.*;

public class MyTicketDAO {

    private final Connection conn;

    public MyTicketDAO() {
        conn = DBContext.getInstance().getConnection();
    }

    // ✅ Base SQL cho mọi loại vé
    private static final String BASE_SQL
            = "SELECT o.OrderID, t.TicketID, e.EventName, z.ZoneName, "
            + "       (s.RowLabel + CAST(s.SeatNumber AS NVARCHAR)) AS SeatLabel, "
            + "       e.StartDate, e.EndDate, "
            + "       od.UnitPrice "
            + "FROM Orders o "
            + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
            + "JOIN Tickets t ON od.TicketID = t.TicketID "
            + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
            + "JOIN Events e ON tt.EventID = e.EventID "
            + "JOIN Zones z ON tt.ZoneID = z.ZoneID "
            + "LEFT JOIN Seats s ON t.SeatID = s.SeatID "
            + "JOIN Status so ON o.StatusID = so.StatusID "
            + "JOIN Status st ON t.StatusID = st.StatusID "
            + "WHERE o.UserID = ? ";

    // 🔹 Tất cả vé
    public List<MyTicket> getAllTicketsByUser(int userId) {
        return getTickets(userId, "");
    }

    // 🔹 Vé sắp diễn ra (StartDate > hiện tại, chưa hủy)
    public List<MyTicket> getUpcomingTicketsByUser(int userId) {
        String filter = "AND e.StartDate > GETDATE() AND st.Code != 'DELETED' AND so.Code != 'DELETED' ";
        return getTickets(userId, filter);
    }

    // 🔹 Vé đã kết thúc (EndDate < hiện tại, chưa hủy)
    public List<MyTicket> getEndedTicketsByUser(int userId) {
        String filter = "AND e.EndDate < GETDATE() AND st.Code != 'DELETED' AND so.Code != 'DELETED' ";
        return getTickets(userId, filter);
    }

    // 🔹 Vé đã hủy
    public List<MyTicket> getCanceledTicketsByUser(int userId) {
        String filter = "AND (st.Code = 'DELETED' OR so.Code = 'DELETED') ";
        return getTickets(userId, filter);
    }

    // ✅ Hàm xử lý chung
    private List<MyTicket> getTickets(int userId, String extraCondition) {
        List<MyTicket> list = new ArrayList<>();
        String sql = BASE_SQL + extraCondition + "ORDER BY e.StartDate DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MyTicket t = new MyTicket();
                t.setOrderId(rs.getInt("OrderID"));
                t.setTicketId(rs.getInt("TicketID"));
                t.setEventName(rs.getString("EventName"));
                t.setZoneName(rs.getString("ZoneName"));
                t.setSeatLabel(rs.getString("SeatLabel"));
                t.setStartDate(rs.getTimestamp("StartDate"));
                t.setEndDate(rs.getTimestamp("EndDate"));
                t.setPrice(rs.getDouble("UnitPrice"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
