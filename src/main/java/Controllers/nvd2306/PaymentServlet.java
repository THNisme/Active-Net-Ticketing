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
import Utils.nvd2603.DBContext;
import Utils.nvd2603.MailService;
import Utils.nvd2603.TicketPDFGenerator;
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
@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment"})
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

        if (wallet.getBalance().compareTo(totalAmount) < 0) {
            request.setAttribute("insufficientBalance", true);
            request.setAttribute("message", "Số dư ví không đủ để thanh toán.");
            request.setAttribute("eventName", eventName);
            request.setAttribute("placeName", placeName);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("selectionsJson", selectionsJson);
            request.getRequestDispatcher("payment-preview.jsp").forward(request, response);
            return;
        }

        // ==== Bắt đầu thanh toán ====
        DBContext db = new DBContext();
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

            // ✅ 1.1. Thêm chi tiết đơn hàng (OrderDetails)
            if (items != null && !items.isEmpty()) {
                for (TicketItem item : items) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderID(orderId);
                    detail.setTicketTypeID(item.getTicketTypeId());
                    detail.setUnitPrice(item.getPrice());
                    detail.setStatusID(1);
                    orderDetailDAO.insertOrderDetail(conn, detail);
                }
            }
            TicketDAO ticketDAO = new TicketDAO();
            List<Integer> soldTicketIds = new ArrayList<>();

            for (TicketItem item : items) {
                soldTicketIds.add(item.getTicketId()); // <-- TicketID thật
            }

            ticketDAO.markTicketsAsSold(conn, soldTicketIds);
            // 2️⃣ Cập nhật số dư ví
            BigDecimal newBalance = wallet.getBalance().subtract(totalAmount);
            walletDao.updateBalance(conn, wallet.getWalletID(), newBalance);

            // 3️⃣ Ghi giao dịch thanh toán
            transactionDAO.insertPayment(conn, wallet.getWalletID(), orderId, totalAmount.negate(), newBalance);

            conn.commit();

            // ==== Tạo PDF vé và gửi mail ====
            String pdfPath = null;
            try {
                File pdfFile = TicketPDFGenerator.createTicketPDF(
                        "C:/ActiveNetTickets",
                        eventName,
                        placeName,
                        "Không rõ thời gian",
                        contactFullname,
                        "Vé điện tử",
                        "SN" + orderId
                );
                pdfPath = pdfFile.getAbsolutePath();
            } catch (Exception ignored) {
            }

            try {
                String subject = "[Active-Net Ticketing] Thanh toán thành công #" + orderId;
                String body = "Đơn hàng #" + orderId + " đã được thanh toán thành công.<br>"
                        + "<b>Sự kiện:</b> " + eventName + "<br>"
                        + "<b>Địa điểm:</b> " + placeName + "<br>"
                        + "<b>Tổng tiền:</b> " + totalAmount + " đ<br>"
                        + "<br>Cảm ơn bạn đã mua vé tại Active-Net Ticketing.";

                List<File> attachments = new ArrayList<>();
                if (pdfPath != null) {
                    attachments.add(new File(pdfPath));
                }

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
