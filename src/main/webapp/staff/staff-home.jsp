<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Staff Dashboard</title>
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f4f4f4;
            }

            /* Sidebar */
            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                width: 230px;
                height: 100vh;
                background: #2c3e50;
                color: white;
                padding-top: 20px;
            }

            .sidebar h2 {
                text-align: center;
                margin-bottom: 25px;
                font-size: 20px;
            }

            .sidebar ul {
                list-style: none;
                padding-left: 0;
            }

            .sidebar ul li {
                padding: 12px 20px;
            }

            .sidebar ul li a {
                color: #ecf0f1;
                text-decoration: none;
                font-size: 15px;
                display: block;
            }

            .sidebar ul li:hover {
                background: #34495e;
            }

            /* Main content */
            .content {
                margin-left: 230px;
                padding: 30px;
            }

            .card {
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                width: 600px;
            }
        </style>
    </head>

    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <h2>STAFF PANEL</h2>
            <ul>
                <li><a href="${pageContext.request.contextPath}/staff/home">🏠 Dashboard</a></li>

                <li><a href="${pageContext.request.contextPath}/staff/orders-pending">
                        📄 Đơn chờ xác nhận
                    </a></li>

                <li><a href="${pageContext.request.contextPath}/staff/orders-confirmed">
                        ✔ Đơn đã xác nhận
                    </a></li>

                <li><a href="${pageContext.request.contextPath}/staff/cancel-requests">
                        ❌ Yêu cầu hủy vé
                    </a></li>
            </ul>

        </div>

        <!-- Content -->
        <div class="content">
            <div class="card">
                <h2>👋 Chào mừng bạn!</h2>
                <p>Đây là dashboard dành cho nhân viên xử lý đơn đặt vé.</p>
                <p>Bạn có thể:</p>
                <ul>
                    <li>Duyệt đơn khách đã thanh toán</li>
                    <li>Xử lý yêu cầu hủy vé</li>
                    <li>Kiểm tra thông tin khách hàng</li>
                </ul>
            </div>
        </div>

    </body>
</html>
