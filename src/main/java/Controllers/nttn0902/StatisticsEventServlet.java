  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.StatisticsEventDAO;
import Models.nttn0902.StatisticsEvent;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 *
 * @author NGUYEN
 */
@WebServlet(name = "StatisticsEventServlet", urlPatterns = {"/statisticsevent"})
public class StatisticsEventServlet extends HttpServlet {

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
            out.println("<title>Servlet StatisticsForEachIndividualEventServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatisticsForEachIndividualEventServlet at " + request.getContextPath() + "</h1>");
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

    try {
        //lấy tham số từ UML
        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số eventId!");
            return;
        }

        int eventId = Integer.parseInt(eventIdParam);
        request.setAttribute("eventId", eventId);

        // thống kê sự kiện hiện tại
        StatisticsEventDAO dao = new StatisticsEventDAO();
        List<StatisticsEvent> ticketStats = dao.getTicketTypeStatistics(eventId);
        double totalEventRevenue = dao.getTotalEventRevenue(eventId);
        int totalTickets = dao.getTotalTickets(eventId);
        int soldTickets = dao.getSoldTickets(eventId);

        double percentTicketsSold = totalTickets == 0 ? 0 : (soldTickets * 100.0 / totalTickets);
        double percentageOfRevenue = (totalTickets> 0)
                ? (double) soldTickets / totalTickets * 100
                : 0;

        String eventName = ticketStats.isEmpty() ? "Không xác định" : ticketStats.get(0).getEventName();

        // dropdown
        DAOs.nttn0902.StatisticsEventsDAO eventDao = new DAOs.nttn0902.StatisticsEventsDAO();
        List<Models.nttn0902.StatisticsEvents> otherEvents = eventDao.getAllEventSummariesExcept(eventId);
        request.setAttribute("otherEvents", otherEvents);

        // gửi dữ liệu sang jsp
        request.setAttribute("eventName", eventName);
        request.setAttribute("ticketStats", ticketStats);
        request.setAttribute("totalRevenue", totalEventRevenue);
        request.setAttribute("percentageOfRevenue", percentageOfRevenue);
        request.setAttribute("totalTicketsSold", soldTickets);
        request.setAttribute("totalTicketsIssued", totalTickets );
        request.setAttribute("percentageOfRevenue", percentTicketsSold);
        request.setAttribute("currentTime", new java.util.Date());

        RequestDispatcher rd = request.getRequestDispatcher("/statistic/StatisticEvent.jsp");
        rd.forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý dữ liệu thống kê!");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
