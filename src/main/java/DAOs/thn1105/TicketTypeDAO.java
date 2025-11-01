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
                        rs.getBigDecimal("Price"),
                        rs.getInt("StatusID")
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
                        rs.getBigDecimal("Price"),
                        rs.getInt("StatusID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE A NEW TICKET TYPE
    public boolean create(TicketType t) {
        String insertSql = "INSERT INTO TicketTypes (EventID, ZoneID, TypeName, Price) VALUES (?, ?, ?, ?)";
        String updateZoneSql = "UPDATE Zones SET StatusID = 2 WHERE ZoneID = ?";

        try {
            conn.setAutoCommit(false); // bắt đầu transaction

            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                psInsert.setInt(1, t.getEventID());
                psInsert.setInt(2, t.getZoneID());
                psInsert.setString(3, t.getTypeName());
                psInsert.setBigDecimal(4, t.getPrice());
                psInsert.executeUpdate();
            }

            try (PreparedStatement psUpdate = conn.prepareStatement(updateZoneSql)) {
                psUpdate.setInt(1, t.getZoneID());
                psUpdate.executeUpdate();
            }

            conn.commit(); // commit cả hai câu lệnh
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // UPDATE A TICKET TYPE
    public boolean update(TicketType t) {
        String updateTicketSql = "UPDATE TicketTypes SET EventID=?, ZoneID=?, TypeName=?, Price=?, StatusID=? WHERE TicketTypeID=?";
        String updateZoneSql = "UPDATE Zones SET StatusID = 2 WHERE ZoneID = ?";

        try {
            conn.setAutoCommit(false); // bắt đầu transaction

            // ===== Cập nhật TicketType =====
            try (PreparedStatement ps = conn.prepareStatement(updateTicketSql)) {
                ps.setInt(1, t.getEventID());
                ps.setInt(2, t.getZoneID());
                ps.setString(3, t.getTypeName());
                ps.setBigDecimal(4, t.getPrice());
                ps.setInt(5, t.getStatusID());
                ps.setInt(6, t.getTicketTypeID());
                ps.executeUpdate();
            }

            // ===== Sau khi update ticket, cập nhật Zone.statusID =====
            try (PreparedStatement ps2 = conn.prepareStatement(updateZoneSql)) {
                ps2.setInt(1, t.getZoneID());
                ps2.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // SOFT DELETE A TICKET TYPE
    public boolean softDelete(int id) {
        String sql = "UPDATE TicketTypes SET StatusID=3 WHERE TicketTypeID=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                        rs.getBigDecimal("Price"),
                        rs.getInt("StatusID")
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
//          updateTicketType.setTicketTypeID(6);
//          updateTicketType.setEventID(3);
//          updateTicketType.setTypeName("AAAA");
//          updateTicketType.setZoneID(4);
//          updateTicketType.setPrice(BigDecimal.valueOf(10000));
//          updateTicketType.setStatusID(1);
//          
//          dao.update(updateTicketType);
//        dao.softDelete(6);
        List<TicketType> list = dao.getAllByEventID(1);
//        System.out.println("Cac loai ve cua su kien co ID = 11");
        for (TicketType t : list) {
            System.out.println("ID: " + t.getTicketTypeID());
            System.out.println("Loai ve thuoc su kien: " + eDAO.getById(t.getEventID()).getEventName());
            System.out.println("Loai ve thuoc zone: " + zDao.getById(t.getZoneID()).getZoneName());
            System.out.println("Ten loai ve: " + t.getTypeName());
            System.out.println("Gia cua ve loai " + t.getTypeName() + " la " + t.getPrice());
            System.out.println("StatusID: " + t.getStatusID());
        }
    }
}
