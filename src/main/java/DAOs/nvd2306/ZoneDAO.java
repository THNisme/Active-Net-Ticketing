/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Zone;
import Utils.singleton.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class ZoneDAO {

    private final Connection conn;

    public ZoneDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    public List<Zone> getZonesByPlace(int placeId) {
        List<Zone> list = new ArrayList<>();
        String sql = "SELECT * FROM Zones WHERE PlaceID = ? AND StatusID = 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, placeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Zone z = new Zone();
                z.setZoneID(rs.getInt("ZoneID"));
                z.setPlaceID(rs.getInt("PlaceID"));
                z.setZoneName(rs.getString("ZoneName"));
                list.add(z);
            }
        } catch (Exception e) {
            System.out.println("ZoneDAO error: " + e.getMessage());
        }
        return list;
    }
}
