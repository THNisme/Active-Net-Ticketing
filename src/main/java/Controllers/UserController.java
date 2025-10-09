package Controllers;

import DAOs.UsersDAO;
import Models.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        UsersDAO dao = new UsersDAO();

        switch (action) {
            case "new":
                req.getRequestDispatcher("/userFrom.jsp").forward(req, res);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("user", dao.getUserById(id));
                req.getRequestDispatcher("/userFrom.jsp").forward(req, res);
                break;
            case "delete":
                dao.deleteUser(Integer.parseInt(req.getParameter("id")));
                res.sendRedirect("UserController?action=list");
                break;
            default:
                List<User> list = dao.getAllUsers();
                req.setAttribute("userList", list);
                req.getRequestDispatcher("/userList.jsp").forward(req, res);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UsersDAO dao = new UsersDAO();

        int id = req.getParameter("userID") != null && !req.getParameter("userID").isEmpty()
                ? Integer.parseInt(req.getParameter("userID")) : 0;

        User user = new User();
        user.setUserID(id);
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("passwordHash"));
        user.setRole(Integer.parseInt(req.getParameter("role")));

        if (id == 0) dao.addUser(user);
        else dao.updateUser(user);

        res.sendRedirect("UserController?action=list");
    }
}
