/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.StatisticsEvent;
import Models.nttn0902.StatisticsEvents;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NGUYEN
 */
public class StatisticsEventDAO {
 private Connection conn = DBContext.getInstance().getConnection();

     public List<StatisticsEvent> getTicketTypeStatistics(int eventId) {
        List<StatisticsEvent> list = new ArrayList<>();
        String sql = 
        "SELECT "
        + "    e.EventName, "
        + "    tt.TypeName AS TicketTypeName, "
        + "    COUNT(t.TicketID) AS TotalTickets, "
        + "    COUNT(DISTINCT od.TicketID) AS SoldTickets, "
        + "    ISNULL(SUM(od.UnitPrice), 0) AS TotalRevenue "
        + "FROM Events e "
        + "LEFT JOIN TicketTypes tt ON e.EventID = tt.EventID "
        + "LEFT JOIN Tickets t ON tt.TicketTypeID = t.TicketTypeID "
        + "LEFT JOIN OrderDetails od ON t.TicketID = od.TicketID "
        + "WHERE e.EventID = ? "
        + "GROUP BY e.EventName, tt.TypeName "
        + "ORDER BY tt.TypeName";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticsEvent tk = new StatisticsEvent();
                tk.setEventName(rs.getString("EventName"));
                tk.setTicketTypeName(rs.getString("TicketTypeName"));
                tk.setTotalTickets(rs.getInt("TotalTickets"));
                tk.setSoldTickets(rs.getInt("SoldTickets"));
                tk.setTotalRevenue(rs.getDouble("TotalRevenue"));
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * total event revenue
     */
    public double getTotalEventRevenue(int eventId) {
        String sql =
        "SELECT ISNULL(SUM(od.UnitPrice), 0) AS totalRevenue "
        + "FROM OrderDetails od "
        + "INNER JOIN Tickets t ON od.TicketID = t.TicketID "
        + "INNER JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
        + "INNER JOIN Events e ON tt.EventID = e.EventID "
        + "WHERE e.EventID = ? ";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("totalRevenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Total Tickets
     */
    public int getTotalTickets(int eventId) {
        String sql =
        "SELECT COUNT(t.TicketID) AS totalTickets "
        + "FROM Tickets t "
        + "INNER JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
        + "WHERE tt.EventID = ? ";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("totalTickets");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     */
    public int getSoldTickets(int eventId) {
        String sql =
        "SELECT COUNT(DISTINCT od.TicketID) AS soldTickets "
        + "FROM OrderDetails od "
        + "INNER JOIN Tickets t ON od.TicketID = t.TicketID "
        + "INNER JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID "
        + "WHERE tt.EventID = ? ";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("soldTickets");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}