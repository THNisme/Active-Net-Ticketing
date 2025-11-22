<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đổi mật khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>

<body class="bg-dark text-white">

<%@include file="/view-hfs/header.jsp" %>
<%@include file="/view-ttk2008/sidebar-for-user.jsp" %>

<div class="container my-5">
    <div class="card bg-dark text-white mx-auto" style="max-width: 600px;">
        <div class="card-body">

            <h3 class="card-title mb-4">Đổi mật khẩu</h3>

            <!-- Server messages -->
            <c:if test="${not empty message}">
                <div class="alert alert-success">${message}</div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <!-- OTP FORM -->
            <c:if test="${showOtpForm}">
                <form action="changepassword" method="post">
                    <div class="mb-3">
                        <label class="form-label">Nhập OTP</label>
                        <input type="text" class="form-control" name="otpInput" required>
                    </div>

                    <button type="submit" class="btn btn-success">Xác nhận OTP</button>
                </form>
            </c:if>

            <!-- PASSWORD FORM -->
            <c:if test="${!showOtpForm}">
                <form action="changepassword" method="post" onsubmit="return validateForm(event)">

                    <!-- New password -->
                    <div class="mb-3">
                        <label class="form-label">Mật khẩu mới</label>

                        <div class="input-group">
                            <input type="password"
                                   class="form-control"
                                   name="newPassword"
                                   id="newPassword"
                                   required>

                            <span class="input-group-text" onclick="togglePassword('newPassword', this)" style="cursor: pointer;">
                                <i class="fa-solid fa-eye"></i>
                            </span>
                        </div>
                    </div>

                    <!-- Confirm password -->
                    <div class="mb-3">
                        <label class="form-label">Xác nhận mật khẩu</label>

                        <div class="input-group">
                            <input type="password"
                                   class="form-control"
                                   name="confirmPassword"
                                   id="confirmPassword"
                                   required>

                            <span class="input-group-text" onclick="togglePassword('confirmPassword', this)" style="cursor: pointer;">
                                <i class="fa-solid fa-eye"></i>
                            </span>
                        </div>
                    </div>

                    <!-- Client error message -->
                    <p id="formMessage" class="text-danger fw-bold mb-3"></p>

                    <button type="submit" class="btn btn-success  mt-2">Gửi OTP</button>
                    <a href="userinfo" class="btn btn-secondary mt-2">Quay lại</a>

                </form>
            </c:if>

        </div>
    </div>
</div>

<script>

    // Show / Hide password
    function togglePassword(inputId, iconSpan) {
        const input = document.getElementById(inputId);
        const icon = iconSpan.querySelector("i");

        if (input.type === "password") {
            input.type = "text";
            icon.classList.remove("fa-eye");
            icon.classList.add("fa-eye-slash");
        } else {
            input.type = "password";
            icon.classList.remove("fa-eye-slash");
            icon.classList.add("fa-eye");
        }
    }


    function validateForm(event) {

        const pass = document.getElementById("newPassword").value.trim();
        const confirm = document.getElementById("confirmPassword").value.trim();
        const messageEl = document.getElementById("formMessage");

        const passwordRegex =
            /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&]).{8,}$/;

        messageEl.textContent = "";

        if (!passwordRegex.test(pass)) {
            messageEl.textContent =
                "Mật khẩu phải ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!";
            event.preventDefault();
            return false;
        }

        if (pass !== confirm) {
            messageEl.textContent =
                "Mật khẩu xác nhận không khớp!";
            event.preventDefault();
            return false;
        }

        return true;
    }

</script>

</body>
</html>
