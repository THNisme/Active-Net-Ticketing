<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                background-color: #222 !important;
                color: #fff !important;
            }
        </style>
        <meta charset="UTF-8">
        <title>Điều khoản khách hàng | ACTIVE-NET</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="../css/header.css" rel="stylesheet">
    </head>
    <body>

        <%@include file="header.jsp" %>

        <div class="container mt-5 mb-5">
            <h3 class="fw-bold mb-4">Điều khoản sử dụng dành cho Khách hàng</h3>

            <p>Cảm ơn quý khách đã tin tưởng và sử dụng dịch vụ đặt vé của <strong>ACTIVE-NET</strong>. 
                Khi mua vé và sử dụng dịch vụ trên website, quý khách đồng ý với các điều khoản sau:</p>

            <h5 class="mt-4">1. Đặt vé và thanh toán</h5>
            <ul>
                <li>Khách hàng cần cung cấp đầy đủ và chính xác thông tin khi đặt vé.</li>
                <li>Thanh toán được thực hiện qua các phương thức hợp lệ (ví điện tử).</li>
                <li>Đơn hàng chỉ được xác nhận khi hệ thống ghi nhận thanh toán thành công.</li>
            </ul>

            <h5 class="mt-4">2. Vé điện tử</h5>
            <ul>
                <li>Sau khi thanh toán, vé sẽ được gửi qua email hoặc hiển thị trong tài khoản khách hàng.</li>
                <li>Mỗi mã vé chỉ có giá trị cho một lần sử dụng và không được sao chép, sửa đổi hoặc bán lại trái phép.</li>
            </ul>

            <h5 class="mt-4">3. Chính sách hủy/đổi vé</h5>
            <ul>
                <li>Vé đã mua không được hoàn tiền trừ trường hợp sự kiện bị hủy.</li>
                <li>Khách hàng có thể đổi thông tin người tham dự (nếu sự kiện cho phép) theo hướng dẫn của ban tổ chức.</li>
            </ul>

            <h5 class="mt-4">4. Trách nhiệm của khách hàng</h5>
            <ul>
                <li>Khách hàng cần giữ gìn thông tin vé và mã QR để sử dụng khi tham dự sự kiện.</li>
                <li>Khách hàng cam kết không sử dụng dịch vụ để thực hiện hành vi gian lận hoặc vi phạm pháp luật.</li>
            </ul>

            <h5 class="mt-4">5. Trách nhiệm của ACTIVE-NET</h5>
            <ul>
                <li>Đảm bảo hệ thống bán vé hoạt động ổn định và bảo mật thông tin khách hàng.</li>
                <li>Hỗ trợ khách hàng trong trường hợp gặp sự cố liên quan đến giao dịch và vé điện tử.</li>
            </ul>

            <h5 class="mt-4">6. Điều khoản chung</h5>
            <p>ACTIVE-NET có quyền thay đổi, bổ sung nội dung điều khoản này và sẽ công bố trên website. 
                Các điều chỉnh sẽ có hiệu lực ngay khi được đăng tải.</p>

            <p class="mt-4"><em>Việc tiếp tục sử dụng dịch vụ đồng nghĩa với việc khách hàng đồng ý với toàn bộ điều khoản trên.</em></p>
        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>
