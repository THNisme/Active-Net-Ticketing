package Controllers.ttk2008;

import DAOs.ttk2008.DepositPromotionDAO;
import DAOs.ttk2008.StatusDAO;
import Models.ttk2008.DepositPromotion;
import Models.nvd2306.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

@WebServlet(name = "DepositPromotionServlet", urlPatterns = {"/promotions"})
public class DepositPromotionServlet extends HttpServlet {

    private DepositPromotionDAO dao = new DepositPromotionDAO();
    private StatusDAO statusDAO = new StatusDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null || user.getRole() != 1) { // chỉ admin
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.isEmpty() || action.equals("list")) {
            // List tất cả promotion
            request.setAttribute("promotions", dao.getAll());
            request.getRequestDispatcher("/view-ttk2008/depositPromotions.jsp").forward(request, response);
            return;
        }

        if (action.equals("create")) {
            request.setAttribute("promotion", null); // form rỗng
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        if (action.equals("edit")) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                DepositPromotion p = dao.getById(id);
                request.setAttribute("promotion", p);
            }
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        if (action.equals("delete")) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                Integer deletedId = statusDAO.getStatusIdByCode("DELETED");

                dao.softDelete(id);
            }
            response.sendRedirect(request.getContextPath() + "/promotions?action=list");
            return;
        }

        // fallback
        response.sendRedirect(request.getContextPath() + "/promotions?action=list");
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(false);
    User user = session != null ? (User) session.getAttribute("user") : null;
    if (user == null || user.getRole() != 1) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    try {
        String idStr = request.getParameter("promotionID");
        String minStr = request.getParameter("minAmount");
        String maxStr = request.getParameter("maxAmount");
        String discountStr = request.getParameter("discountPercent");
        String startStr = request.getParameter("startDate");
        String endStr = request.getParameter("endDate");
        String statusCode = request.getParameter("statusCode");

        // Validation cơ bản
        if (minStr == null || minStr.isEmpty() || discountStr == null || discountStr.isEmpty()
                || startStr == null || startStr.isEmpty() || endStr == null || endStr.isEmpty()) {
            DepositPromotion p = new DepositPromotion();
            p.setMinAmount(minStr != null && !minStr.isEmpty() ? new BigDecimal(minStr) : null);
            p.setMaxAmount(maxStr != null && !maxStr.isEmpty() ? new BigDecimal(maxStr) : null);
            p.setDiscountPercent(discountStr != null && !discountStr.isEmpty() ? new BigDecimal(discountStr) : null);
            request.setAttribute("promotion", p);
            request.setAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc.");
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        // Chuyển kiểu dữ liệu
        BigDecimal min = new BigDecimal(minStr);
        BigDecimal max = (maxStr == null || maxStr.isEmpty()) ? null : new BigDecimal(maxStr);
        BigDecimal discount = new BigDecimal(discountStr);
        Timestamp start = Timestamp.valueOf(startStr + " 00:00:00");
        Timestamp end = Timestamp.valueOf(endStr + " 23:59:59");

        // Validation logic
        if (max != null && min.compareTo(max) > 0) {
            DepositPromotion p = new DepositPromotion();
            p.setMinAmount(min);
            p.setMaxAmount(max);
            p.setDiscountPercent(discount);
            p.setStartDate(start);
            p.setEndDate(end);
            request.setAttribute("promotion", p);
            request.setAttribute("error", "Giá trị tối thiểu không được lớn hơn giá trị tối đa.");
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        if (start.after(end)) {
            DepositPromotion p = new DepositPromotion();
            p.setMinAmount(min);
            p.setMaxAmount(max);
            p.setDiscountPercent(discount);
            p.setStartDate(start);
            p.setEndDate(end);
            request.setAttribute("promotion", p);
            request.setAttribute("error", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        if (discount.signum() < 0) {
            DepositPromotion p = new DepositPromotion();
            p.setMinAmount(min);
            p.setMaxAmount(max);
            p.setDiscountPercent(discount);
            p.setStartDate(start);
            p.setEndDate(end);
            request.setAttribute("promotion", p);
            request.setAttribute("error", "Phần trăm thưởng không được âm.");
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        // Lấy StatusID
        Integer statusId = statusDAO.getStatusIdByCode(statusCode != null ? statusCode : "ACTIVE");
        if (statusId == null) statusId = 1; // fallback

        // Tạo object promotion
        DepositPromotion p = new DepositPromotion();
        p.setMinAmount(min);
        p.setMaxAmount(max);
        p.setDiscountPercent(discount);
        p.setStartDate(start);
        p.setEndDate(end);
        p.setStatusID(statusId);

        boolean success;
        if (idStr == null || idStr.isEmpty()) {
            success = dao.create(p);
        } else {
            p.setPromotionID(Integer.parseInt(idStr));
            success = dao.update(p);
        }

        if (!success) {
            request.setAttribute("promotion", p); // giữ dữ liệu
            request.setAttribute("error", (idStr == null ? "Tạo" : "Cập nhật") + " khuyến mãi thất bại.");
            request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
            return;
        }

        // Nếu thành công thì redirect
        response.sendRedirect(request.getContextPath() + "/promotions?action=list");

    } catch (Exception ex) {
        ex.printStackTrace();
        DepositPromotion p = new DepositPromotion();
        request.setAttribute("promotion", p);
        request.setAttribute("error", "Lỗi hệ thống: " + ex.getMessage());
        request.getRequestDispatcher("/view-ttk2008/depositPromotionForm.jsp").forward(request, response);
    }
}
}