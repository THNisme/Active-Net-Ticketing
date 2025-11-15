/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Ticket;
import Utils.singleton.DBContext;
import java.sql.*;
import java.util.*;

/**
 *
 * @author NguyenDuc
 */
public class TicketDAO {

    private final Connection conn;

    public TicketDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    // === Lấy danh sách vé còn trống của một loại ===
    public List<Ticket> pickAvailableTickets(Connection conn, int ticketTypeId, int qty) throws SQLException {
        List<Ticket> res = new ArrayList<>();
        String sql = """
            SELECT TOP (?) TicketID, TicketTypeID, SeatID, SerialNumber, StatusID, IssuedAt
            FROM Tickets
            WHERE TicketTypeID = ? AND StatusID = 1
            ORDER BY TicketID;
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, ticketTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket(
                            rs.getInt("TicketID"),
                            rs.getInt("TicketTypeID"),
                            (Integer) rs.getObject("SeatID"),
                            rs.getString("SerialNumber"),
                            rs.getInt("StatusID"),
                            rs.getTimestamp("IssuedAt")
                    );
                    res.add(t);
                }
            }
        }
        return res;
    }

    // === Đánh dấu danh sách vé đã bán (StatusID = 2) ===
    public void markTicketsSold(Connection conn, List<Integer> ticketIds) throws SQLException {
        if (ticketIds == null || ticketIds.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ticketIds.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        String sql = "UPDATE Tickets SET StatusID = 2 WHERE TicketID IN (" + sb + ")";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Integer id : ticketIds) {
                ps.setInt(idx++, id);
            }
            ps.executeUpdate();
        }
    }

    // === Lấy tên loại vé theo ID (hữu ích cho PDF, email) ===
    public Map<Integer, String> loadTypeNames(Connection conn, Collection<Integer> typeIds) throws SQLException {
        Map<Integer, String> map = new HashMap<>();
        if (typeIds == null || typeIds.isEmpty()) {
            return map;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < typeIds.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        String sql = "SELECT TicketTypeID, TypeName FROM TicketTypes WHERE TicketTypeID IN (" + sb + ")";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Integer id : typeIds) {
                ps.setInt(idx++, id);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getInt("TicketTypeID"), rs.getString("TypeName"));
                }
            }
        }
        return map;
    }

    // === Đếm số vé trống còn lại của một loại ===
    public int countAvailableByType(Connection conn, int ticketTypeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE TicketTypeID = ? AND StatusID = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void markTicketAsSold(Connection conn, int ticketId) throws SQLException {
        String sql = "UPDATE Tickets SET StatusID = 4 WHERE TicketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            ps.executeUpdate();
        }
    }

// Đánh dấu nhiều vé cùng lúc
    public void markTicketsAsSold(Connection conn, List<Integer> ticketIds) throws SQLException {
        String sql = "UPDATE Tickets SET StatusID = 4 WHERE TicketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int id : ticketIds) {
                ps.setInt(1, id);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public List<Integer> pickTicketIds(Connection conn, int ticketTypeId, int quantity) throws SQLException {
        List<Integer> ticketIds = new ArrayList<>();

        String sql = """
        SELECT TOP (?) TicketID 
        FROM Tickets
        WHERE TicketTypeID = ? AND StatusID = 1
        ORDER BY TicketID
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, ticketTypeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ticketIds.add(rs.getInt("TicketID"));
                }
            }
        }

        return ticketIds;
    }

    public int getTypeIdByTicketId(Connection conn, int ticketId) throws SQLException {
        String sql = "SELECT TicketTypeID FROM Tickets WHERE TicketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("TicketTypeID");
                }
            }
        }
        return -1;
    }
    // === Lấy SerialNumber thật theo TicketID ===

    public String getSerialByTicketId(Connection conn, int ticketId) throws SQLException {
        String sql = "SELECT SerialNumber FROM Tickets WHERE TicketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("SerialNumber");
                }
            }
        }
        return null;
    }

    // === Lấy tên loại vé theo TicketTypeID ===
    public String getTicketTypeName(Connection conn, int ticketTypeId) throws SQLException {
        String sql = "SELECT TypeName FROM TicketTypes WHERE TicketTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("TypeName");
                }
            }
        }
        return null;
    }

    public boolean ticketTypeHasSeat(int ticketTypeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE TicketTypeID = ? AND SeatID IS NOT NULL";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int countAvailableByTypeAndSeat(int ticketTypeId, int seatId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE TicketTypeID = ? AND SeatID = ? AND StatusID = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketTypeId);
            ps.setInt(2, seatId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
