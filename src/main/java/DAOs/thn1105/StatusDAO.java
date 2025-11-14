/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.thn1105;

import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class StatusDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    public int getStatusIdByCode(String code) {
        String sql = "SELECT StatusID FROM Status WHERE Code = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("StatusID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // không tìm thấy
    }
    
    
    
    public static void main(String[] args) {
        StatusDAO dao = new StatusDAO();
        int id = dao.getStatusIdByCode("hasTicket");
        System.out.println("ID: " + id);
    }
}
