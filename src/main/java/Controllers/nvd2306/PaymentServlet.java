/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/pay"})
public class PaymentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1) Lấy dữ liệu từ form
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        if (userId == null) {
            response.sendError(401, "Bạn cần đăng nhập.");
            return;
        }

        String eventIdStr = request.getParameter("eventId");
        String selectionsJson = request.getParameter("selectionsJson");
        String totalAmountStr = request.getParameter("totalAmount");
        String fullName = request.getParameter("contactFullName");
        String phone = request.getParameter("contactPhone");
        String email = request.getParameter("contactEmail");

        if (eventIdStr == null || selectionsJson == null || totalAmountStr == null) {
            response.sendError(400, "Thiếu tham số bắt buộc.");
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);
        long clientTotal = Math.round(Double.parseDouble(totalAmountStr)); // số nguyên VND

        // 2) Parse selections: { "Vé VIP": {qty:1, price:500000}, ... }
        JSONObject selections = new JSONObject(selectionsJson);

        // 3) Chuẩn bị DAO & transaction
        try (Connection cn = Db.getConnection()) {
            cn.setAutoCommit(false);

            WalletDao walletDao = new WalletDao(cn);
            OrderDao orderDao = new OrderDao(cn);
            OrderDetailDao orderDetailDao = new OrderDetailDao(cn);
            TicketDao ticketDao = new TicketDao(cn);
            TransactionDao txnDao = new TransactionDao(cn);
            TicketTypeDao ticketTypeDao = new TicketTypeDao(cn);

            // 4) Kiểm tra lại số tiền & tồn kho (server-side)
            long serverTotal = 0L;
            // Map<ticketTypeId, qty>
            Map<Integer, Integer> needAllocate = new HashMap<>();

            for (String typeName : selections.keySet()) {
                JSONObject item = selections.getJSONObject(typeName);
                int qty = item.getInt("qty");
                long clientPrice = Math.round(item.getDouble("price"));

                if (qty <= 0) {
                    continue;
                }

                // Tìm ticket type theo event + tên
                TicketType tt = ticketTypeDao.findByEventAndName(eventId, typeName);
                if (tt == null) {
                    cn.rollback();
                    response.sendError(400, "Loại vé không hợp lệ: " + typeName);
                    return;
                }

                // Chống sửa giá phía client
                if (clientPrice != tt.getPrice()) {
                    cn.rollback();
                    response.sendError(400, "Giá vé đã thay đổi hoặc dữ liệu không hợp lệ.");
                    return;
                }

                // Kiểm tra tồn kho vé rời (bảng Tickets) còn đủ không
                int remain = ticketDao.countAvailableTickets(tt.getTicketTypeId());
                if (remain < qty) {
                    cn.rollback();
                    response.sendError(409, "Vé '" + typeName + "' chỉ còn " + remain + " chiếc.");
                    return;
                }

                serverTotal += (long) qty * tt.getPrice();
                needAllocate.put(tt.getTicketTypeId(), qty);
            }

            if (serverTotal <= 0) {
                cn.rollback();
                response.sendError(400, "Không có vé nào được chọn.");
                return;
            }

            if (serverTotal != clientTotal) {
                cn.rollback();
                response.sendError(400, "Tổng tiền không khớp. Vui lòng tải lại trang.");
                return;
            }

            // 5) Kiểm tra số dư ví
            long balance = walletDao.getBalance(userId);
            if (balance < serverTotal) {
                cn.rollback();
                response.sendError(402, "Số dư không đủ. Vui lòng nạp thêm.");
                return;
            }

            // 6) Tạo đơn (Orders)
            Order order = new Order();
            order.setUserId(userId);
            order.setContactFullName(fullName);
            order.setContactPhone(phone);
            order.setContactEmail(email);
            order.setTotalAmount(serverTotal);
            order.setStatusId(1); // 1 = CREATED/COMPLETED tuỳ bạn định nghĩa
            int orderId = orderDao.create(order);

            // 7) Phân bổ vé: pick TOP qty ticketId (AVAILABLE=1) rồi update -> SOLD(=2)
            //    & chèn vào OrderDetails theo TỪNG TICKET hoặc theo TYPE (tuỳ thiết kế).
            //    Ở đây: gộp theo TYPE (giữ UnitPrice/Quantity).
            for (Map.Entry<Integer, Integer> e : needAllocate.entrySet()) {
                int ticketTypeId = e.getKey();
                int qty = e.getValue();

                // Lấy danh sách ticketId khả dụng (TOP qty) & đánh dấu đã bán
                List<Integer> allocated = ticketDao.allocateTickets(ticketTypeId, qty);
                if (allocated.size() != qty) {
                    cn.rollback();
                    response.sendError(409, "Số vé khả dụng bị thay đổi. Vui lòng thử lại.");
                    return;
                }

                long unitPrice = ticketTypeDao.getPrice(ticketTypeId);
                OrderDetail od = new OrderDetail();
                od.setOrderId(orderId);
                od.setTicketTypeId(ticketTypeId);
                od.setUnitPrice(unitPrice);
                od.setQuantity(qty);
                od.setStatusId(1);
                orderDetailDao.insert(od);

                // (tuỳ hệ thống) nếu cần ràng buộc đến từng ticketId → insert bảng mapping khác.
                // VD: orderDetailDao.insertTicketLinks(odId, allocated);
            }

            // 8) Trừ tiền ví
            walletDao.decrease(userId, serverTotal);

            // 9) Ghi lịch sử giao dịch
            int walletId = walletDao.getWalletIdByUser(userId);
            Transaction txn = new Transaction();
            txn.setWalletId(walletId);
            txn.setTransactionTypeId(2); // 2 = Thanh toán đơn hàng
            txn.setAmount(serverTotal);
            txn.setStatusId(1);
            txn.setOrderId(orderId);
            txnDao.insert(txn);

            // 10) Commit
            cn.commit();

            // 11) Chuyển tới trang success
            request.setAttribute("orderId", orderId);
            request.setAttribute("paid", serverTotal);
            request.setAttribute("remaining", balance - serverTotal);
            request.getRequestDispatcher("/payment-success.jsp").forward(req, resp);

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendError(500, "Lỗi hệ thống khi xử lý thanh toán.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
