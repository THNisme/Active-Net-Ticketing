/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.OrderDetail;
import Models.nvd2306.TicketItem;
import Utils.nvd2603.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class OrderDetailDAO extends DBContext {

    // === Chèn 1 chi tiết đơn hàng (dùng TicketID theo DB thật) ===
    public void insertOrderDetail(Connection conn, OrderDetail detail) throws SQLException {

        String sql = """
            INSERT INTO OrderDetails (OrderID, TicketID, UnitPrice, StatusID)
            VALUES (?, ?, ?, 1);
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detail.getOrderID());
            ps.setInt(2, detail.getTicketTypeID());  // ticketTypeId = ticketId
            ps.setBigDecimal(3, detail.getUnitPrice());
            ps.executeUpdate();
        }
    }

    // === Chèn hàng loạt (nếu cần thêm nhiều vé 1 lúc) ===
    public void batchInsert(Connection conn, int orderId, List<TicketItem> items) throws SQLException {
        String sql = """
            INSERT INTO OrderDetails (OrderID, TicketTypeID, UnitPrice, Quantity, StatusID)
            VALUES (?, ?, ?, ?, 1);
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (TicketItem item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getTicketTypeId());
                ps.setBigDecimal(3, item.getPrice());
                ps.setInt(4, item.getQuantity());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
