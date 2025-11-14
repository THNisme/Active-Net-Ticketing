package Filters;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import Models.nvd2306.User;

/**
 *
 * @author Acer
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();

        // ✅ 1. Cho phép truy cập các tài nguyên công cộng (không cần login)
        if (uri.startsWith(ctx + "/login")
                || uri.startsWith(ctx + "/register")
                || uri.startsWith(ctx + "/assets")
                || uri.startsWith(ctx + "/css")
                || uri.startsWith(ctx + "/js")
                || uri.startsWith(ctx + "/img")
                || uri.startsWith(ctx + "/uploads")
                || uri.equals(ctx + "/")
                || uri.equals(ctx + "/home")
                || uri.startsWith(ctx + "/accessDenied")) { // THÊM DÒNG NÀY
            chain.doFilter(request, response);
            return;
        }

        // ✅ 2. Kiểm tra người dùng đã đăng nhập chưa
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            res.sendRedirect(ctx + "/login");
            return;
        }

        int role = user.getRole(); // 0 = user thường, 1 = admin

        // ✅ 3. Phân quyền truy cập
        //block User
        if (uri.startsWith(ctx + "/admin")) {
            if (role != 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }

        if (uri.startsWith(ctx + "/event-form")) {
            if (role != 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }

        
        if (uri.startsWith(ctx + "/config")) {
            if (role != 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        if (uri.startsWith(ctx + "/statistic")) {
            if (role != 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        if (uri.startsWith(ctx + "/froala")) {
            if (role != 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        
        if (uri.startsWith(ctx + "/place")) {
            if (role != 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        // Block Admin
        if (uri.startsWith(ctx + "/wallet")) {
            if (role == 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        if (uri.startsWith(ctx + "/deposit")) {
            if (role == 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        if (uri.startsWith(ctx + "/payment")) {
            if (role == 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        if (uri.startsWith(ctx + "/transactions")) {
            if (role == 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }
        if (uri.startsWith(ctx + "/userinfo")) {
            if (role == 1) {
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }

        // ✅ 4. Nếu người đã đăng nhập mà vẫn cố vào /login → chuyển đúng trang
        if (uri.equals(ctx + "/login")) {
            if (role == 1) {
                res.sendRedirect(ctx + "/admincenter");
            } else {
                res.sendRedirect(ctx + "/home");
            }
            return;
        }

        // ✅ 5. Cho phép qua nếu hợp lệ
        chain.doFilter(request, response);
    }
}
