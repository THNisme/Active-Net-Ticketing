<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Xác thực OTP</title>

        <style>
            body {
                background: #f2f3f7;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .otp-box {
                background: white;
                width: 380px;
                padding: 30px;
                border-radius: 20px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
                text-align: center;
            }

            input {
                width: 100%;
                padding: 12px;
                border-radius: 8px;
                border: 1px solid #ddd;
                margin-bottom: 15px;
            }

            button {
                width: 100%;
                padding: 12px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-size: 15px;
                margin-top: 5px;
            }

            #confirm-btn {
                background: #d36b6b;
                color: white;
            }

            #resend-btn {
                background: #888;
                color: white;
                opacity: 0.5;
            }

            #resend-btn.enabled {
                opacity: 1;
                cursor: pointer;
            }

            .msg-error {
                color: red;
                margin-top: 10px;
            }
        </style>
    </head>

    <body>
        <div class="otp-box">

            <h2>Nhập mã OTP</h2>

            <p style="margin-bottom:10px; color:#e74c3c; font-weight:bold;">
                Mã sẽ hết hạn sau: <span id="countdown">60s</span>
            </p>

            <form action="verify-otp" method="post">
                <input type="text" name="otp" placeholder="Nhập OTP" required>
                <button type="submit" id="confirm-btn">Xác nhận</button>
            </form>

            <form action="resend-otp" method="post">
                <button type="submit" id="resend-btn" disabled>Gửi lại mã</button>
            </form>
            <c:if test="${not empty errorOTP}">
                <p class="msg-error">${errorOTP}</p>
            </c:if>

            <c:if test="${not empty infoOTP}">
                <p class="msg-info" style="color: green;">${infoOTP}</p>
            </c:if>
        </div>

        <script>
            let timeLeft = 60;
            const countdownElem = document.getElementById("countdown");
            const resendBtn = document.getElementById("resend-btn");
            const confirmBtn = document.getElementById("confirm-btn");

            const timer = setInterval(() => {
                timeLeft--;
                countdownElem.textContent = timeLeft + "s";

                if (timeLeft <= 0) {
                    clearInterval(timer);
                    countdownElem.textContent = "OTP đã hết hạn!";
                    confirmBtn.disabled = true;
                    confirmBtn.style.background = "#aaa";

                    resendBtn.disabled = false;
                    resendBtn.classList.add("enabled");
                }
            }, 1000);
        </script>

    </body>
</html>
