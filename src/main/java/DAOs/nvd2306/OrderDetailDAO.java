/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.OrderDetail;
import Models.nvd2306.TicketItem;
import Utils.singleton.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class OrderDetailDAO {

    private final Connection conn;

    public OrderDetailDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

    // === Chèn 1 chi tiết đơn hàng (dùng TicketID theo DB thật) ===
//    public void insertOrderDetail(Connection conn, OrderDetail detail) throws SQLException {
//
//        String sql = """
//            INSERT INTO OrderDetails (OrderID, TicketID, UnitPrice, StatusID)
//            VALUES (?, ?, ?, 1);
//        """;
//
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, detail.getOrderID());
//            ps.setInt(2, detail.getTicketTypeID());  // ticketTypeId = ticketId
//            ps.setBigDecimal(3, detail.getUnitPrice());
//            ps.executeUpdate();
//        }
//    }
    public void insertOrderDetail(Connection conn, OrderDetail detail) throws SQLException {

        String sql = """
        INSERT INTO OrderDetails (OrderID, TicketID, UnitPrice, StatusID)
        VALUES (?, ?, ?, 1);
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detail.getOrderID());
            ps.setInt(2, detail.getTicketID());  // <-- ticketID thật
            ps.setBigDecimal(3, detail.getUnitPrice());
            ps.executeUpdate();
        }
    }

    public List<Integer> getTicketIdsByOrderId(Connection conn, int orderId) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT TicketID FROM OrderDetails WHERE OrderID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("TicketID"));
                }
            }
        }
        return ids;
    }

    public int getOrderIdByTicketId(Connection conn, int ticketId) throws SQLException {
        String sql = "SELECT OrderID FROM OrderDetails WHERE TicketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

}
