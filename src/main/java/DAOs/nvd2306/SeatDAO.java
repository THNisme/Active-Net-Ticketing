/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Seat;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class SeatDAO {

    private final Connection conn;

    public SeatDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    public List<Seat> getSeatsByZone(int zoneId) {
        List<Seat> list = new ArrayList<>();
        String sql
                = "SELECT s.SeatID, s.ZoneID, s.RowLabel, s.SeatNumber, "
                + "       t.StatusID AS TicketStatus "
                + // status ghế sau khi bán
                "FROM Seats s "
                + "LEFT JOIN Tickets t ON s.SeatID = t.SeatID "
                + "WHERE s.ZoneID = ? "
                + "ORDER BY s.RowLabel, s.SeatNumber";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, zoneId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seat s = new Seat();
                    s.setSeatID(rs.getInt("SeatID"));
                    s.setZoneID(rs.getInt("ZoneID"));
                    s.setRowLabel(rs.getString("RowLabel"));
                    s.setSeatNumber(rs.getInt("SeatNumber"));

                    int ticketStatus = rs.getInt("TicketStatus");
                    if (ticketStatus == 4) {
                        s.setStatusID(4); // soldout
                    } else {
                        s.setStatusID(1); // active
                    }

                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Seat> getSeatsByTicketType(int ticketTypeID, int zoneId) {
        List<Seat> list = new ArrayList<>();
        String sql = """
        SELECT  s.SeatID, s.ZoneID, s.RowLabel, s.SeatNumber,
                t.StatusID AS TicketStatus
        FROM    Tickets t
        JOIN    Seats s ON s.SeatID = t.SeatID
        WHERE   t.TicketTypeID = ?
            AND s.ZoneID = ?
        ORDER BY s.RowLabel, s.SeatNumber
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketTypeID);
            ps.setInt(2, zoneId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seat s = new Seat();
                    s.setSeatID(rs.getInt("SeatID"));
                    s.setZoneID(rs.getInt("ZoneID"));
                    s.setRowLabel(rs.getString("RowLabel"));
                    s.setSeatNumber(rs.getInt("SeatNumber"));

                    int ticketStatus = rs.getInt("TicketStatus");
                    s.setStatusID(ticketStatus);   // 1 = còn, 4 = soldout...

                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
