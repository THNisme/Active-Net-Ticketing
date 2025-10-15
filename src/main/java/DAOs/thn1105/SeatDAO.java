/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.Seat;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class SeatDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    //GET ALL
    public List<Seat> getAll() {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM Seats";
        try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Seat s = new Seat(
                        rs.getInt("SeatID"),
                        rs.getInt("ZoneID"),
                        rs.getString("RowLabel"),
                        rs.getInt("SeatNumber"),
                        rs.getInt("StatusID")
                );
                list.add(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //GET BY ID
    public Seat getById(int id) {
        String sql = "SELECT * FROM Seats WHERE SeatID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Seat(
                            rs.getInt("SeatID"),
                            rs.getInt("ZoneID"),
                            rs.getString("RowLabel"),
                            rs.getInt("SeatNumber"),
                            rs.getInt("StatusID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE A NEW SEAT
    public boolean create(Seat seat) {
        String sql = "INSERT INTO Seats (ZoneID, RowLabel, SeatNumber) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, seat.getZoneID());
            st.setString(2, seat.getRowLabel());
            st.setInt(3, seat.getSeatNumber());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE A SEAT
    public boolean update(Seat seat) {
        String sql = "UPDATE Seats SET ZoneID = ?, RowLabel = ?, SeatNumber = ?, StatusID = ? WHERE SeatID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, seat.getZoneID());
            st.setString(2, seat.getRowLabel());
            st.setInt(3, seat.getSeatNumber());
            st.setInt(4, seat.getStatusID());
            st.setInt(5, seat.getSeatID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // SOTF DELETE
    public boolean softDelete(int seatID) {
        String sql = "UPDATE Seats SET StatusID = 3 WHERE SeatID = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, seatID);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== DELETE (PHYSICAL) =====
    public boolean delete(int id) {
        String deleteTickets = "DELETE FROM Tickets WHERE SeatID = ?";
        String deleteSeat = "DELETE FROM Seats WHERE SeatID = ?";

        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa các Ticket liên quan đến seat bị xóa
            try (PreparedStatement st1 = conn.prepareStatement(deleteTickets)) {
                st1.setInt(1, id);
                st1.executeUpdate();
            }

            // Xóa ghế
            int affectedRows = 0;
            try (PreparedStatement st2 = conn.prepareStatement(deleteSeat)) {
                st2.setInt(1, id);
                affectedRows = st2.executeUpdate();
            }

            conn.commit(); // commit nếu cả 2 bước thành công
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // rollback nếu lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SeatDAO dao = new SeatDAO();
        ZoneDAO zdao = new ZoneDAO();

        dao.softDelete(17);

        List<Seat> list = dao.getAll();

        for (Seat s : list) {
            System.out.println("SID: " + s.getSeatID());
            System.out.println("Zone: " + zdao.getById(s.getZoneID()).getZoneName());
            System.out.println("ROW: " + s.getRowLabel());
            System.out.println("Number: " + s.getSeatNumber());
            System.out.println("Status: " + s.getStatusID());
            System.out.println("");
        }
//        Seat s = dao.getById(9);
//        System.out.println("SID: " + s.getSeatID());
//        System.out.println("Zone: " + zdao.getById(s.getZoneID()).getZoneName());
//        System.out.println("ROW: " + s.getRowLabel());
//        System.out.println("Number: " + s.getSeatNumber());
//        System.out.println("Status: " + s.getStatusID());
//        System.out.println("");
//          Seat newSeat = new Seat();
//          newSeat.setZoneID(3);
//          newSeat.setRowLabel("A");
//          newSeat.setSeatNumber(1);
//          
//          dao.create(newSeat);

//        Seat updateSeat = new Seat();
//        updateSeat.setSeatID(18);
//        updateSeat.setZoneID(4);
//        updateSeat.setRowLabel("B");
//        updateSeat.setSeatNumber(3);
//        updateSeat.setStatusID(2);
//
//        dao.update(updateSeat);
//
//        Seat s = dao.getById(18);
//        System.out.println("SID: " + s.getSeatID());
//        System.out.println("Zone: " + zdao.getById(s.getZoneID()).getZoneName());
//        System.out.println("ROW: " + s.getRowLabel());
//        System.out.println("Number: " + s.getSeatNumber());
//        System.out.println("Status: " + s.getStatusID());
//        System.out.println("");
    }
}
