/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.Order;
import Models.nvd2306.TicketItem;
import Utils.nvd2603.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author NguyenDuc
 */
public class OrderDAO extends DBContext {

    /**
     * Insert Orders và trả về OrderID (identity)
     */
    public int insertOrder(Connection conn, Order order) throws SQLException {
        String sql = """
            INSERT INTO Orders(UserID, ContactFullname, ContactPhone, ContactEmail, TotalAmount, OrderDate, StatusID)
            VALUES (?, ?, ?, ?, ?, GETDATE(), ?);
        """;
        try (PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, order.getUserID());                 // ✅ đúng getter
            stm.setString(2, order.getContactFullname());
            stm.setString(3, order.getContactPhone());
            stm.setString(4, order.getContactEmail());
            stm.setBigDecimal(5, order.getTotalAmount());     // ✅ BigDecimal từ model
            stm.setInt(6, order.getStatusID());               // ✅ đúng getter
            stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * (Dùng khi cần) Insert một dòng OrderDetails từ TicketItem trong cùng
     * transaction
     */
    public void insertOrderDetail(Connection conn, int orderId, TicketItem ticket) throws SQLException {
        String sql = """
            INSERT INTO OrderDetails (OrderID, TicketTypeID, UnitPrice, Quantity, StatusID)
            VALUES (?, ?, ?, ?, 1);
        """;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, orderId);
            stm.setInt(2, ticket.getTicketTypeId());
            stm.setBigDecimal(3, ticket.getPrice());          // ✅ BigDecimal từ bridge getter
            stm.setInt(4, ticket.getQuantity());
            stm.executeUpdate();
        }
    }
}
