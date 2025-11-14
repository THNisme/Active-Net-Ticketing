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

            .card {
                background-color: transparent;
                border: none;
                border-radius: 10px;
                padding: 0;
            }

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

            /* Dropdown style */
            .event-dropdown {
                position: absolute;
                top: 120%;
                right: 0;
                background-color: #1c1c1c;
                border: 1px solid #0dcaf0;
                border-radius: 8px;
                min-width: 220px;
                display: none;
                z-index: 1000;
            }

            .event-dropdown ul {
                list-style: none;
                margin: 0;
                padding: 0;
            }

            .event-dropdown ul li a {
                display: block;
                color: #fff;
                text-decoration: none;
                padding: 10px 15px;
                transition: background 0.2s ease, color 0.2s ease;
            }

            .event-dropdown ul li a:hover {
                background-color: #0dcaf0;
                color: #000;
            }

            .table-custom thead th {
                border-right: 1px solid #333;
            }

            .table-custom thead th:last-child {
                border-right: none;
            }
        </style>
    </head>
    <body>
        <div class="container py-4">
            <!-- Header: Time + Event switch -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <i class="bi bi-calendar-event me-2"></i>
                    <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss" />
                </div>

                <div class="d-flex align-items-center gap-2">
                    <!-- Nút quay lại -->
                    <button type="button" class="btn btn-outline-success btn-sm" onclick="history.back()">
                        <i class="bi bi-arrow-left"></i>
                    </button>

                    <!-- Nút đổi sự kiện -->
                    <div class="position-relative">
                        <button id="toggleEventList" type="button" class="btn btn-outline-info btn-sm">
                            <i class="bi bi-arrow-repeat me-1"></i> Đổi sự kiện
                            <i class="bi bi-caret-down-fill ms-1"></i>
                        </button>

                        <!-- Dropdown danh sách sự kiện -->
                        <div id="eventDropdown" class="event-dropdown shadow">
                            <ul>
                                <c:forEach var="e" items="${otherEvents}">
                                    <li>
                                        <a href="order_management?eventID=${e.eventID}">
                                            <i class="bi bi-calendar-event me-2 text-info"></i>${e.eventName}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <hr>

            <!-- Tiêu đề -->
            <h4 class="section-title mb-5">
                Danh sách đơn hàng 
                <c:if test="${not empty eventName}">
                    của sự kiện: <span class="text-info">${eventName}</span>
                </c:if>
            </h4>

            <!-- Search + Export -->
            <div class="mb-5">
                <form action="order_management" method="get" class="mb-4 position-relative">
                    <i class="bi bi-search position-absolute top-50 start-0 translate-middle-y ms-3 text-secondary"></i>

                    <input 
                        type="text" 
                        name="keyword"
                        value="${keyword}"
                        autocomplete="off"
                        class="form-control ps-5 search-box"
                        placeholder="Tìm kiếm..."
                        />

                    <!-- Giữ lại eventID khi search -->
                    <c:if test="${not empty eventID}">
                        <input type="hidden" name="eventID" value="${eventID}" />
                    </c:if>
                </form>


                <div class="d-flex gap-2">
                    <a href="export_order?eventID=${eventID}" class="btn btn-outline-info btn-sm">
                        <i class="bi bi-download"></i> Xuất báo cáo
                    </a>

                </div>
            </div>

            <!-- Bảng đơn hàng -->
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
                                            <a 
                                                href="order_details?orderId=${o.orderId}" 
                                                class="btn btn-sm btn-outline-primary"
                                                title="Xem chi tiết"
                                                >
                                                <i class="bi bi-eye"></i>
                                            </a>

                                            <a 
                                                href="javascript:void(0)" 
                                                class="btn btn-sm btn-outline-danger"
                                                onclick="confirmDelete(${o.orderId})">
                                                <i class="bi bi-trash"></i>
                                            </a>



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
        <script>
            document.querySelector("input[name='keyword']").addEventListener("keydown", function (e) {
                if (e.key === "Enter") {
                    e.preventDefault(); // tránh submit form

                    const keyword = this.value.trim();
                    const eventID = new URLSearchParams(window.location.search).get("eventID");

                    let url = "order_management?";

                    if (eventID) {
                        url += "eventID=" + eventID + "&";
                    }

                    if (keyword.length > 0) {
                        url += "keyword=" + encodeURIComponent(keyword);
                    }

                    window.location.href = url;
                }
            });
        </script>
        <script>
            function confirmDelete(orderId) {
                if (confirm("Bạn có chắc muốn xóa đơn hàng này?")) {
                    document.getElementById("deleteOrderId").value = orderId;
                    document.getElementById("deleteForm").submit();
                }
            }
        </script>
        <form id="deleteForm" action="order_management" method="post" style="display:none;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="orderID" id="deleteOrderId">
            <input type="hidden" name="eventID" value="${eventID}">
        </form>


    </body>
</html>
