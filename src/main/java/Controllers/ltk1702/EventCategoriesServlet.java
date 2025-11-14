/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.ltk1702;

import DAOs.ltk1702.EventCategoriesDAO;
import Models.ltk1702.EventCategories;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
@WebServlet(name = "EventCategoriesServlet", urlPatterns = {"/eventcategories"})
public class EventCategoriesServlet extends HttpServlet {

    private final EventCategoriesDAO dao = new EventCategoriesDAO();

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
            out.println("<title>Servlet EventCategoriesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventCategoriesServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                req.getRequestDispatcher("/view-eventcategories/CategoryForm.jsp").forward(req, resp);
                break;
            case "edit":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("category", dao.getCategoryById(id));
                } catch (NumberFormatException e) {
                    // ignore, forward to new
                }
                req.getRequestDispatcher("/view-eventcategories/CategoryForm.jsp").forward(req, resp);
                break;
            case "delete":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    dao.softDeleteCategory(id);
                } catch (NumberFormatException e) {
                }
                resp.sendRedirect("eventcategories?action=list");
                break;
            default:
                req.setAttribute("categoryList", dao.getAllCategories());
                req.getRequestDispatcher("/view-eventcategories/CategoryList.jsp").forward(req, resp);
                break;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("categoryName");
        String desc = req.getParameter("description");
        String idParam = req.getParameter("categoryID");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

        // === TẠO OBJECT ĐỂ GIỮ LẠI FORM ===
        EventCategories cat = new EventCategories();
        cat.setCategoryID(id);
        cat.setCategoryName(name);
        cat.setDescription(desc);

        // === VALIDATION: EMPTY NAME ===
        if (name == null || name.trim().isEmpty()) {
            req.getSession().setAttribute("error", "Tên thể loại không được để trống!");
            req.getSession().setAttribute("cateData", cat);   // giữ lại dữ liệu
            if (id > 0) {
                resp.sendRedirect("eventcategories?action=edit&id=" + id);
            } else {
                resp.sendRedirect("eventcategories?action=new");
            }
            return;
        }

        // === VALIDATION: UNIQUE WHEN ADD ===
        if (id == 0 && dao.checkNameExists(name.trim())) {
            req.getSession().setAttribute("error", "Tên thể loại đã tồn tại!");
            req.getSession().setAttribute("cateData", cat);
            resp.sendRedirect("eventcategories?action=new");
            return;
        }

        // === LƯU CATEGORY ===
        cat.setCategoryName(name.trim());
        cat.setDescription(desc != null ? desc.trim() : "");
        cat.setStatusID(1);

        if (id == 0) {
            dao.addCategory(cat);
            req.getSession().setAttribute("success", "Đã tạo thể loại mới!");
        } else {
            dao.updateCategory(cat);
            req.getSession().setAttribute("success", "Đã cập nhật thể loại!");
        }

        resp.sendRedirect("eventcategories?action=list");
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
