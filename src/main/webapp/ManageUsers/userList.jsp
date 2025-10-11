<%-- 
    Document   : userList
    Created on : 8 Oct 2025, 14.10.26
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*, Models.User" %>
<link href="<%= request.getContextPath()%>/css/navigationUI/header.css" rel="stylesheet" type="text/css"/>
<link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>
        <link href="../css/navigationUI/header.css" rel="stylesheet" type="text/css"/>
    </head>
    <%@include file="../view-hfs/header.jsp" %>
    <body class="bg-dark text-white">
        <div class="container-fluid p-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-pink">Danh sách người dùng</h4>
                <a href="UserServlet?action=new" class="btn btn-pink btn-sm">+ Thêm người dùng</a>
            </div>

            <div class="table-responsive bg-dark border rounded">
                <table id="users-table" class="table table-dark table-bordered align-middle mb-0">
                    <thead class="table-secondary text-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên đăng nhập</th>
                            <th>Vai trò</th>
                            <th>Ngày tạo</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<User> list = (List<User>) request.getAttribute("userList");
                            if (list != null && !list.isEmpty()) {
                                for (User u : list) {
                        %>
                        <tr>
                            <td><%=u.getUserID()%></td>
                            <td><%=u.getUsername()%></td>
                            <td>
                                <% if (u.getRole() == 1) { %>
                                <span class="badge bg-danger">Admin</span>
                                <% } else { %>
                                <span class="badge bg-primary">User</span>
                                <% }%>
                            </td>
                            <td><%=u.getCreatedAt()%></td>
                            <td>
                                <a href="UserServlet?action=edit&id=<%=u.getUserID()%>" class="btn btn-warning btn-sm me-1">Sửa</a>
                                <a href="UserServlet?action=delete&id=<%=u.getUserID()%>" class="btn btn-danger btn-sm"
                                   onclick="return confirm('Bạn có chắc muốn xóa người dùng này không?')">Xóa</a>
                            </td>
                        </tr>
                        <% }
        } else { %>
                        <tr><td colspan="5" class="text-center">Không có người dùng nào</td></tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
    <%@include file="../view-hfs/footer.jsp" %>
</html>

