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

        // ✅ Cho phép public resources
        if (uri.endsWith("login.jsp")
                || uri.endsWith("/login")
                || uri.endsWith("/home")
                || uri.contains("/register")
                || uri.contains("/css")
                || uri.contains("/js")
                || uri.contains("/images")
                || uri.contains("/assets")) {
            chain.doFilter(request, response);
            return;
        }

        //  Kiểm tra đăng nhập
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null) {
            // guest => quay lại login
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        //  Lấy role
        int role = user.getRole();

        //  Bảo vệ vùng admin
        if (uri.contains("/admin") && role != 1) {
            res.sendRedirect(req.getContextPath() + "/accessDenied");
            return;
        }

        //  Bảo vệ vùng user (nếu cần)
        if (uri.contains("/user") && role != 0) {
            res.sendRedirect(req.getContextPath() + "/accessDenied");
            return;
        }

        // Cho phép request tiếp tục
        chain.doFilter(request, response);
    }
}
