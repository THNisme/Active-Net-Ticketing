<%@page contentType="text/html" pageEncoding="UTF-8"%>

<style>
    footer {
        font-family: sans-serif;
        color: #ccc;
        font-size: 14px;
        background: #2d3240;
        padding: 40px 0;
    }

    footer a {
        color: inherit;
        text-decoration: none;
        transition: color 0.2s ease, transform 0.1s ease;
    }

    footer a:hover {
        color: #00b894;
    }

    footer a:active {
        color: #900;
        transform: scale(0.97);
    }

    footer p, footer h4 {
        margin: 6px 0;
    }

    footer h4 {
        font-size: 1.4em;
        font-weight: bold;
        margin-bottom: 12px;
        color: #fff;
    }

    .footer-container {
        max-width: 1280px;
        margin: 0 auto;
        padding: 0 20px;
    }

    .footer-flex {
        display: flex;
        flex-wrap: wrap;
        gap: 40px;
    }

    .footer-col {
        flex: 1;
        min-width: 200px;
    }

    .highlight {
        color: #1dd1a1;
        font-size: 18px;
        font-weight: bold;
    }
</style>

<footer>
    <div class="footer-container">
        <div class="footer-flex">

            <!-- Cột 1: Hotline + Email + Văn phòng -->
            <div class="footer-col">
                <h4>Hotline</h4>
                <p>📞 Thứ 2 - Chủ Nhật (8:00 - 22:00)</p>
                <p class="highlight">0808.1508</p>

                <h4>Email</h4>
                <p>✉️ <a>hifive.team1201@gmail.com</a></p>

                <h4>Văn phòng chính</h4>
                <p>📍 600 Nguyễn Văn Cừ Nối Dài, An Bình, Bình Thủy, Cần Thơ</p>
            </div>
            <!-- Cột 2: Dành cho khách hàng -->
            <div class="footer-col">
                <h4>Dành cho Khách hàng</h4>
                <p><a href="terms.jsp">Điều khoản sử dụng cho khách hàng</a></p>
                <br>

                <h4>Dành cho Ban Tổ chức</h4>
                <p><a href="termsorganizer.jsp">Điều khoản sử dụng cho ban tổ chức</a></p>
            </div>

            <!-- Cột 3: Về công ty -->
            <div class="footer-col">
                <h4>Về công ty chúng tôi</h4>
                <p><a href="regulation.jsp">Quy chế hoạt động</a></p>
                <p><a href="privacy.jsp">Chính sách bảo mật thông tin</a></p>
                <p><a href="complaint.jsp">Cơ chế giải quyết tranh chấp/ khiếu nại</a></p>
                <p><a href="returnpolicy.jsp">Chính sách đổi trả và kiểm hàng</a></p>
                <p><a href="shipping.jsp">Điều kiện vận chuyển và giao nhận</a></p>
                <p><a href="#">Phương thức thanh toán</a></p>
            </div>

        </div>
    </div>
</footer>
