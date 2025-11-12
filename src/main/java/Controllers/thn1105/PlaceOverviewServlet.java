/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.thn1105;

import DAOs.thn1105.EventDAO;
import DAOs.thn1105.PlaceDAO;
import Models.thn1105.Event;
import Models.thn1105.Place;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(name = "PlaceOverviewServlet", urlPatterns = {"/place-overview"})
public class PlaceOverviewServlet extends HttpServlet {

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
            out.println("<title>Servlet PlaceOverviewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlaceOverviewServlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        PlaceDAO pDao = new PlaceDAO();
        EventDAO eDao = new EventDAO();

        if (action == null) {
            HttpSession session = request.getSession(false); // false để không tạo mới nếu chưa có
            if (session != null) {
                session.invalidate(); // xóa toàn bộ session
            }

            List<Place> placeList = pDao.getAll();

            request.setAttribute("places", placeList);
            request.getRequestDispatcher("view-thn1105/place-overview.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("detail")) {
            String placeIdStr = request.getParameter("pid");
            int placeID = Integer.parseInt(placeIdStr);

            List<Event> eventList = eDao.getEventsByPlaceID(placeID);
            List<Map<String, Object>> jsonList = new ArrayList<>();

            for (Event e : eventList) {
                Place p = pDao.getById(e.getPlaceID());

                Map<String, Object> map = new HashMap<>();
                map.put("id", e.getEventID());
                map.put("title", e.getEventName());

                // format thời gian
                String formattedTime = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(e.getStartDate());
                map.put("time", formattedTime);

                map.put("location", p.getPlaceName());
                map.put("address", p.getAddress());
                map.put("image", e.getImageURL());

                jsonList.add(map);
            }

            String json = new Gson().toJson(jsonList); // JSON bình thường
//            String safeJson = new Gson().toJson(json); // JSON string literal, escape dấu "

            Place p = pDao.getById(placeID);
            int quantity = eventList.size();

            request.setAttribute("place", p);
            request.setAttribute("eventsJsonString", json);
            request.setAttribute("quantity", quantity);
            request.getRequestDispatcher("view-thn1105/place-details.jsp").forward(request, response);
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
        PlaceDAO pDao = new PlaceDAO();

        if (action.equalsIgnoreCase("delete")) {
            String pIdStr = request.getParameter("placeID");
            int pID = Integer.parseInt(pIdStr);
            pDao.softDelete(pID);
            response.sendRedirect("place-overview");
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
