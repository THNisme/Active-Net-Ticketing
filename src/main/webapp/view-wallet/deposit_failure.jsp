<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh toán thất bại</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f6;
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
            color: #dc3545;
        }
        h2 {
            color: #dc3545;
            margin-top: 0.5em;
        }
        p {
            color: #555;
            line-height: 1.6;
        }
        .reason {
            margin-top: 1.5em;
            padding: 1em;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            border-radius: 5px;
        }
        .actions {
            margin-top: 2em;
        }
        .action-button {
            display: inline-block;
            margin: 0.5em;
            padding: 0.8em 1.5em;
            background-color: #007bff;
            color: #ffffff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .action-button:hover {
            background-color: #0056b3;
        }
        .contact-support {
            background-color: #6c757d;
        }
        .contact-support:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="icon">
            <i class="fas fa-times-circle"></i>
        </div>
        <h2>Thanh toán thất bại!</h2>
        <p>Đã có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại hoặc liên hệ với chúng tôi để được hỗ trợ.</p>
        <%
            String errorCode = request.getParameter("vnp_TransactionStatus");
            String message = "Lý do không xác định.";
            if (errorCode != null) {
                switch (errorCode) {
                    case "02": message = "Giao dịch không thành công do thông tin thẻ/tài khoản không hợp lệ."; break;
                    case "07": message = "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới fraud)."; break;
                    case "09": message = "Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng."; break;
                    case "10": message = "Thẻ/Tài khoản của khách hàng đã bị khóa."; break;
                    case "11": message = "Khách hàng nhập sai mật khẩu xác thực giao dịch (OTP)."; break;
                    case "24": message = "Khách hàng hủy giao dịch."; break;
                    // Thêm các mã lỗi khác của VNPay tại đây
                }
            }
        %>
        <div class="reason">
            <p><strong>Lý do:</strong> <%= message %></p>
        </div>
        <div class="actions">
            <a href="view-wallet/deposit.jsp" class="action-button">Thử lại</a>
            <a href="../view-hfs/support.jsp" class="action-button contact-support">Liên hệ hỗ trợ</a>
        </div>
    </div>
</body>
</html>