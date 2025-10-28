<%-- 
    Document   : userList
    Created on : 8 Oct 2025, 14.10.26
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*, Models.ltk1702.User" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>     
        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">
    </head>
   
    
    <body class="bg-dark text-white">
        <div class="container p-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-pink">Danh sách người dùng</h4>
                <a href="UserServlet?action=new" class="btn btn-pink btn-sm">+ Thêm người dùng</a>
            </div>

            <!-- 🔹 Thông báo thành công hoặc lỗi -->
            <%
                String mailStatus = (String) session.getAttribute("mailStatus");
                String error = (String) session.getAttribute("error");
                if (mailStatus != null) {
            %>
            <div class="alert alert-success"><%= mailStatus%></div>
            <%
                    session.removeAttribute("mailStatus");
                }
                if (error != null) {
            %>
            <div class="alert alert-danger"><%= error%></div>
            <%
                    session.removeAttribute("error");
                }
            %>

            <div class="table-responsive bg-dark border rounded">
                <table id="users-table" class="table table-dark table-bordered align-middle mb-0">
                    <thead class="table-secondary text-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên đăng nhập</th>
                            <th>Vai trò</th>
                            <th>Ngày tạo</th>
                            <th>Trạng thái</th>
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
                            <td><%= u.getUserID()%></td>
                            <td><%= u.getUsername()%></td>
                            <td>
                                <% if (u.getRole() == 1) { %>
                                <span class="badge bg-danger">Admin</span>
                                <% } else { %>
                                <span class="badge bg-primary">User</span>
                                <% }%>
                            </td>
                            <td><%= u.getCreatedAt()%></td>
                            <td>
                                <% if (u.getStatusID() == 1) { %>
                                <span class="badge bg-success">Hoạt động</span>
                                <% } else if (u.getStatusID() == 2) { %>
                                <span class="badge bg-secondary">Ngừng</span>
                                <% } else { %>
                                <span class="badge bg-danger">Đã xóa</span>
                                <% }%>
                            </td>
                            <td>
                                <a href="UserServlet?action=edit&id=<%=u.getUserID()%>" class="btn btn-warning btn-sm me-1">Sửa</a>
                                <% if (u.getStatusID() != 3) {%>
                                <a href="UserServlet?action=delete&id=<%=u.getUserID()%>" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Bạn có chắc muốn xóa người dùng này không?')">Xóa</a>
                                <% } %>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr><td colspan="6" class="text-center">Không có người dùng nào</td></tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>

</html>