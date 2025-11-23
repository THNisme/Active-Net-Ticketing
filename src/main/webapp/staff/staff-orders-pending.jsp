<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff - Đơn chờ xác nhận</title>

        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f4f4f4;
            }

            .sidebar {
                width: 230px;
                background: #1f2937;
                height: 100vh;
                position: fixed;
                top: 0;
                left: 0;
                padding-top: 25px;
                color: white;
            }

            .sidebar h2 {
                text-align: center;
                margin-bottom: 30px;
                font-size: 20px;
            }

            .sidebar a {
                display: block;
                padding: 12px 20px;
                text-decoration: none;
                color: #d1d5db;
                margin-bottom: 5px;
            }

            .sidebar a:hover,
            .sidebar a.active {
                background: #374151;
                color: white;
            }

            .content {
                margin-left: 250px;
                padding: 30px;
            }

            h1 {
                font-size: 28px;
                margin-bottom: 8px;
            }

            .subtitle {
                color: #666;
                margin-bottom: 20px;
            }

            .card {
                background: white;
                border-radius: 10px;
                padding: 0;
                box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            }

            .card-header {
                padding: 15px 20px;
                font-weight: bold;
                background: #f3f4f6;
                border-bottom: 1px solid #e5e7eb;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            table th, table td {
                padding: 12px 14px;
                border-bottom: 1px solid #eee;
                text-align: center;
            }

            table th {
                background: #fafafa;
            }

            .badge {
                padding: 5px 10px;
                border-radius: 12px;
                font-size: 12px;
                font-weight: bold;
                display: inline-block;
            }

            .badge-pending {
                background: #fff3cd;
                color: #856404;
            }

            .btn-link {
                color: #2563eb;
                text-decoration: none;
                font-weight: bold;
            }

            .btn-link:hover {
                text-decoration: underline;
            }

            .empty-row {
                padding: 25px;
                text-align: center;
                color: #666;
            }

        </style>
    </head>

    <body>

        <div class="sidebar">
            <h2>STAFF PANEL</h2>

            <a href="${pageContext.request.contextPath}/staff/home">🏠 Dashboard</a>

            <a class="active" href="${pageContext.request.contextPath}/staff/orders-pending">
                📄 Đơn chờ xác nhận
            </a>

            <a href="${pageContext.request.contextPath}/staff/orders-confirmed">
                ✔ Đơn đã xác nhận
            </a>

            <a href="${pageContext.request.contextPath}/staff/cancel-requests">
                ❌ Yêu cầu hủy vé
            </a>
        </div>

        <div class="content">

            <h1>Đơn hàng chờ xác nhận</h1>
            <div class="subtitle">Các đơn khách đã thanh toán và đang chờ nhân viên duyệt.</div>

            <div class="card">
                <div class="card-header">Danh sách đơn hàng</div>

                <table>
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
                            <td colspan="8" class="empty-row">Không có đơn nào chờ xác nhận.</td>
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

                            <td>
                                <span class="badge badge-pending">Chờ xác nhận</span>
                            </td>

                            <td>
                                <a class="btn-link"
                                   href="${pageContext.request.contextPath}/staff/confirm?orderId=${o.orderID}">
                                    ✔ Xác nhận
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>

    </body>
</html>
