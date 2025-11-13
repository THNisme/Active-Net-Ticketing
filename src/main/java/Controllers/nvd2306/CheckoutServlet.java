/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.EventDao;

import Models.nvd2306.Event;
import Models.nvd2306.TicketItem;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckoutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath() + "</h1>");
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
        response.sendRedirect(request.getContextPath() + "/select-ticket");
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
        String eventId = request.getParameter("eventId");
        String eventName = request.getParameter("eventName");
        String placeName = request.getParameter("placeName");
        String startStr = request.getParameter("startStr"); // nhận từ hidden input
        String selectionsJson = request.getParameter("selectionsJson");
        String totalAmount = request.getParameter("totalAmount");

        // Kiểm tra dữ liệu JSON vé
        if (selectionsJson == null || selectionsJson.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/select-ticket?id=" + eventId);
            return;
        }

        List<TicketItem> tickets = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(selectionsJson);

            for (String key : jsonObj.keySet()) {
                JSONObject t = jsonObj.getJSONObject(key);
                String name = t.getString("name");
                int qty = t.getInt("qty");
                double price = t.getDouble("price");
                double total = qty * price;

                int ticketId = t.getInt("ticketId");
                tickets.add(new TicketItem(ticketId, name, qty, price, total));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Gửi dữ liệu sang checkout.jsp
        request.setAttribute("eventId", eventId);
        request.setAttribute("eventName", eventName);
        request.setAttribute("placeName", placeName);
        request.setAttribute("startStr", startStr);
        request.setAttribute("tickets", tickets);
        request.setAttribute("totalAmount", totalAmount);
        HttpSession session = request.getSession();
        session.setAttribute("tickets", tickets);
        session.setAttribute("totalAmount", totalAmount);

        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
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
