<%-- 
    Document   : admincenter
    Created on : Nov 12, 2025, 1:45:26 AM
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>Sự kiện của tôi</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">
    </head>

    <body>
        <style>
            body {
                background: #111827;
                color: white;
                font-family: 'Inter', sans-serif;
                padding: 20px;
            }

            h1 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .event {
                background: #1f2937;
                border-radius: 10px;
                margin-bottom: 20px;
                overflow: hidden;
                display: flex;
                flex-direction: column;
            }

            /* phần trên */
            .event-main {
                display: flex;
                padding: 15px;
                gap: 15px;
            }

            .event-main img {
                width: 192px;
                height: 132px;
                object-fit: cover;
                border-radius: 6px;
            }

            .event-content {
                flex: 1;
            }

            .event-content h2 {
                font-size: 18px;
                font-weight: bold;
                margin: 0 0 6px 0;
            }

            .event-content .time {
                display: flex;
                align-items: center;
                gap: 6px;
                color: #22c55e;
                font-weight: bold;
                font-size: 14px;
                margin: 4px 0;
            }

            .event-content .location {
                display: flex;
                align-items: center;
                gap: 6px;
                font-size: 14px;
                margin: 4px 0;
                color: #ffffff;
            }

            .event-content .address {
                color: #9ca3af;
                font-size: 13px;
                margin-top: 2px;
            }

            /* actions dưới */
            .actions {
                display: flex;
                justify-content: center;
                /* gom vào giữa */
                align-items: center;
                /* căn giữa dọc */
                gap: 40px;
                /* khoảng cách giữa các nút */
                background: #374151;
                padding: 12px 0;
                border-top: 1px solid #2c2d31;
                border-radius: 0 0 10px 10px;
            }

            .actions a {
                background: none;
                border: none;
                color: white;
                cursor: pointer;
                font-size: 13px;
                display: flex;
                flex-direction: column;
                /* icon trên, chữ dưới */
                align-items: center;
                justify-content: center;
                gap: 6px;
                text-decoration: none;
            }

            .actions a:hover {
                color: #38bdf8;
            }

            /* phân trang */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
                gap: 5px;
            }

            .pagination button {
                background: #1f2937;
                border: none;
                color: white;
                padding: 8px 12px;
                border-radius: 5px;
                cursor: pointer;
            }

            .pagination button.active {
                background: #22c55e;
            }

            .pagination button:disabled {
                background: #374151;
                cursor: not-allowed;
                color: #9ca3af;
            }

            /* Khung tổng chiếm toàn bộ ngang */
            .search-filter {
                padding: 12px 15px;
                border-radius: 6px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                gap: 20px;
                width: 100%;
                box-sizing: border-box;
                margin-bottom: 2rem;
            }

            .search-box {
                flex: 1;
                display: flex;
                align-items: center;
                background: #fff;
                border-radius: 30px;
                overflow: hidden;
                max-width: 500px;
            }

            .search-icon {
                display: flex;
                align-items: center;
                justify-content: center;
                padding-left: 12px;
                padding-right: 8px;
                color: #888;
            }

            .search-box svg {
                stroke: #666;
                /* màu xám nhạt */
            }

            .search-box input {
                border: none;
                padding: 8px 12px;
                outline: none;
                flex: 1;
                font-size: 14px;
            }

            .search-box button {
                border: none;
                background: #9ca3af;
                color: #fff;
                padding: 8px 16px;
                border-radius: 0 30px 30px 0;
                cursor: pointer;
                font-size: 14px;
            }

            .search-box button:hover {
                background: #45a049;
            }


            /* Bộ lọc nút nằm ngang bên phải */
            .filters {
                display: flex;
                gap: 10px;
                flex-shrink: 0;
                /* không co lại */
            }

            .filters .filter {
                text-decoration: none;
                color: #000;
                background: #fff;
                border: 1px solid #ccc;
                border-radius: 20px;
                padding: 8px 18px;
                cursor: pointer;
                font-size: 14px;
            }

            .filters .filter:hover {
                background-color: #4CAF50;
                color: #fff;
            }

            .filters .filter.active {
                background: #4CAF50;
                color: #fff;
                border-color: #4CAF50;
            }
        </style>
        <div class="d-flex mb-3" style="justify-content: space-between; align-items: center">
            <div>
                <h1 class="m-0">Sự kiện của tôi</h1>
            </div>
            <div>
                <a href="logout" class="btn btn-primary">Đăng xuất</a>
            </div>

        </div>



        <div class="search-filter">
            <!-- Ô tìm kiếm -->
            <div class="search-box">
                <span class="search-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="m21 21-4.34-4.34" />
                    <circle cx="11" cy="11" r="8" />
                    </svg>
                </span>
                <input type="text" placeholder="Tìm kiếm sự kiện">
                <button>Tìm kiếm</button>
            </div>

            <!-- Bộ lọc -->
            <div class="filters">
                <a href="event-form" class="filter">Tạo sự kiện mới</a>
                <a href="place-overview" class="filter">Quản lí nơi tổ chức</a>
                <a href="user-manage" class="filter">Tài khoản</a>
                <a href="#" class="filter">Thông kế chung</a>
            </div>
        </div>

        <div id="event-list"></div>
        <div class="pagination" id="pagination"></div>

        <!-- Modal Delete -->
        <div class="modal fade" id="modalDelete" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalDeleteLable" aria-hidden="true">
            <div class="modal-dialog">

                <form action="event-form?action=delete" method="POST">
                    <div class="modal-content modal-theme delete">

                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="modalDeleteLable">Xác nhận xóa sự kiện</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <div class="mb-3">
                                <p class="text-center">
                                    Bạn có chắc muốn xóa <span class="fw-bolder text-danger" id="eventName"></span>
                                <p>
                            </div>

                            <div class="mb-3">
                                <p class="text-center">Lưu ý: hành động sẽ xóa <strong class="text-danger">tất cả vé và loại vé</strong> của sự kiện này !</p>
                                <input type="hidden" id="eventID" name="eid">
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-danger d-flex justify-content-end">Xóa</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
        <script>
            const events = <c:out value="${eventsJsonString}" escapeXml="false"/>;
        </script>
        <script src="${pageContext.request.contextPath}/js/admincenter/handle-admincenter.js"></script>
    </body>

</html>
