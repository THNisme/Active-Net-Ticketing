/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.StatisticsEventsDAO;
import Models.nttn0902.StatisticsEvents;
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
@WebServlet(name = "StatisticsEventsServlet", urlPatterns = {"/statisticsevents"})
public class StatisticsEventsServlet extends HttpServlet {

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
            out.println("<title>Servlet StatisticsEventsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatisticsEventsServlet at " + request.getContextPath() + "</h1>");
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
         StatisticsEventsDAO dao = new StatisticsEventsDAO ();


        List<StatisticsEvents> ticketStats = dao.getStatisticsEvent();
        double totalRevenue = dao.getTotalRevenue();
        int totalTicketsIssued = dao.getTotalTicketsIssued();
        int totalTicketsSold = dao.getTotalTicketsSold();

        double percentageOfRevenue = (totalTicketsIssued > 0)
                ? (double) totalTicketsSold / totalTicketsIssued * 100
                : 0;

        double percentTicketsSold = (totalTicketsIssued > 0)
                ? (double) totalTicketsSold / totalTicketsIssued * 100
                : 0;

        // Gửi dữ liệu sang JSP
        request.setAttribute("ticketStats", ticketStats);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalTicketsIssued", totalTicketsIssued);
        request.setAttribute("totalTicketsSold", totalTicketsSold);
        request.setAttribute("percentageOfRevenue", percentageOfRevenue);
        request.setAttribute("percentTicketsSold", percentTicketsSold);
        request.setAttribute("currentTime", new java.util.Date());

        RequestDispatcher rd = request.getRequestDispatcher("/statistic/StatisticEvents.jsp");
        rd.forward(request, response);
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
