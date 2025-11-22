package Controllers.ttk2008;

import DAOs.ttk2008.UserInfoDAO;
import Models.nvd2306.User;
import Models.ttk2008.UserInfo;
import MD5.HashPassword;
import Services.ltk1702.MailService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/changepassword"})
public class ChangePasswordServlet extends HttpServlet {

    // Thời gian OTP hợp lệ (ms)
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserInfoDAO dao = new UserInfoDAO();
        UserInfo userInfo = dao.getUserById(loginUser.getUserID());

        request.setAttribute("user", userInfo);
        request.getRequestDispatcher("view-ttk2008/changepassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserInfoDAO dao = new UserInfoDAO();
        UserInfo userInfo = dao.getUserById(loginUser.getUserID());

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String otpInput = request.getParameter("otpInput");
        if (otpInput != null) {
            otpInput = otpInput.trim();
        }

        // -----------------------------
        // Xử lý OTP submit
        // -----------------------------
        if (otpInput != null) {
            String otpSession = (String) session.getAttribute("otp");
            String newPasswordTemp = (String) session.getAttribute("newPasswordTemp");
            Long otpCreatedTime = (Long) session.getAttribute("otpCreatedTime");

            boolean otpExpired = otpCreatedTime == null || (System.currentTimeMillis() - otpCreatedTime > OTP_EXPIRATION_TIME);

            if (otpExpired) {
                // OTP hết hạn
                session.removeAttribute("otp");
                session.removeAttribute("newPasswordTemp");
                session.removeAttribute("otpCreatedTime");

                request.setAttribute("error", "OTP đã hết hạn. Vui lòng gửi lại OTP mới.");
                request.setAttribute("user", userInfo);
                request.getRequestDispatcher("view-ttk2008/changepassword.jsp").forward(request, response);
                return;
            }

            if (otpSession != null && otpInput.equals(otpSession)) {
                // OTP đúng → đổi mật khẩu
                userInfo.setPasswordHash(HashPassword.hashMD5(newPasswordTemp));
                dao.updateUser(userInfo);

                // Xóa session OTP
                session.removeAttribute("otp");
                session.removeAttribute("newPasswordTemp");
                session.removeAttribute("otpCreatedTime");

                request.setAttribute("message", "Cập nhật mật khẩu thành công!");
            } else {
                request.setAttribute("error", "OTP không hợp lệ! Mật khẩu không được cập nhật.");
                request.setAttribute("showOtpForm", true);
            }

            request.setAttribute("user", userInfo);
            request.getRequestDispatcher("view-ttk2008/changepassword.jsp").forward(request, response);
            return;
        }

        // -----------------------------
        // Submit mật khẩu mới → gửi OTP
        // -----------------------------
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu không trùng khớp!");
                request.setAttribute("user", userInfo);
                request.getRequestDispatcher("view-ttk2008/changepassword.jsp").forward(request, response);
                return;
            }

            // Sinh OTP 6 chữ số
            String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
            session.setAttribute("otp", otp);
            session.setAttribute("newPasswordTemp", newPassword);
            session.setAttribute("otpCreatedTime", System.currentTimeMillis());

            // Gửi OTP email
            MailService.sendOtpEmail(userInfo.getContactEmail(), userInfo.getContactFullname(), otp);

            request.setAttribute("showOtpForm", true);
            request.setAttribute("user", userInfo);
            request.getRequestDispatcher("view-ttk2008/changepassword.jsp").forward(request, response);
            return;
        }

        request.setAttribute("user", userInfo);
        request.getRequestDispatcher("view-ttk2008/changepassword.jsp").forward(request, response);
    }
}
