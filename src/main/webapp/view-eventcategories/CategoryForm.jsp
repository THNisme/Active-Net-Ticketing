<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="Models.ltk1702.EventCategories" %>

<%
    EventCategories category = (EventCategories) request.getAttribute("category");
    boolean isEdit = (category != null);
    int categoryId = isEdit ? category.getCategoryID() : 0;
    String categoryName = isEdit ? category.getCategoryName() : "";
    String description = isEdit ? category.getDescription() : "";
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Chỉnh sửa" : "Thêm mới"%> danh mục sự kiện</title>    
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/place-page-style/style.css">

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
        <div class="col-md-6 mx-auto mt-4">
            <h3 class="text-center mb-4">
                <%= isEdit ? "Chỉnh sửa danh mục sự kiện" : "Thêm mới danh mục sự kiện"%>
            </h3>

            <% if (session.getAttribute("error") != null) {%>
            <div class="alert alert-danger text-center">
                <%= session.getAttribute("error")%>

            </div>
            <% session.removeAttribute("error");
                } %>

            <% if (session.getAttribute("success") != null) {%>
            <div class="alert alert-success text-center">
                <%= session.getAttribute("success")%>

            </div>
           <% session.removeAttribute("success");
                } %>

            <form action="eventcategories" method="post" class="bg-dark p-4 rounded-4 shadow-lg border border-secondary">
                <input type="hidden" name="categoryID" value="<%= categoryId%>">

                <div class="mb-3">
                    <label class="form-label">Tên danh mục</label>
                    <input type="text" name="categoryName" class="form-control" placeholder="Nhập tên danh mục sự kiện"
                           value="<%= categoryName%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô tả</label>
                    <textarea name="description" class="form-control" rows="4"
                              placeholder="Nhập mô tả cho danh mục (tuỳ chọn)"><%= description%></textarea>
                </div>

                <div class="d-flex justify-content-between mt-4">
                    <a href="eventcategories?action=list" class="btn btn-outline-light">
                        <i class="bi bi-arrow-left-circle"></i> Quay lại
                    </a>

                    <% if (isEdit) { %>
                    <button type="submit" name="action" value="update" class="btn btn-success px-4">
                        <i class="bi bi-pencil-square"></i> Cập nhật
                    </button>
                    <% } else { %>
                    <button type="submit" name="action" value="create" class="btn btn-primary px-4">
                        <i class="bi bi-plus-circle"></i> Thêm mới
                    </button>
                    <% }%>
                </div>
            </form>
        </div>
    </body>
</html>
