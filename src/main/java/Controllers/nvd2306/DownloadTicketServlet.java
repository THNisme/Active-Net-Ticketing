/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nvd2306;

import DAOs.nvd2306.OrderDAO;
import DAOs.nvd2306.OrderDetailDAO;
import DAOs.nvd2306.TicketDAO;
import Models.nvd2306.Order;
import Utils.nvd2603.TicketPDFGenerator;
import Utils.singleton.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NguyenDuc
 */
@WebServlet(name = "DownloadTicketServlet", urlPatterns = {"/download-ticket"})
public class DownloadTicketServlet extends HttpServlet {

    private final TicketDAO ticketDAO = new TicketDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO detailDAO = new OrderDetailDAO();

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
            out.println("<title>Servlet DownloadTicketServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownloadTicketServlet at " + request.getContextPath() + "</h1>");
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

        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        String sql = """
            SELECT 
                e.EventName,
                p.PlaceName,
                e.StartDate,
                e.EndDate,
                tt.TypeName AS TicketTypeName,
                t.SerialNumber,
                o.ContactFullname
            FROM Tickets t
            JOIN TicketTypes tt ON t.TicketTypeID = tt.TicketTypeID
            JOIN Events e ON tt.EventID = e.EventID
            JOIN Places p ON e.PlaceID = p.PlaceID
            JOIN OrderDetails od ON t.TicketID = od.TicketID
            JOIN Orders o ON od.OrderID = o.OrderID
            WHERE t.TicketID = ?
        """;

        String eventName = "", placeName = "", startDate = "", endDate = "", ticketType = "", serial = "", buyerName = "";

        try (Connection conn = DBContext.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ticketId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                eventName = rs.getString("EventName");
                placeName = rs.getString("PlaceName");
                startDate = String.valueOf(rs.getTimestamp("StartDate"));
                endDate = String.valueOf(rs.getTimestamp("EndDate"));
                ticketType = rs.getString("TicketTypeName");
                serial = rs.getString("SerialNumber");
                buyerName = rs.getString("ContactFullname");
            }

        } catch (Exception e) {
            response.getWriter().println("Lỗi SQL: " + e.getMessage());
            return;
        }

        File pdf = null;
        try {
            pdf = TicketPDFGenerator.createTicketPDF(
                    "C:/ActiveNetTickets",
                    eventName,
                    placeName,
                    "Từ " + startDate + " đến " + endDate,
                    buyerName,
                    ticketType,
                    serial
            );
        } catch (Exception ex) {
            Logger.getLogger(DownloadTicketServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + pdf.getName());

        try (FileInputStream fis = new FileInputStream(pdf); OutputStream os = response.getOutputStream()) {

            fis.transferTo(os);
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
