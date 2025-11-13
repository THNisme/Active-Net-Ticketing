/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.EventDetails;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NGUYEN
 */
public class EventDetailsDAO {
    private final Connection conn = DBContext.getInstance().getConnection();

    public EventDetails getEventDetailsById(int eventId) {
        EventDetails event = null;

       String sql =
    "SELECT " +
    "    e.EventID, " +
    "    e.EventName, " +
    "    e.Description, " +
    "    e.ImageURL, " +
    "    e.StartDate, " +
    "    p.PlaceName, " +
    "    p.Address, " +
    "    MIN(tt.Price) AS LowestPrice " +
    "FROM Events e " +
    "JOIN Places p ON e.PlaceID = p.PlaceID " +
    "LEFT JOIN TicketTypes tt ON e.EventID = tt.EventID " +
    "WHERE e.EventID = ? " +
    "GROUP BY e.EventID, e.EventName, e.Description, e.ImageURL, e.StartDate, p.PlaceName, p.Address;";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                event = new EventDetails();
                event.setEventID(rs.getInt("EventID"));
                event.setEventName(rs.getString("EventName"));
                event.setDescription(rs.getString("Description"));
                event.setImageURL(rs.getString("ImageURL"));
                event.setStartDate(rs.getDate("StartDate"));
                event.setPlaceName(rs.getString("PlaceName"));
                event.setAddress(rs.getString("Address"));
                event.setLowestPrice(rs.getDouble("LowestPrice"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }
}