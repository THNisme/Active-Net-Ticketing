/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.OrderDAO;
import DAOs.nvd2306.OrderDetailDAO;
import DAOs.nvd2306.TransactionDAO;
import DAOs.nvd2306.WalletDao;
import Models.nvd2306.Order;
import Models.nvd2306.OrderDetail;
import Models.nvd2306.TicketItem;
import Models.nvd2306.Transaction;
import Models.nvd2306.User;
import Models.nvd2306.Wallet;
import Utils.nvd2603.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment"})
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
        // Nhận dữ liệu từ checkout.jsp
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String eventId = request.getParameter("eventId");
        String eventName = request.getParameter("eventName");
        String placeName = request.getParameter("placeName");
        String startStr = request.getParameter("startStr");
        String totalAmountStr = request.getParameter("totalAmount");

        // Lấy vé từ session (đã lưu ở CheckoutServlet)
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<TicketItem> tickets = (List<TicketItem>) session.getAttribute("tickets");

        // Gửi dữ liệu sang payment.jsp để hiển thị
        request.setAttribute("fullName", fullName);
        request.setAttribute("phone", phone);
        request.setAttribute("email", email);
        request.setAttribute("eventId", eventId);
        request.setAttribute("eventName", eventName);
        request.setAttribute("placeName", placeName);
        request.setAttribute("startStr", startStr);
        request.setAttribute("totalAmount", totalAmountStr);
        request.setAttribute("tickets", tickets);

        request.getRequestDispatcher("/payment.jsp").forward(request, response);
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
