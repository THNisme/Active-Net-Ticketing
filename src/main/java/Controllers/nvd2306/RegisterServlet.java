/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.UserDAO;
import MD5.HashPassword;
import Models.nvd2306.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

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
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("login.jsp").forward(request, response);
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        UserDAO userDAO = new UserDAO();

        // ✅ Kiểm tra trùng username
        if (userDAO.checkUsernameExists(username)) {
            request.setAttribute("errorRegister", "Tên người dùng đã tồn tại!");
            request.setAttribute("showRegister", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ✅ Kiểm tra tiêu chuẩn mật khẩu
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
        if (!password.matches(passwordPattern)) {
            request.setAttribute("errorRegister", "Mật khẩu phải có ít nhất 8 ký tự, gồm ít nhất 1 chữ in hoa, 1 số và 1 ký tự đặc biệt!");
            request.setAttribute("showRegister", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ✅ Kiểm tra khớp mật khẩu
        if (!password.equals(repassword)) {
            request.setAttribute("errorRegister", "Mật khẩu nhập lại không khớp!");
            request.setAttribute("showRegister", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ✅ Mã hóa mật khẩu
        String hashedPassword = HashPassword.hashMD5(password);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setRole(0);

        boolean success = userDAO.register(user);

        if (success) {
            // ✅ Chuyển hướng về login với thông báo
            request.setAttribute("message", "Đăng ký thành công, vui lòng đăng nhập!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // ❗ Khi thất bại → vẫn giữ form đăng ký mở
            request.setAttribute("errorRegister", "Có lỗi khi đăng ký, vui lòng thử lại!");
            request.setAttribute("showRegister", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
