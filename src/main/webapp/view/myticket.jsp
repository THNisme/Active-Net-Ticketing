<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Vé của tôi</title>
        <link href="../css/myticket.css" rel="stylesheet" type="text/css"/>
    </head>
    <%@include file="header.jsp" %>
    <body>
        <div class="container">
            <h2>Vé của tôi</h2>

            <!-- Tabs trạng thái vé -->
            <div class="tabs">
                <button class="tab active">Tất cả</button>
                <button class="tab">Thành công</button>
                <button class="tab">Đang xử lý</button>
                <button class="tab">Đã hủy</button>
            </div>

            <!-- Tabs phụ -->
            <div class="sub-tabs">
                <span class="sub-tab active">Tất cả</span>
                <span class="sub-tab">Sắp diễn ra</span>
                <span class="sub-tab">Đã kết thúc</span>
            </div>

            <!-- Nội dung vé -->
            <div class="ticket-empty">
                <img src="../img/noticket.jpg" alt=""/>
                <p>Bạn chưa có vé nào</p>
                <a href="events.jsp" class="btn-buy">Mua vé ngay</a>  
            </div>
        </div>
        <script>
            // Xử lý tab chính
            const tabs = document.querySelectorAll(".tab");
            tabs.forEach(tab => {
                tab.addEventListener("click", () => {
                    // Bỏ active tab cũ
                    tabs.forEach(t => t.classList.remove("active"));
                    // Gắn active cho tab được bấm
                    tab.classList.add("active");
                    // TODO: chỗ này bạn có thể gọi AJAX / JSP để load danh sách vé theo trạng thái
                });
            });

            // Xử lý tab phụ
            const subTabs = document.querySelectorAll(".sub-tab");
            subTabs.forEach(sub => {
                sub.addEventListener("click", () => {
                    subTabs.forEach(s => s.classList.remove("active"));
                    sub.classList.add("active");
                    // TODO: load nội dung vé theo sub-tab (sắp diễn ra, đã kết thúc...)
                });
            });
        </script>

    </body>
    <%@include file="footer.jsp" %>
</html>
