package Controllers.thn1105;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import DAOs.thn1105.PlaceDAO;
import DAOs.thn1105.ZoneDAO;
import Models.thn1105.Event;
import Models.thn1105.Place;
import Models.thn1105.TicketType;
import Models.thn1105.Zone;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(urlPatterns = {"/config-zone"})
public class ConfigZoneServlet extends HttpServlet {

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
            out.println("<title>Servlet ConfigZoneServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfigZoneServlet at " + request.getContextPath() + "</h1>");
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
        PlaceDAO pDAO = new PlaceDAO();
        ZoneDAO zDAO = new ZoneDAO();

        String action = request.getParameter("action");

        if (action == null) {
            String placeIDStr = request.getParameter("placeID");

            HttpSession session = request.getSession();
            int currentPlaceID = (int) session.getAttribute("currentPlaceID");

//            TEST PlaceID
//            int currentPlaceID = 17;
            System.out.println("In config-zone/view - currentPlaceID: " + currentPlaceID);
            Place p = pDAO.getById(currentPlaceID);
            List<Zone> zoneList = zDAO.getAllZoneOfPlace(currentPlaceID);

            request.setAttribute("place", p);
            request.setAttribute("zones", zoneList);
            request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            // ĐI THEO LUỒNG UPDATE PLACE
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
        String action = request.getParameter("action");
        ZoneDAO zDAO = new ZoneDAO();

        if (action.equalsIgnoreCase("create")) {
            handleCreateZone(request, response, zDAO);
        } else if (action.equalsIgnoreCase("delete")) {
            handleDeleteZone(request, response, zDAO);
        } else if (action.equalsIgnoreCase("update")) {
            handleUpdateZone(request, response, zDAO);
        }
    }

    private void handleCreateZone(HttpServletRequest request, HttpServletResponse response, ZoneDAO zDAO) throws ServletException, IOException {
        try {
            String placeIdStr = request.getParameter("placeID");
            String zoneName = request.getParameter("zoneName");
            int pID = Integer.parseInt(placeIdStr.trim());

            Zone newZone = new Zone();
            newZone.setPlaceID(pID);
            newZone.setZoneName(zoneName);

            boolean success = zDAO.create(newZone);
            System.out.println("Create zone: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/config-zone");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
        }
    }

    private void handleUpdateZone(HttpServletRequest request, HttpServletResponse response, ZoneDAO zDAO) throws ServletException, IOException {
        try {
            String placeIdStr = request.getParameter("placeID");
            String zoneIdStr = request.getParameter("zoneID");
            String statusIdStr = request.getParameter("statusID");
            String zoneName = request.getParameter("zoneName");
            int pID = Integer.parseInt(placeIdStr.trim());
            int zID = Integer.parseInt(zoneIdStr.trim());
            int sID = Integer.parseInt(statusIdStr.trim());

            Zone updateZone = new Zone();
            updateZone.setZoneID(zID);
            updateZone.setPlaceID(pID);
            updateZone.setStatusID(sID);
            updateZone.setZoneName(zoneName);

            boolean success = zDAO.update(updateZone);
            System.out.println("Update zone: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/config-zone");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
        }
    }

    private void handleDeleteZone(HttpServletRequest request, HttpServletResponse response, ZoneDAO zDAO) throws ServletException, IOException {
        try {
            String zoneIdStr = request.getParameter("zoneID");
            int zID = Integer.parseInt(zoneIdStr.trim());

            boolean success = zDAO.softDelete(zID);
            System.out.println("Delete zone: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/config-zone");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-zone.jsp").forward(request, response);
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
