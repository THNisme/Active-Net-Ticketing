<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán thành công - ${eventName}</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        <style>
            body {
                background: #0f0f0f;
                color: #fff;
                font-family: "Segoe UI", sans-serif;
                margin: 0;
            }

            .container {
                max-width: 900px;
                margin: 80px auto;
                background: #1b1b1b;
                padding: 40px;
                border-radius: 16px;
                box-shadow: 0 0 25px rgba(0,255,128,0.15);
                text-align: center;
            }

            h1 {
                color: #ffb6b6;
                margin-bottom: 10px;
                font-size: 30px;
            }

            p {
                color: #ccc;
                font-size: 16px;
                line-height: 1.5;
            }

            .info-box {
                text-align: left;
                background: #222;
                padding: 20px 25px;
                border-radius: 10px;
                margin-top: 25px;
            }

            .info-box h3 {
                color: #00ff80;
                margin-bottom: 10px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
                text-align: left;
            }

            th, td {
                padding: 10px;
                border-bottom: 1px solid #333;
            }

            th {
                color: #aaa;
            }

            td {
                color: #eee;
            }

            .total {
                text-align: right;
                font-size: 20px;
                font-weight: 700;
                margin-top: 20px;
                color: #00ff80;
            }

            .buttons {
                margin-top: 35px;
            }

            .btn {
                background: #ffb6b6;
                border: none;
                color: white;
                padding: 12px 26px;
                margin: 0 10px;
                border-radius: 10px;
                font-size: 16px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                transition: background 0.25s ease;
            }

            .btn:hover {
                background: #00a75a;
            }

            .icon-success {
                font-size: 65px;
                color: #00ff80;
                margin-bottom: 20px;
            }

            .divider {
                height: 1px;
                background: #333;
                margin: 25px 0;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <i class="fa-solid fa-circle-check icon-success"></i>
            <h1>Thanh toán thành công!</h1>
            <p>Cảm ơn bạn <strong>${contactFullname}</strong> đã mua vé tại Active-Net Ticketing.</p>
            <p>Mã đơn hàng: <strong>#${orderId}</strong></p>

            <div class="info-box">
                <h3>Thông tin đơn hàng</h3>
                <p><strong>Sự kiện:</strong> ${eventName}</p>
                <p><strong>Địa điểm:</strong> ${placeName}</p>
                <p><strong>Người mua:</strong> ${contactFullname}</p>
                <p><strong>Email:</strong> ${contactEmail}</p>
                <p><strong>Số điện thoại:</strong> ${contactPhone}</p>
            </div>

            <div class="info-box">
                <h3>Chi tiết vé</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Loại vé</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${items}">
                            <tr>
                                <td>${t.name}</td>
                                <td>${t.qty}</td>
                                <td><fmt:formatNumber value="${t.price}" type="number" groupingUsed="true"/> đ</td>
                                <td><fmt:formatNumber value="${t.total}" type="number" groupingUsed="true"/> đ</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="total">
                    Tổng cộng: <fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="true"/> đ
                </div>
            </div>

            <div class="divider"></div>

            <div class="buttons">
                <a href="home.jsp" class="btn"><i class="fa-solid fa-house"></i> Về trang chủ</a>

                <c:if test="${not empty pdfPath}">
                    <a href="${pdfPath}" target="_blank" class="btn">
                        <i class="fa-solid fa-file-pdf"></i> Xem vé (PDF)
                    </a>
                </c:if>
            </div>
        </div>
    </body>
</html>
