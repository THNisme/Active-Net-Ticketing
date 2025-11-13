/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.StatisticsOffAllEvent;
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
public class StatisticsOffAllEventDAO {

    private Connection conn = DBContext.getInstance().getConnection();

     public List<StatisticsOffAllEvent> getThongKeTheoSuKien() {
        List<StatisticsOffAllEvent> list = new ArrayList<>();

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
            GROUP BY e.EventName
            ORDER BY e.EventName
        """;

        try {
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticsOffAllEvent tk = new StatisticsOffAllEvent();
                tk.setEventName(rs.getString("EventName"));
                tk.setTotalTickets(rs.getInt("totalTickets"));
                tk.setSoldTickets(rs.getInt("soldTickets"));
                tk.setTotalRevenue(rs.getDouble("totalRevenue"));
                list.add(tk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tính tổng doanh thu toàn hệ thống
    public double getTongDoanhThu() {
        String sql = "SELECT ISNULL(SUM(UnitPrice), 0) AS total FROM OrderDetails";
        try {
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm tổng vé phát hành
    public int getTongVePhatHanh() {
        String sql = "SELECT COUNT(TicketID) AS total FROM Tickets";
        try {
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm tổng vé đã bán
    public int getTongVeDaBan() {
        String sql = "SELECT COUNT(DISTINCT TicketID) AS sold FROM OrderDetails";
        try {
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("sold");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    }