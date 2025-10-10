<%-- 
    Document   : userFrom
    Created on : 8 Oct 2025, 14.10.48
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="Models.User" %>
<%
    User user = (User) request.getAttribute("user");
    boolean isEdit = (user != null);
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>
    </head>
    <%@include file="../view-hfs/header.jsp" %>
    <body class="bg-dark text-white">
        <div class="container py-4">
            <h3 class="text-pink mb-3"><%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> người dùng</h3>

            <form action="UserServet" method="post">
                <input type="hidden" name="userID" value="<%= isEdit ? user.getUserID() : ""%>">

                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" name="username" class="form-control"
                           value="<%= isEdit ? user.getUsername() : ""%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mật khẩu (đã mã hóa)</label>
                    <input type="text" name="passwordHash" class="form-control"
                           value="<%= isEdit ? user.getPassword() : ""%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Vai trò</label>
                    <select name="role" class="form-select">
                        <option value="0" <%= isEdit && user.getRole() == 0 ? "selected" : ""%>>User</option>
                        <option value="1" <%= isEdit && user.getRole() == 1 ? "selected" : ""%>>Admin</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-pink"><%= isEdit ? "Cập nhật" : "Thêm mới"%></button>
                <a href="UserServlet?action=list" class="btn btn-outline-light ms-2">Quay lại</a>
            </form>
        </div>

    </body>
    <%@include file="../view-hfs/footer.jsp" %>
</html>

