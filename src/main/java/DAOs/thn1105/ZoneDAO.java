/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.Zone;
import Utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class ZoneDAO extends DBContext {

    // GET ALL
    public List<Zone> getAll() {
        List<Zone> list = new ArrayList<>();
        String sql = "SELECT * FROM Zones";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Zone(
                        rs.getInt("ZoneID"),
                        rs.getInt("PlaceID"),
                        rs.getString("ZoneName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET ALL ZONE OF A PLACE
    public List<Zone> getAllZoneOfPlace(int placeID) {
        List<Zone> list = new ArrayList<>();
        String sql = "SELECT * FROM Zones WHERE PlaceID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, placeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Zone(
                        rs.getInt("ZoneID"),
                        rs.getInt("PlaceID"),
                        rs.getString("ZoneName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //GET BY ID
    public Zone getById(int id) {
        String sql = "SELECT * FROM Zones WHERE ZoneID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Zone(
                        rs.getInt("ZoneID"),
                        rs.getInt("PlaceID"),
                        rs.getString("ZoneName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE A ZONE
    public boolean create(Zone z) {
        String sql = "INSERT INTO Zones (PlaceID, ZoneName) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, z.getPlaceID());
            ps.setString(2, z.getZoneName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPADTE A ZONE
    public boolean update(Zone z) {
        String sql = "UPDATE Zones SET PlaceID=?, ZoneName=? WHERE ZoneID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, z.getPlaceID());
            ps.setString(2, z.getZoneName());
            ps.setInt(3, z.getZoneID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //DELETE A ZONE
    public boolean delete(int id) {
        String sql = "DELETE FROM Zones WHERE ZoneID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        ZoneDAO dao = new ZoneDAO();

//        List<Zone> list = dao.getAllZoneOfPlace(10);
//
//        for (Zone z : list) {
//            System.out.println("ID: " + z.getZoneID());
//            System.out.println("Zone Name: " + z.getZoneName());
//            System.out.println("Zone của place (ID): " + z.getPlaceID());
//        }
//        Zone z = dao.getById(5);
//        System.out.println("ID: " + z.getZoneID());
//        System.out.println("Zone Name: " + z.getZoneName());
//        System.out.println("Zone của place (ID): " + z.getPlaceID());
//        Zone newZ = new Zone();
//        
//        newZ.setPlaceID(10);
//        newZ.setZoneName("Super");
//        
//        dao.create(newZ);
//        
//        List<Zone> list = dao.getAllZoneOfPlace(10);
//
//        for (Zone z : list) {
//            System.out.println("ID: " + z.getZoneID());
//            System.out.println("Zone Name: " + z.getZoneName());
//            System.out.println("Zone của place (ID): " + z.getPlaceID());
//        }


//        Zone updateZ = new Zone();
//        
//        updateZ.setZoneID(7);
//        updateZ.setPlaceID(10);
//        updateZ.setZoneName("Super-V3");
//        
//        dao.update(updateZ);

        dao.delete(6);
        
        List<Zone> list = dao.getAllZoneOfPlace(10);

        for (Zone z : list) {
            System.out.println("ID: " + z.getZoneID());
            System.out.println("Zone Name: " + z.getZoneName());
            System.out.println("Zone của place (ID): " + z.getPlaceID());
        }
    }
}
