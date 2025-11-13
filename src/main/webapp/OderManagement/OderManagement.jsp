<%-- 
    Document   : OrderManagement
    Created on : Nov 11, 2025
    Author     : NGUYEN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách đơn hàng - OrderManagement</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <style>
            body {
                background-color: #000;
                color: #fff;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            /* Card trong suốt để không có nền xám */
            .card {
                background-color: transparent;
                border: none;
                border-radius: 10px;
                padding: 0;
            }

            /* Bảng tối, viền ô, hover, không có viền ngoài */
            .table-custom {
                background-color: #1c1c1c;
                border-collapse: separate;
                border-spacing: 0;
            }

            .table-custom th {
                background-color: #222;
                color: #ffb6b6;
            }

            .table-custom td {
                border: 1px solid #333;
            }

            .table-custom tbody tr:hover {
                background-color: #2a2a2a;
            }

            .no-data {
                color: #777;
                text-align: center;
                padding: 40px 0;
            }

            .search-box {
                background-color: #1c1c1c;
                color: #fff;
                border: 1px solid #333;
            }

            .section-title {
                color: #ffb6b6;
                font-weight: 600;
                margin-top: 15px;
            }

            .btn-order {
                margin-bottom: 15px;
            }

            .btn-outline-success {
                border-color: #28a745;
                color: #28a745;
            }

            .btn-outline-success:hover {
                background-color: #28a745;
                color: #fff;
            }

            .btn-outline-info {
                border-color: #0dcaf0;
                color: #0dcaf0;
            }

            .btn-outline-info:hover {
                background-color: #0dcaf0;
                color: #000;
            }

            .table-custom thead th {
                border-right: 1px solid #333; /* màu xám nhạt cho table-dark */
            }

            .table-custom thead th:last-child {
                border-right: none; /* bỏ gạch dọc cuối cùng */
            }


        </style>
    </head>
    <body>
        <div class="container py-4">
            <!-- Current Time + Change Event -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <i class="bi bi-calendar-event me-2"></i>
                    <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss" />
                </div>

                <div class="d-flex align-items-center">
                    <div class="position-relative">
                        <button id="toggleEventList" type="button" class="btn btn-outline-info btn-sm">
                            <i class="bi bi-arrow-repeat me-1"></i> Đổi sự kiện
                        </button>
                        <div id="eventDropdown" class="event-dropdown shadow position-absolute end-0 mt-2"
                             style="background:#1c1c1c; border:1px solid #0dcaf0; border-radius:8px; display:none;">
                            <ul class="list-unstyled m-0">
                                <c:forEach var="e" items="${otherEvents}">
                                    <li>
                                        <a href="statisticsevent?eventId=${e.eventID}" 
                                           class="dropdown-item text-light px-3 py-2 d-block">${e.eventName}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <hr>

            <h4 class="section-title mb-5">Danh sách đơn hàng</h4>

            <!-- Button, Search, and Actions -->
            <div class="mb-5">
                <button class="btn btn-outline-info btn-sm mb-4">
                    <i class="bi bi-bag"></i> Đơn hàng
                </button>

                <div class="mb-4 position-relative">
                    <i class="bi bi-search position-absolute top-50 start-0 translate-middle-y ms-3 text-secondary"></i>
                    <input type="text" class="form-control ps-5 search-box" placeholder="Tìm đơn hàng">
                </div>

                <div class="d-flex gap-2">
                    <a href="export_order" class="btn btn-outline-info btn-sm">
                        <i class="bi bi-download"></i> Xuất báo cáo
                    </a>
                    <button class="btn btn-outline-info btn-sm">
                        <i class="bi bi-envelope"></i> Gửi email
                    </button>
                </div>
            </div>

            <!-- Table -->
            <div class="card mt-3 p-0">
                <table class="table table-dark table-custom align-middle text-center mb-0">
                    <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Ngày tạo đơn</th>
                            <th>Người mua</th>
                            <th>Email</th>
                            <th>Số điện thoại</th>
                            <th>Giá trị đơn</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty orderList}">
                                <tr>
                                    <td colspan="7" class="text-center">
                                        <div class="no-data">
                                            <i class="bi bi-inbox fs-2"></i>
                                            <div>Không có dữ liệu</div>
                                        </div>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="o" items="${orderList}">
                                    <tr>
                                        <td>${o.orderId}</td>
                                        <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy - HH:mm:ss"/></td>
                                        <td>${o.contactFullname}</td>
                                        <td>${o.contactEmail}</td>
                                        <td>${o.contactPhone}</td>
                                        <td><fmt:formatNumber value="${o.totalAmount}" type="number"/>đ</td>
                                        <td>
                                            <button class="btn btn-sm btn-outline-success"><i class="bi bi-eye"></i></button>
                                            <button class="btn btn-sm btn-outline-warning"><i class="bi bi-pencil"></i></button>
                                            <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <script>
            const toggleBtn = document.getElementById("toggleEventList");
            const dropdown = document.getElementById("eventDropdown");

            toggleBtn.addEventListener("click", () => {
                dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
            });

            document.addEventListener("click", (e) => {
                if (!toggleBtn.contains(e.target) && !dropdown.contains(e.target)) {
                    dropdown.style.display = "none";
                }
            });
        </script>
    </body>
</html>
