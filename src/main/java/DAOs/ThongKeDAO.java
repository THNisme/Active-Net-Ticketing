/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.ThongKe;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import Utils.singleton.DBContext;
import jakarta.enterprise.event.Event;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NGUYEN
 */
public class ThongKeDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    // ✅ Tổng doanh thu (từ vé đã bán)
    public double getTongDoanhThu() {
        String sql = """
            SELECT ISNULL(SUM(od.UnitPrice), 0) AS totalRevenue
            FROM OrderDetails od
            JOIN Tickets t ON od.TicketID = t.TicketID
            JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID
            JOIN Events e ON tt.EventID = e.EventID
            WHERE e.StatusID = 1
        """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            {
                if (rs.next()) {
                    return rs.getDouble("totalRevenue");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Tổng số vé phát hành (tức tổng số vé có trong bảng Tickets)
    public int getTongVePhatHanh() {
        String sql = """
            SELECT COUNT(*) AS totalTickets
            FROM Tickets t
            JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID
            JOIN Events e ON tt.EventID = e.EventID
            WHERE e.StatusID = 1
        """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
                if (rs.next()) {
                    return rs.getInt("totalTickets");
                }
            }catch (Exception e) {
            e.printStackTrace();
        }
            return 0;
        }
        // ✅ Tổng vé đã bán (chỉ tính những vé có trong OrderDetails)
    public int getTongVeDaBan() {
        String sql = """
            SELECT COUNT(DISTINCT t.TicketID) AS soldTickets
            FROM OrderDetails od
            JOIN Tickets t ON od.TicketID = t.TicketID
            JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID
            JOIN Events e ON tt.EventID = e.EventID
            WHERE e.StatusID = 1
        """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           
            if (rs.next()) {
                return rs.getInt("soldTickets");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Tính phần trăm vé bán ra
    public double getPhanTramVeBan() {
        int tongVePhatHanh = getTongVePhatHanh();
        int tongVeDaBan = getTongVeDaBan();
        if (tongVePhatHanh == 0) {
            return 0;
        }
        return (tongVeDaBan * 100.0 / tongVePhatHanh);
    }

    // ✅ Tính phần trăm doanh thu đạt được
    // Giả sử tổng giá trị vé phát hành = tổng giá trị của tất cả vé (giá vé * số vé)
    public double getPhanTramDoanhThu() {
        String sql = """
            SELECT 
                ISNULL(SUM(tt.Price), 0) AS totalValue,
                (SELECT ISNULL(SUM(od.UnitPrice), 0)
                 FROM OrderDetails od
                 JOIN Tickets t ON od.TicketID = t.TicketID
                 JOIN TicketTypes tt2 ON t.TicketTypeID = tt2.TicketTypeID
                 JOIN Events e2 ON tt2.EventID = e2.EventID
                 WHERE e2.StatusID = 1) AS soldRevenue
            FROM TicketTypes tt
            JOIN Tickets t ON tt.TicketTypeID = t.TicketTypeID
            JOIN Events e ON tt.EventID = e.EventID
            WHERE e.StatusID = 1
        """;

       try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                double totalValue = rs.getDouble("totalValue");
                double soldRevenue = rs.getDouble("soldRevenue");
                if (totalValue == 0) {
                    return 0;
                }
                return (soldRevenue * 100.0 / totalValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<ThongKe> getThongKeTheoSuKien() {
    List<ThongKe> list = new ArrayList<>();
    String sql = """
        SELECT 
            e.EventName,
            COUNT(t.TicketID) AS totalTickets,
            COUNT(DISTINCT od.TicketID) AS soldTickets,
            ISNULL(SUM(od.UnitPrice), 0) AS totalRevenue
        FROM Events e
        LEFT JOIN TicketTypes tt ON e.EventID = tt.EventID
        LEFT JOIN Tickets t ON tt.TicketTypeID = t.TicketTypeID
        LEFT JOIN OrderDetails od ON t.TicketID = od.TicketID
        WHERE e.StatusID = 1
        GROUP BY e.EventName
        ORDER BY e.EventName
    """;

    try {
              PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             ThongKe tk = new ThongKe(
                rs.getString("EventName"),
                rs.getInt("totalTickets"),
                rs.getInt("soldTickets"),
                rs.getDouble("totalRevenue")
            );
            list.add(tk);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
}
