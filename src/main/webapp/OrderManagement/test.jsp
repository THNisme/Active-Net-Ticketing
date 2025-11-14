<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Models.nttn0902.Event" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý sự kiện</title>
        <link rel="stylesheet" 
              href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
    </head>
    <body class="p-4 bg-light">
        <div class="container">
            <h2 class="mb-4 text-center text-primary fw-bold">Danh sách sự kiện</h2>

            <table class="table table-bordered table-striped align-middle shadow-sm">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tên sự kiện</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Hình ảnh</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Event> list = (List<Event>) request.getAttribute("events");
                        if (list != null && !list.isEmpty()) {
                            for (Event e : list) {
                    %>
                    <tr>
                        <td><%= e.getEventID()%></td>
                        <td><%= e.getEventName()%></td>
                        <td><%= e.getStartDate()%></td>
                        <td><%= e.getEndDate()%></td>
                        <td>
                            <% if (e.getImageURL() != null && !e.getImageURL().isEmpty()) {%>
                            <img src="<%= e.getImageURL()%>" alt="Event Image" width="100" class="rounded shadow-sm"/>
                            <% } else { %>
                            <span class="text-muted">Không có ảnh</span>
                            <% }%>
                        </td>
                        <td>
                            <form action="order_management" method="get" class="d-inline">
                                <input type="hidden" name="eventID" value="<%= e.getEventID()%>"/>
                                <button type="submit" class="btn btn-primary btn-sm">
                                    Đơn hàng
                                </button>
                            </form>

                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">Không có sự kiện nào</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </body>
</html>
