package Controllers.ttk2008;

import DAOs.ttk2008.UserInfoDAO;
import Models.nvd2306.User; // <-- Lấy User login từ session
import Models.ttk2008.UserInfo;
import MD5.HashPassword;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UserInfoServlet", urlPatterns = {"/userinfo"})
public class UserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserInfoDAO dao = new UserInfoDAO();
        UserInfo userInfo = dao.getUserById(loginUser.getUserID());

        request.setAttribute("user", userInfo);
        request.getRequestDispatcher("view-ttk2008/userinfo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = loginUser.getUserID();
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        UserInfoDAO dao = new UserInfoDAO();
        UserInfo userInfo = dao.getUserById(userId);

      

        if (dao.isUsernameTaken(username, userId)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.setAttribute("user", userInfo);
            request.getRequestDispatcher("view-ttk2008/userinfo.jsp").forward(request, response);
            return;
        }

        // Cập nhật thông tin
        userInfo.setUsername(username);
        userInfo.setContactFullname(fullname);
        userInfo.setContactEmail(email);
        userInfo.setContactPhone(phone);

        boolean updated = dao.updateUser(userInfo);

        if (updated) {
            request.setAttribute("message", "Cập nhật thành công!");
            // Không update session loginUser, vì session chỉ lưu User login
        } else {
            request.setAttribute("error", "Cập nhật thất bại!");
        }

        request.setAttribute("user", userInfo);
        request.getRequestDispatcher("view-ttk2008/userinfo.jsp").forward(request, response);
    }
}
