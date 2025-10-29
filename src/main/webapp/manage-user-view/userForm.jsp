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
    String username = request.getParameter("username") != null ? request.getParameter("username") : (isEdit ? user.getUsername() : "");
    String password = request.getParameter("passwordHash") != null ? request.getParameter("passwordHash") : (isEdit ? user.getPassword() : "");
    String confirmPassword = request.getParameter("confirmPassword") != null ? request.getParameter("confirmPassword") : (isEdit ? user.getPassword() : "");
    String email = request.getParameter("email") != null ? request.getParameter("email") : "";
    String roleParam = request.getParameter("role");
    int role = 0;
    if (roleParam != null && !roleParam.isEmpty()) {
        role = Integer.parseInt(roleParam);
    } else if (isEdit) {
        role = user.getRole();
    }
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> người dùng</title>        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>  
        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">
    </head>



    <body class="bg-dark text-white">
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

            <form id="userForm" action="UserServlet" method="post" onsubmit="return validateForm();">
                <input type="hidden" name="userID" value="<%= isEdit ? user.getUserID() : ""%>">

                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" name="username" class="form-control" 
                           placeholder="Nhập tên đăng nhập người dùng" 
                           value="<%= username%>" required>                 

                </div>

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
                    <label class="form-label">Mật khẩu</label><small class="text-warning">
                        (có ít nhất 8 ký tự, gồm chữ, chữ in hoa, số và ký tự đặc biệt)
                    </small>                    
                    <div class="input-group">
                        <input type="password" id="password" name="passwordHash" class="form-control"
                               value="<%= password%>" required>
                        <button type="button" class="btn btn-light" onclick="togglePassword('password')"><i class="bi bi-eye"></i></button>
                    </div>

                </div>

                <div class="mb-3">
                    <label class="form-label">Xác nhận mật khẩu</label>                   
                    <div class="input-group">
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"
                               value="<%= confirmPassword%>" required>
                        <button type="button" class="btn btn-light" onclick="togglePassword('confirmPassword')"><i class="bi bi-eye"></i></button>
                    </div>

                </div>

                <div class="mb-3">
                    <label class="form-label">Vai trò</label>
                    <select name="role" class="form-select">
                        <option value="0" <%= role == 0 ? "selected" : ""%>>User</option>
                        <option value="1" <%= role == 1 ? "selected" : ""%>>Admin</option>

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
                <a href="UserServlet?action=list" class="btn btn-outline-light ms-2">Quay lại</a>
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