<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Staff Home</title>
        <style>
            body {
                font-family: Arial;
                padding: 20px;
                background: #f4f4f4;
            }
            .box {
                background: white;
                padding: 25px;
                width: 360px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            a.btn {
                display: inline-block;
                margin-top: 15px;
                padding: 10px 20px;
                background: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
            }
            a.btn:hover {
                background: #0056b3;
            }
        </style>
    </head>
    <body>

        <div class="box">
            <h2>Staff dashboard</h2>
            <p>Xin chào, đây là trang dành cho nhân viên.</p>

            <a class="btn" href="${pageContext.request.contextPath}/staff/orders">
                ➜ Quản lý đơn hàng chờ xác nhận
            </a>
        </div>

    </body>
</html>
