/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.EventDao;
import DAOs.nvd2306.SeatDAO;
import DAOs.nvd2306.TicketDAO;
import DAOs.nvd2306.TicketTypeDAO;
import DAOs.nvd2306.ZoneDAO;
import Models.nvd2306.Event;
import Models.nvd2306.Seat;
import Models.nvd2306.TicketType;
import Models.nvd2306.Zone;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "SelectTicketServlet", urlPatterns = {"/select-ticket"})
public class SelectTicketServlet extends HttpServlet {

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
            out.println("<title>Servlet SelectTicketServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SelectTicketServlet at " + request.getContextPath() + "</h1>");
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

        int eventId = Integer.parseInt(request.getParameter("id"));

        EventDao eventDao = new EventDao();
        TicketTypeDAO ticketTypeDAO = new TicketTypeDAO();
        ZoneDAO zoneDAO = new ZoneDAO();
        SeatDAO seatDAO = new SeatDAO();
        TicketDAO ticketDAO = new TicketDAO();

        Event event = eventDao.getEventDetailById(eventId);
        request.setAttribute("event", event);

        List<Zone> zones = zoneDAO.getZonesByEvent(eventId);
        request.setAttribute("zones", zones);

        String zoneParam = request.getParameter("zone");
        int zoneId;
        if (zoneParam == null) {
            zoneId = zones.get(0).getZoneID();
        } else {
            zoneId = Integer.parseInt(zoneParam);
        }
        request.setAttribute("selectedZoneId", zoneId);

        List<TicketType> ticketTypes = ticketTypeDAO.getTicketTypesByEventAndZone(eventId, zoneId);

        try {
            for (TicketType t : ticketTypes) {
                boolean hasSeat = ticketDAO.ticketTypeHasSeat(t.getTicketTypeID());
                t.setHasSeat(hasSeat);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        List<Seat> seats = seatDAO.getSeatsByZone(zoneId);
        request.setAttribute("ticketTypes", ticketTypes);
        request.setAttribute("seats", seats);

        request.getRequestDispatcher("SelectTicket.jsp").forward(request, response);
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
