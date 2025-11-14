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

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/statistic/statisticevent.css">
    </head>

    <body>

        <style>
            body {
                background: #111827;
                color: white;
                font-family: 'Inter', sans-serif;
                padding: 20px;
            }

            /* ===========================
               DROPDOWN EVENT CUSTOM CSS
               =========================== */
            .event-dropdown {
                position: absolute;
                top: 110%;
                right: 0;
                background-color: #1f2937; /* nền xám đậm */
                padding: 10px;
                border-radius: 8px;
                width: 240px;
                display: none;
                z-index: 1000;
            }

            .event-dropdown ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }

            .event-item {
                display: block;
                background-color: #374151; /* nền xám */
                color: #ffffff; /* chữ trắng */
                padding: 10px 14px;
                border-radius: 6px;
                text-decoration: none;
                margin-bottom: 6px;
                transition: color 0.2s ease;
            }

            /* Hover chỉ đổi màu chữ */
            .event-item:hover {
                color: #22c55e !important; /* xanh lá */
                background-color: #374151 !important; /* giữ nguyên nền */
            }
        </style>

        <div class="container py-4">
            <!-- CurrentTime -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <i class="bi bi-calendar-event m-2"></i>
                    <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss" />
                </div>

                <div class="d-flex align-items-center">
                    <!-- Back -->
                    <a href="statisticsevents" class="btn btn-outline-info btn-sm me-2 back-btn btn-outline-custom">
                        <i class="bi bi-arrow-left"></i>
                    </a>

                    <!-- Change + Dropdown -->
                    <div class="position-relative">
                        <button id="toggleEventList" type="button" class="btn btn-outline-info btn-sm swap-btn btn-outline-custom">
                            <i class="bi bi-arrow-repeat me-1"></i> Đổi sự kiện
                        </button>

                        <!-- Dropdown -->

                        <!-- Dropdown -->
                        <div id="eventDropdown" class="shadow"
                             style="
                             position:absolute;
                             top:110%;
                             right:0;
                             background-color:#1f2937;
                             padding:10px;
                             border-radius:8px;
                             width:240px;
                             display:none;
                             z-index:1000;
                             ">
                            <ul class="m-0"
                                style="
                                list-style:none;
                                padding:0;
                                margin:0;
                                ">
                                <c:forEach var="e" items="${otherEvents}">
                                    <li style="margin-bottom:6px;">
                                        <a href="statisticsevent?eventId=${e.eventID}"
                                           style="
                                           display:block;
                                           text-decoration:none;
                                           background-color:#374151;
                                           color:#ffffff;
                                           padding:10px 14px;
                                           border-radius:6px;
                                           transition:background-color .2s ease,color .2s ease;
                                           "
                                           onmouseover="this.style.backgroundColor = '#22c55e'; this.style.color = '#ffffff';"
                                           onmouseout="this.style.backgroundColor = '#374151'; this.style.color = '#ffffff';"
                                           >
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

            <h3 class="title mb-3 text-center">Tổng quan doanh thu</h3>

            <div class="row g-3">
                <!-- Total revenue -->
                <div class="col-md-6">
                    <div class="card d-flex flex-row align-items-center justify-content-between">
                        <div>
                            <div class="fw-bold fs-6">Tổng doanh thu</div>
                            <div class="fs-5 green-text">
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
                            <div class="fs-5 green-text">
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
<script>
const btn = document.getElementById("toggleEventList");
const dropdown = document.getElementById("eventDropdown");

btn.addEventListener("click", function (e) {
    e.stopPropagation();
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
});

// Click ra ngoài đóng
document.addEventListener("click", function (e) {
    if (!dropdown.contains(e.target) && e.target !== btn) {
        dropdown.style.display = "none";
    }
});
</script>

