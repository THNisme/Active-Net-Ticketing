/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

import Utils.singleton.DBContext;
import Models.nvd2306.Order;
import Models.nvd2306.TicketItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author NguyenDuc
 */
public class OrderDAO {

    private Connection conn;

    public OrderDAO() {
        this.conn = DBContext.getInstance().getConnection();
    }

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
    // ======================
// Lấy đơn theo StatusID
// ======================

    public List<Order> getOrdersByStatus(int statusId) {
        List<Order> list = new ArrayList<>();
        String sql = """
        SELECT * FROM Orders
        WHERE StatusID = ?
        ORDER BY OrderDate DESC
    """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, statusId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderID(rs.getInt("OrderID"));
                o.setUserID(rs.getInt("UserID"));
                o.setContactFullname(rs.getString("ContactFullname"));
                o.setContactEmail(rs.getString("ContactEmail"));
                o.setContactPhone(rs.getString("ContactPhone"));
                o.setOrderDate(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setStatusID(rs.getInt("StatusID"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

// =============================
// Lấy danh sách đơn Pending Staff
// =============================
    public List<Order> getPendingOrdersForStaff() {
        return getOrdersByStatus(11); // PENDING_STAFF
    }

// ======================
// Lấy đơn theo OrderID
// ======================
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setOrderID(rs.getInt("OrderID"));
                o.setUserID(rs.getInt("UserID"));
                o.setContactFullname(rs.getString("ContactFullname"));
                o.setContactEmail(rs.getString("ContactEmail"));
                o.setContactPhone(rs.getString("ContactPhone"));
                o.setOrderDate(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setStatusID(rs.getInt("StatusID"));
                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

// =============================
// Cập nhật trạng thái đơn
// =============================
    public boolean updateOrderStatus(int orderId, int newStatus) {
        String sql = "UPDATE Orders SET StatusID = ? WHERE OrderID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// ===============================
// Kiểm tra đơn này thuộc User nào
// ===============================
    public boolean isOrderOwnedByUser(int orderId, int userId) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ? AND UserID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateStatus(Connection conn, int orderId, int statusId) throws SQLException {
        String sql = "UPDATE Orders SET StatusID = ? WHERE OrderID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, statusId);
        ps.setInt(2, orderId);
        ps.executeUpdate();
    }

    public List<Order> getOrdersByStatusList(List<Integer> statuses) {
        List<Order> list = new ArrayList<>();
        String inQuery = statuses.stream()
                .map(s -> "?")
                .collect(Collectors.joining(","));

        String sql = "SELECT * FROM Orders WHERE StatusID IN (" + inQuery + ") ORDER BY OrderDate DESC";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < statuses.size(); i++) {
                ps.setInt(i + 1, statuses.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = extractOrder(rs); // nếu bạn có hàm này
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderID(rs.getInt("OrderID"));
        o.setUserID(rs.getInt("UserID"));
        o.setContactFullname(rs.getString("ContactFullname"));
        o.setContactEmail(rs.getString("ContactEmail"));
        o.setContactPhone(rs.getString("ContactPhone"));
        o.setOrderDate(rs.getTimestamp("OrderDate"));
        o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
        o.setStatusID(rs.getInt("StatusID"));
        return o;
    }
}
