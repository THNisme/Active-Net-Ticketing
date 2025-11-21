package Controllers.ttk2008;

import DAOs.ttk2008.WalletDAO;
import Models.nvd2306.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/payment-return")
public class PaymentReturnServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("vnp_TransactionStatus");

        if ("00".equals(status)) {
            String txnRef = request.getParameter("vnp_TxnRef");
            long Balance = Long.parseLong(request.getParameter("vnp_Amount")) / 100;
            String payDateStr = request.getParameter("vnp_PayDate");

            Timestamp lastUpdated = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = sdf.parse(payDateStr);
                lastUpdated = new Timestamp(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            int userId = user.getUserID();
            WalletDAO dao = new WalletDAO();
            dao.updateBalance(userId, Balance, lastUpdated);
            request.getRequestDispatcher("view-wallet/deposit_success.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("view-wallet/deposit_failure.jsp").forward(request, response);
        }
    }
}
