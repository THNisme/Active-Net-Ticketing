/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.TicketManagementDAO;
import DAOs.nttn0902.TicketManagementDAOImpl;
import Models.nttn0902.RemainingTicket;
import Models.nttn0902.SoldTicket;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author NGUYEN
 */
@WebServlet(name = "TicketManagementServlet", urlPatterns = {"/ticket_management"})
public class TicketManagementServlet extends HttpServlet {

    private TicketManagementDAO ticketDAO;

    @Override
    public void init() {
        ticketDAO = new TicketManagementDAOImpl();
    }

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
            out.println("<title>Servlet TicketManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TicketManagementServlet at " + request.getContextPath() + "</h1>");
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
              String eventParam = request.getParameter("eventID");
        int eventId = 0;

        try {
            if (eventParam != null) {
                eventId = Integer.parseInt(eventParam);
            }
        } catch (NumberFormatException ignored) {
            eventId = 0;
        }

        String action = request.getParameter("action");

        // ============================================================
        // 🔥 GET: LOAD 1 TICKET FOR EDIT (AJAX MODAL)
        // ============================================================
        if ("edit".equals(action)) {

            response.setContentType("application/json;charset=UTF-8");

            try {
                int ticketId = Integer.parseInt(request.getParameter("ticketId"));
                RemainingTicket t = ticketDAO.getRemainingTicketById(ticketId);

                if (t == null) {
                    response.getWriter().write("{\"success\": false}");
                    return;
                }

                JSONObject json = new JSONObject();
                json.put("ticketId", t.getTicketId());
                json.put("serialNumber", t.getSerialNumber());
                json.put("typeName", t.getTypeName());
                json.put("zoneName", t.getZoneName());
                json.put("seat", t.getSeat());
                json.put("price", t.getPrice());

                response.getWriter().write(json.toString());

            } catch (Exception e) {
                response.getWriter().write("{\"success\": false}");
            }

            return;
        }

        // ============================================================
        // 🔥 GET: DELETE BY AJAX
        // ============================================================
        if ("delete".equals(action)) {

            response.setContentType("application/json;charset=UTF-8");

            try {
                int ticketId = Integer.parseInt(request.getParameter("ticketId"));

                boolean success = ticketDAO.updateRemainingTicketStatus(ticketId, 3);
                response.getWriter().write("{\"success\": " + success + "}");

            } catch (Exception e) {
                response.getWriter().write("{\"success\": false}");
            }

            return;
        }

        // ============================================================
        // 🔥 FILTER + LOAD TABLE
        // ============================================================
        String filter = request.getParameter("filter");
        if (filter == null || filter.isEmpty()) {
            filter = "remaining";
        }

        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        request.setAttribute("currentTime", new Date());
        request.setAttribute("filter", filter);
        request.setAttribute("keyword", keyword);
        request.setAttribute("eventID", eventId);

        try {
            if (eventId > 0) {

                request.setAttribute("otherEvents",
                        ticketDAO.getOtherEvents(eventId));

                if (filter.equals("sold")) {
                    request.setAttribute("ticketList",
                            ticketDAO.getSoldTickets(eventId, keyword));
                } else {
                    request.setAttribute("ticketList",
                            ticketDAO.getRemainingTickets(eventId, keyword));
                }

                request.setAttribute("eventName",
                        ticketDAO.getEventNameById(eventId));

            } else {
                request.setAttribute("ticketList", List.of());
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        request.getRequestDispatcher("/TicketManagement/TicketManagement.jsp")
                .forward(request, response);
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
      String action = request.getParameter("action");
      
      if ("updateRemaining".equals(action)) {
            response.setContentType("application/json;charset=UTF-8");

            try {
                int ticketId = Integer.parseInt(request.getParameter("ticketId"));
                int price = Integer.parseInt(request.getParameter("price"));

                boolean success = ticketDAO.updateRemainingTicketPrice(ticketId, price);

                JSONObject json = new JSONObject();
                json.put("success", success);

                response.getWriter().write(json.toString());
                return;

            } catch (Exception e) {
                response.getWriter().write("{\"success\": false}");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        if ("toggleStatus".equals(action)) {
            response.setContentType("application/json;charset=UTF-8");

            try {
                int ticketId = Integer.parseInt(request.getParameter("ticketId"));
                int newStatus = Integer.parseInt(request.getParameter("status"));

                // 🔥 CẬP NHẬT TRẠNG THÁI VÉ ĐÃ BÁN
                boolean success = ticketDAO.updateSoldTicketStatus(ticketId, newStatus);

                JSONObject json = new JSONObject();
                json.put("success", success);

                response.getWriter().write(json.toString());
                return;

            } catch (Exception e) {
                response.getWriter().write("{\"success\": false}");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
