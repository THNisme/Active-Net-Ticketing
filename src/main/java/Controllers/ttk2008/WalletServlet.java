package Controllers.ttk2008;

import DAOs.ttk2008.WalletDAO;
import Models.ttk2008.Wallet;
import Models.nvd2306.User; // Lớp User trong session
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "WalletServlet", urlPatterns = {"/wallet"})
public class WalletServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = user.getUserID();

        try {
            WalletDAO walletDAO = new WalletDAO();
            Wallet wallet = walletDAO.getWalletByUserId(userId);

            if (wallet != null) {
                request.setAttribute("wallet", wallet);
            } else {
                request.setAttribute("error", "Không tìm thấy ví của người dùng!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi truy xuất dữ liệu ví: " + e.getMessage());
        }

        // Chuyển tiếp tới JSP hiển thị ví
        request.getRequestDispatcher("/view-wallet/wallet.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "WalletServlet - Hiển thị thông tin ví người dùng";
    }
}
