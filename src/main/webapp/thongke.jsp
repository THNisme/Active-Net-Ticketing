<%-- 
    Document   : thongke
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
        <title>Dashboard - Thống kê doanh thu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

        <style>
            body {
                background-color: #000;
                color: #fff;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .card {
                background-color: #1c1c1c;
                border: none;
                border-radius: 10px;
                color: #fff;
                padding: 20px;
                transition: transform 0.2s ease-in-out;
            }

            .card:hover {
                transform: translateY(-3px);
            }

            /* --- Vòng tròn phần trăm --- */
            .progress-ring {
                position: relative;
                width: 100px;
                height: 100px;
            }

            .progress-ring svg {
                transform: rotate(-90deg);
                width: 100%;
                height: 100%;
            }

            .progress-ring circle {
                fill: none;
                stroke-width: 10;
                stroke-linecap: round;
                transform-origin: 50% 50%;
            }

            .progress-ring circle.bg {
                stroke: #333;
            }

            .progress-ring circle.fg {
                stroke: #4caf50;
                stroke-dasharray: 283;
                stroke-dashoffset: 283;
                transition: stroke-dashoffset 0.35s ease;
            }

            .progress-ring .percent-text {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                font-size: 1rem;
                font-weight: bold;
                color: #fff;
            }

            .section-title {
                font-weight: bold;
                font-size: 1.1rem;
                margin-top: 25px;
                color: #ffb6b6;
            }

            .progress {
                background-color: #333;
                border-radius: 5px;
                overflow: hidden;
            }

            .progress-bar {
                transition: width 1s ease-out;
            }

            .percent {
                min-width: 45px;
                text-align: right;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <div class="container py-4">

            <!-- Ngày giờ thực -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <i class="bi bi-calendar-event"></i>
                    <span id="realTime"></span>
                </div>
            </div>
            <hr>

            <h5 class="mb-3">Tổng quan doanh thu</h5>

            <div class="row g-3">
                <!-- Tổng doanh thu -->
                <div class="col-md-6">
                    <div class="card d-flex flex-row align-items-center justify-content-between">
                        <div>
                            <div class="fw-bold fs-6">Tổng doanh thu</div>
                            <div class="fs-5 text-success">
                                <fmt:formatNumber value="${tongDoanhThu}" type="number" />đ
                            </div>
                        </div>

                        <div class="progress-ring" data-percent="${phanTramDoanhThu}">
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
                            <div class="fs-5 text-info">
                                ${tongVeDaBan} / ${tongVePhatHanh} vé
                            </div>
                        </div>

                        <div class="progress-ring" data-percent="${phanTramVeBan}">
                            <svg viewBox="0 0 100 100">
                            <circle class="bg" cx="50" cy="50" r="45"></circle>
                            <circle class="fg" cx="50" cy="50" r="45"></circle>
                            </svg>
                            <div class="percent-text">0%</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Chi tiết vé -->
            <div class="section-title mt-4 mb-2">Thống kê theo sự kiện</div>

            <div class="table-responsive">
                <table class="table table-dark table-bordered align-middle text-center">
                    <thead>
                        <tr>
                            <th>Tên sự kiện</th>
                            <th>Tổng vé</th>
                            <th>Đã bán</th>
                            <th>Doanh thu</th>
                            <th>Tỉ lệ bán</th>
                        </tr>
                    <tbody>
                        <c:forEach var="s" items="${ticketStats}">
                            <c:set var="percent" value="${(s.totalTickets > 0) ? (s.soldTickets * 100 / s.totalTickets) : 0}" />
                            <tr>
                                <td>${s.eventName}</td>
                                <td>${s.totalTickets}</td>
                                <td>${s.soldTickets}</td>
                                <td><fmt:formatNumber value="${s.totalRevenue}" type="number"/>đ</td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="progress flex-grow-1 me-2" style="height: 8px;">
                                            <div class="progress-bar bg-success" data-target="${percent}" style="width: 0%"></div>
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

        <script>
            document.addEventListener("DOMContentLoaded", function () { 
                // ===== Vòng tròn phần trăm =====
                document.querySelectorAll('.progress-ring').forEach(ring => {
                    const circle = ring.querySelector('.fg');
                    const text = ring.querySelector('.percent-text');
                    const radius = circle.r.baseVal.value;
                    const circumference = 2 * Math.PI * radius;
                    const targetPercent = parseFloat(ring.getAttribute('data-percent')) || 0;

                    circle.style.strokeDasharray = `${circumference}`;
                    circle.style.strokeDashoffset = circumference;

                    function setProgress(percent) {
                        const offset = circumference - (percent / 100) * circumference;
                        circle.style.strokeDashoffset = offset;
                        text.textContent = Math.round(percent) + '%';
                    }

                    let current = 0;
                    function animate() {
                        if (current <= targetPercent) {
                            setProgress(current);
                            current++;
                            requestAnimationFrame(animate);
                        } else {
                            setProgress(targetPercent);
                        }
                    }
                    animate();
                });

                // ===== Thanh tiến trình ngang (tỉ lệ vé) =====
                document.querySelectorAll('.progress-bar').forEach(bar => {
                    const target = parseFloat(bar.getAttribute('data-target')) || 0;
                    const percentText = bar.closest('td').querySelector('.percent');
                    let width = 0;

                    const animateBar = () => {
                        if (width <= target) {
                            bar.style.width = width + '%';
                            percentText.textContent = Math.floor(width) + '%';
                            width += 1;
                            requestAnimationFrame(animateBar);
                        } else {
                            bar.style.width = target + '%';
                            percentText.textContent = Math.floor(target) + '%';
                        }
                    };
                    animateBar();
                });

                // ===== Hiển thị ngày giờ thực =====
                function updateRealTime() {
                    const now = new Date();
                    const day = String(now.getDate()).padStart(2, '0');
                    const month = String(now.getMonth() + 1).padStart(2, '0');
                    const year = now.getFullYear();
                    const hours = String(now.getHours()).padStart(2, '0');
                    const minutes = String(now.getMinutes()).padStart(2, '0');
                    const seconds = String(now.getSeconds()).padStart(2, '0');

                    const formatted = `${day}/${month}/${year} - ${hours}:${minutes}:${seconds}`;
                                const el = document.getElementById("realTime");
                                if (el)
                                    el.textContent = formatted;
                                else
                                    console.warn("⚠️ Không tìm thấy phần tử #realTime");
                            }

                            updateRealTime();
                            setInterval(updateRealTime, 1000);
                        });
        </script>


        <!--<script>
            function updateRealTime() {
                const now = new Date();
                const formatted = now.toLocaleString('vi-VN');
                const el = document.getElementById("realTime");
                if (el) el.textContent = formatted;
                else console.log("Không tìm thấy phần tử #realTime");
            }
        
            document.addEventListener("DOMContentLoaded", function() {
                updateRealTime();
                setInterval(updateRealTime, 1000);
            });
        </script>-->
    </body>
</html>
