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
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> người dùng</title>
        <base href="<%= request.getContextPath()%>/">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>
        <link href="<%= request.getContextPath()%>/css/navigationUI/header.css" rel="stylesheet" type="text/css"/>
    </head>

    

    <body class="bg-dark text-white">
        <div class="container py-4">
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
                           value="<%= isEdit ? user.getUsername() : ""%>" required>

                </div>

                <% if (!isEdit) { %>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control"
                           placeholder="Nhập email để gửi thông tin đăng nhập cho người dùng" required>
                </div>
                <% }%>

                <div class="mb-3">
                    <label class="form-label">Mật khẩu</label><small class="text-warning">
                        (có ít nhất 8 ký tự, gồm chữ, chữ in hoa, số và ký tự đặc biệt)
                    </small>
                    <input type="password" id="password" name="passwordHash" class="form-control"
                           value="<%= isEdit ? user.getPassword() : ""%>" required>                    
                </div>

                <div class="mb-3">
                    <label class="form-label">Xác nhận mật khẩu</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"
                           value="<%= isEdit ? user.getPassword() : ""%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Vai trò</label>
                    <select name="role" class="form-select">
                        <option value="0" <%= isEdit && user.getRole() == 0 ? "selected" : ""%>>User</option>
                        <option value="1" <%= isEdit && user.getRole() == 1 ? "selected" : ""%>>Admin</option>
                    </select>
                </div>

                <% if (!isEdit) { %>
                <button type="submit" name="actionType" value="saveAndSend" class="btn btn-pink">
                    Thêm và gửi mail
                </button>
                <% }%>

                <a href="UserServlet?action=list" class="btn btn-outline-light ms-2">Quay lại</a>
            </form>
        </div>          

        <script>
            function validateForm() {
                const pass = document.getElementById("password").value.trim();
                const confirm = document.getElementById("confirmPassword").value.trim();
                const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&]).{8,}$/;
                if (!passwordRegex.test(pass)) {
                    alert("⚠️ Mật khẩu phải có ít nhất 8 ký tự, gồm chữ, chữ in hoa, số và ký tự đặc biệt!");
                    return false;
                }

                if (pass !== confirm) {
                    alert("❌ Mật khẩu xác nhận không khớp. Vui lòng nhập lại!");
                    return false;
                }

                return true;
            }
        </script>

    </body>

    
</html>