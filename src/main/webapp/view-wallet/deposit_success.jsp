<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Thanh toán thành công</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #000;
                margin: 0;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                background-color: #ffffff;
                padding: 2em;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                text-align: center;
                max-width: 500px;
                width: 100%;
            }
            .icon {
                font-size: 5em;
                color: #28a745;
            }
            h2 {
                color: #28a745;
                margin-top: 0.5em;
            }
            p {
                color: #555;
                line-height: 1.6;
            }
            .info {
                text-align: left;
                margin: 2em 0;
                border-top: 1px solid #eee;
                padding-top: 1em;
            }
            .info p {
                display: flex;
                justify-content: space-between;
            }
            .info span {
                font-weight: bold;
            }
            .back-button {
                display: inline-block;
                margin-top: 1.5em;
                padding: 0.8em 1.5em;
                background-color: #007bff;
                color: #ffffff;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            .back-button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="icon">
                <i class="fas fa-check-circle"></i>
            </div>
            <h2>Thanh toán thành công!</h2>
            <p>Cảm ơn bạn đã hoàn tất thanh toán. Giao dịch của bạn đã được xử lý thành công.</p>
            <div class="info">
                <p><span>Mã giao dịch:</span> <%= request.getParameter("vnp_TxnRef") != null ? request.getParameter("vnp_TxnRef") : ""%></p>
                <%
                    // Lấy các giá trị từ request attribute
                    Long amount = (request.getAttribute("amount") != null) ? (Long) request.getAttribute("amount") : 0L;
                    Long bonus = (request.getAttribute("bonus") != null) ? (Long) request.getAttribute("bonus") : 0L;
                    Long total = (request.getAttribute("total") != null) ? (Long) request.getAttribute("total") : 0L;
                %>
                <p><span>Số tiền nạp:</span> <%= String.format("%,d", amount)%> VND</p>
                <% if (bonus > 0) {%>
                <p><span>Bonus:</span> <%= String.format("%,d", bonus)%> VND</p>
                <% }%>
                <p><span>Tổng cộng:</span> <%= String.format("%,d", total)%> VND</p>
                <p><span>Mã ngân hàng:</span> <%= request.getParameter("vnp_BankCode") != null ? request.getParameter("vnp_BankCode") : ""%></p>
                <p><span>Thời gian:</span> 
                    <%
                        try {
                            String payDateStr = request.getParameter("vnp_PayDate");
                            if (payDateStr != null) {
                                java.text.SimpleDateFormat sdfIn = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
                                java.util.Date date = sdfIn.parse(payDateStr);
                                java.text.SimpleDateFormat sdfOut = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                out.print(sdfOut.format(date));
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                    %>
                </p>
            </div>
            <a href="wallet" class="back-button">Quay lại trang chủ</a>
        </div>
    </body>
</html>
