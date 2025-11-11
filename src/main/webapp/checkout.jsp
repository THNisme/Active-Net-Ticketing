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
                color:#00ff80;
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
                color:#00cc66;
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
                background:#00cc66;
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
                background:#00b35a;
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

                <button type="button" class="btn-continue">Tiếp tục</button>
            </div>
        </div>

        <!-- ==== Countdown Script ==== -->
        <script>
            let m = 15, s = 0;
            const minEl = document.getElementById('min');
            const secEl = document.getElementById('sec');
            const timer = setInterval(() => {
                if (s === 0) {
                    if (m === 0) {
                        clearInterval(timer);
                        alert('Hết thời gian giữ vé!');
                        window.location.href = 'select-ticket?id=${eventId}';
                        return;
                    }
                    m--;
                    s = 59;
                } else
                    s--;
                minEl.textContent = String(m).padStart(2, '0');
                secEl.textContent = String(s).padStart(2, '0');
            }, 1000);
        </script>

    </body>
</html>
