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

    public int createOrder(Order order) throws SQLException {
        String sql = """
            INSERT INTO Orders(UserID, ContactFullname, ContactPhone, ContactEmail, TotalAmount, OrderDate, StatusID)
            VALUES (?, ?, ?, ?, ?, GETDATE(), ?);
        """;
        PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setInt(1, order.getUserId());
        stm.setString(2, order.getContactFullname());
        stm.setString(3, order.getContactPhone());
        stm.setString(4, order.getContactEmail());
        stm.setDouble(5, order.getTotalAmount());
        stm.setInt(6, order.getStatusId());
        stm.executeUpdate();

        ResultSet rs = stm.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void insertOrderDetail(int orderId, TicketItem ticket) throws SQLException {
        String sql = """
            INSERT INTO OrderDetails(OrderID, TicketID, UnitPrice, Quantity, StatusID)
            VALUES (?, ?, ?, ?, 1);
        """;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, orderId);
        stm.setInt(2, ticket.getTicketId());
        stm.setDouble(3, ticket.getPrice());
        stm.setInt(4, ticket.getQty());
        stm.executeUpdate();
    }
}
