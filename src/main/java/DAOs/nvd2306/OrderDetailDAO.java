/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Models.nvd2306.OrderDetail;
import Utils.nvd2603.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
public class OrderDetailDAO extends DBContext {

    public void insertOrderDetail(OrderDetail d) throws Exception {
        String sql = "INSERT INTO OrderDetail (OrderID, TicketID, UnitPrice, Quantity, StatusID) VALUES (?, ?, ?, ?, 1)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, d.getOrderID());
            ps.setInt(2, d.getTicketID());
            ps.setDouble(3, d.getUnitPrice());
            ps.setInt(4, d.getQuantity());
            ps.executeUpdate();
        }
    }

    public void batchInsert(int orderId, List<OrderDetail> items) throws Exception {
        String sql = "INSERT INTO OrderDetail (OrderID, TicketID, UnitPrice, Quantity, StatusID) VALUES (?, ?, ?, ?, 1)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (OrderDetail d : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, d.getTicketID());
                ps.setDouble(3, d.getUnitPrice());
                ps.setInt(4, d.getQuantity());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
