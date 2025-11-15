<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Dashboard - StatisticsEvent</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

        <!-- Inter font -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/statistic/statisticevent.css">
    </head>

    <body>

        <div class="container py-4">

            <!-- Time + Back + Event Switch -->
            <div class="d-flex justify-content-between align-items-center mb-3">

                <div>
                    <i class="bi bi-calendar-event m-2"></i>
                    <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss"/>
                </div>

                <div class="d-flex align-items-center">
                    <a href="statisticsevents" class="btn btn-outline-success btn-sm me-2">
                        <i class="bi bi-arrow-left"></i>
                    </a>

                    <div class="position-relative">
                        <button id="toggleEventList" class="btn btn-outline-success btn-sm">
                            <i class="bi bi-arrow-repeat"></i> Đổi sự kiện
                        </button>

                        <div id="eventDropdown" class="event-dropdown shadow">
                            <ul class="list-unstyled m-0">
                                <c:forEach var="e" items="${otherEvents}">
                                    <li><a href="statisticsevent?eventId=${e.eventID}">${e.eventName}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>

            <hr>

            <h3 class="mb-3 text-center">Tổng quan doanh thu</h3>

            <!-- Summary Cards -->
            <div class="row g-3">

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
                            <circle class="bg" cx="50" cy="50" r="45"/>
                            <circle class="fg" cx="50" cy="50" r="45"/>
                            </svg>
                            <div class="percent-text">0%</div>
                        </div>
                    </div>
                </div>

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
                            <circle class="bg" cx="50" cy="50" r="45"/>
                            <circle class="fg" cx="50" cy="50" r="45"/>
                            </svg>
                            <div class="percent-text">0%</div>
                        </div>
                    </div>
                </div>

            </div>

            <p class="section-title mt-4">Thống kê của sự kiện: <span class="event-name">${eventName}</span></p>

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
                            <c:set var="percent" value="${(s.totalTickets > 0) ? (s.soldTickets * 100 / s.totalTickets) : 0}"/>

                            <tr>
                                <td>${s.ticketTypeName}</td>
                                <td>${s.totalTickets}</td>
                                <td>${s.soldTickets}</td>
                                <td><fmt:formatNumber value="${s.totalRevenue}" type="number"/>đ</td>

                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="progress flex-grow-1 me-2" style="height: 8px;">
                                            <div class="progress-bar" data-target="${percent}" style="width: 0;"></div>
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
