<%-- 
    Document   : userFrom
    Created on : 8 Oct 2025, 14.10.48
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="Models.ltk1702.User" %>

<%
    User user = (User) request.getAttribute("user");
    boolean isEdit = (user != null);
    int userId = 0;
    if (isEdit) {
        userId = user.getUserID();
    } else if (request.getParameter("userID") != null && !request.getParameter("userID").isEmpty()) {
        userId = Integer.parseInt(request.getParameter("userID"));
    }
    String username = request.getParameter("username") != null ? request.getParameter("username") : (isEdit ? user.getUsername() : "");
    String password = request.getParameter("passwordHash") != null ? request.getParameter("passwordHash") : (isEdit ? user.getPassword() : "");
    String confirmPassword = request.getParameter("confirmPassword") != null ? request.getParameter("confirmPassword") : (isEdit ? user.getPassword() : "");
    String email = request.getParameter("email") != null ? request.getParameter("email")
            : (isEdit && user.getContactEmail() != null ? user.getContactEmail() : "");
    String fullname = request.getParameter("fullname") != null ? request.getParameter("fullname")
            : (isEdit && user.getContactFullname() != null ? user.getContactFullname() : "");

    String phone = request.getParameter("phone") != null ? request.getParameter("phone")
            : (isEdit && user.getContactPhone() != null ? user.getContactPhone() : "");

    String roleParam = request.getParameter("role");
    int role = 0;
    if (roleParam != null && !roleParam.isEmpty()) {
        role = Integer.parseInt(roleParam);
    } else if (isEdit) {
        role = user.getRole();
    }

    if (session.getAttribute("error") != null) {
        request.setAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");
    }
    if (session.getAttribute("mailStatus") != null) {
        request.setAttribute("mailStatus", session.getAttribute("mailStatus"));
        session.removeAttribute("mailStatus");
    }

    if (session.getAttribute("userManage") != null) {
        request.setAttribute("userManage", session.getAttribute("userManage"));
        session.removeAttribute("userManage");
    }
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> người dùng</title>    

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        <!--<link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>-->  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/place-page-style/style.css">

    </head>



    <body>
        <style>
            body {
                background: #111827;
                color: white;
                font-family: 'Inter', sans-serif;
                padding: 20px;
            }

            h1 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .event {
                background: #1f2937;
                border-radius: 10px;
                margin-bottom: 20px;
                overflow: hidden;
                display: flex;
                flex-direction: column;
            }

            /* phần trên */
            .event-main {
                display: flex;
                padding: 15px;
                gap: 15px;
            }

            .event-main img {
                width: 192px;
                height: 132px;
                object-fit: cover;
                border-radius: 6px;
            }

            .event-content {
                flex: 1;
            }

            .event-content h2 {
                font-size: 18px;
                font-weight: bold;
                margin: 0 0 6px 0;
            }

            .event-content .time {
                display: flex;
                align-items: center;
                gap: 6px;
                color: #22c55e;
                font-weight: bold;
                font-size: 14px;
                margin: 4px 0;
            }

            .event-content .location {
                display: flex;
                align-items: center;
                gap: 6px;
                font-size: 14px;
                margin: 4px 0;
                color: #ffffff;
            }

            .event-content .address {
                color: #9ca3af;
                font-size: 13px;
                margin-top: 2px;
            }

            /* actions dưới */
            .actions {
                display: flex;
                justify-content: center;
                /* gom vào giữa */
                align-items: center;
                /* căn giữa dọc */
                gap: 40px;
                /* khoảng cách giữa các nút */
                background: #374151;
                padding: 12px 0;
                border-top: 1px solid #2c2d31;
                border-radius: 0 0 10px 10px;
            }

            .actions a {
                background: none;
                border: none;
                color: white;
                cursor: pointer;
                font-size: 13px;
                display: flex;
                flex-direction: column;
                /* icon trên, chữ dưới */
                align-items: center;
                justify-content: center;
                gap: 6px;
                text-decoration: none;
            }

            .actions a:hover {
                color: #38bdf8;
            }

            /* phân trang */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
                gap: 5px;
            }

            .pagination button {
                background: #1f2937;
                border: none;
                color: white;
                padding: 8px 12px;
                border-radius: 5px;
                cursor: pointer;
            }

            .pagination button.active {
                background: #22c55e;
            }

            .pagination button:disabled {
                background: #374151;
                cursor: not-allowed;
                color: #9ca3af;
            }

            /* Khung tổng chiếm toàn bộ ngang */
            .search-filter {
                padding: 12px 15px;
                border-radius: 6px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                gap: 20px;
                width: 100%;
                box-sizing: border-box;
                margin-bottom: 2rem;
            }

            .search-box {
                flex: 1;
                display: flex;
                align-items: center;
                background: #fff;
                border-radius: 30px;
                overflow: hidden;
                max-width: 500px;
            }

            .search-icon {
                display: flex;
                align-items: center;
                justify-content: center;
                padding-left: 12px;
                padding-right: 8px;
                color: #888;
            }

            .search-box svg {
                stroke: #666;
                /* màu xám nhạt */
            }

            .search-box input {
                border: none;
                padding: 8px 12px;
                outline: none;
                flex: 1;
                font-size: 14px;
            }

            .search-box button {
                border: none;
                background: #9ca3af;
                color: #fff;
                padding: 8px 16px;
                border-radius: 0 30px 30px 0;
                cursor: pointer;
                font-size: 14px;
            }

            .search-box button:hover {
                background: #45a049;
            }


            /* Bộ lọc nút nằm ngang bên phải */
            .filters {
                display: flex;
                gap: 10px;
                flex-shrink: 0;
                /* không co lại */
            }

            .filters .filter {
                text-decoration: none;
                color: #000;
                background: #fff;
                border: 1px solid #ccc;
                border-radius: 20px;
                padding: 8px 18px;
                cursor: pointer;
                font-size: 14px;
            }

            .filters .filter:hover {
                background-color: #4CAF50;
                color: #fff;
            }

            .filters .filter.active {
                background: #4CAF50;
                color: #fff;
                border-color: #4CAF50;
            }
        </style>
        <div class="col-md-6 mx-auto mt-5">
            <h3 class="text-pink mb-3">
                <%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> người dùng
            </h3>

            <% if (request.getAttribute("error") != null) {%>
            <div class="alert alert-danger">
                <%= request.getAttribute("error")%>
            </div>
            <% } %>

            <% if (request.getAttribute("mailStatus") != null) {%>
            <div class="alert alert-success">
                <%= request.getAttribute("mailStatus")%>
            </div>
            <% }%>

            <form id="userForm" action="user-manage" method="post" onsubmit="return validateForm();">
                <input type="hidden" name="userID" value="<%= isEdit ? userId : ""%>">

                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" name="username" class="form-control" 
                           placeholder="Nhập tên đăng nhập người dùng" 
                           value="<%= username%>" required>              
                </div>
                <% if (isEdit) {%>
                <div class="mb-3">
                    <label class="form-label">Họ và tên</label>
                    <input type="text" name="fullname" class="form-control"
                           placeholder="Nhập họ tên người dùng"
                           value="<%= fullname%>">
                </div>

                <div class="mb-3">
                    <label class="form-label">Số điện thoại</label>
                    <input type="text" name="phone" class="form-control"
                           placeholder="Nhập số điện thoại người dùng"
                           value="<%= phone%>">
                </div>
                <% }%>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <% if (!isEdit) {%>
                    <input type="email" name="email" class="form-control" value="<%= email%>" 
                           placeholder="Nhập email để gửi thông tin đăng nhập cho người dùng" required>
                    <% } else {%>
                    <input type="email" name="email" class="form-control" value="<%= email%>" 
                           placeholder="Nhập email để gửi thông báo tài khoản cập nhật" required>
                    <% }%>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mật khẩu</label>                  
                    <div class="input-group">
                        <input type="password" id="password" name="passwordHash" class="form-control"
                               value="" placeholder="Nhập mật khẩu (có ít nhất 8 ký tự, gồm chữ, chữ in hoa, số và ký tự đặc biệt)" required>
                        <button type="button" class="btn btn-light" onclick="togglePassword('password')"><i class="bi bi-eye"></i></button>
                    </div>

                </div>

                <div class="mb-3">
                    <label class="form-label">Xác nhận mật khẩu</label>                   
                    <div class="input-group">
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"
                               value="" placeholder="Nhập lại mật khẩu" required>
                        <button type="button" class="btn btn-light" onclick="togglePassword('confirmPassword')"><i class="bi bi-eye"></i></button>
                    </div>

                </div>

                <div class="mb-3">
                    <label class="form-label">Vai trò</label>
                    <select name="role" class="form-select">
                        <option value="0" <%= role == 0 ? "selected" : ""%>>User</option>
                        <option value="1" <%= role == 1 ? "selected" : ""%>>Admin</option>
                        <option value="2" <%= role == 2 ? "selected" : ""%>>Staff</option>

                    </select>
                </div>

                <% if (!isEdit) { %>
                <button type="submit" name="actionType" value="saveAndSend" class="btn btn-pink">
                    Thêm và gửi mail
                </button>
                <% } else {%>
                <button type="submit" name="actionType" value="editAndSend" class="btn btn-pink">
                    Sửa và gửi mail
                </button>
                <% }%>
                <a href="user-manage?action=list" class="btn prev-btn">Quay lại</a>
            </form>
        </div>      
        <script>
            function togglePassword(id) {
                const input = document.getElementById(id);
                const icon = event.currentTarget.querySelector('i');
                if (input.type === "password") {
                    input.type = "text";
                    icon.classList.remove("bi-eye");
                    icon.classList.add("bi-eye-slash");
                } else {
                    input.type = "password";
                    icon.classList.remove("bi-eye-slash");
                    icon.classList.add("bi-eye");
                }
            }
        </script>

    </body>
</html>