/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.Event;
import Models.nttn0902.OrderManagement;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NGUYEN
 */
public class OrderManagementDAO {

    private final Connection conn = DBContext.getInstance().getConnection();

    // ✅ Lấy toàn bộ danh sách đơn hàng (toàn hệ thống)
    public List<OrderManagement> getAllOrders() {
        List<OrderManagement> list = new ArrayList<>();
        String sql
                = "SELECT DISTINCT "
                + "o.OrderID, "
                + "o.ContactFullname, "
                + "o.ContactEmail, "
                + "o.ContactPhone, "
                + "o.OrderDate, "
                + "o.TotalAmount, "
                + "e.EventName "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                + "JOIN Tickets t ON od.TicketID = t.TicketID "
                + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
                + "JOIN Events e ON tt.EventID = e.EventID "
                + "WHERE o.StatusID = 1 "
                + "AND t.StatusID  = 1 "
                + "ORDER BY o.OrderDate DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderManagement o = new OrderManagement();
                o.setOrderId(rs.getInt("OrderID"));
                o.setContactFullname(rs.getString("ContactFullname"));
                o.setContactEmail(rs.getString("ContactEmail"));
                o.setContactPhone(rs.getString("ContactPhone"));
                o.setOrderDate(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setEventName(rs.getString("EventName"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Lấy danh sách đơn hàng theo ID sự kiện (eventID)
    public List<OrderManagement> getAllOrdersByEventId(int eventId) {
        List<OrderManagement> list = new ArrayList<>();
        String sql
                = "SELECT DISTINCT "
                + "o.OrderID, "
                + "o.ContactFullname, "
                + "o.ContactEmail, "
                + "o.ContactPhone, "
                + "o.OrderDate, "
                + "o.TotalAmount, "
                + "e.EventName "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                + "JOIN Tickets t ON od.TicketID = t.TicketID "
                + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
                + "JOIN Events e ON tt.EventID = e.EventID "
                + "WHERE tt.EventID = ? "
                + "AND o.StatusID = 1 "
                + "AND t.StatusID = 1 "
                + "ORDER BY o.OrderDate DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderManagement o = new OrderManagement();
                o.setOrderId(rs.getInt("OrderID"));
                o.setContactFullname(rs.getString("ContactFullname"));
                o.setContactEmail(rs.getString("ContactEmail"));
                o.setContactPhone(rs.getString("ContactPhone"));
                o.setOrderDate(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setEventName(rs.getString("EventName"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Lấy đơn hàng theo ID
    public OrderManagement getOrderById(int orderId) {
        String sql
                = "SELECT o.OrderID, o.ContactFullname, o.ContactEmail, o.ContactPhone, "
                + "o.OrderDate, o.TotalAmount, e.EventName "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                + "JOIN Tickets t ON od.TicketID = t.TicketID "
                + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
                + "JOIN Events e ON tt.EventID = e.EventID "
                + "WHERE o.OrderID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                OrderManagement o = new OrderManagement();
                o.setOrderId(rs.getInt("OrderID"));
                o.setContactFullname(rs.getString("ContactFullname"));
                o.setContactEmail(rs.getString("ContactEmail"));
                o.setContactPhone(rs.getString("ContactPhone"));
                o.setOrderDate(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setEventName(rs.getString("EventName"));
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    // ✅ Thêm mới đơn hàng
//    public boolean insertOrder(OrderManagement o) {
//        String sql =
//            "INSERT INTO Orders (ContactFullname, ContactEmail, ContactPhone, OrderDate, TotalAmount, StatusID) "
//          + "VALUES (?, ?, ?, GETDATE(), ?, 1)";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, o.getContactFullname());
//            ps.setString(2, o.getContactEmail());
//            ps.setString(3, o.getContactPhone());
//            ps.setBigDecimal(4, o.getTotalAmount());
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    // ✅ Cập nhật đơn hàng
//    public boolean updateOrder(OrderManagement o) {
//        String sql =
//            "UPDATE Orders "
//          + "SET ContactFullname = ?, "
//          + "ContactEmail = ?, "
//          + "ContactPhone = ?, "
//          + "TotalAmount = ? "
//          + "WHERE OrderID = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, o.getContactFullname());
//            ps.setString(2, o.getContactEmail());
//            ps.setString(3, o.getContactPhone());
//            ps.setBigDecimal(4, o.getTotalAmount());
//            ps.setInt(5, o.getOrderId());
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    //  Lấy tên sự kiện theo ID
    public String getEventNameById(int eventId) {
        String sql = "SELECT EventName FROM Events WHERE EventID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("EventName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderManagement> searchOrders(String keyword, Integer eventId) {
        List<OrderManagement> list = new ArrayList<>();

        String sql
                = "SELECT DISTINCT "
                + " o.OrderID, o.ContactFullname, o.ContactEmail, o.ContactPhone, "
                + " o.OrderDate, o.TotalAmount, e.EventName "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                + "JOIN Tickets t ON od.TicketID = t.TicketID "
                + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
                + "JOIN Events e ON tt.EventID = e.EventID "
                + "WHERE o.StatusID = 1 AND t.StatusID = 1 ";

        // Chỉ search trong đúng event này
        if (eventId != null) {
            sql += "AND tt.EventID = ? ";
        }

        // Điều kiện SEARCH
        sql
                += "AND ("
                + " CAST(o.OrderID AS NVARCHAR) LIKE ? OR "
                + " o.ContactFullname LIKE ? OR "
                + " o.ContactEmail LIKE ? OR "
                + " o.ContactPhone LIKE ? OR "
                + " CAST(o.TotalAmount AS NVARCHAR) LIKE ? OR "
                + " FORMAT(o.OrderDate, 'dd/MM/yyyy') LIKE ? "
                + ") "
                + "ORDER BY o.OrderDate DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            int idx = 1;

            // Bind eventId
            if (eventId != null) {
                ps.setInt(idx++, eventId);
            }

            // Bind keyword
            String kw = "%" + keyword + "%";
            ps.setString(idx++, kw); // OrderID
            ps.setString(idx++, kw); // fullname
            ps.setString(idx++, kw); // email
            ps.setString(idx++, kw); // phone
            ps.setString(idx++, kw); // total amount
            ps.setString(idx++, kw); // date

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                OrderManagement o = new OrderManagement();
                o.setOrderId(rs.getInt("OrderID"));
                o.setContactFullname(rs.getString("ContactFullname"));
                o.setContactEmail(rs.getString("ContactEmail"));
                o.setContactPhone(rs.getString("ContactPhone"));
                o.setOrderDate(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setEventName(rs.getString("EventName"));

                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean softDeleteOrder(int orderID) {
        String sql = "UPDATE Orders SET StatusID = 3 WHERE OrderID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// ✅ Lấy danh sách tất cả sự kiện
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT EventID, EventName FROM Events WHERE StatusID = 1 ORDER BY EventName";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
