/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.UserDAO;
import Models.nvd2306.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "VerifyOTPServlet", urlPatterns = {"/verify-otp"})
public class VerifyOTPServlet extends HttpServlet {

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
            out.println("<title>Servlet VerifyOTPServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyOTPServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("pendingUserID");

        if (userID == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String inputOTP = request.getParameter("otp");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByID(userID);

        // Sai user hoặc chưa có OTP
        if (user == null || user.getOTPCode() == null) {
            request.setAttribute("errorOTP", "OTP không tồn tại hoặc đã bị xoá!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

        // Kiểm tra hết hạn
        if (user.getOTPExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {
            request.setAttribute("errorOTP", "OTP đã hết hạn, vui lòng gửi mã mới!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

        // Kiểm tra đúng mã
        if (!user.getOTPCode().equals(inputOTP)) {
            request.setAttribute("errorOTP", "OTP không đúng, vui lòng thử lại!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

        // OTP đúng → xoá OTP + set verified = 1
        userDAO.markVerified(userID);

        // Xoá session
        session.removeAttribute("pendingUserID");

        // Alert + quay về login
        request.setAttribute("successMsg", "🎉 Xác thực thành công! Tài khoản đã được tạo.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
