<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông tin tài khoản</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <link href="<%= request.getContextPath()%>/css/userinfo-page/userinfo.css" rel="stylesheet" type="text/css"/>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body class="bg-dark text-white">

        <%@include file="/view-hfs/header.jsp" %>
        <%@include file="/view-ttk2008/sidebar-for-user.jsp" %>

        <div class="container my-5">
            <div class="card bg-dark text-white">
                <div class="card-body">

                    <h3 class="card-title mb-4">Thông tin tài khoản</h3>

                    <!-- Server Messages -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <!-- Client Messages -->
                    <p id="formMessage" class="fw-bold mb-3"></p>

                    <form action="userinfo" method="post" onsubmit="return validateForm(event)">

                        <!-- Username -->
                        <div class="mb-3">
                            <label class="form-label">Tên đăng nhập</label>
                            <input type="text"
                                   class="form-control form-control-userinfor"
                                   name="username"
                                   value="${user.username}"
                                   required
                                    >
                        </div>

                        <!-- Fullname -->
                        <div class="mb-3">
                            <label class="form-label">Họ và tên</label>
                            <input type="text"
                                   class="form-control form-control-userinfor"
                                   name="fullname"
                                   value="${user.contactFullname}"
                                   required
                                   minlength="3"
                                   maxlength="100">
                        </div>

                        <!-- Email -->
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email"
                                   class="form-control form-control-userinfor"
                                   name="email"
                                   value="${user.contactEmail}"
                                   required>
                        </div>

                        <!-- Phone -->
                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text"
                                   class="form-control form-control-userinfor"
                                   name="phone"
                                   value="${user.contactPhone}"
                                   required
                                   pattern="^[0-9]{10}$"
                                   title="Số điện thoại phải đủ 10 chữ số">
                        </div>

                        <!-- System info (readonly) -->
                        <div class="mb-3">
                            <label class="form-label">Ngày tạo tài khoản</label>
                            <input type="text"
                                   class="form-control form-control-userinfor"
                                   value="${user.createdAt}"
                                   readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <input type="text"
                                   class="form-control form-control-userinfor"
                                   value="${user.statusId == 1 ? 'Đang hoạt động' : user.statusId == 2 ? 'Ngừng hoạt động' : 'Đã bị xóa'}"
                                   readonly>
                        </div>

                        <div class="mb-4">
                            <label class="form-label">Vai trò</label>
                            <input type="text"
                                   class="form-control form-control-userinfor"
                                   value="${user.role == 1 ? 'Quản trị viên' : 'Người dùng'}"
                                   readonly>
                        </div>

                        <!-- Change password -->
                        <div class="mb-4">
                            <a href="changepassword" class="btn btn-danger w-100">
                                <i class="fa-solid fa-key me-2"></i>Đổi mật khẩu
                            </a>
                        </div>

                        <button type="submit" class="btn btn-warning w-100">
                            <i class="fa-solid fa-floppy-disk me-2"></i>Cập nhật thông tin
                        </button>

                    </form>
                </div>
            </div>
        </div>

        <script>
            function validateForm(event) {

                const username = document.querySelector("input[name='username']").value.trim();
                const fullname = document.querySelector("input[name='fullname']").value.trim();
                const email = document.querySelector("input[name='email']").value.trim();
                const phone = document.querySelector("input[name='phone']").value.trim();
                const messageEl = document.getElementById("formMessage");

                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                const phoneRegex = /^[0-9]{10}$/;

                messageEl.textContent = "";
                messageEl.className = "fw-bold mb-3";


                if (!emailRegex.test(email)) {
                    messageEl.textContent = "Email không hợp lệ";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }

                if (!phoneRegex.test(phone)) {
                    messageEl.textContent = "Số điện thoại phải gồm đúng 10 chữ số";
                    messageEl.classList.add("text-danger");
                    event.preventDefault();
                    return false;
                }

                return true;
            }
        </script>

    </body>
</html>
