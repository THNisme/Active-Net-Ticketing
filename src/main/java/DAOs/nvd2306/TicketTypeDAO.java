/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.TicketType;
import Utils.singleton.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class TicketTypeDAO  {

    private final java.sql.Connection conn;

    public TicketTypeDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    public List<TicketType> getTicketTypesByEventAndZone(int eventId, int zoneId) {
        List<TicketType> list = new ArrayList<>();

        String sql = """
        SELECT 
            tt.TicketTypeID,
            tt.EventID,
            tt.ZoneID,
            tt.TypeName,
            tt.Price,
            tt.StatusID,
            COUNT(t.TicketID) AS availableCount
        FROM TicketTypes tt
        LEFT JOIN Tickets t
            ON tt.TicketTypeID = t.TicketTypeID
            AND t.StatusID = 1  -- chỉ đếm vé còn
        WHERE tt.EventID = ? AND tt.ZoneID = ? AND tt.StatusID = 1
        GROUP BY 
            tt.TicketTypeID, tt.EventID, tt.ZoneID,
            tt.TypeName, tt.Price, tt.StatusID
        ORDER BY tt.Price ASC
    """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, eventId);
            ps.setInt(2, zoneId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TicketType t = new TicketType();
                t.setTicketTypeID(rs.getInt("TicketTypeID"));
                t.setEventID(rs.getInt("EventID"));
                t.setZoneID(rs.getInt("ZoneID"));
                t.setTypeName(rs.getString("TypeName"));
                t.setPrice(rs.getBigDecimal("Price"));
                t.setStatusID(rs.getInt("StatusID"));
                t.setAvailableCount(rs.getInt("availableCount"));

                list.add(t);
            }

        } catch (Exception e) {
            System.out.println("TicketTypeDAO error: " + e.getMessage());
        }

        return list;
    }
}
