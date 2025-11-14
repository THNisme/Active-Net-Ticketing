<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #000;
            color: #fff;
        }
        .card-custom {
            background-color: #1c1c1c;
            border: 1px solid #333;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 25px;
        }
        .section-title {
            color: #0dcaf0;
            border-bottom: 1px solid #333;
            padding-bottom: 5px;
            margin-bottom: 15px;
            font-weight: 600;
        }
        .table-custom thead th {
            background-color: #222;
            color: #0dcaf0;
        }
        .table-custom tbody tr:hover {
            background-color: #2a2a2a;
        }
    </style>
</head>

<body>

<div class="container py-4">

    <!-- Header + Back Button -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="text-info m-0">Chi tiết đơn hàng ${details[0].orderId}</h3>

        <button class="btn btn-outline-light btn-sm" onclick="history.back()">
            <i class="bi bi-arrow-left"></i> Quay lại
        </button>
    </div>

    <!-- 1. Thông tin người mua -->
    <div class="card-custom">
        <div class="section-title">Thông tin người mua</div>

        <p><strong>Họ tên:</strong> ${details[0].contactFullname}</p>
        <p><strong>Email:</strong> ${details[0].contactEmail}</p>
        <p><strong>SĐT:</strong> ${details[0].contactPhone}</p>
        <p><strong>Ngày tạo đơn:</strong>
            <fmt:formatDate value="${details[0].createdDate}" pattern="dd/MM/yyyy - HH:mm:ss" />
        </p>
    </div>

    <!-- 2. Thông tin sự kiện -->
    <div class="card-custom">
        <div class="section-title">Thông tin sự kiện</div>

        <p><strong>Tên sự kiện:</strong> ${details[0].eventName}</p>
        <p><strong>Loại sự kiện:</strong> ${details[0].categoryName}</p>
        <p><strong>Ngày bắt đầu:</strong>
            <fmt:formatDate value="${details[0].startDate}" pattern="dd/MM/yyyy" />
        </p>
        <p><strong>Ngày kết thúc:</strong>
            <fmt:formatDate value="${details[0].endDate}" pattern="dd/MM/yyyy" />
        </p>
    </div>

    <!-- 3. Địa điểm tổ chức -->
    <div class="card-custom">
        <div class="section-title">Địa điểm tổ chức</div>

        <p><strong>Địa điểm:</strong> ${details[0].placeName}</p>
        <p><strong>Địa chỉ:</strong> ${details[0].placeAddress}</p>
    </div>

    <!-- 4. Danh sách vé -->
    <div class="card-custom">
        <div class="section-title">Vé trong đơn hàng</div>

        <table class="table table-dark table-custom text-center">
            <thead>
            <tr>
                <th>Serial</th>
                <th>Loại vé</th>
                <th>Khu (Zone)</th>
                <th>Hàng - Ghế</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="d" items="${details}">
                <tr>
                    <td>${d.serialNumber}</td>
                    <td>${d.ticketTypeName}</td>
                    <td>${d.zoneName}</td>
                    <td>${d.rowLabel}${d.seatNumber}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
