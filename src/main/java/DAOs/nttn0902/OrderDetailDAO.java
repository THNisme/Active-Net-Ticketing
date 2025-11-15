/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.OrderDetail;
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
public class OrderDetailDAO {

    private final Connection conn = DBContext.getInstance().getConnection();

    // Lấy chi tiết đơn hàng theo orderId
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> list = new ArrayList<>();

        String sql = ""
                + "SELECT "
                + "o.OrderID, o.ContactFullname, o.ContactEmail, o.ContactPhone, o.OrderDate, "
                + "e.EventName, c.CategoryName, e.StartDate, e.EndDate, "
                + "p.PlaceName, p.Address AS PlaceAddress, "
                + "t.SerialNumber, tt.TypeName AS TicketTypeName, tt.Price AS TicketPrice, "
                + "z.ZoneName, s.RowLabel, s.SeatNumber "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                + "JOIN Tickets t ON od.TicketID = t.TicketID "
                + "JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
                + "JOIN Events e ON tt.EventID = e.EventID "
                + "JOIN EventCategories c ON e.CategoryID = c.CategoryID "
                + "JOIN Places p ON e.PlaceID = p.PlaceID "
                + "LEFT JOIN Seats s ON t.SeatID = s.SeatID "
                + "LEFT JOIN Zones z ON s.ZoneID = z.ZoneID "
                + "WHERE o.OrderID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail d = new OrderDetail();

                // Buyer info
                d.setOrderId(rs.getInt("OrderID"));
                d.setContactFullname(rs.getString("ContactFullname"));
                d.setContactEmail(rs.getString("ContactEmail"));
                d.setContactPhone(rs.getString("ContactPhone"));
                d.setCreatedDate(rs.getTimestamp("OrderDate"));

                // Event info
                d.setEventName(rs.getString("EventName"));
                d.setCategoryName(rs.getString("CategoryName"));
                d.setStartDate(rs.getTimestamp("StartDate"));
                d.setEndDate(rs.getTimestamp("EndDate"));

                // Place info
                d.setPlaceName(rs.getString("PlaceName"));
                d.setPlaceAddress(rs.getString("PlaceAddress"));

                // Ticket info
                d.setSerialNumber(rs.getString("SerialNumber"));
                d.setTicketTypeName(rs.getString("TicketTypeName"));
                d.setTicketPrice(rs.getDouble("TicketPrice")); 
                d.setZoneName(rs.getString("ZoneName"));
                d.setRowLabel(rs.getString("RowLabel"));
                d.setSeatNumber(rs.getInt("SeatNumber"));

                list.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error in getOrderDetails(): " + e.getMessage());
        }

        return list;
    }
}
