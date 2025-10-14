<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sidebar</title>
    <link href="../css/navigationUI/sidebar.css" rel="stylesheet" type="text/css"/>
    <!-- Font Awesome để có icon -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <button class="btn-back" onclick="history.back()">
        <i class="fa-solid fa-arrow-left"></i> Quay lại trang chính
    </button>
            <ul>
                <li><a href="#"><i class="fa-solid fa-chart-pie"></i> Tổng kết</a></li>
                <!--<li><a href="#"><i class="fa-solid fa-chart-line"></i> Phân tích</a></li>-->
                <li><a href="#"><i class="fa-solid fa-list"></i> Danh sách đơn hàng</a></li>
                <li class="menu-title">Cài đặt sự kiện</li>
                <!--<li><a href="#"><i class="fa-solid fa-user"></i> Thành viên</a></li>-->
                <li><a href="#"><i class="fa-solid fa-pen"></i> Chỉnh sửa</a></li>
                <li><a href="#"><i class="fa-solid fa-trash"></i>Xóa</a></li>
                <!--<li class="menu-title">Marketing</li>-->
                <!--<li><a href="#"><i class="fa-solid fa-ticket"></i> Voucher</a></li>-->
            </ul>
        </div>

        <!-- Nội dung bên phải -->
        <div class="main-content">
            <h1>Trang nội dung</h1>
            <p>Đây là phần nội dung bên phải sidebar.</p>
        </div>
    </div>
</body>
</html>
