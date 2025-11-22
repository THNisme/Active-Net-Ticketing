package Controllers.ttk2008;

import DAOs.ttk2008.MyTicketDAO;
import Models.nvd2306.User;
import Models.ttk2008.MyTicket;
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

        HttpSession session = request.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = loginUser.getUserID();

        String filter = request.getParameter("filter");
        String action = request.getParameter("action");
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
        if ("delete".equals(action)) {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            dao.deleteTicket(ticketId);
            response.sendRedirect("myticket?filter=all");
            return;
        }
        request.setAttribute("tickets", tickets);
        request.setAttribute("filter", filter);
        RequestDispatcher rd = request.getRequestDispatcher("/view-ttk2008/myticket.jsp");
        rd.forward(request, response);

    }
}
