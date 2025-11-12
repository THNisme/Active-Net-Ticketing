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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(name = "AdminCenterServlet", urlPatterns = {"/admincenter"})
public class AdminCenterServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminCenterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminCenterServlet at " + request.getContextPath() + "</h1>");
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
        EventDAO eDao = new EventDAO();
        PlaceDAO pDao = new PlaceDAO();

        if (action == null) {
            List<Event> eventList = eDao.getAll();
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
            request.setAttribute("eventsJsonString", json);
            request.getRequestDispatcher("view-thn1105/admincenter.jsp").forward(request, response);
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
