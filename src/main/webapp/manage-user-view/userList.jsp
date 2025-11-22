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

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/place-page-style/style.css">
       <!--<link href="<%= request.getContextPath()%>/css/cssForUser/pink.css" rel="stylesheet" type="text/css"/>-->     

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

        <div class="container p-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-pink">Danh sách người dùng</h4>   
                <div>
                    <a href="user-manage?action=new" class="btn btn-primary mx-3">+ Thêm người dùng</a>
                    <a type="button" class="btn prev-btn" href="admincenter">
                        Về trung tâm
                    </a>
                </div>
            </div>   
            <form method="get" action="user-manage" class="d-flex mb-2">
                <input type="hidden" name="action" value="list"/>
                <select name="roleFilter" class="form-select me-2" style="width:160px">
                    <option value="">-- Tất cả --</option>
                    <option value="1" ${param.roleFilter == '1' ? "selected" : ""}>Admin</option>
                    <option value="0" ${param.roleFilter == '0' ? "selected" : ""}>Customer</option>
                    <option value="2" ${param.roleFilter == '2' ? "selected" : ""}>Staff</option>
                </select>
                <button type="submit" class="btn btn-light">Lọc</button>
            </form>
            <div class="mx-auto"  
                 style="max-width: 600px; width: fit-content;">
                <%
                    String mailStatus = (String) session.getAttribute("mailStatus");
                    String error = (String) session.getAttribute("error");
                    if (mailStatus != null) {
                %>
                <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
                    <%= mailStatus%>
                </div>
                <%
                        session.removeAttribute("mailStatus");
                    }
                    if (error != null) {
                %>
                <div class="alert alert-danger alert-dismissible fade show text-center" role="alert">
                    <%= error%>                   
                </div>
                <%
                        session.removeAttribute("error");
                    }
                %>
            </div>
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
                                <% } else if (u.getRole() == 0) { %>
                                <span class="badge bg-primary">Customer</span>
                                <% } else {%>
                                <span class="badge bg-success">Staff</span>
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
                                <a href="user-manage?action=edit&id=<%=u.getUserID()%>" class="btn btn-warning btn-sm me-1">Sửa</a>
                                <% if (u.getStatusID() != 3) {%>
                                <a href="user-manage?action=delete&id=<%=u.getUserID()%>" 
                                   class="btn delete-btn"
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