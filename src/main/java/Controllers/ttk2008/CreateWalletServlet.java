package Controllers.ttk2008;

import DAOs.ttk2008.WalletDAO;
import Models.nvd2306.User;
import Models.ttk2008.Wallet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "CreateWalletServlet", urlPatterns = {"/wallet/create"})
public class CreateWalletServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = user.getUserID();

        WalletDAO dao = new WalletDAO();
        Wallet existing = dao.getWalletByUserId(userId);
        if (existing != null) {
            response.sendRedirect(request.getContextPath() + "/wallet");
            return;
        }

        Wallet created = dao.createWalletForUser(userId);
        if (created != null) {
            session.setAttribute("message", "Tạo ví thành công!");
            response.sendRedirect(request.getContextPath() + "/wallet");
        } else {
            request.setAttribute("error", "Không thể tạo ví — vui lòng thử lại.");
            request.getRequestDispatcher("/view-wallet/wallet.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/wallet");
    }
}
