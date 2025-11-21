package DAOs.ttk2008;

import Models.ttk2008.DepositPromotion;
import Utils.singleton.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class DepositPromotionDAO {

    private Connection conn = DBContext.getInstance().getConnection();

    // Lấy tất cả promotion (admin view, bao gồm ACTIVE và INACTIVE)
    public List<DepositPromotion> getAll() {
        List<DepositPromotion> list = new ArrayList<>();
        String sql = "SELECT dp.*, s.Code AS StatusCode, s.Description AS StatusName "
                + "FROM DepositPromotions dp "
                + "LEFT JOIN Status s ON dp.StatusID = s.StatusID "
                + "WHERE s.StatusID <> 3  "
                + "ORDER BY dp.PromotionID DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapPromotion(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// Lấy promotion đang active (cho deposit flow)
    public List<DepositPromotion> getActivePromotions() {
        List<DepositPromotion> list = new ArrayList<>();
        String sql = "SELECT dp.*, s.Code AS StatusCode, s.Description AS StatusName "
                + "FROM DepositPromotions dp "
                + "JOIN Status s ON dp.StatusID = s.StatusID "
                + "WHERE s.Code = 'active' " // chỉ lấy active
                + "ORDER BY dp.PromotionID DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapPromotion(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy promotion theo ID
    public DepositPromotion getById(int id) {
        String sql = "SELECT dp.*, s.Code AS StatusCode, s.Description AS StatusName "
                + "FROM DepositPromotions dp "
                + "JOIN Status s ON dp.StatusID = s.StatusID "
                + "WHERE dp.PromotionID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPromotion(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tạo promotion mới
    public boolean create(DepositPromotion p) {
        String sql = "INSERT INTO DepositPromotions "
                + "(MinAmount, MaxAmount, DiscountPercent, StartDate, EndDate, StatusID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, p.getMinAmount());
            if (p.getMaxAmount() != null) {
                ps.setBigDecimal(2, p.getMaxAmount());
            } else {
                ps.setNull(2, Types.DECIMAL);
            }
            ps.setBigDecimal(3, p.getDiscountPercent());
            ps.setTimestamp(4, p.getStartDate());
            ps.setTimestamp(5, p.getEndDate());
            ps.setInt(6, p.getStatusID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật promotion
    public boolean update(DepositPromotion p) {
        String sql = "UPDATE DepositPromotions SET MinAmount=?, MaxAmount=?, DiscountPercent=?, StartDate=?, EndDate=?, StatusID=? WHERE PromotionID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, p.getMinAmount());
            if (p.getMaxAmount() != null) {
                ps.setBigDecimal(2, p.getMaxAmount());
            } else {
                ps.setNull(2, Types.DECIMAL);
            }
            ps.setBigDecimal(3, p.getDiscountPercent());
            ps.setTimestamp(4, p.getStartDate());
            ps.setTimestamp(5, p.getEndDate());
            ps.setInt(6, p.getStatusID());
            ps.setInt(7, p.getPromotionID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Soft delete: chỉ chuyển trạng thái sang INACTIVE
    public boolean softDelete(int promotionId) {
        String sql = "UPDATE DepositPromotions SET StatusID = 3 WHERE PromotionID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promotionId); // ví dụ DELETED = 3

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Map ResultSet -> DepositPromotion
    private DepositPromotion mapPromotion(ResultSet rs) throws SQLException {
        DepositPromotion p = new DepositPromotion();
        p.setPromotionID(rs.getInt("PromotionID"));
        BigDecimal min = rs.getBigDecimal("MinAmount");
        p.setMinAmount(min != null ? min : BigDecimal.ZERO);
        BigDecimal max = rs.getBigDecimal("MaxAmount");
        p.setMaxAmount(max);
        BigDecimal disc = rs.getBigDecimal("DiscountPercent");
        p.setDiscountPercent(disc != null ? disc : BigDecimal.ZERO);
        p.setStartDate(rs.getTimestamp("StartDate"));
        p.setEndDate(rs.getTimestamp("EndDate"));
        p.setStatusID(rs.getInt("StatusID"));
        try {
            p.setStatusCode(rs.getString("StatusCode"));
        } catch (Exception ex) {
            p.setStatusCode(null);
        }
        try {
            p.setStatusName(rs.getString("StatusName"));
        } catch (Exception ex) {
            p.setStatusName(null);
        }
        return p;
    }
}
