<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Phương thức thanh toán — Ví ảo | ACTIVE-NET</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="../css/header.css" rel="stylesheet">
    </head>
<body>

<%@include file="header.jsp" %>

<div class="container mt-5 mb-5">
  <h3 class="fw-bold mb-4">Hướng dẫn thanh toán bằng Ví ảo AOE</h3>

  <p>Ví ảo ANET là số dư nội bộ gắn với tài khoản của bạn trên hệ thống ACTIVE-NET. 
     Bạn có thể nạp tiền trước vào ví và sử dụng số dư này để thanh toán khi mua vé.</p>

  <h5 class="mt-4">1. Nạp tiền vào ví ANET</h5>
  <ul>
    <li>Đăng nhập vào tài khoản ACTIVE-NET.</li>
    <li>Vào mục <strong>Ví ANET</strong> &rarr; chọn <em>Nạp tiền</em>.</li>
    <li>Sau khi nạp thành công, số dư hiển thị ngay trong tài khoản.</li>
  </ul>

  <h5 class="mt-4">2. Thanh toán mua vé</h5>
  <ul>
    <li>Chọn sự kiện và tiến hành đặt vé như bình thường.</li>
    <li>Tại bước thanh toán, chọn phương thức <strong>Ví ANET</strong>.</li>
    <li>Nếu số dư đủ: hệ thống trừ trực tiếp và xác nhận đơn hàng.</li>
    <li>Nếu số dư không đủ: hệ thống thông báo và hướng dẫn nạp thêm.</li>
  </ul>

  <h5 class="mt-4">3. Hoàn tiền</h5>
  <ul>
    <li>Nếu sự kiện bị hủy hoặc phát sinh lỗi giao dịch, tiền sẽ được hoàn lại vào Ví ANET của bạn.</li>
    <li>Bạn có thể kiểm tra lịch sử hoàn tiền trong mục <em>Lịch sử ví</em>.</li>
  </ul>

  <h5 class="mt-4">4. Ưu điểm</h5>
  <ul>
    <li>Thanh toán nhanh chóng, không cần qua ngân hàng.</li>
    <li>Quản lý dễ dàng ngay trong tài khoản ACTIVE-NET.</li>
    <li>Phù hợp cho trải nghiệm demo/test chức năng.</li>
  </ul>

  <p class="mt-4"><em>👉 Chỉ cần nạp tiền vào Ví ANET một lần, bạn có thể dùng số dư để mua vé bất kỳ lúc nào.</em></p>
</div>

<%@include file="footer.jsp" %>
</body>
</html>
