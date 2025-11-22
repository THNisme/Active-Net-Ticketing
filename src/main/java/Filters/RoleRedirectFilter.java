package Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import Models.nvd2306.User;

@WebFilter(filterName = "RoleRedirectFilter", urlPatterns = {"/login", "/home"})
public class RoleRedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();

        // =============================
        // CHƯA LOGIN → cho vào login, home
        // =============================
        if (user == null) {
            chain.doFilter(request, response);
            return;
        }

        int role = user.getRole();

        // =============================
        // ROLE 1 = ADMIN
        // =============================
        if (role == 1) {

            // Nếu admin vào login → chuyển admincenter
            if (uri.endsWith("/login")) {
                res.sendRedirect(ctx + "/admincenter");
                return;
            }

            // Nếu admin truy cập staff page → chặn
            if (uri.startsWith(ctx + "/staff")) {
                res.sendRedirect(ctx + "/admincenter");
                return;
            }
        }

        // =============================
        // ROLE 2 = STAFF
        // =============================
        if (role == 2) {

            // Nếu staff vào login → chuyển vào trang staff
            if (uri.endsWith("/login")) {
                res.sendRedirect(ctx + "/staff/orders");
                return;
            }

            // Nếu staff vào customer-only page → CHO QUA
            // Nếu bạn muốn redirect staff khi vào home → bật dòng dưới:
            // if (uri.endsWith("/home")) res.sendRedirect(ctx + "/staff/orders");
        }

        // =============================
        // ROLE 0 = CUSTOMER
        // =============================
        if (role == 0) {

            // Nếu customer vào trang staff → CHẶN
            if (uri.startsWith(ctx + "/staff")) {
                res.sendRedirect(ctx + "/home");
                return;
            }

            // Nếu customer vào login → chuyển home
            if (uri.endsWith("/login")) {
                res.sendRedirect(ctx + "/home");
                return;
            }
        }

        // Cho qua các trường hợp hợp lệ
        chain.doFilter(request, response);
    }
}
