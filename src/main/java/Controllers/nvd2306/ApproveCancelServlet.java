/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.OrderDAO;
import DAOs.nvd2306.TicketDAO;
import DAOs.nvd2306.TransactionDAO;
import DAOs.nvd2306.WalletDao;
import Models.nvd2306.Order;
import Models.nvd2306.Transaction;
import Models.nvd2306.Wallet;
import Utils.nvd2603.MailService;
import Utils.singleton.DBContext;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "ApproveCancelServlet", urlPatterns = {"/staff/approve-cancel"})
public class ApproveCancelServlet extends HttpServlet {

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
            out.println("<title>Servlet ApproveCancelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApproveCancelServlet at " + request.getContextPath() + "</h1>");
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
            throws ServletException, IOException, UnsupportedEncodingException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDAO orderDAO = new OrderDAO();
        TicketDAO ticketDAO = new TicketDAO();
        WalletDao walletDAO = new WalletDao();
        TransactionDAO transDAO = new TransactionDAO();

// Lấy order
        Order order = orderDAO.getOrderById(orderId);
        int userId = order.getUserID();

// 1. Cập nhật trạng thái sang CANCELLED (15)
        orderDAO.updateOrderStatus(orderId, 15);

// 2. Mở khóa vé
        ticketDAO.unlockTicketsByOrder(orderId);

// 3. Hoàn tiền ví
        BigDecimal refundAmount = order.getTotalAmount();

// Lấy wallet của user
        Wallet wallet = null;
        try {
            wallet = walletDAO.getWalletByUserId(userId);
        } catch (SQLException ex) {
            Logger.getLogger(ApproveCancelServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int walletId = wallet.getWalletID();

// Tính balance mới
        BigDecimal newBalance = wallet.getBalance().add(refundAmount);

        try {
            // Update lại DB
            walletDAO.updateBalance(DBContext.getInstance().getConnection(), walletId, newBalance);
        } catch (SQLException ex) {
            Logger.getLogger(ApproveCancelServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

// 4. Lưu transaction refund
        Transaction t = new Transaction();
        t.setWalletID(walletId);
        t.setOrderID(orderId);
        t.setAmount(refundAmount);
        t.setTransactionTypeID(3); // REFUND
        t.setDescription("Hoàn tiền khi hủy vé");

// Insert transaction
        transDAO.insert(t);

// 5. Gửi email báo cho khách
        String email = order.getContactEmail();
        String fullname = order.getContactFullname();

        String subject = "Yêu cầu hủy vé đã được duyệt";
        String content = "Xin chào " + fullname
                + "<br>Yêu cầu hủy vé của bạn đã được chấp nhận."
                + "<br>Số tiền <b>" + refundAmount + " VND</b> đã được hoàn vào ví của bạn."
                + "<br>Cảm ơn bạn đã sử dụng dịch vụ.";

        try {
            MailService.sendEmail(email, subject, content, null);
        } catch (MessagingException ex) {
            Logger.getLogger(ApproveCancelServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("cancel-requests?msg=approved");
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
