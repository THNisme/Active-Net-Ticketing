/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.*;
import Models.nvd2306.*;
import DAOs.nvd2306.OrderDAO;
import DAOs.nvd2306.OrderDetailDAO;
import DAOs.nvd2306.TicketDAO;
import DAOs.nvd2306.TransactionDAO;
import DAOs.nvd2306.WalletDao;
import Utils.nvd2603.MailService;
import Utils.nvd2603.TicketPDFGenerator;
import Utils.singleton.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "StaffConfirmOrderServlet", urlPatterns = {"/staff/confirm"})
public class StaffConfirmOrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO detailDAO = new OrderDetailDAO();
    private final WalletDao walletDao = new WalletDao();
    private final TicketDAO ticketDAO = new TicketDAO();
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
            out.println("<title>Servlet StaffConfirmOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffConfirmOrderServlet at " + request.getContextPath() + "</h1>");
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

        HttpSession session = request.getSession(false);
        Integer role = (session != null) ? (Integer) session.getAttribute("role") : null;

        if (role == null || role != 2) { // ROLE=2 = STAFF
            response.sendRedirect("../login");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            response.getWriter().println("Order không tồn tại!");
            return;
        }

        BigDecimal totalAmount = order.getTotalAmount();

        Wallet wallet;
        try {
            wallet = walletDao.getWalletByUserId(order.getUserID());
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi lấy ví người dùng: " + e.getMessage());
            return;
        }

        try (Connection conn = DBContext.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            // 1) Check tiền ví
            if (wallet.getBalance().compareTo(totalAmount) < 0) {
                response.getWriter().println("Ví không đủ tiền để xác nhận đơn này!");
                return;
            }

            // 2) Trừ tiền
            BigDecimal newBalance = wallet.getBalance().subtract(totalAmount);
            walletDao.updateBalance(conn, wallet.getWalletID(), newBalance);

            transactionDAO.insertPayment(conn,
                    wallet.getWalletID(),
                    orderId,
                    totalAmount.negate(),
                    newBalance
            );

            // 3) Lấy tất cả ticketID
            List<Integer> ticketIds = detailDAO.getTicketIdsByOrderId(conn, orderId);

            // 4) Gửi vé PDF
            List<File> attachments = new ArrayList<>();

            for (int ticketId : ticketIds) {

                String serial = ticketDAO.getSerialByTicketId(conn, ticketId);
                int ticketTypeId = ticketDAO.getTypeIdByTicketId(conn, ticketId);
                String typeName = ticketDAO.getTicketTypeName(conn, ticketTypeId);

                // Lấy full info của vé
                TicketFullInfo info = ticketDAO.getFullTicketInfo(ticketId);

                File pdf = TicketPDFGenerator.createTicketPDF(
                        "C:/ActiveNetTickets",
                        info.eventName,
                        info.placeName,
                        info.time,
                        order.getContactFullname(),
                        info.typeName,
                        info.serial
                );
                attachments.add(pdf);
            }

            MailService.sendEmail(
                    order.getContactEmail(),
                    "[Active-Net] Xác nhận thanh toán #" + orderId,
                    "Staff đã xác nhận đơn hàng #" + orderId + ". Vé được đính kèm.",
                    attachments
            );

            // 5) Cập nhật trạng thái đơn → CONFIRMED
            orderDAO.updateStatus(conn, orderId, 12); // 12 = CONFIRMED

            conn.commit();

            response.sendRedirect(request.getContextPath() + "/staff/orders-pending");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Có lỗi xảy ra khi xác nhận đơn: " + e.getMessage());
        }
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
        processRequest(request, response);
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
