<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff - Quản lý đơn hàng</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            .wrapper {
                max-width: 1100px;
                margin: 0 auto;
            }

            h1 {
                margin: 0 0 10px;
                font-size: 26px;
            }

            .subtitle {
                color: #666;
                margin-bottom: 20px;
            }

            .card {
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                overflow: hidden;
            }

            .card-header {
                padding: 14px 18px;
                font-weight: bold;
                background: #f8f8f8;
                border-bottom: 1px solid #e0e0e0;
            }

            table.order-table {
                width: 100%;
                border-collapse: collapse;
                font-size: 14px;
            }

            table.order-table th,
            table.order-table td {
                padding: 10px 12px;
                border-bottom: 1px solid #eee;
                text-align: left;
            }

            table.order-table th {
                background: #fafafa;
                font-weight: 600;
            }

            table.order-table tr:nth-child(even) {
                background: #fcfcfc;
            }

            .badge {
                display: inline-block;
                padding: 3px 8px;
                border-radius: 999px;
                font-size: 12px;
                font-weight: 600;
            }

            .badge-pending {
                background: #fff3cd;
                color: #856404;
                border: 1px solid #ffeeba;
            }

            .badge-confirmed {
                background: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }

            a.btn-link {
                color: #0d6efd;
                text-decoration: none;
                font-weight: 600;
            }

            a.btn-link:hover {
                text-decoration: underline;
            }

            .empty-row {
                text-align: center;
                padding: 30px 0;
                color: #777;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <h1>Quản lý đơn hàng</h1>
            <div class="subtitle">Danh sách đơn hàng khách đã đặt và chờ nhân viên xử lý.</div>

            <div class="card">
                <div class="card-header">Danh sách đơn hàng</div>

                <table class="order-table">
                    <tr>
                        <th>Order ID</th>
                        <th>Khách hàng</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Tổng tiền</th>
                        <th>Ngày đặt</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>

                    <c:if test="${empty orders}">
                        <tr>
                            <td colspan="8" class="empty-row">
                                Hiện chưa có đơn hàng nào trong danh sách.
                            </td>
                        </tr>
                    </c:if>

                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <td>${o.orderID}</td>
                            <td>${o.contactFullname}</td>
                            <td>${o.contactEmail}</td>
                            <td>${o.contactPhone}</td>
                            <td>${o.totalAmount}</td>
                            <td>${o.orderDate}</td>

                            <!-- Cột trạng thái -->
                            <td>
                                <c:choose>
                                    <c:when test="${o.statusID == 11}">
                                        <span class="badge badge-pending">Chờ xác nhận</span>
                                    </c:when>
                                    <c:when test="${o.statusID == 12}">
                                        <span class="badge badge-confirmed">Đã xác nhận</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge">Không rõ</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- Cột hành động -->
                            <td>
                                <c:choose>
                                    <c:when test="${o.statusID == 11}">
                                        <a class="btn-link"
                                           href="${pageContext.request.contextPath}/staff/confirm?orderId=${o.orderID}">
                                            Xác nhận thanh toán
                                        </a>
                                    </c:when>
                                    <c:when test="${o.statusID == 12}">
                                        <span style="color: #28a745; font-weight: 600;">✔ Đã xác nhận</span>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
