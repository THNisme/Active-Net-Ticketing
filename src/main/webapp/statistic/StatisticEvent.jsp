<%-- 
    Document   : StatisticEvent
    Created on : Oct 24, 2025
    Author     : NGUYEN
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - StatisticEvent</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/statistic/statisticevent.css">
</head>

<body>
    <div class="container py-4">
        <!-- CurrentTime -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <i class="bi bi-calendar-event m-2"></i>
                <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss" />
            </div>

            <div class="d-flex align-items-center">
                <!-- Back -->
                <a href="statisticsevents" class="btn btn-outline-info btn-sm me-2 back-btn">
                    <i class="bi bi-arrow-left"></i>
                </a>

                <!-- Change + Dropdown -->
                <div class="position-relative">
                    <button id="toggleEventList" type="button" class="btn btn-outline-info btn-sm swap-btn">
                        <i class="bi bi-arrow-repeat me-1"></i> Đổi sự kiện
                    </button>

                    <!-- Dropdown -->
                    <div id="eventDropdown" class="event-dropdown shadow">
                        <ul class="list-unstyled m-0">
                            <c:forEach var="e" items="${otherEvents}">
                                <li>
                                    <a href="statisticsevent?eventId=${e.eventID}" class="dropdown-item text-light">
                                        ${e.eventName}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <hr>

        <h5 class="title mb-3">Tổng quan doanh thu</h5>

        <div class="row g-3">
            <!-- Total revenue -->
            <div class="col-md-6">
                <div class="card d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <div class="fw-bold fs-6">Tổng doanh thu</div>
                        <div class="fs-5 text-info">
                            <fmt:formatNumber value="${totalRevenue}" type="number" />đ
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

            <!-- Total tickets sold -->
            <div class="col-md-6">
                <div class="card d-flex flex-row align-items-center justify-content-between">
                    <div>
                        <div class="fw-bold fs-6">Tổng vé đã bán</div>
                        <div class="fs-5 text-info">
                            ${totalTicketsSold} / ${totalTicketsIssued} vé
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
        </div>

        <!-- Ticket details -->
        <div class="section-title mb-3">
            Thống kê của sự kiện:
            <span class="event-name">${eventName}</span>
        </div>

        <div class="table-responsive">
            <table class="table table-dark table-striped table-bordered align-middle text-center">
                <thead>
                    <tr>
                        <th>Loại vé</th>
                        <th>Tổng vé</th>
                        <th>Đã bán</th>
                        <th>Doanh thu</th>
                        <th>Tỉ lệ bán</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${ticketStats}">
                        <c:set var="percent" value="${(s.totalTickets > 0) ? (s.soldTickets * 100 / s.totalTickets) : 0}" />
                        <tr>
                            <td>${s.ticketTypeName}</td>
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
    <script src="${pageContext.request.contextPath}/js/statistic/statisticevent.js"></script>
</body>
</html>
