package DAOs.thn1105;


import DAOs.thn1105.EventDAO;
import DAOs.thn1105.ZoneDAO;
import Models.thn1105.TicketType;
import Utils.singleton.DBContext;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class TicketTypeDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    // GET ALL TICKET TYPE
    public List<TicketType> getAll() {
        List<TicketType> list = new ArrayList<>();
        String sql = "SELECT * FROM TicketTypes";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TicketType t = new TicketType(
                        rs.getInt("TicketTypeID"),
                        rs.getInt("EventID"),
                        rs.getInt("ZoneID"),
                        rs.getString("TypeName"),
                        rs.getBigDecimal("Price")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET BY ID
    public TicketType getByID(int id) {
        String sql = "SELECT * FROM TicketTypes WHERE TicketTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TicketType(
                        rs.getInt("TicketTypeID"),
                        rs.getInt("EventID"),
                        rs.getInt("ZoneID"),
                        rs.getString("TypeName"),
                        rs.getBigDecimal("Price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE A NEW TICKET TYPE
    public boolean create(TicketType t) {
        String sql = "INSERT INTO TicketTypes (EventID, ZoneID, TypeName, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getEventID());
            ps.setInt(2, t.getZoneID());
            ps.setString(3, t.getTypeName());
            ps.setBigDecimal(4, t.getPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE A TICKET TYPE
    public boolean update(TicketType t) {
        String sql = "UPDATE TicketTypes SET EventID=?, ZoneID=?, TypeName=?, Price=? WHERE TicketTypeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getEventID());
            ps.setInt(2, t.getZoneID());
            ps.setString(3, t.getTypeName());
            ps.setBigDecimal(4, t.getPrice());
            ps.setInt(5, t.getTicketTypeID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELELTE TICKET -> TICKET TYPE
    public boolean delete(int id) {
        String deleteTickets = "DELETE FROM Tickets WHERE TicketTypeID = ?";
        String deleteTicketType = "DELETE FROM TicketTypes WHERE TicketTypeID = ?";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(deleteTickets); PreparedStatement ps2 = conn.prepareStatement(deleteTicketType)) {

                ps1.setInt(1, id);
                ps1.executeUpdate(); // Xóa tất cả ticket thuộc ticket type đó

                ps2.setInt(1, id);
                int rows = ps2.executeUpdate(); // Rồi mới xóa ticket type

                conn.commit();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
        return false;
    }

    // thêm optional: lấy tất cả loại vé của 1 event
    public List<TicketType> getAllByEventID(int eventID) {
        List<TicketType> list = new ArrayList<>();
        String sql = "SELECT * FROM TicketTypes WHERE EventID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TicketType t = new TicketType(
                        rs.getInt("TicketTypeID"),
                        rs.getInt("EventID"),
                        rs.getInt("ZoneID"),
                        rs.getString("TypeName"),
                        rs.getBigDecimal("Price")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {

        TicketTypeDAO dao = new TicketTypeDAO();
        ZoneDAO zDao = new ZoneDAO();
        EventDAO eDAO = new EventDAO();
//        List<TicketType> list = dao.getAll();

//        List<TicketType> list = dao.getAllByEventID(3);
//
//        System.out.println("Cac loai ve cua su kien co ID = 3");
//        for (TicketType t : list) {
//            System.out.println("ID: " + t.getTicketTypeID());
//            System.out.println("Loai ve thuoc su kien: " + eDAO.getById(t.getEventID()).getEventName());
//            System.out.println("Loai ve thuoc zone: " + zDao.getById(t.getZoneID()).getZoneName());
//            System.out.println("Ten loai ve: " + t.getTypeName());
//            System.out.println("Gia cua ve loai " + t.getTypeName() + " la " + t.getPrice());
//        }
//          TicketType updateTicketType = new TicketType();
//          
//          updateTicketType.setTicketTypeID(11);
//          updateTicketType.setEventID(11);
//          updateTicketType.setTypeName("FAC Member");
//          updateTicketType.setZoneID(2);
//          updateTicketType.setPrice(BigDecimal.valueOf(499000));
//          
//          dao.update(updateTicketType);


        dao.delete(4);
        List<TicketType> list = dao.getAll();

//        System.out.println("Cac loai ve cua su kien co ID = 11");
        for (TicketType t : list) {
            System.out.println("ID: " + t.getTicketTypeID());
            System.out.println("Loai ve thuoc su kien: " + eDAO.getById(t.getEventID()).getEventName());
            System.out.println("Loai ve thuoc zone: " + zDao.getById(t.getZoneID()).getZoneName());
            System.out.println("Ten loai ve: " + t.getTypeName());
            System.out.println("Gia cua ve loai " + t.getTypeName() + " la " + t.getPrice());
        }
    }
}
