<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán thất bại</title>
        <style>
            body {
                background:#111;
                color:#fff;
                text-align:center;
                font-family:"Segoe UI", sans-serif;
                padding-top:70px;
            }
            .box {
                width:380px;
                margin:auto;
                padding:40px;
                border-radius:14px;
                background:#1b1b1b;
                border:1px solid #333;
                box-shadow:0 0 20px rgba(0,0,0,0.4);
            }
            h1 {
                color:#ff4444;
                margin-bottom:20px;
            }
            .amount{
                color:#ffb6b6;
                font-weight:bold;
            }
            a{
                color:#ffb6b6;
                text-decoration:none;
                display:block;
                margin-top:15px;
                font-size:18px;
            }
            .back{
                color:#aaa;
                margin-top:10px;
                font-size:16px;
            }
        </style>
    </head>
    <body>
        <div class="box">
            <h1>Thanh toán thất bại</h1>

            <p><%= request.getAttribute("message")%></p>

            <p>Số dư hiện tại: <span class="amount">
                    <%= request.getAttribute("currentBalance")%>
                </span> đ</p>

            <p>Số tiền cần thanh toán: <span class="amount">
                    <%= request.getAttribute("requiredAmount")%>
                </span> đ</p>

            <a href="wallet.jsp">Nạp thêm tiền vào ví</a>

            <a href="home.jsp" class="back">Quay về trang chủ</a>
        </div>
    </body>
</html>
