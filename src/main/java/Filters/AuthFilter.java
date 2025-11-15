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

//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpSession session = req.getSession(false);
//
//        String uri = req.getRequestURI();
//        String ctx = req.getContextPath();
//
//         
//        if (uri.startsWith(ctx + "/login")
//                || uri.startsWith(ctx + "/register")
//                || uri.startsWith(ctx + "/assets")
//                || uri.startsWith(ctx + "/css")
//                || uri.startsWith(ctx + "/js")
//                || uri.startsWith(ctx + "/img")
//                || uri.startsWith(ctx + "/uploads")
//                || uri.equals(ctx + "/")
//                || uri.equals(ctx + "/home")
//                || uri.equals(ctx + "/check")
//                || uri.startsWith(ctx + "/accessDenied")) { // THÊM DÒNG NÀY
//            chain.doFilter(request, response);
//            return;
//        }
//     
//
//        // ✅ 2. Kiểm tra người dùng đã đăng nhập chưa
//        User user = (session != null) ? (User) session.getAttribute("user") : null;
//        if (user == null) {
//            res.sendRedirect(ctx + "/login");
//            return;
//        }
//
//        int role = user.getRole(); // 0 = user thường, 1 = admin
//
//        // ✅ 3. Phân quyền truy cập
//        //block User
//        if (uri.startsWith(ctx + "/admin")) {
//            if (role != 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//
//        if (uri.startsWith(ctx + "/event-form")) {
//            if (role != 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//
//        if (uri.startsWith(ctx + "/config")) {
//            if (role != 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        if (uri.startsWith(ctx + "/statistic")) {
//            if (role != 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        if (uri.startsWith(ctx + "/froala")) {
//            if (role != 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//
//        if (uri.startsWith(ctx + "/place")) {
//            if (role != 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        // Block Admin
//        if (uri.startsWith(ctx + "/wallet")) {
//            if (role == 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        if (uri.startsWith(ctx + "/deposit")) {
//            if (role == 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        if (uri.startsWith(ctx + "/payment")) {
//            if (role == 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        if (uri.startsWith(ctx + "/transactions")) {
//            if (role == 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//        if (uri.startsWith(ctx + "/userinfo")) {
//            if (role == 1) {
//                res.sendRedirect(ctx + "/accessDenied");
//                return;
//            }
//        }
//
//        // ✅ 4. Nếu người đã đăng nhập mà vẫn cố vào /login → chuyển đúng trang
//        if (uri.equals(ctx + "/login")) {
//            if (role == 1) {
//                res.sendRedirect(ctx + "/admincenter");
//            } else {
//                res.sendRedirect(ctx + "/home");
//            }
//            return;
//        }
//
//        // ✅ 5. Cho phép qua nếu hợp lệ
//        chain.doFilter(request, response);
//    }
//}
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();      // /Active_Net_Ticketing/home
        String ctx = req.getContextPath();     // /Active_Net_Ticketing

        // Lấy user trong session (nếu có)
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        Integer role = (user != null) ? user.getRole() : null;   // 0: user, 1: admin

        // ===== 1. Các đường dẫn PUBLIC (không cần đăng nhập) =====
        boolean isPublic
                = uri.startsWith(ctx + "/login")
                || uri.startsWith(ctx + "/register")
                || uri.startsWith(ctx + "/assets")
                || uri.startsWith(ctx + "/css")
                || uri.startsWith(ctx + "/js")
                || uri.startsWith(ctx + "/img")
                || uri.startsWith(ctx + "/uploads")
                || uri.contains(ctx + "/event-detail")
                || uri.equals(ctx + "/")
                || uri.startsWith(ctx + "/home")
                || uri.startsWith(ctx + "/event-detail")
                || uri.startsWith(ctx + "/filter-events")
                || uri.startsWith(ctx + "/select-ticket")
                || uri.startsWith(ctx + "/checkout")
                || uri.startsWith(ctx + "/payments")
                || uri.startsWith(ctx + "/check")
                || uri.startsWith(ctx + "/accessDenied");

        // ===== 2. Đang login mà user đã đăng nhập rồi → chuyển hướng đúng trang =====
        if (uri.startsWith(ctx + "/login") && user != null) {
            if (role != null && role == 1) {
                res.sendRedirect(ctx + "/admincenter");
            } else {
                res.sendRedirect(ctx + "/home");
            }
            return;
        }

        // ===== 3. Nếu là đường dẫn public → cho qua luôn =====
        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        // ===== 4. Từ đây về sau là đường dẫn CẦN ĐĂNG NHẬP =====
        if (user == null) {   // chưa đăng nhập
            res.sendRedirect(ctx + "/login");
            return;
        }

        int r = role;   // chắc chắn không null vì user != null

        // ===== 5. Chặn user thường truy cập phần admin =====
        if (uri.startsWith(ctx + "/admin")
                || uri.startsWith(ctx + "/event-form")
                || uri.startsWith(ctx + "/config")
                || uri.startsWith(ctx + "/statistic")
                || uri.startsWith(ctx + "/froala")
                || uri.startsWith(ctx + "/place")) {

            if (r != 1) { // không phải admin
                res.sendRedirect(ctx + "/accessDenied");
                return;
            }
        }

        // ===== 6. Chặn admin đi vào khu chỉ dành cho user thường =====
        if (r == 1 && (uri.startsWith(ctx + "/wallet")
                || uri.startsWith(ctx + "/deposit")
                || uri.startsWith(ctx + "/payment") // nếu servlet là /payments thì đổi lại
                || uri.startsWith(ctx + "/payments")
                || uri.startsWith(ctx + "/transactions")
                || uri.startsWith(ctx + "/userinfo"))) {

            res.sendRedirect(ctx + "/accessDenied");
            return;
        }

        // ===== 7. Mọi thứ hợp lệ → tiếp tục =====
        chain.doFilter(request, response);

    }
}
