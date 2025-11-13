/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.Ticket;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class TicketDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    // GET ALL
    public List<Ticket> getAll() {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM Tickets";
        try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Ticket t = new Ticket(
                        rs.getInt("TicketID"),
                        rs.getInt("TicketTypeID"),
                        rs.getObject("SeatID") != null ? rs.getInt("SeatID") : null,
                        rs.getString("SerialNumber"),
                        rs.getInt("StatusID"),
                        rs.getTimestamp("IssuedAt")
                );
                list.add(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // GET ALL BY TICKET TYPE ID
    public List<Ticket> getAllByTicketTypeID(int ticketTypeID) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM Tickets WHERE TicketTypeID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ticketTypeID); // ✅ Set giá trị cho tham số
            try (ResultSet rs = st.executeQuery()) { // ✅ Thực thi truy vấn sau khi set parameter
                while (rs.next()) {
                    Ticket t = new Ticket(
                            rs.getInt("TicketID"),
                            rs.getInt("TicketTypeID"),
                            rs.getObject("SeatID") != null ? rs.getInt("SeatID") : null,
                            rs.getString("SerialNumber"),
                            rs.getInt("StatusID"),
                            rs.getTimestamp("IssuedAt")
                    );
                    list.add(t);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // GET BY ID
    public Ticket getById(int id) {
        String sql = "SELECT * FROM Tickets WHERE TicketID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                            rs.getInt("TicketID"),
                            rs.getInt("TicketTypeID"),
                            rs.getObject("SeatID") != null ? rs.getInt("SeatID") : null,
                            rs.getString("SerialNumber"),
                            rs.getInt("StatusID"),
                            rs.getTimestamp("IssuedAt")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE A NEW TICKET
    public boolean create(Ticket ticket) {
        String sql = "INSERT INTO Tickets (TicketTypeID, SeatID, SerialNumber, IssuedAt) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ticket.getTicketTypeID());
            if (ticket.getSeatID() != null) {
                st.setInt(2, ticket.getSeatID());
            } else {
                st.setNull(2, Types.INTEGER);
            }
            st.setString(3, ticket.getSerialNumber());
            st.setTimestamp(4, ticket.getIssuedAt());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE A TICKET
    public boolean update(Ticket ticket) {
        String sql = "UPDATE Tickets SET TicketTypeID = ?, SeatID = ?, SerialNumber = ?, StatusID = ?, IssuedAt = ? WHERE TicketID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ticket.getTicketTypeID());
            if (ticket.getSeatID() != null) {
                st.setInt(2, ticket.getSeatID());
            } else {
                st.setNull(2, Types.INTEGER);
            }
            st.setString(3, ticket.getSerialNumber());
            st.setInt(4, ticket.getStatusID());
            st.setTimestamp(5, ticket.getIssuedAt());
            st.setInt(6, ticket.getTicketID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // SOFT DELETE
    public boolean softDelete(int id) {
        String sql = "UPDATE Tickets SET StatusID = 3 WHERE TicketID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // CHECK IF TICKETS EXIST BY TICKET TYPE ID
    public boolean existsByTicketTypeID(int ticketTypeID) {
        String sql = "SELECT 1 FROM Tickets WHERE TicketTypeID = ? LIMIT 1";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, ticketTypeID);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next(); // Nếu có kết quả => true
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public TicketDAO() {
    }

    public static void main(String[] args) {
        TicketDAO dao = new TicketDAO();
        SeatDAO sdao = new SeatDAO();
        TicketTypeDAO tpdao = new TicketTypeDAO();
        ZoneDAO zdao = new ZoneDAO();

//        Ticket updateTicket = new Ticket();
//
//        updateTicket.setTicketID(10);
//        updateTicket.setTicketTypeID(4);
//        updateTicket.setSeatID(8);
//        updateTicket.setSerialNumber("sdfsdvsdvsd");
//        updateTicket.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()));
//        updateTicket.setStatusID(1);
//
//        dao.update(updateTicket);
//        dao.softDelete(10);
        List<Ticket> list = dao.getAllByTicketTypeID(1);

        for (Ticket t : list) {
            int typeID = t.getTicketTypeID();
            String typeName = tpdao.getByID(typeID).getTypeName();
            int seatID;
            String seatName = "Stand";
            if (t.getSeatID() != null) {
                seatID = t.getSeatID();
                seatName = sdao.getById(seatID).getRowLabel() + sdao.getById(seatID).getSeatNumber() + "";
            }

            int zoneID = tpdao.getByID(typeID).getZoneID();
            String zoneName = zdao.getById(zoneID).getZoneName();

            System.out.println("TID: " + t.getTicketID());
            System.out.println("Type: " + typeName);
            System.out.println("Zone: " + zoneName);
            System.out.println("Seat: " + seatName);
            System.out.println("Serial: " + t.getSerialNumber());
            System.out.println("Status: " + t.getStatusID());
            System.out.println("Release date: " + t.getIssuedAt());
            System.out.println("");
        }

//        Ticket t = dao.getById(2);
//        System.out.println("TID: " + t.getTicketID());
//        System.out.println("Type: " + tpdao.getByID(t.getTicketTypeID()).getTypeName());
//        System.out.println("Seat: " + sdao.getById(t.getSeatID()).getRowLabel() + sdao.getById(t.getSeatID()).getSeatNumber());
//        System.out.println("Serial: " + t.getSerialNumber());
//        System.out.println("Status: " + t.getStatusID());
//        System.out.println("Release date: " + t.getIssuedAt());
//        System.out.println("");
    }

}
