package DAOs.ttk2008;

import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatusDAO {
    private Connection conn = DBContext.getInstance().getConnection();

    public Integer getStatusIdByCode(String code) {
        String sql = "SELECT StatusID FROM Status WHERE Code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("StatusID");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
