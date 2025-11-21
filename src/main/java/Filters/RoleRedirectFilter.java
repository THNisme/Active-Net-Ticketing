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
        if (user == null) {
            chain.doFilter(request, response); 
            return;
        }

        int role = user.getRole();
       
        if ((role == 1 || role == 2) && (req.getRequestURI().endsWith("/login") || req.getRequestURI().endsWith("/home"))) {
            res.sendRedirect(req.getContextPath() + "/admincenter");
            return;
        }
       
        if (role == 0 && req.getRequestURI().endsWith("/login")) {
            res.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        
        chain.doFilter(request, response);
    }
}
