<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff - Yêu cầu hủy vé</title>

        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f4f4f4;
            }

            /* Sidebar chuẩn giống các trang còn lại */
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
                color: #fff;
            }

            /* Content giống pending + confirmed */
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

            .badge-request {
                padding: 5px 12px;
                border-radius: 12px;
                font-size: 12px;
                background: #fff3cd;
                color: #856404;
                font-weight: bold;
            }

            .btn-approve {
                color: #2d8a34;
                font-weight: bold;
                text-decoration: none;
            }

            .btn-reject {
                color: #c53030;
                font-weight: bold;
                text-decoration: none;
            }

            .btn-approve:hover,
            .btn-reject:hover {
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

            <a href="${pageContext.request.contextPath}/staff/orders-pending">
                📄 Đơn chờ xác nhận
            </a>

            <a href="${pageContext.request.contextPath}/staff/orders-confirmed">
                ✔ Đơn đã xác nhận
            </a>

            <a class="active" href="${pageContext.request.contextPath}/staff/cancel-requests">
                ❌ Yêu cầu hủy vé
            </a>
        </div>

        <div class="content">

            <h1>Yêu cầu hủy vé</h1>
            <div class="subtitle">Danh sách yêu cầu hủy từ khách hàng.</div>

            <div class="card">
                <div class="card-header">Danh sách yêu cầu</div>

                <table>
                    <tr>
                        <th>Order ID</th>
                        <th>Khách hàng</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Tổng tiền</th>
                        <th>Ngày đặt</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>

                    <c:if test="${empty orders}">
                        <tr>
                            <td colspan="8" class="empty-row">Hiện không có yêu cầu hủy nào.</td>
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
                                <span class="badge-request">Yêu cầu hủy</span>
                            </td>

                            <td>
                                <a class="btn-approve"
                                   href="${pageContext.request.contextPath}/staff/approve-cancel?orderId=${o.orderID}">
                                    ✔ Duyệt
                                </a>
                                &nbsp;&nbsp;
                                <a class="btn-reject"
                                   href="${pageContext.request.contextPath}/staff/reject-cancel?orderId=${o.orderID}">
                                    ✖ Từ chối
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>

        </div>

    </body>
</html>
