/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.thn1105;

import DAOs.thn1105.EventCategoryDAO;
import DAOs.thn1105.EventDAO;
import DAOs.thn1105.PlaceDAO;
import DAOs.thn1105.SeatDAO;
import DAOs.thn1105.StatusDAO;
import DAOs.thn1105.TicketDAO;
import DAOs.thn1105.TicketTypeDAO;
import DAOs.thn1105.ZoneDAO;
import Models.thn1105.Event;
import Models.thn1105.EventCategory;
import Models.thn1105.Place;
import Models.thn1105.Seat;
import Models.thn1105.Ticket;
import Models.thn1105.TicketType;
import Models.thn1105.Zone;
import Utils.TicketUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(name = "ConfigTicketServlet", urlPatterns = {"/config-ticket"})
public class ConfigTicketServlet extends HttpServlet {

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
            out.println("<title>Servlet ConfigTicketServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfigTicketServlet at " + request.getContextPath() + "</h1>");
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
        PlaceDAO placeDAO = new PlaceDAO();
        ZoneDAO zDao = new ZoneDAO();
        EventDAO eDao = new EventDAO();
        TicketTypeDAO ticketTypeDao = new TicketTypeDAO();

        String action = request.getParameter("action");

        if (action == null) {
            HttpSession session = request.getSession();
//            int currentEID = (int) session.getAttribute("currentEventID");

//            TEST EID
            int currentEID = 1030;
            System.out.println("currentEID: " + currentEID);
            Event e = eDao.getById(currentEID);
            Place p = placeDAO.getById(e.getPlaceID());
            List<TicketType> ticketTypeList = ticketTypeDao.getAllByEventID(currentEID);
            List<Zone> zoneList = zDao.getAllZoneOfPlace(e.getPlaceID());

            request.setAttribute("event", e);
            request.setAttribute("place", p);
            request.setAttribute("ticketTypes", ticketTypeList);
            request.setAttribute("zones", zoneList);
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
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
        TicketTypeDAO ticketTypeDao = new TicketTypeDAO();
        TicketDAO tDao = new TicketDAO();
        SeatDAO seatDao = new SeatDAO();
        String action = request.getParameter("action");

        if (action == null) {
            action = "create";
        }

        if (action.equalsIgnoreCase("create")) {
            handleCreateTicketType(request, response, ticketTypeDao);
        } else if (action.equalsIgnoreCase("update")) {
            handleUpdateTicketType(request, response, ticketTypeDao);
        } else if (action.equalsIgnoreCase("delete")) {
            handleDeleteTicketType(request, response, ticketTypeDao);
        } else if (action.equalsIgnoreCase("generate-ticket")) {
            handleGenerateTicket(request, response, seatDao, tDao, ticketTypeDao);
        }
    }

    private void handleCreateTicketType(HttpServletRequest request, HttpServletResponse response, TicketTypeDAO tickTypeDao)
            throws ServletException, IOException {
        try {
            String eidStr = request.getParameter("eventID");
            String ticketTypeName = request.getParameter("ticketTypeName");
            String ticketTypePriceStr = request.getParameter("ticketTypePrice");
            String zoneIDStr = request.getParameter("zoneID");

            int eID = Integer.parseInt(eidStr.trim());
            int zID = Integer.parseInt(zoneIDStr.trim());

            BigDecimal tickPrice = null;
            if (ticketTypePriceStr != null && !ticketTypePriceStr.isEmpty()) {
                tickPrice = new BigDecimal(ticketTypePriceStr);
            }

            TicketType newTicketType = new TicketType();
            newTicketType.setEventID(eID);
            newTicketType.setTypeName(ticketTypeName);
            newTicketType.setPrice(tickPrice);
            newTicketType.setZoneID(zID);

            boolean success = tickTypeDao.create(newTicketType);
            System.out.println("Tạo thành công: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
        }
    }

    private void handleUpdateTicketType(HttpServletRequest request, HttpServletResponse response, TicketTypeDAO tickTypeDao)
            throws ServletException, IOException {
        try {
            String eidStr = request.getParameter("eventID-U");
            String ticketTypeIdStr = request.getParameter("ticketTypeID-U");
            String ticketTypeName = request.getParameter("ticketTypeName-U");
            String ticketTypePriceStr = request.getParameter("ticketTypePrice-U");
            String zoneIDStr = request.getParameter("zoneID-U");
            String statusStr = request.getParameter("statusID-U");

            int eID = Integer.parseInt(eidStr.trim());
            int zID = Integer.parseInt(zoneIDStr.trim());
            int tpID = Integer.parseInt(ticketTypeIdStr.trim());
            int statusID = Integer.parseInt(statusStr.trim());

            BigDecimal tickPrice = null;
            if (ticketTypePriceStr != null && !ticketTypePriceStr.isEmpty()) {
                tickPrice = new BigDecimal(ticketTypePriceStr);
            }

            System.out.println("TTID: " + tpID);
            System.out.println("EID: " + eID);
            System.out.println("TName: " + ticketTypeName);
            System.out.println("TPrice: " + tickPrice);
            System.out.println("ZID: " + zID);
            System.out.println("SID: " + statusID);

            TicketType updateTicketType = new TicketType();
            updateTicketType.setTicketTypeID(tpID);
            updateTicketType.setEventID(eID);
            updateTicketType.setTypeName(ticketTypeName);
            updateTicketType.setPrice(tickPrice);
            updateTicketType.setZoneID(zID);
            updateTicketType.setStatusID(statusID);

            boolean success = tickTypeDao.update(updateTicketType);
            System.out.println("Cập nhật thành công: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
        }
    }

    private void handleDeleteTicketType(HttpServletRequest request, HttpServletResponse response, TicketTypeDAO tickTypeDao)
            throws ServletException, IOException {
        try {
            String ticketTypeIdStr = request.getParameter("ticketTypeID-D");

            int tpID = Integer.parseInt(ticketTypeIdStr.trim());

            System.out.println("TTID: " + tpID);

            boolean success = tickTypeDao.softDelete(tpID);
            System.out.println("Cập nhật thành công: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
        }
    }

    private void handleGenerateTicket(HttpServletRequest request, HttpServletResponse response, SeatDAO seatDao, TicketDAO ticketDao, TicketTypeDAO tpDao)
            throws ServletException, IOException {
        StatusDAO sDao = new StatusDAO();

        try {
            int ticketTypeId = Integer.parseInt(request.getParameter("ticketTypeID-GenTicket"));
            int totalTickets = Integer.parseInt(request.getParameter("ticketQuantity"));
            boolean hasSeat = request.getParameter("hasSeat") != null;
            TicketType ticketTypeObj = tpDao.getByID(ticketTypeId);
            int zID = ticketTypeObj.getZoneID();
            boolean success = true;

            if (hasSeat) {
                String[] rowLabels = request.getParameterValues("rowLabels[]");
                String[] seatCounts = request.getParameterValues("seatCounts[]");

                for (int i = 0; i < rowLabels.length; i++) {
                    String row = rowLabels[i].toUpperCase();
                    int count = Integer.parseInt(seatCounts[i]);

                    for (int seatNum = 1; seatNum <= count; seatNum++) {
                        Seat newSeat = new Seat();
                        newSeat.setZoneID(zID);
                        newSeat.setRowLabel(row);
                        newSeat.setSeatNumber(seatNum);

                        boolean seatCreated = seatDao.create(newSeat);
                        if (seatCreated) {
                            int seatId = newSeat.getSeatID();

                            String serial = TicketUtils.generateSerialNumber();
                            Timestamp issuedAt = new Timestamp(System.currentTimeMillis());

                            Ticket newTicket = new Ticket();
                            newTicket.setTicketTypeID(ticketTypeId);
                            newTicket.setSeatID(seatId);
                            newTicket.setSerialNumber(serial);
                            newTicket.setIssuedAt(issuedAt);

                            boolean ticketCreated = ticketDao.create(newTicket);
                            if (!ticketCreated) {
                                success = false;
                                break;
                            }
                        } else {
                            success = false;
                            break;
                        }
                    }
                    if (!success) {
                        break;
                    }
                }
            } else {
                for (int i = 0; i < totalTickets; i++) {
                    String serial = TicketUtils.generateSerialNumber();
                    Timestamp issuedAt = new Timestamp(System.currentTimeMillis());

                    Ticket newTicket = new Ticket();
                    newTicket.setTicketTypeID(ticketTypeId);
                    newTicket.setSeatID(null);
                    newTicket.setSerialNumber(serial);
                    newTicket.setIssuedAt(issuedAt);

                    boolean ticketCreated = ticketDao.create(newTicket);
                    if (!ticketCreated) {
                        success = false;
                        break;
                    }
                }
            }

            System.out.println("Tạo vé thành công: " + success);

            if (success) {
                int activeStatusId = sDao.getStatusIdByCode("hasTicket");
                boolean updated = tpDao.setStatus(ticketTypeId, activeStatusId);
                System.out.println("Cập nhật status thành công: " + updated);

                response.sendRedirect(request.getContextPath() + "/config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
        }
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
