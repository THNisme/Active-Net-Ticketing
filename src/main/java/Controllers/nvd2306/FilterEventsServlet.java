/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.EventDao;
import Models.nvd2306.Event;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "FilterEventsServlet", urlPatterns = {"/filter-events"})
public class FilterEventsServlet extends HttpServlet {

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
            out.println("<title>Servlet FilterEventsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterEventsServlet at " + request.getContextPath() + "</h1>");
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
//           // Get the parameters from the request
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        // Convert the string to LocalDate, handle potential nulls
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }

        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        // Initialize EventDao
        EventDao eventDao = new EventDao();
        List<Event> events = null;

        // If both dates are provided, filter events by the date range
        if (startDate != null || endDate != null) {
            events = eventDao.filterEventsByDateRange(startDate, endDate);
            request.setAttribute("isSearch", true);
        } else {
            // If no dates are provided, fetch all events
            events = eventDao.getAllEvents();
            request.setAttribute("isSearch", false);
        }

        // Forward the filtered events to the JSP page
        request.setAttribute("events", events);
        request.getRequestDispatcher("/home.jsp").forward(request, response);

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
protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
