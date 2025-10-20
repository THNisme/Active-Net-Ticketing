<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Models.kietTT208.MyTicket" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Vé của tôi</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background-color: #121212;
                color: #f5f5f5;
                font-family: "Inter", sans-serif;
            }

            h2 {
                color: #ffb6b6;
                font-weight: bold;
                text-shadow: 0 0 5px rgba(255, 107, 107, 0.5);
            }

            .nav-tabs {
                border-bottom: 1px solid #444;
            }

            .nav-tabs .nav-link {
                color: #bbb;
                border: none;
                transition: all 0.3s ease;
            }

            .nav-tabs .nav-link:hover {
                color: #ff6b6b;
            }

            .nav-tabs .nav-link.active {
                color: #fff;
                background-color: #ffb6b6;
                border-radius: 8px 8px 0 0;
            }

            .card {
                background-color: #1e1e1e;
                border: 1px solid #333;
                border-radius: 12px;
            }

            .table {
                color: #e0e0e0;
            }

            .table thead {
                background-color: #ffb6b6;
                color: #fff;
            }

            .table-hover tbody tr:hover {
                background-color: #2a2a2a;
                transition: background-color 0.3s;
            }

            td:last-child {
                color: #ffb6b6;
                font-weight: bold;
            }

            td:nth-last-child(2),
            td:nth-last-child(3) {
                font-weight: 600;
            }

            .btn-ff6b6b {
                background-color: #ffb6b6;
                color: #fff;
                border: none;
                border-radius: 6px;
                padding: 8px 14px;
                transition: 0.3s;
            }

            .btn-ff6b6b:hover {
                background-color: #ff8787;
                transform: scale(1.03);
            }
        </style>
    </head>
    <%@include file="../view-hfs/header.jsp" %> 
    <body>

        <div class="container mt-4">
            <h2 class="text-center mb-4">Vé của tôi</h2>

            <!-- Tabs lọc vé -->
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

            <!-- Danh sách vé -->
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
                <a href="events.jsp" class="btn btn-ff6b6b"> Mua thêm vé</a>
            </div>
        </div>

    </body>
</html>
