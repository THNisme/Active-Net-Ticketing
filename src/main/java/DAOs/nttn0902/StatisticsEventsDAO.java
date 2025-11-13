/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.StatisticsEvents;
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
public class StatisticsEventsDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    public List<StatisticsEvents> getStatisticsEvent() {
        List<StatisticsEvents> list = new ArrayList<>();

        String sql = """
             SELECT 
                            e.EventID,
                            e.EventName,
                            COUNT(t.TicketID) AS totalTickets,
                            COUNT(DISTINCT od.TicketID) AS soldTickets,
                            ISNULL(SUM(od.UnitPrice), 0) AS totalRevenue
                        FROM Events e
                        LEFT JOIN TicketTypes tt ON e.EventID = tt.EventID
                        LEFT JOIN Tickets t ON tt.TicketTypeID = t.TicketTypeID
                        LEFT JOIN OrderDetails od ON t.TicketID = od.TicketID
                        GROUP BY e.EventID, e.EventName
                        ORDER BY e.EventName
        """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticsEvents tk = new StatisticsEvents();
                tk.setEventID(rs.getInt("EventID"));
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

    // totalRevenue
    public double getTotalRevenue() {
        String sql = "SELECT ISNULL(SUM(UnitPrice), 0) AS totalRevenue FROM OrderDetails";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("totalRevenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // totalTicketsIssued
    public int getTotalTicketsIssued() {
        String sql = "SELECT COUNT(TicketID) AS totalTicketsIssued FROM Tickets";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalTicketsIssued");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm tổng vé đã bán
    public int getTotalTicketsSold() {
        String sql = "SELECT COUNT(DISTINCT TicketID) AS totalTicketsSold FROM OrderDetails";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalTicketsSold");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // AllEvents
    public List<StatisticsEvents> getAllEvents() {
        List<StatisticsEvents> list = new ArrayList<>();
        String sql = "SELECT EventID, EventName FROM Events ORDER BY EventName";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StatisticsEvents e = new StatisticsEvents();
                e.setEventID(rs.getInt("EventID"));
                e.setEventName(rs.getString("EventName"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<StatisticsEvents> getAllEventSummariesExcept(int excludeEventId) {
        List<StatisticsEvents> list = new ArrayList<>();
        String sql = "SELECT EventID, EventName FROM Events WHERE EventID <> ? ORDER BY EventName";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, excludeEventId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticsEvents s = new StatisticsEvents();
                s.setEventID(rs.getInt("EventID"));
                s.setEventName(rs.getString("EventName"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
