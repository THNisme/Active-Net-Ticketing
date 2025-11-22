<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Models.ttk2008.MyTicket" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Vé của tôi</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

        <link href="<%= request.getContextPath()%>/css/myticket-page/myticket.css" rel="stylesheet" type="text/css"/>
        <link href="<%= request.getContextPath()%>/css/wallet-page/wallet.css" rel="stylesheet" type="text/css"/>

    </head>
    <%@include file="../view-hfs/header.jsp" %> 
    <%@include file="/view-ttk2008/sidebar-for-user.jsp" %>

    <body class="bg-dark">

        <div class="container mt-4">
            <h2 class="text-center mb-4">Vé của tôi</h2>
            <ul class="nav nav-tabs justify-content-center mb-3">
                <li class="nav-item">
                    <a class="nav-link <%= "all".equals(request.getAttribute("filter")) ? "active" : ""%>" href="myticket?filter=all">Tất cả</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "upcoming".equals(request.getAttribute("filter")) ? "active" : ""%>" href="myticket?filter=upcoming">Sắp diễn ra</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "ended".equals(request.getAttribute("filter")) ? "active" : ""%>" href="myticket?filter=ended">Đã kết thúc</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= "canceled".equals(request.getAttribute("filter")) ? "active" : ""%>" href="myticket?filter=canceled">Đã hủy</a>
                </li>
            </ul>
            <div class="card shadow-lg">
                <div class="card-body">
                    <table class="table table-hover align-middle text-center">
                        <thead>
                            <tr>
                                <th>Mã đơn</th>
                                <th>Mã vé</th>
                                <th>Tên sự kiện</th>
                                <th>Khu vực</th>
                                <th>Ghế</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Giá (VNĐ)</th>
                                <th>Trạng thái</th>       <!-- thêm vào -->
                                <th>Tải vé</th>            <!-- thêm vào -->
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<MyTicket> tickets = (List<MyTicket>) request.getAttribute("tickets");

                                if (tickets != null && !tickets.isEmpty()) {
                                    for (MyTicket t : tickets) {
                            %>
                            <tr>
                                <td><%= t.getOrderId()%></td>
                                <td><%= t.getTicketId()%></td>
                                <td><%= t.getEventName()%></td>
                                <td><%= t.getZoneName()%></td>
                                <td><%= t.getSeatLabel() != null ? t.getSeatLabel() : "Không định danh"%></td>
                                <td><%= t.getStartDate()%></td>
                                <td><%= t.getEndDate()%></td>
                                <td><%= String.format("%,.0f", t.getPrice())%></td>
                                <td>
                                    <% if (t.getOrderStatus() == 11) { %>
                                    <span class="badge bg-warning text-dark">⏳ Chờ xác nhận</span>
                                    <% } else if (t.getOrderStatus() == 12) { %>
                                    <span class="badge bg-success">✔ Đã xác nhận</span>
                                    <% } else { %>
                                    <span class="badge bg-secondary">Khác</span>
                                    <% } %>
                                </td>

                                <td>
                                    <% if (t.getOrderStatus() == 12) {%>
                                    <a href="download-ticket?ticketId=<%= t.getTicketId()%>" 
                                       class="btn btn-sm btn-primary">
                                        <i class="fa fa-download"></i> Tải vé
                                    </a>
                                    <% } else { %>
                                    <span class="text-muted">Chưa có vé</span>
                                    <% } %>
                                </td>

                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="10" class="text-center text-muted">Không có vé nào thuộc danh mục này.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="text-center mt-4">
                <a href="home" class="btn btn-accent"> Mua thêm vé</a>
            </div>
        </div>

    </body>
</html>
