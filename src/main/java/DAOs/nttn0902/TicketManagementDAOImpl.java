/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.Event;
import Models.nttn0902.RemainingTicket;
import Models.nttn0902.SoldTicket;
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
public class TicketManagementDAOImpl implements TicketManagementDAO {

    @Override
    public List<SoldTicket> getSoldTickets(int eventId, String keyword) {
        List<SoldTicket> list = new ArrayList<>();

        String sql
                = "SELECT "
                + " o.OrderID, "
                + " t.TicketID, "
                + " t.SerialNumber, "
                + " tt.TypeName, "
                + " o.OrderDate, "
                + " od.UnitPrice, "
                + " s.StatusID "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                + "JOIN Tickets t ON od.TicketID = t.TicketID "
                + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
                + "JOIN Status s ON od.StatusID = s.StatusID "
                + "WHERE tt.EventID = ? "
                + "AND (t.SerialNumber LIKE ? OR tt.TypeName LIKE ?) "
                + "ORDER BY o.OrderDate DESC";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SoldTicket s = new SoldTicket();
                s.setOrderId(rs.getInt(1));
                s.setTicketId(rs.getInt(2));
                s.setSerialNumber(rs.getString(3));
                s.setTypeName(rs.getString(4));
                s.setOrderDate(rs.getTimestamp(5));
                s.setUnitPrice(rs.getDouble(6));
                s.setStatusId(rs.getInt(7));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<RemainingTicket> getRemainingTickets(int eventId, String keyword) {
        List<RemainingTicket> list = new ArrayList<>();

        String sql
    = "SELECT "
    + " t.TicketID, "
    + " t.SerialNumber, "
    + " tt.TypeName, "
    + " z.ZoneName, "
    + " CONCAT(s.RowLabel, s.SeatNumber) AS Seat, "
    + " tt.Price "
    + "FROM Tickets t "
    + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
    + "LEFT JOIN Seats s ON t.SeatID = s.SeatID "
    + "LEFT JOIN Zones z ON s.ZoneID = z.ZoneID "
    + "WHERE tt.EventID = ? "
    + "AND t.StatusID = 1 "       // ✅ CHỈ LẤY VÉ ACTIVE
    + "AND t.TicketID NOT IN (SELECT TicketID FROM OrderDetails) "
    + "AND (t.SerialNumber LIKE ? OR tt.TypeName LIKE ?) "
    + "ORDER BY z.ZoneName, s.RowLabel, s.SeatNumber";


        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RemainingTicket r = new RemainingTicket();
                r.setTicketId(rs.getInt(1));
                r.setSerialNumber(rs.getString(2));
                r.setTypeName(rs.getString(3));
                r.setZoneName(rs.getString(4));
                r.setSeat(rs.getString(5));  // ✅ A1, B2,...
                r.setPrice(rs.getDouble(6));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String getEventNameById(int eventId) {
        String sql = "SELECT EventName FROM Events WHERE EventID = ?";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId); // ✅ bắt buộc

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("EventName");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Event> getOtherEvents(int currentEventId) {
        List<Event> list = new ArrayList<>();
        String sql
                = "SELECT EventID, EventName FROM Events "
                + "WHERE StatusID = 1 AND EventID <> ? "
                + "ORDER BY EventName";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, currentEventId); // ✅ cần thiết

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

//@Override
//public int toggleSoldTicketStatus(int ticketId, int eventId) {
//    String sql =
//    "UPDATE OrderDetails SET StatusID = CASE " +
//    "WHEN StatusID = 1 THEN 2 ELSE 1 END " +
//    "OUTPUT inserted.StatusID " +
//    "WHERE TicketID = ? AND OrderID IN (" +
//        "SELECT OrderID FROM Orders WHERE EventID = ?" +
//    ")";
//
//
//     try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//        ps.setInt(1, ticketId);
//        ps.setInt(2, eventId);
//
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            return rs.getInt(1);
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return -1;
//}
    @Override
public boolean updateSoldTicketStatus(int ticketId, int newStatus) {
    String sql =
        "UPDATE OrderDetails " +
        "SET StatusID = ? " +
        "WHERE TicketID = ?";

    try (Connection conn = DBContext.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, newStatus);
        ps.setInt(2, ticketId);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}



    @Override
    public boolean updateRemainingTicketStatus(int ticketId, int status) {
        String sql = "UPDATE Tickets SET statusId = ? WHERE ticketId = ?";
        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            ps.setInt(2, ticketId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
@Override
public RemainingTicket getRemainingTicketById(int ticketId) {
    String sql =
        "SELECT "
        + " t.TicketID, "
        + " t.SerialNumber, "
        + " tt.TypeName, "
        + " z.ZoneName, "
        + " CONCAT(s.RowLabel, s.SeatNumber) AS Seat, "
        + " tt.Price "
        + "FROM Tickets t "
        + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
        + "LEFT JOIN Seats s ON t.SeatID = s.SeatID "
        + "LEFT JOIN Zones z ON s.ZoneID = z.ZoneID "
        + "WHERE t.TicketID = ? "
        + "AND t.TicketID NOT IN (SELECT TicketID FROM OrderDetails)";

    try (Connection conn = DBContext.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, ticketId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            RemainingTicket rt = new RemainingTicket();
            rt.setTicketId(rs.getInt("TicketID"));
            rt.setSerialNumber(rs.getString("SerialNumber"));
            rt.setTypeName(rs.getString("TypeName"));
            rt.setZoneName(rs.getString("ZoneName"));
            rt.setSeat(rs.getString("Seat"));
            rt.setPrice(rs.getDouble("Price"));
            return rt;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}


@Override
public boolean updateRemainingTicketPrice(int ticketId, double price) {

    String sql = 
        "UPDATE tt " +
        "SET tt.Price = ? " +
        "FROM TicketTypes tt " +
        "JOIN Tickets t ON tt.TicketTypeID = t.TicketTypeID " +
        "WHERE t.TicketID = ?";

    try (Connection conn = DBContext.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDouble(1, price);
        ps.setInt(2, ticketId);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


@Override
public List<SoldTicket> searchSoldTickets(int eventId, String keyword) {
    List<SoldTicket> list = new ArrayList<>();
    String sql =
        "SELECT s.orderId, s.ticketId, t.serialNumber, t.typeName, "
      + "       s.orderDate, s.unitPrice, s.statusId "
      + "FROM SoldTicket s "
      + "JOIN Ticket t ON s.ticketId = t.ticketId "
      + "WHERE t.eventId = ? "
      + "  AND ( "
      + "        s.orderId LIKE ? OR "
      + "        s.ticketId LIKE ? OR "
      + "        t.serialNumber LIKE ? OR "
      + "        t.typeName LIKE ? "
      + "      )";


    try (Connection conn = DBContext.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, eventId);
        String kw = "%" + keyword + "%";
        for (int i = 2; i <= 5; i++) ps.setString(i, kw);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SoldTicket s = new SoldTicket();
            s.setOrderId(rs.getInt("orderId"));
            s.setTicketId(rs.getInt("ticketId"));
            s.setSerialNumber(rs.getString("serialNumber"));
            s.setTypeName(rs.getString("typeName"));
            s.setOrderDate(rs.getTimestamp("orderDate"));
            s.setUnitPrice(rs.getDouble("unitPrice"));
            s.setStatusId(rs.getInt("statusId"));
            list.add(s);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
@Override
public List<RemainingTicket> searchRemainingTickets(int eventId, String keyword) {
    List<RemainingTicket> list = new ArrayList<>();
    String sql =
      "SELECT ticketId, serialNumber, typeName, zoneName, seat, price "
    + "FROM RemainingTicket "
    + "WHERE eventId = ? "
    + "  AND ( "
    + "        ticketId LIKE ? OR "
    + "        serialNumber LIKE ? OR "
    + "        typeName LIKE ? OR "
    + "        zoneName LIKE ? OR "
    + "        seat LIKE ? "
    + "      )";


    try (Connection conn = DBContext.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, eventId);
        String kw = "%" + keyword + "%";
        for (int i = 2; i <= 7; i++) ps.setString(i, kw);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            RemainingTicket r = new RemainingTicket();
            r.setTicketId(rs.getInt("ticketId"));
            r.setSerialNumber(rs.getString("serialNumber"));
            r.setTypeName(rs.getString("typeName"));
            r.setZoneName(rs.getString("zoneName"));
            r.setSeat(rs.getString("seat"));
            r.setPrice(rs.getDouble("price"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
