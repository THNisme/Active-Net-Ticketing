<%-- 
    Document   : place-details
    Created on : Nov 12, 2025, 1:44:41 AM
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Place details - [PlaceName]</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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
                background-color: #1f2937;
                border-radius: 10px 10px 0px 0px;
            }

            .event-main img {
                width: 160px;
                height: 110px;
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

            .actions button:hover {
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
            .table-wrapper{
                padding: 10px;
                background-color: #212529;
                border-radius: 10px;
            }

            #modalZoneMapImageWrapper {
                overflow: hidden;
                background-color: #1f2937;
            }

            .zoom-control-btn-group {
                position: relative;
                z-index: 999;
            }
        </style>


        <div class="container">
            <div class="mt-5">
                <h5 class="text-center mb-3">Thông tin chi tiết nơi tổ chức</h5>
                <h2 class="text-center text-uppercase">${place.placeName}</h2>
            </div>

            <section class="general-infor mb-5">
                <h4 class="general-infor-label">Thông tin chung:</h4>
                <div class="table-wrapper">
                    <table class="table table-dark align-middle">
                        <thead>
                            <tr>
                                <th scope="col">${place.placeName}</th>
                            </tr>
                        </thead>
                        <tbody class="table-group-divider">
                            <tr>
                                <th scope="row">Địa chỉ</th>
                                <td>${place.address}</td>
                            </tr>
                            <tr>
                                <th scope="row">Mô tả</th>
                                <td>${place.description}</td>
                            </tr>
                            <tr>
                                <th scope="row">Trạng thái</th>
                                <td>Đang được dùng (${quantity} sự kiện).</td>
                            </tr>
                            <tr>
                                <th scope="row">
                                    Sơ đồ nơi tổ chức
                                </th>
                                <td class="zonemap-size-cell">
                                    <div id="modalZoneMapImageWrapper">
                                        <img src="${place.seatMapURL}" alt="" id="modalZoneMapImage" width="600px">
                                        <div class="zoom-control-btn-group">
                                            <button type="button" class="btn btn-warning" onclick="zoomIn()">Zoom In</button>
                                            <button type="button" class="btn btn-warning" onclick="zoomOut()">Zoom Out</button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </section>


            <section class="event-list mb-5">
                <h4 class="general-infor-label">Các sự kiện đang được tổ chức tại ${place.placeName} </h4>
                <c:if test="${quantity == 0}"><p class="text-success">Nơi tổ chức hiện đang trống ! <a href="event-form">Tạo sự kiện ngay!</a></p></c:if>

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
                        </div>
                    </div>

                    <div id="event-list"></div>
                    <div class="pagination" id="pagination"></div>
            </section>

        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/place-form-js/handle-zoom-image.js"></script>
        <script>
            const events = <c:out value="${eventsJsonString}" escapeXml="false"/>;
        </script>        
        <script src="${pageContext.request.contextPath}/js/place-details/handle-events.js"></script>
    </body>

</html>
