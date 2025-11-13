/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;


import DAOs.ThongKeDAO;
import Models.ThongKe;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 *
 * @author NGUYEN
 */
@WebServlet(name = "ThongKeServlet", urlPatterns = {"/thongke"})
public class ThongKeServlet extends HttpServlet {

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
            out.println("<title>Servlet ThongKeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ThongKeServlet at " + request.getContextPath() + "</h1>");
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
   ThongKeDAO dao = new ThongKeDAO();

        // ✅ Lấy dữ liệu thống kê tổng
        double tongDoanhThu = dao.getTongDoanhThu();
        int tongVePhatHanh = dao.getTongVePhatHanh();
        int tongVeDaBan = dao.getTongVeDaBan();
        double phanTramVeBan = dao.getPhanTramVeBan();
        double phanTramDoanhThu = dao.getPhanTramDoanhThu();
        List<ThongKe> ticketStats = dao.getThongKeTheoSuKien();

        // ✅ Đẩy dữ liệu lên JSP
        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("tongVePhatHanh", tongVePhatHanh);
        request.setAttribute("tongVeDaBan", tongVeDaBan);
        request.setAttribute("phanTramVeBan", phanTramVeBan);
        request.setAttribute("phanTramDoanhThu", phanTramDoanhThu);
        request.setAttribute("ticketStats", ticketStats);
        // ✅ Chuyển hướng sang trang JSP hiển thị
        request.getRequestDispatcher("thongke.jsp").forward(request, response);
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
