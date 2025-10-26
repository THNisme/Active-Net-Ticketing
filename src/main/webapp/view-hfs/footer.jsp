<%@page contentType="text/html" pageEncoding="UTF-8"%>

<style>
    :root {
        --pink: #ffb6b6;
    }
    
    footer {
        font-family: "Inter", sans-serif;
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
        color: var(--pink);
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
        /*margin-bottom: 12px;*/
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
        color: var(--pink);
        font-size: 18px;
        font-weight: bold;
    }

    .mb-3 {
        margin-bottom: 1rem;
    }
</style>

<footer>
    <div class="footer-container">
        <div class="footer-flex">

            <!-- Cột 1: Hotline + Email + Văn phòng -->
            <div class="footer-col">
                <div class="mb-3">
                    <h4>Hotline</h4>
                    <p>Thứ 2 - Chủ Nhật (8:00 - 22:00)</p>
                    <p class="highlight">070 490 6670</p>
                </div>
                <div class="mb-3">
                    <h4>Email</h4>
                    <p><a>hclbfactive1420@gmail.com</a></p>
                </div>
                <div class="mb-3">
                    <h4>Văn phòng chính</h4>
                    <p>600 Nguyễn Văn Cừ Nối Dài, An Bình, Bình Thủy, Cần Thơ</p>
                </div>
            </div>
            <!-- Cột 2: Dành cho khách hàng -->
            <div class="footer-col">
                <div class="mb-3">
                    <h4>Dành cho Khách hàng</h4>
                    <p><a href="${pageContext.request.contextPath}/view-hfs/terms.jsp">Điều khoản sử dụng cho khách hàng</a></p>
                </div>
                <div class="mb-3">
                    <h4>Dành cho Ban Tổ chức</h4>
                    <p><a href="${pageContext.request.contextPath}/view-hfs/termsorganizer.jsp">Điều khoản sử dụng cho ban tổ chức</a></p>
                </div>
            </div>

            <!-- Cột 3: Về công ty -->
            <div class="footer-col">
                <h4>Về công ty chúng tôi</h4>
                <p><a href="${pageContext.request.contextPath}/view-hfs/regulation.jsp">Quy chế hoạt động</a></p>
                <p><a href="${pageContext.request.contextPath}/view-hfs/privacy.jsp">Chính sách bảo mật thông tin</a></p>
                <p><a href="${pageContext.request.contextPath}/view-hfs/complaint.jsp">Cơ chế giải quyết tranh chấp/ khiếu nại</a></p>
                <p><a href="${pageContext.request.contextPath}/view-hfs/returnpolicy.jsp">Chính sách đổi trả và kiểm hàng</a></p>
                <p><a href="${pageContext.request.contextPath}/view-hfs/shipping.jsp">Điều kiện vận chuyển và giao nhận</a></p>
            </div>

        </div>
    </div>
</footer>
