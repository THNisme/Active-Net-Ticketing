/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.OrderDAO;
import DAOs.nvd2306.OrderDetailDAO;
import DAOs.nvd2306.TicketDAO;
import DAOs.nvd2306.TransactionDAO;
import DAOs.nvd2306.WalletDao;
import Models.nvd2306.Order;
import Models.nvd2306.OrderDetail;
import Models.nvd2306.TicketItem;
import Models.nvd2306.Transaction;
import Models.nvd2306.User;
import Models.nvd2306.Wallet;
import Utils.singleton.DBContext;

import Utils.nvd2603.MailService;
import Utils.nvd2603.TicketPDFGenerator;
import Utils.original.DBContextOrigin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/payments"})
public class PaymentServlet extends HttpServlet {

    private final WalletDao walletDao = new WalletDao();
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

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
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        // ===== CHỐT 1 LẦN THANH TOÁN =====
        String formToken = request.getParameter("paymentToken");
        String sessionToken = (session != null) ? (String) session.getAttribute("paymentToken") : null;

        if (sessionToken == null || formToken == null || !sessionToken.equals(formToken)) {
            String eventId = request.getParameter("eventId");

            if (eventId != null && !eventId.isBlank()) {
                // có eventId trong form → quay lại đúng sự kiện đó
                response.sendRedirect("event-detail?id=" + eventId);
            } else {
                // không có eventId → cho về trang danh sách sự kiện
                response.sendRedirect("events"); // hoặc "home", tùy bạn
            }
            return;
        }

        // xóa token ngay để form này không dùng lại được
        session.removeAttribute("paymentToken");
        // ==== Nhận dữ liệu từ form ====
        String eventIdStr = request.getParameter("eventId");
        String eventName = request.getParameter("eventName");
        String placeName = request.getParameter("placeName");
        String totalAmountStr = request.getParameter("totalAmount");
        String selectionsJson = request.getParameter("selectionsJson");
        String contactFullname = request.getParameter("fullName");
        String contactPhone = request.getParameter("phone");
        String contactEmail = nvl(request.getParameter("email"), user.getContactEmail());

        int eventId = parseIntSafe(eventIdStr, 0);
        BigDecimal totalAmount = parseMoney(totalAmountStr);

        Type listType = new TypeToken<List<TicketItem>>() {
        }.getType();
        List<TicketItem> items = new Gson().fromJson(selectionsJson, listType);

        // ==== Kiểm tra ví ====
        Wallet wallet;
        try {
            wallet = walletDao.getWalletByUserId(user.getUserID());
        } catch (Exception e) {
            forwardFail(request, response, "Không thể truy xuất ví người dùng.");
            return;
        }

        if (wallet == null) {
            forwardFail(request, response, "Ví không tồn tại.");
            return;
        }

        if (totalAmount.compareTo(BigDecimal.ZERO) > 0
                && wallet.getBalance().compareTo(totalAmount) < 0) {
            // Gán thêm 2 attribute cho JSP
            request.setAttribute("currentBalance", wallet.getBalance());
            request.setAttribute("requiredAmount", totalAmount);

            // Thông báo lỗi
            request.setAttribute("message", "Số dư ví không đủ để thanh toán.");
            request.getRequestDispatcher("payment-fail.jsp").forward(request, response);
            return;
        }

        // ==== Bắt đầu thanh toán ====
        DBContextOrigin db = new DBContextOrigin();
        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            // 1️⃣ Tạo đơn hàng
            Order order = new Order();
            order.setUserID(user.getUserID());
            order.setContactFullname(contactFullname);
            order.setContactEmail(contactEmail);
            order.setContactPhone(contactPhone);
            order.setOrderDate(Timestamp.from(Instant.now()));
            order.setTotalAmount(totalAmount);
            order.setStatusID(1);

            int orderId = orderDAO.insertOrder(conn, order);
            TicketDAO ticketDAO = new TicketDAO();

            for (TicketItem item : items) {

                int ticketTypeId = item.getTicketTypeId();
                int quantity = item.getQuantity();

                // 🟢 1) Lấy đúng số lượng TicketID thật từ DB
                List<Integer> pickedTicketIds = ticketDAO.pickTicketIds(conn, ticketTypeId, quantity);

                if (pickedTicketIds.size() < quantity) {
                    conn.rollback();
                    forwardFail(request, response, "Không đủ vé để hoàn tất đơn hàng.");
                    return;
                }

                // 🟢 2) Lưu OrderDetail với TỪNG TicketID thật
                for (int ticketId : pickedTicketIds) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderID(orderId);
                    detail.setTicketID(ticketId); // <-- DÙNG TICKETID THẬT
                    detail.setUnitPrice(item.getPrice());
                    detail.setStatusID(1);
                    orderDetailDAO.insertOrderDetail(conn, detail);
                }

                // 🟢 3) Đánh dấu vé đã bán
                ticketDAO.markTicketsAsSold(conn, pickedTicketIds);
            }
            // 2️⃣ Cập nhật số dư ví
            // === 2️⃣ & 3️⃣ Xử lý thanh toán ===
            BigDecimal newBalance = wallet.getBalance(); // mặc định giữ nguyên

            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                // Chỉ khi tổng tiền > 0 mới cần trừ tiền
                newBalance = wallet.getBalance().subtract(totalAmount);

                // Cập nhật số dư ví
                walletDao.updateBalance(conn, wallet.getWalletID(), newBalance);

                // Ghi giao dịch thanh toán (amount phải âm)
                transactionDAO.insertPayment(conn, wallet.getWalletID(), orderId, totalAmount.negate(), newBalance);
            }

            conn.commit();

            // ==== Tạo PDF đúng theo từng vé ====
// 1) Lấy lại toàn bộ TicketID đã lưu trong OrderDetail
            List<Integer> allTicketIds = orderDetailDAO.getTicketIdsByOrderId(conn, orderId);

// 2) Danh sách file để gửi mail
            List<File> attachments = new ArrayList<>();

            for (int ticketId : allTicketIds) {

                // SerialNumber thật
                String serial = ticketDAO.getSerialByTicketId(conn, ticketId);

                // Lấy ticketTypeId từ ticketId
                int tTypeId = ticketDAO.getTypeIdByTicketId(conn, ticketId);

                // Tên loại vé thật
                String typeName = ticketDAO.getTicketTypeName(conn, tTypeId);

                // Tạo file PDF cho từng vé
                File pdfFile = TicketPDFGenerator.createTicketPDF(
                        "C:/ActiveNetTickets",
                        eventName,
                        placeName,
                        request.getParameter("startStr"),
                        contactFullname,
                        typeName,
                        serial
                );

                attachments.add(pdfFile); // thêm PDF của vé vào danh sách
            }

            try {
                String subject = "[Active-Net Ticketing] Thanh toán thành công #" + orderId;
                String body = "Đơn hàng #" + orderId + " đã được thanh toán thành công.<br>"
                        + "<b>Sự kiện:</b> " + eventName + "<br>"
                        + "<b>Địa điểm:</b> " + placeName + "<br>"
                        + "<b>Tổng tiền:</b> " + totalAmount + " đ<br>"
                        + "<br>Cảm ơn bạn đã mua vé tại Active-Net Ticketing.";

                MailService.sendEmail(contactEmail, subject, body, attachments);
            } catch (Exception ignored) {
            }

            // ==== Chuyển sang trang thành công ====
            request.setAttribute("orderId", orderId);
            request.setAttribute("eventId", eventId);
            request.setAttribute("eventName", eventName);
            request.setAttribute("placeName", placeName);
            request.setAttribute("contactFullname", contactFullname);
            request.setAttribute("contactPhone", contactPhone);
            request.setAttribute("contactEmail", contactEmail);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("items", items);
            request.getRequestDispatcher("payment-success.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            forwardFail(request, response, "Có lỗi xảy ra khi thanh toán. Vui lòng thử lại.<br>" + ex.getMessage());
        }
    }
    // ===== Helpers =====

    private static String nvl(String v, String fallback) {
        return (v == null || v.isBlank()) ? fallback : v;
    }

    private static int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static BigDecimal parseMoney(String s) {
        if (s == null) {
            return BigDecimal.ZERO;
        }
        String cleaned = s.replace(".", "").replace(",", "").replaceAll("[^0-9-]", "");
        if (cleaned.isBlank()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(cleaned);
    }

    private void forwardFail(HttpServletRequest request, HttpServletResponse response, String msg)
            throws ServletException, IOException {
        request.setAttribute("message", msg);
        request.getRequestDispatcher("payment-fail.jsp").forward(request, response);
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
