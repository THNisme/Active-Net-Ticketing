<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông tin tài khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <link href="<%= request.getContextPath()%>/css/userinfo-page/userinfo.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="bg-dark text-white">

        <%@include file="/view-hfs/header.jsp" %>

        <div class="container my-5">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 mb-3">
                    <div class="list-group">
                        <a href="<%= request.getContextPath() %>/userinfo" class="list-group-item list-group-item-action active">
                            <i class="fa-solid fa-user me-2"></i>Thông tin tài khoản
                        </a>
                        <a href="<%= request.getContextPath() %>/myticket" class="list-group-item list-group-item-action">
                            <i class="fa-solid fa-ticket me-2"></i>Vé của tôi
                        </a>
                        <a href="<%= request.getContextPath() %>/myevents" class="list-group-item list-group-item-action">
                            <i class="fa-solid fa-wallet me-2"></i>Ví của tôi
                        </a>
                    </div>
                </div>

                <!-- Content -->
                <div class="col-md-9">
                    <div class="card bg-dark text-white">
                        <div class="card-body">
                            <h3 class="card-title mb-4">Thông tin tài khoản</h3>

                            <!-- Messages -->
                            <c:if test="${not empty message}">
                                <div class="alert alert-success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                            <!-- Form -->
                            <form action="userinfo" method="post" onsubmit="return validateForm(event)">
                                <div class="mb-3">
                                    <label class="form-label">Tên đăng nhập</label>
                                    <input type="text" class="form-control" name="username" value="${user.username}" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Họ và tên</label>
                                    <input type="text" class="form-control" name="fullname" value="${user.contactFullname}">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" class="form-control" name="email" value="${user.contactEmail}">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Số điện thoại</label>
                                    <input type="text" class="form-control" name="phone" value="${user.contactPhone}">
                                </div>

                                <!-- Password -->
                                <div class="mb-3 position-relative">
                                    <label class="form-label">Mật khẩu mới</label>
                                    <input type="password" class="form-control" name="newPassword" id="newPassword" oninput="toggleConfirm()">
                                    <i class="fa-solid fa-eye position-absolute top-70 end-0 translate-middle-y me-3 toggle-password" id="togglePass"></i>
                                </div>

                                <div id="confirmSection" style="display:none;" class="mb-3 position-relative">
                                    <label class="form-label">Xác nhận mật khẩu</label>
                                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword">
                                    <i class="fa-solid fa-eye position-absolute top-70 end-0 translate-middle-y me-3 toggle-password" id="toggleConfirm"></i>
                                </div>

                                <!-- Readonly info -->
                                <div class="mb-3">
                                    <label class="form-label">Ngày tạo tài khoản</label>
                                    <input type="text" class="form-control" value="${user.createdAt}" readonly>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Trạng thái</label>
                                    <input type="text" class="form-control"
                                           value="${user.statusId == 1 ? 'Đang hoạt động' : user.statusId == 2 ? 'Ngừng hoạt động' : 'Đã bị xóa'}"
                                           readonly>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Vai trò</label>
                                    <input type="text" class="form-control"
                                           value="${user.role == 1 ? 'Quản trị viên' : 'Người dùng'}"
                                           readonly>
                                </div>

                                <button type="submit" class="btn btn-warning">Cập nhật</button>
                                <p id="formMessage" class="mt-3"></p>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            // Toggle password visibility
            function toggleConfirm() {
                const newPass = document.getElementById('newPassword').value;
                document.getElementById('confirmSection').style.display = newPass ? 'block' : 'none';
            }

            function toggleVisibility(inputId, toggleId) {
                const input = document.getElementById(inputId);
                const toggle = document.getElementById(toggleId);
                toggle.addEventListener('click', () => {
                    input.type = input.type === 'password' ? 'text' : 'password';
                    toggle.classList.toggle('fa-eye');
                    toggle.classList.toggle('fa-eye-slash');
                });
            }

            toggleVisibility('newPassword', 'togglePass');
            toggleVisibility('confirmPassword', 'toggleConfirm');

            // Validate form
            function validateForm(event) {
                const fullname = document.querySelector("input[name='fullname']").value.trim();
                const email = document.querySelector("input[name='email']").value.trim();
                const phone = document.querySelector("input[name='phone']").value.trim();
                const pass = document.getElementById("newPassword").value.trim();
                const confirm = document.getElementById("confirmPassword").value.trim();
                const messageEl = document.getElementById("formMessage");

                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                const phoneRegex = /^[0-9]{10}$/;
                const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&]).{8,}$/;

                messageEl.textContent = "";
                messageEl.className = "form-message";

                if (fullname === "") {
                    messageEl.textContent = "Họ và tên không được để trống!";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }

                if (!emailRegex.test(email)) {
                    messageEl.textContent = "Email không hợp lệ!";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }

                if (!phoneRegex.test(phone)) {
                    messageEl.textContent = "Số điện thoại không hợp lệ!";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }

                if (pass && !passwordRegex.test(pass)) {
                    messageEl.textContent = "Mật khẩu phải ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }

                if (pass && pass !== confirm) {
                    messageEl.textContent = "Mật khẩu xác nhận không khớp!";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }
            }
        </script>

    </body>
</html>
