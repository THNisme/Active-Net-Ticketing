/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.UsersDAO;
import Models.User;
import Services.MailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import MD5.HashPassword;
/**
 *
 * @author Acer
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

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
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        UsersDAO dao = new UsersDAO();

        switch (action) {
            case "new":
                req.getRequestDispatcher("/ManageUsers/userForm.jsp").forward(req, res);
                break;

            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("user", dao.getUserById(id));
                req.getRequestDispatcher("/ManageUsers/userForm.jsp").forward(req, res);
                break;

            case "delete":
                int deleteId = Integer.parseInt(req.getParameter("id"));
                dao.softDeleteUser(deleteId);
                res.sendRedirect("UserServlet?action=list");
                break;

            default:
                List<User> list = dao.getAllUsers();
                req.setAttribute("userList", list);
                req.getRequestDispatcher("/ManageUsers/userList.jsp").forward(req, res);
                break;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UsersDAO dao = new UsersDAO();

        int id = req.getParameter("userID") != null && !req.getParameter("userID").isEmpty()
                ? Integer.parseInt(req.getParameter("userID")) : 0;

        String username = req.getParameter("username");
        String password = req.getParameter("passwordHash");
        String confirmPassword = req.getParameter("confirmPassword");
        int role = Integer.parseInt(req.getParameter("role"));
        String email = req.getParameter("email");
        String actionType = req.getParameter("actionType");
        
        if (id == 0 && dao.checkUsernameExists(username)) {
            req.setAttribute("error", "Tài khoản đã tồn tại!");
            req.getRequestDispatcher("/ManageUsers/userForm.jsp").forward(req, res);
            return;
        }
        
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            req.setAttribute("error", "❌ Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("/ManageUsers/userForm.jsp").forward(req, res);
            return;
        }
        
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$")) {
            req.setAttribute("error", "❌ Mật khẩu phải có ít nhất 8 ký tự, gồm chữ, số và ký tự đặc biệt!");
            req.getRequestDispatcher("/ManageUsers/userForm.jsp").forward(req, res);
            return;
        }
      
        User user = new User();
        user.setUserID(id);
        user.setUsername(username);
        user.setPassword(HashPassword.hashMD5(password));
        user.setRole(role);
        user.setStatusID(1); 

        if (id == 0) {
            dao.addUser(user);
            
            if ("saveAndSend".equals(actionType)) {
                try {
                    MailService.sendAccountEmail(email, username, password);
                    req.setAttribute("mailStatus", "Đã gửi mail cho " + email);
                } catch (Exception e) {
                    req.setAttribute("error", "Không thể gửi mail: " + e.getMessage());
                }
            }

        } else {
            dao.updateUser(user);
        }     
        res.sendRedirect("UserServlet?action=list");
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
