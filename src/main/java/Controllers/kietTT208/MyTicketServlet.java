package Controllers.kietTT208;

import DAOs.kietTT208.MyTicketDAO;
import Models.kietTT208.MyTicket;
//import Models.Account; // Import model Account

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MyTicketServlet", urlPatterns = {"/myticket"})
public class MyTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
          // Lấy thông tin tài khoản từ session (được set khi user login thành công)
//        Account acc = (Account) session.getAttribute("account");

        // Kiểm tra nếu người dùng chưa đăng nhập thì chuyển về trang login
//        if (acc == null) {
//            response.sendRedirect("login.jsp"); // hoặc /auth/login nếu có cấu trúc thư mục riêng
//            return; // dừng luôn, tránh NullPointerException
//        }
//
//        //  Lấy userId từ tài khoản trong session
//        int userId = acc.getUserId();
//        
        

        int userId = 1;

        String filter = request.getParameter("filter");
        MyTicketDAO dao = new MyTicketDAO();
        List<MyTicket> tickets;

        if (filter == null || filter.equals("all")) {
            tickets = dao.getAllTicketsByUser(userId);
        } else if (filter.equals("upcoming")) {
            tickets = dao.getUpcomingTicketsByUser(userId);
        } else if (filter.equals("ended")) {
            tickets = dao.getEndedTicketsByUser(userId);
        } else if (filter.equals("canceled")) {
            tickets = dao.getCanceledTicketsByUser(userId);
        } else {
            tickets = dao.getAllTicketsByUser(userId);
        }

        request.setAttribute("tickets", tickets);
        request.setAttribute("filter", filter);
        RequestDispatcher rd = request.getRequestDispatcher("/view-myticket/myticket.jsp");
        rd.forward(request, response);
    }
}
