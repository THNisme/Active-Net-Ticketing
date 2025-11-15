/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.OrderManagementDAO;
import Models.nttn0902.OrderManagement;
import jakarta.servlet.RequestDispatcher;
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
 * @author NGUYEN
 */
@WebServlet(name = "OrderManagement", urlPatterns = {"/order_management"})
public class OrderManagementServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderManagement at " + request.getContextPath() + "</h1>");
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
        OrderManagementDAO dao = new OrderManagementDAO();
        List<OrderManagement> orderList;

        // Lấy tham số request
        String keyword = request.getParameter("keyword");
        String eventIdParam = request.getParameter("eventID");

        Integer eventId = null;
        if (eventIdParam != null && !eventIdParam.isEmpty()) {
            eventId = Integer.parseInt(eventIdParam);
        }

        // Lấy tên sự kiện
        String eventName = null;
        if (eventId != null) {
            eventName = dao.getEventNameById(eventId);
        }

        // Gửi eventID + eventName sang JSP
        request.setAttribute("eventID", eventId);
        request.setAttribute("eventName", eventName);

        // Lấy danh sách các sự kiện khác (dropdown)
        List<Models.nttn0902.Event> otherEvents;
        if (eventId != null) {
            otherEvents = dao.getOtherEvents(eventId); // trừ sự kiện hiện tại
        } else {
            otherEvents = dao.getAllEvents(); // nếu chưa chọn event, hiển thị tất cả
        }
        request.setAttribute("otherEvents", otherEvents);

        //  SEARCH
        if (keyword != null && !keyword.trim().isEmpty()) {

            // Có search → tìm trong phạm vi eventID
            orderList = dao.searchOrders(keyword.trim(), eventId);

            // Giữ keyword để hiển thị lại
            request.setAttribute("keyword", keyword);

        } else {

            // Không search → trả danh sách theo event
            if (eventId != null) {
                orderList = dao.getAllOrdersByEventId(eventId);
            } else {
                orderList = dao.getAllOrders();
            }
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("orderList", orderList);
        request.setAttribute("currentTime", new java.util.Date());

        RequestDispatcher rd = request.getRequestDispatcher("/OrderManagement/OrderManagement.jsp");
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {

            case "delete":
                int orderID = Integer.parseInt(request.getParameter("orderID"));
                int eventID = Integer.parseInt(request.getParameter("eventID"));
                OrderManagementDAO dao = new OrderManagementDAO();
                boolean deleted = dao.softDeleteOrder(orderID);

                if (deleted) {
                    request.getSession().setAttribute("message", "Xóa đơn hàng thành công!");
                } else {
                    request.getSession().setAttribute("error", "Xóa đơn hàng thất bại!");
                }

                // Reload lại đúng eventID đang xem
                response.sendRedirect("order_management?eventID=" + eventID);
                return;
            default:
                // Nếu không phải thao tác POST nào → quay về GET
                doGet(request, response);
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
