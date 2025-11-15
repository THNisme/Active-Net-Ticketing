<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán - ${eventName}</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        <style>
            body{
                margin:0;
                background:#0f0f0f;
                font-family:"Segoe UI",sans-serif;
                color:#fff;
            }
            /* ==== Header ==== */
            .event-header{
                display:flex;
                justify-content:space-between;
                align-items:center;
                padding:30px 80px;
                background:#161616;
            }
            .event-info h2{
                margin:0 0 10px;
                color: #ff9999;
            }
            .event-info p{
                margin:6px 0;
                color:#ccc;
            }
            .countdown{
                text-align:center;
                background:#242424;
                padding:20px 30px;
                border-radius:12px;
            }
            .timer{
                display:flex;
                gap:16px;
                justify-content:center;
            }
            .timer-box{
                background:#e94d58;
                width:72px;
                height:72px;
                border-radius:12px;
                display:flex;
                align-items:center;
                justify-content:center;
                font-weight:700;
                font-size:28px;
            }
            /* ==== Layout ==== */
            .container{
                display:flex;
                gap:40px;
                padding:40px 80px;
            }
            .left{
                flex:2;
            }
            .form-card{
                background:#212121;
                border-radius:12px;
                padding:24px 28px;
            }
            .form-card h3{
                margin:0 0 16px;
            }
            .form-group{
                margin-bottom:16px;
            }
            .form-group label{
                display:block;
                margin-bottom:8px;
                color:#bbb;
            }
            .form-group input{
                width:100%;
                padding:12px;
                border-radius:8px;
                border:1px solid #333;
                background:#101010;
                color:#fff;
            }
            .right{
                flex:1;
                background:#1b1b1b;
                border-radius:12px;
                padding:24px 28px;
                position:sticky;
                top:20px;
                height:fit-content;
            }
            .summary-header{
                display:flex;
                justify-content:space-between;
                align-items:center;
                margin-bottom:12px;
            }
            .summary-header a{
                color:#ffb6b6;
                text-decoration:none;
            }
            .summary-item{
                display:flex;
                justify-content:space-between;
                margin:10px 0;
                color:#ddd;
            }
            .total-line{
                display:flex;
                justify-content:space-between;
                font-weight:700;
                font-size:18px;
                border-top:1px solid #333;
                padding-top:14px;
                margin-top:10px;
            }
            .btn-continue{
                width:100%;
                background:#ffb6b6;
                color:#fff;
                border:none;
                padding:14px;
                border-radius:10px;
                font-size:18px;
                font-weight:700;
                cursor:pointer;
                margin-top:20px;
            }
            .btn-continue:hover{
                background:#ff9999;
            }
        </style>
    </head>
    <body>

        <!-- ==== Header (event + countdown) ==== -->
        <div class="event-header">
            <div class="event-info">
                <h2>${eventName}</h2>
                <p><i class="fa-regular fa-clock"></i> ${startStr}</p>
                <p><i class="fa-solid fa-location-dot"></i> ${placeName}</p>
            </div>

            <div class="countdown">
                <div>Hoàn tất đặt vé trong</div>
                <div class="timer">
                    <div id="min" class="timer-box">15</div>
                    <div id="sec" class="timer-box">00</div>
                </div>
            </div>
        </div>

        <!-- ==== Content ==== -->
        <div class="container">

            <form action="payment-preview" method="post" style="display:flex;gap:40px;width:100%;">
                <!-- LEFT: Bảng câu hỏi -->
                <div class="left">
                    <div class="form-card">
                        <h3>BẢNG CÂU HỎI</h3>
                        <div class="form-group">
                            <label>Họ và tên *</label>
                            <input type="text" name="fullName" placeholder="Điền câu trả lời của bạn" required>
                        </div>
                        <div class="form-group">
                            <label>Số điện thoại *</label>
                            <input type="tel" name="phone" placeholder="Điền câu trả lời của bạn" required>
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ email *</label>
                            <input type="email" name="email" placeholder="Điền câu trả lời của bạn" required>
                        </div>
                    </div>
                </div>

                <!-- RIGHT: Tóm tắt vé -->
                <div class="right">
                    <div class="summary-header">
                        <h3>Thông tin đặt vé</h3>
                        <a href="select-ticket?id=${eventId}">Chọn lại vé</a>
                    </div>

                    <c:forEach var="t" items="${tickets}">
                        <div class="summary-item">
                            <span>${t.name} x${t.qty}</span>
                            <span><fmt:formatNumber value="${t.total}" type="number" groupingUsed="true"/> đ</span>
                        </div>
                    </c:forEach>

                    <div class="total-line">
                        <span>Tổng cộng</span>
                        <span><fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="true"/> đ</span>
                    </div>

                    <!-- Hidden gửi sang PaymentServlet -->
                    <input type="hidden" name="eventId" value="${eventId}">
                    <input type="hidden" name="totalAmount" value="${totalAmount}">
                    <input type="hidden" name="eventName" value="${eventName}">
                    <input type="hidden" name="placeName" value="${placeName}">
                    <input type="hidden" name="startStr" value="${startStr}">

                    <button type="submit" class="btn-continue">Tiếp tục</button>
                </div>
            </form>
        </div>

        <!-- ==== Countdown Script ==== -->
        <script>
           const COUNTDOWN_MINUTES = 10;
            const STORAGE_KEY = "checkout_expire_time";

            let expireTime = sessionStorage.getItem(STORAGE_KEY);
            if (!expireTime) {
                // Nếu chưa có, đặt thời gian hết hạn mới (tính bằng milliseconds)
                expireTime = Date.now() + COUNTDOWN_MINUTES * 60 * 1000;
                sessionStorage.setItem(STORAGE_KEY, expireTime);
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
    </body>
</html>
