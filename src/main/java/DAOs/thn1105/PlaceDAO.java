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
import static java.sql.Types.NULL;
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

    //GET BY ID
    public Place getById(int id) {
        String sql = "SELECT * FROM Places WHERE PlaceID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Place(
                        rs.getInt("PlaceID"),
                        rs.getString("PlaceName"),
                        rs.getString("Address"),
                        rs.getString("SeatMapURL"),
                        rs.getString("Description"),
                        rs.getInt("StatusID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CREATE A PLACE
    public boolean create(Place p) {
        String sql = "INSERT INTO Places (PlaceName, Address, SeatMapURL, Description, StatusID) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getPlaceName());
            ps.setString(2, p.getAddress());
            ps.setString(3, p.getSeatMapURL());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getStatusID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        PlaceDAO dao = new PlaceDAO();

//        List<Place> list = dao.getAll();
//
//        for (Place p : list) {
//            System.out.println("ID: " + p.getPlaceID());
//            System.out.println("Name: " + p.getPlaceName());
//            System.out.println("Address: " + p.getAddress());
//            System.out.println("ImgURL: " + p.getSeatMapURL());
//            System.out.println("Description: " + p.getDescription());
//            System.out.println("Status: " + p.getStatusID());
//
//        }
//        Place p = dao.getById(2);
//
//        System.out.println("ID: " + p.getPlaceID());
//        System.out.println("Name: " + p.getPlaceName());
//        System.out.println("Address: " + p.getAddress());
//        System.out.println("ImgURL: " + p.getSeatMapURL());
//        System.out.println("Description: " + p.getDescription());
//        System.out.println("Status: " + p.getStatusID());
//    }


//        Place newP = new Place();
//        
//        newP.setPlaceName("G404");
//        newP.setAddress("FTPU");
//        newP.setSeatMapURL("place/img/seatmap/sm4.jpg");
//        newP.setDescription("Phong hoc G404");
//        newP.setStatusID(1);
//        
//        dao.create(newP);
//
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
