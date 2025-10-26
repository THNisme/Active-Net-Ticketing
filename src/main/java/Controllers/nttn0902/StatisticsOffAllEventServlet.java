/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.StatisticsOffAllEventDAO;
import Models.nttn0902.StatisticsOffAllEvent;
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
@WebServlet(name = "StatisticsOffAllEventServlet", urlPatterns = {"/statisticsoffallevent"})
public class StatisticsOffAllEventServlet extends HttpServlet {

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
         StatisticsOffAllEventDAO dao = new StatisticsOffAllEventDAO ();

        List<StatisticsOffAllEvent> ticketStats = dao.getThongKeTheoSuKien();
        double tongDoanhThu = dao.getTongDoanhThu();
        int tongVePhatHanh = dao.getTongVePhatHanh();
        int tongVeDaBan = dao.getTongVeDaBan();

        double phanTramDoanhThu = (tongVePhatHanh > 0)
                ? (double) tongVeDaBan / tongVePhatHanh * 100
                : 0;

        double phanTramVeBan = (tongVePhatHanh > 0)
                ? (double) tongVeDaBan / tongVePhatHanh * 100
                : 0;

        // Gửi dữ liệu sang JSP
        request.setAttribute("ticketStats", ticketStats);
        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("tongVePhatHanh", tongVePhatHanh);
        request.setAttribute("tongVeDaBan", tongVeDaBan);
        request.setAttribute("phanTramDoanhThu", phanTramDoanhThu);
        request.setAttribute("phanTramVeBan", phanTramVeBan);
        request.setAttribute("currentTime", new java.util.Date());

        RequestDispatcher rd = request.getRequestDispatcher("/StatisticsOffAllEvent.jsp");
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
