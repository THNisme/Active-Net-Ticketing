<%-- 
    Document   : StatisticEvents
    Updated on : Oct 12, 2025
    Author     : NGUYEN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thống kê sự kiện</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <!-- Inter Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/statistic/statisticevents.css">
</head>

<body>

<div class="container my-5">

    <!-- Time + Back -->
    <div class="d-flex align-items-center justify-content-between mb-3">
        <div>
            <i class="bi bi-calendar-event me-2"></i>
            <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss"/>
        </div>
        <a href="admincenter" class="btn btn-outline-success">Quay lại</a>
    </div>

    <hr>

    <!-- Title -->
    <h3 class="mb-3 text-center">Tổng quan doanh thu</h3>

    <div class="row g-3">

        <!-- Tổng doanh thu -->
        <div class="col-md-6">
            <div class="card d-flex flex-row align-items-center justify-content-between">
                <div>
                    <div class="fw-bold fs-6">Tổng doanh thu</div>
                    <div class="fs-5 green-text">
                        <fmt:formatNumber value="${totalRevenue}" type="number"/>đ
                    </div>
                </div>

                <div class="progress-ring" data-percent="${percentageOfRevenue}">
                    <svg viewBox="0 0 100 100">
                        <circle class="bg" cx="50" cy="50" r="45"></circle>
                        <circle class="fg" cx="50" cy="50" r="45"></circle>
                    </svg>
                    <div class="percent-text">0%</div>
                </div>
            </div>
        </div>

        <!-- Tổng vé đã bán -->
        <div class="col-md-6">
            <div class="card d-flex flex-row align-items-center justify-content-between">
                <div>
                    <div class="fw-bold fs-6">Tổng vé đã bán</div>
                    <div class="fs-5 green-text">
                        ${totalTicketsSold} / ${totalTicketsIssued} vé
                    </div>
                </div>

                <div class="progress-ring" data-percent="${percentTicketsSold}">
                    <svg viewBox="0 0 100 100">
                        <circle class="bg" cx="50" cy="50" r="45"></circle>
                        <circle class="fg" cx="50" cy="50" r="45"></circle>
                    </svg>
                    <div class="percent-text">0%</div>
                </div>
            </div>
        </div>

    </div>

    <!-- Thống kê sự kiện -->
    <p class="my-3 fw-bold">Thống kê theo sự kiện</p>

    <div class="table-responsive">
        <table class="table table-dark table-striped table-bordered align-middle text-center">
            <thead>
                <tr>
                    <th>Tên sự kiện</th>
                    <th>Tổng vé</th>
                    <th>Đã bán</th>
                    <th>Doanh thu</th>
                    <th>Tỉ lệ bán</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="s" items="${ticketStats}">
                    <c:set var="percent" value="${(s.totalTickets > 0) ? (s.soldTickets * 100 / s.totalTickets) : 0}"/>

                    <tr>
                        <td>
                            <a href="statisticsevent?eventId=${s.eventID}" 
                               class="text-decoration-none text-white fw-bold">
                                ${s.eventName}
                            </a>
                        </td>

                        <td>${s.totalTickets}</td>
                        <td>${s.soldTickets}</td>
                        <td><fmt:formatNumber value="${s.totalRevenue}" type="number"/>đ</td>

                        <td>
                            <div class="d-flex align-items-center">
                                <div class="progress flex-grow-1 me-2" style="height: 8px;">
                                    <div class="progress-bar" data-target="${percent}" style="width: 0%"></div>
                                </div>
                                <span class="percent">0%</span>
                            </div>
                        </td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<!-- Custom JS -->
<script src="${pageContext.request.contextPath}/js/statistic/statisticevents.js"></script>

</body>
</html>
