/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Models.thn1105.Place;
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
public class PlaceDAO extends DBContext {

    // GET ALL PLACE
    public List<Place> getAll() {
        List<Place> list = new ArrayList<>();
        String sql = "SELECT * FROM Places";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Place(
                        rs.getInt("PlaceID"),
                        rs.getString("PlaceName"),
                        rs.getString("Address"),
                        rs.getString("SeatMapURL"),
                        rs.getString("Description"),
                        rs.getInt("StatusID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        PlaceDAO dao = new PlaceDAO();

        List<Place> list = dao.getAll();

        for (Place p : list) {
            System.out.println("ID: " + p.getPlaceID());
            System.out.println("Name: " + p.getPlaceName());
            System.out.println("Address: " + p.getAddress());
            System.out.println("ImgURL: " + p.getSeatMapURL());
            System.out.println("Description: " + p.getDescription());
            System.out.println("Status: " + p.getStatusID());

        }
    }
}
