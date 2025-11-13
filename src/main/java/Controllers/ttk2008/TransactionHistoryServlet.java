package Controllers.ttk2008;

import DAOs.ttk2008.TransactionDAO;
import Models.nvd2306.User;
import Models.ttk2008.Transaction;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TransactionHistoryServlet", urlPatterns = {"/transactions"})
public class TransactionHistoryServlet extends HttpServlet {

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
        TransactionDAO dao = new TransactionDAO();
        List<Transaction> list = dao.getTransactionsByUserId(userId);

        request.setAttribute("transactions", list);
        request.getRequestDispatcher("/view-wallet/transactionHistory.jsp").forward(request, response);
    }
}
