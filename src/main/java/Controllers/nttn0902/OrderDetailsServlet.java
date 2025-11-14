/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.OrderDetailDAO;
import Models.nttn0902.OrderDetail;
import Models.nttn0902.OrderManagement;
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
@WebServlet(name = "OrderDetailsServlet", urlPatterns = {"/order_details"})
public class OrderDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderDetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailsServlet at " + request.getContextPath() + "</h1>");
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
      try {
           String id_raw = request.getParameter("orderId");
System.out.println("===> orderId nhận được: " + id_raw);

            if (id_raw == null || id_raw.isEmpty()) {
                request.setAttribute("error", "ID đơn hàng không hợp lệ.");
                request.getRequestDispatcher("OrderManagement/OrderDetail.jsp").forward(request, response);
                return;
            }

            int orderId = Integer.parseInt(id_raw);

            OrderDetailDAO dao = new OrderDetailDAO();
            List<OrderDetail> details = dao.getOrderDetails(orderId);

            if (details == null || details.isEmpty()) {
                request.setAttribute("error", "Không tìm thấy dữ liệu đơn hàng.");
                request.getRequestDispatcher("OrderManagement/OrderDetail.jsp").forward(request, response);
                return;
            }

            request.setAttribute("details", details);

            // Forward đúng file JSP trong cấu trúc của bạn
            request.getRequestDispatcher("OrderManagement/OrderDetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi tải chi tiết đơn hàng.");
            request.getRequestDispatcher("OrderManagement/OrderDetail.jsp").forward(request, response);
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
