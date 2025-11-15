<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Xác nhận thanh toán - ${eventName}</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        <style>
            body {
                margin: 0;
                background: #0f0f0f;
                font-family: "Segoe UI", sans-serif;
                color: #fff;
            }

            /* ===== Header giống checkout ===== */
            .event-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 40px 80px;

                background-size: cover;
                background-position: center;
                border-bottom: 1px solid #333;
            }

            .event-info h2 {
                margin: 0 0 10px;
                color: #ffb6b6;
                font-size: 28px;
                font-weight: 600;
            }

            .event-info p {
                margin: 6px 0;
                color: #ccc;
                font-size: 16px;
            }

            .countdown {
                text-align: center;
                background: rgba(36, 36, 36, 0.85);
                padding: 20px 30px;
                border-radius: 12px;
                box-shadow: 0 0 10px rgba(0,0,0,0.4);
            }

            .countdown-title {
                color: #eee;
                margin-bottom: 8px;
            }

            .timer {
                display: flex;
                align-items: center;
                gap: 10px;
                justify-content: center;
            }

            .timer-box {
                background: #e94d58;
                width: 70px;
                height: 70px;
                border-radius: 12px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: 700;
                font-size: 28px;
            }

            .timer-colon {
                color: #fff;
                font-size: 28px;
                font-weight: 700;
            }

            /* ===== Nội dung thanh toán ===== */
            .container {
                max-width: 1000px;
                margin: 50px auto;
                background: #1a1a1a;
                border-radius: 12px;
                padding: 40px;
            }

            h3, h2 {
                color: #ffb6b6;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 12px;
                border-bottom: 1px solid #333;
            }

            th {
                text-align: left;
                color: #ccc;
            }

            .total {
                text-align: right;
                font-size: 20px;
                font-weight: 700;
                margin-top: 20px;
            }

            .btn-pay {
                display: block;
                margin: 30px auto 0;
                padding: 14px 28px;
                background: #ffb6b6;
                border: none;
                border-radius: 10px;
                font-size: 18px;
                font-weight: 700;
                color: #fff;
                cursor: pointer;
            }

            .btn-pay:hover {
                background: #ff9999;
            }
        </style>
    </head>
    <body>

        <!-- ==== HEADER (tên sự kiện + địa điểm + đồng hồ) ==== -->
        <div class="event-header">
            <div class="event-info">
                <h2>${eventName}</h2>
                <p><i class="fa-regular fa-clock"></i> ${startStr}</p>
                <p><i class="fa-solid fa-location-dot"></i> ${placeName}</p>
            </div>

            <div class="countdown">
                <div class="countdown-title">Hoàn tất đặt vé trong</div>
                <div class="timer">
                    <div id="min" class="timer-box">15</div>
                    <div class="timer-colon">:</div>
                    <div id="sec" class="timer-box">00</div>
                </div>
            </div>
        </div>

        <!-- ==== BODY: Xác nhận thanh toán ==== -->
        <div class="container">
            <h2>THANH TOÁN</h2>

            <h3>Thông tin người mua</h3>
            <p><strong>Họ tên:</strong> ${fullName}</p>
            <p><strong>Số điện thoại:</strong> ${phone}</p>
            <p><strong>Email:</strong> ${email}</p>

            <h3>Chi tiết vé</h3>
            <table>
                <tr>
                    <th>Loại vé</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                </tr>
                <c:forEach var="t" items="${tickets}">
                    <tr>
                        <td>${t.name}</td>
                        <td>${t.qty}</td>
                        <td><fmt:formatNumber value="${t.total}" type="number" groupingUsed="true"/> đ</td>
                    </tr>
                </c:forEach>
            </table>

            <div class="total">
                Tổng cộng: <fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="true"/> đ
            </div>

            <form action="payments" method="post">
                <input type="hidden" name="paymentToken" value="${paymentToken}">
                <input type="hidden" name="fullName" value="${fullName}">
                <input type="hidden" name="phone" value="${phone}">
                <input type="hidden" name="email" value="${email}">
                <input type="hidden" name="eventId" value="${eventId}">
                <input type="hidden" name="eventName" value="${eventName}">
                <input type="hidden" name="placeName" value="${placeName}">
                <input type="hidden" name="startStr" value="${startStr}">
                <input type="hidden" name="totalAmount" value="${totalAmount}">
                <input type="hidden" name="selectionsJson" value='${selectionsJson}'>
                <button type="submit" class="btn-pay">Xác nhận và thanh toán</button>
            </form>

        </div>

        <!-- ==== Countdown giữ nguyên từ checkout ==== -->
        <script>
            const STORAGE_KEY = "checkout_expire_time";
            let expireTime = sessionStorage.getItem(STORAGE_KEY);

            if (!expireTime) {
                alert("Hết thời gian giữ vé!");
                window.location.href = "select-ticket?id=${eventId}";
            }

            function updateTimer() {
                const now = Date.now();
                const diff = expireTime - now;

                if (diff <= 0) {
                    document.getElementById("min").textContent = "00";
                    document.getElementById("sec").textContent = "00";
                    alert("Hết thời gian giữ vé!");
                    sessionStorage.removeItem(STORAGE_KEY);
                    window.location.href = "select-ticket?id=${eventId}";
                    return;
                }

                const minutes = Math.floor(diff / 60000);
                const seconds = Math.floor((diff % 60000) / 1000);
                document.getElementById("min").textContent = String(minutes).padStart(2, "0");
                document.getElementById("sec").textContent = String(seconds).padStart(2, "0");
            }

            setInterval(updateTimer, 1000);
            updateTimer();
        </script>
        <script>
            // Reset countdown khi thanh toán thành công
            sessionStorage.removeItem("checkout_expire_time");
        </script>

        <script>
            const CTX = '<%= request.getContextPath()%>';

            // Đẩy state để bắt sự kiện back
            history.pushState(null, '', location.href);

            window.addEventListener('popstate', function () {

                const confirmCancel = confirm(
                        "Bạn có chắc muốn hủy quá trình đặt vé?\n" +
                        "⚠ Vé đang giữ sẽ bị hủy.\n" +
                        "⚠ Thời gian giữ vé sẽ bị xóa."
                        );

                if (confirmCancel) {
                    // Xóa timer
                    sessionStorage.removeItem("checkout_expire_time");

                    // Điều hướng về home
                    window.location.href = CTX + "/home";
                } else {
                    // Nếu cancel thì push lại state để chặn back
                    history.pushState(null, '', location.href);
                }
            });
        </script>
    </body>
</html>
