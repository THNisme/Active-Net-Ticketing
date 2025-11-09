<%-- 
    Document   : home
    Created on : Oct 4, 2025, 5:25:12 PM
    Author     : NguyenDuc
--%>
<%@page import="Models.nvd2306.Event"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Trang chủ</title>
        <link href="css/customer-page/home.css" rel="stylesheet" type="text/css"/>     
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            #monthsDisplay {
                display: flex;
                justify-content: space-between; /* Điều chỉnh khoảng cách giữa các tháng */
                gap: 40px;
                font-weight: 600;
                font-size: 16px;
                align-items: center;
                text-align: center;
                width: 100%;
                /* Căn giữa nội dung tháng */
            }
            /* Calendar Button Styles */
            .calendar-trigger {
                background: #404040;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 8px;
                font-size: 15px;
                cursor: pointer;
                display: inline-flex;
                align-items: center;
                gap: 8px;
                transition: all 0.2s;
            }

            .calendar-trigger:hover {
                background: #ffb6b6;
            }

            .calendar-trigger i {
                font-size: 18px;
            }

            /* Calendar Overlay */
            .calendar-overlay {
                position: relative; /* Đặt tương đối để popup bám theo */
                display: inline-block;
                z-index: 9999;
            }

            .calendar-overlay.active {
                display: flex;
            }

            .calendar-popup {
                position: absolute;
                top: calc(100% + 8px); /* nằm ngay dưới nút */
                right: 0; /* canh phải nút */
                background: white;
                border-radius: 12px;
                width: 600px; /* Tăng chiều rộng */
                max-height: 85vh;
                overflow: auto;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.25);
                color: #333;
                display: none;
                animation: dropdownFade 0.15s ease-out;
            }
            .calendar-overlay.active .calendar-popup {
                display: block;
            }

            /* Hiệu ứng trượt mượt */
            @keyframes dropdownFade {
                from {
                    opacity: 0;
                    transform: translateY(-5px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            .calendar-header {
                background: #ffb6b6;
                color: white;
                padding: 16px 24px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-radius: 12px 12px 0 0;
            }

            .calendar-header h2 {
                font-size: 18px;
                font-weight: 500;
                margin: 0;
            }

            .close-btn {
                background: none;
                border: none;
                color: white;
                font-size: 28px;
                cursor: pointer;
                padding: 0;
                width: 30px;
                height: 30px;
                display: flex;
                align-items: center;
                justify-content: center;
                line-height: 1;
            }

            .filter-buttons {
                display: flex;
                padding: 16px 24px;
                gap: 8px;
                border-bottom: 1px solid #e0e0e0;
                flex-wrap: wrap;
            }

            .filter-btn {
                background: white;
                border: none;
                padding: 10px 20px;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                color: #666;
                transition: all 0.2s;
            }

            .filter-btn:hover {
                background: #f0f0f0;
            }

            .filter-btn.active {
                color: black;
                background: #ffb6b6;
                font-weight: 500;
            }

            .calendar-navigation {
                display: flex;
                justify-content: center; /* Căn giữa các phần tử */
                align-items: center; /* Căn giữa theo chiều dọc */
                padding: 20px 24px;
                width: 100%;
            }

            .nav-btn {
                background: none;
                border: none;
                font-size: 24px;
                cursor: pointer;
                color: #52c178;
                padding: 8px;
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                border-radius: 50%;
            }

            .nav-btn:hover {
                background: #f0f0f0;
            }

            .months-container {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 24px;
                padding: 0 24px 24px;
            }

            .month-calendar {
                min-width: 0;
            }

            .month-title {
                text-align: center;
                font-size: 16px;
                font-weight: 600;
                margin-bottom: 16px;
                color: #333;
            }

            .weekdays {
                display: grid;
                grid-template-columns: repeat(7, 1fr);
                gap: 4px;
                margin-bottom: 8px;
            }

            .weekday {
                text-align: center;
                font-size: 13px;
                font-weight: 600;
                color: #666;
                padding: 8px 4px;
            }

            .days {
                display: grid;
                grid-template-columns: repeat(7, 1fr);
                gap: 4px;
            }

            .day {
                aspect-ratio: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                border-radius: 6px;
                font-size: 14px;
                transition: all 0.2s;
                color: #333;
            }

            .day:not(.other-month):hover {
                background: #f0f0f0;
            }

            .day.other-month {
                color: #ccc;
                cursor: default;
            }

            .day.today {
                background: #d4e5ff;
                font-weight: 600;
            }

            .day.selected {
                background: #ffb6b6;
                color: white;
                font-weight: 600;
            }

            .day.in-range {
                background: #f0f0f0;
            }

            .calendar-actions {
                display: flex;
                gap: 12px;
                padding: 16px 24px;
                border-top: 1px solid #e0e0e0;
            }

            .action-btn {
                flex: 1;
                padding: 12px;
                border-radius: 8px;
                font-size: 15px;
                cursor: pointer;
                border: none;
                transition: all 0.2s;
            }

            .reset-btn {
                background: white;
                border: 1px solid #ddd;
                color: #666;
            }

            .reset-btn:hover {
                background: #f5f5f5;
            }

            .apply-btn {
                background: #ffb6b6;
                color: white;
                font-weight: 500;
            }

            .apply-btn:hover {
                background: #ff9999;
            }

            @media (max-width: 768px) {
                .months-container {
                    grid-template-columns: 1fr;
                }
            }
            .calendar-container {
                position: relative;
                display: inline-block;
            }

            #calendarPopupContainer {
                position: absolute;
                top: 110%;       /* Hạ xuống một chút để cách nút */
                right: -20px;    /* Đẩy sang trái nhẹ để không dính mép màn hình */
                z-index: 9999;
            }


            .calendar-navigation .nav-btn {
                background: none;
                border: none;
                font-size: 22px;
                color: #333;
                cursor: pointer;
                transition: 0.2s;
            }

            .calendar-navigation .nav-btn:hover {
                color: #000;
            }
        </style>
    </head>
    <body style="background-color:#0d0d0d; color:#fff;">
        <%@include file="view-hfs/header.jsp" %>

        <section class="events-section py-5">
            <div class="container">
                <%
                    List<Event> events = (List<Event>) request.getAttribute("events");
                    Boolean isSearch = (Boolean) request.getAttribute("isSearch");
                %>

                <!-- Search Header with Calendar Button -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <% if (isSearch != null && isSearch) { %>
                    <h2 class="mb-0" style="color: #ffb6b6">Kết quả tìm kiếm:</h2>
                    <% } else { %>
                    <h2 class="mb-0" style="color: #ffb6b6">Sự kiện nổi bật</h2>
                    <% } %>

                    <!-- Calendar Trigger Button -->
                    <div class="calendar-container">
                        <button class="calendar-trigger" id="calendarTrigger" onclick="openCalendar()">
                            <i class="far fa-calendar"></i>
                            <span>Tất cả các ngày</span>
                        </button>

                        <!-- Calendar Popup (di chuyển xuống ngay đây) -->
                        <div class="calendar-overlay" id="calendarPopupContainer" onclick="closeOnOverlay(event)">
                            <div class="calendar-popup">
                                <div class="calendar-header">
                                    <h2>📅 Tất cả các ngày</h2>
                                    <button class="close-btn" onclick="closeCalendar()">×</button>
                                </div>

                                <div class="filter-buttons">
                                    <button class="filter-btn active" onclick="setFilter('all')">Tất cả các ngày</button>
                                    <button class="filter-btn" onclick="setFilter('today')">Hôm nay</button>
                                    <button class="filter-btn" onclick="setFilter('tomorrow')">Ngày mai</button>
                                    <button class="filter-btn" onclick="setFilter('thisWeek')">Cuối tuần này</button>
                                    <button class="filter-btn" onclick="setFilter('thisMonth')">Tháng này</button>
                                </div>

                                <div class="calendar-navigation">
                                    <button class="nav-btn" onclick="previousMonths()">‹</button>
                                     <div class="months-container" id="monthsContainer"></div>
                                    <button class="nav-btn" onclick="nextMonths()">›</button>
                                </div>

                             

                                <div class="calendar-actions">
                                    <button class="action-btn reset-btn" onclick="resetSelection()">Thiết lập lại</button>
                                    <button class="action-btn apply-btn" onclick="applySelection()">Áp dụng</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Events Grid (đưa ra ngoài calendar-container) -->
                <div class="row g-4">
                    <% if (events == null || events.isEmpty()) { %>
                    <div class="col-12">
                        <p class="text-center " >Không có sự kiện nào được tìm thấy.</p>
                    </div>
                    <% } else {
                        for (Event e : events) {%>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="h-100 border-0 event-card">
                            <img src="<%= e.getImageURL() != null ? e.getImageURL() : "https://via.placeholder.com/400x250?text=No+Image"%>"
                                 alt="<%= e.getEventName()%>"
                                 loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title"><%= e.getEventName()%></h5>
                                <p class="price mb-2" style="color:#ffb6b6">Từ 
                                    <%= (e.getStatusID() == 1) ? "Miễn phí" : "Liên hệ"%>
                                </p>
                                <p class="date mb-0">
                                    <i class="bi bi-calendar3"></i>
                                    <%= e.getStartDate() != null ? e.getStartDate().toLocalDateTime().toLocalDate() : "Chưa có ngày"%>
                                </p>
                            </div>
                        </div>
                    </div>
                    <% }
                        }%>
                </div>
            </div>
        </section>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                        console.log('🚀 Script loaded');

                                        let currentDate = new Date();
                                        let currentMonthOffset = 0;
                                        let selectedStart = null;
                                        let selectedEnd = null;
                                        let currentFilter = 'all';

                                        const weekdaysVi = ['T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'CN'];
                                        const monthsVi = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
                                            'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];

                                        function openCalendar() {
                                            console.log('📅 Opening calendar');
                                            const overlay = document.getElementById("calendarPopupContainer");
                                            if (!overlay) {
                                                console.error('❌ Không tìm thấy calendarPopupContainer');
                                                return;
                                            }
                                            overlay.classList.add("active");
                                            renderCalendars();
                                        }

                                        function closeCalendar() {
                                            console.log('❌ Closing calendar');
                                            const overlay = document.getElementById("calendarPopupContainer");
                                            if (overlay) {
                                                overlay.classList.remove("active");
                                            }
                                        }

                                        function closeOnOverlay(event) {
                                            if (event.target.id === "calendarPopupContainer") {
                                                closeCalendar();
                                            }
                                        }

                                        function setFilter(filter) {
                                            currentFilter = filter;
                                            const buttons = document.querySelectorAll('.filter-btn');
                                            buttons.forEach(btn => btn.classList.remove('active'));
                                            event.target.classList.add('active');

                                            const today = new Date();
                                            today.setHours(0, 0, 0, 0);

                                            switch (filter) {
                                                case 'today':
                                                    selectedStart = new Date(today);
                                                    selectedEnd = new Date(today);
                                                    break;
                                                case 'tomorrow':
                                                    selectedStart = new Date(today);
                                                    selectedStart.setDate(selectedStart.getDate() + 1);
                                                    selectedEnd = new Date(selectedStart);
                                                    break;
                                                case 'thisWeek':
                                                    selectedStart = new Date(today);
                                                    const dayOfWeek = today.getDay();
                                                    const daysToFriday = (5 - dayOfWeek + 7) % 7;
                                                    selectedStart.setDate(today.getDate() + daysToFriday);
                                                    selectedEnd = new Date(selectedStart);
                                                    selectedEnd.setDate(selectedEnd.getDate() + 2);
                                                    break;
                                                case 'thisMonth':
                                                    selectedStart = new Date(today.getFullYear(), today.getMonth(), today.getDate());
                                                    selectedEnd = new Date(today.getFullYear(), today.getMonth() + 1, 0);
                                                    selectedStart.setHours(0, 0, 0, 0);
                                                    selectedEnd.setHours(0, 0, 0, 0);
                                                    break;
                                                case 'all':
                                                    selectedStart = null;
                                                    selectedEnd = null;
                                                    break;
                                            }
                                            renderCalendars();
                                        }

                                        function renderCalendars() {
                                            console.log('🔄 Rendering calendars...');

                                            const container = document.getElementById('monthsContainer');
                                            const monthsDisplay = document.getElementById('monthsDisplay');

                                            if (!container) {
                                                console.error('❌ monthsContainer không tồn tại!');
                                                return;
                                            }

                                            // Xóa nội dung cũ
                                            container.innerHTML = '';

                                            // Tạo hai tháng liên tiếp
                                            const firstMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + currentMonthOffset, 1);
                                            const secondMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + currentMonthOffset + 1, 1);

                                            console.log(`📆 Rendering: ${monthsVi[firstMonth.getMonth()]} ${firstMonth.getFullYear()} và ${monthsVi[secondMonth.getMonth()]} ${secondMonth.getFullYear()}`);

                                            // Kiểm tra giá trị firstMonth và secondMonth
                                            console.log(`firstMonth: ${firstMonth}, secondMonth: ${secondMonth}`);

                                            // Kiểm tra giá trị của monthsVi
                                            console.log(`monthsVi[firstMonth.getMonth()]: ${monthsVi[firstMonth.getMonth()]}`);
                                            console.log(`monthsVi[secondMonth.getMonth()]: ${monthsVi[secondMonth.getMonth()]}`);

                                            // Tạo lịch cho hai tháng
                                            container.appendChild(createMonthCalendar(firstMonth));
                                            container.appendChild(createMonthCalendar(secondMonth));

                                            // Hiển thị tháng và năm ở navigation
                                            if (monthsDisplay) {
                                                monthsDisplay.innerHTML = `
            <div>${monthsVi[firstMonth.getMonth()]} ${firstMonth.getFullYear()}</div>
            <div>${monthsVi[secondMonth.getMonth()]} ${secondMonth.getFullYear()}</div>
        `;
                                                console.log('✅ Month display updated');
                                            } else {
                                                console.warn('⚠️ monthsDisplay không tồn tại');
                                            }
                                        }

                                        function createMonthCalendar(date) {
                                            const monthDiv = document.createElement('div');
                                            monthDiv.className = 'month-calendar';

                                            // Tiêu đề tháng
                                            const title = document.createElement('div');
                                            title.className = 'month-title';
                                            title.textContent = monthsVi[date.getMonth()] + ' ' + date.getFullYear();
                                            monthDiv.appendChild(title);

                                            // Các ngày trong tuần
                                            const weekdays = document.createElement('div');
                                            weekdays.className = 'weekdays';
                                            weekdaysVi.forEach(day => {
                                                const dayDiv = document.createElement('div');
                                                dayDiv.className = 'weekday';
                                                dayDiv.textContent = day;
                                                weekdays.appendChild(dayDiv);
                                            });
                                            monthDiv.appendChild(weekdays);

                                            // Grid các ngày
                                            const days = document.createElement('div');
                                            days.className = 'days';

                                            const firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
                                            const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

                                            let startDay = firstDay.getDay();
                                            startDay = startDay === 0 ? 6 : startDay - 1;

                                            // Các ngày tháng trước
                                            const prevMonthDays = new Date(date.getFullYear(), date.getMonth(), 0).getDate();
                                            for (let i = startDay - 1; i >= 0; i--) {
                                                const dayDiv = createDayElement(prevMonthDays - i, date, true);
                                                days.appendChild(dayDiv);
                                            }

                                            // Các ngày tháng hiện tại
                                            for (let i = 1; i <= lastDay.getDate(); i++) {
                                                const dayDiv = createDayElement(i, date, false);
                                                days.appendChild(dayDiv);
                                            }

                                            // Các ngày tháng sau
                                            const remainingDays = 42 - days.children.length;
                                            for (let i = 1; i <= remainingDays; i++) {
                                                const dayDiv = createDayElement(i, new Date(date.getFullYear(), date.getMonth() + 1, 1), true);
                                                days.appendChild(dayDiv);
                                            }

                                            monthDiv.appendChild(days);
                                            return monthDiv;
                                        }

                                        function createDayElement(day, month, isOtherMonth) {
                                            const dayDiv = document.createElement('div');
                                            dayDiv.className = 'day';
                                            dayDiv.textContent = day;

                                            const dayDate = new Date(month.getFullYear(), month.getMonth() + (isOtherMonth ? (day > 15 ? -1 : 1) : 0), day);
                                            dayDate.setHours(0, 0, 0, 0);

                                            if (isOtherMonth) {
                                                dayDiv.classList.add('other-month');
                                            } else {
                                                const today = new Date();
                                                today.setHours(0, 0, 0, 0);

                                                if (dayDate.getTime() === today.getTime()) {
                                                    dayDiv.classList.add('today');
                                                }

                                                if (selectedStart && selectedEnd) {
                                                    if (dayDate.getTime() === selectedStart.getTime() || dayDate.getTime() === selectedEnd.getTime()) {
                                                        dayDiv.classList.add('selected');
                                                    } else if (dayDate > selectedStart && dayDate < selectedEnd) {
                                                        dayDiv.classList.add('in-range');
                                                    }
                                                }

                                                dayDiv.onclick = () => selectDate(dayDate);
                                            }

                                            return dayDiv;
                                        }

                                        function selectDate(date) {
                                            if (!selectedStart || (selectedStart && selectedEnd)) {
                                                selectedStart = date;
                                                selectedEnd = null;
                                            } else {
                                                if (date < selectedStart) {
                                                    selectedEnd = selectedStart;
                                                    selectedStart = date;
                                                } else {
                                                    selectedEnd = date;
                                                }
                                            }
                                            currentFilter = 'all';
                                            const buttons = document.querySelectorAll('.filter-btn');
                                            buttons.forEach(btn => btn.classList.remove('active'));
                                            buttons[0].classList.add('active');
                                            renderCalendars();
                                        }

                                        function previousMonths() {
                                            currentMonthOffset -= 2;
                                            renderCalendars();
                                        }

                                        function nextMonths() {
                                            currentMonthOffset += 2;
                                            renderCalendars();
                                        }

                                        function resetSelection() {
                                            selectedStart = null;
                                            selectedEnd = null;
                                            currentFilter = 'all';
                                            const buttons = document.querySelectorAll('.filter-btn');
                                            buttons.forEach(btn => btn.classList.remove('active'));
                                            buttons[0].classList.add('active');
                                            renderCalendars();
                                        }
                                        function applySelection() {
                                            console.log("✅ Apply selection clicked");

                                            let buttonText = 'Tất cả các ngày';

                                            // === Xác định start & end cho các filter nhanh ===
                                            if (currentFilter === 'today') {
                                                const today = new Date();
                                                today.setHours(0, 0, 0, 0);
                                                selectedStart = today;
                                                selectedEnd = today;
                                            } else if (currentFilter === 'tomorrow') {
                                                const tomorrow = new Date();
                                                tomorrow.setDate(tomorrow.getDate() + 1);
                                                tomorrow.setHours(0, 0, 0, 0);
                                                selectedStart = tomorrow;
                                                selectedEnd = tomorrow;
                                            } else if (currentFilter === 'thisWeek') {
                                                const today = new Date();
                                                const dayOfWeek = today.getDay();
                                                const daysToFriday = (5 - dayOfWeek + 7) % 7;
                                                const start = new Date(today);
                                                start.setDate(today.getDate() + daysToFriday);
                                                const end = new Date(start);
                                                end.setDate(start.getDate() + 2);
                                                selectedStart = start;
                                                selectedEnd = end;
                                            } else if (currentFilter === 'thisMonth') {
                                                const today = new Date();
                                                const start = new Date(today.getFullYear(), today.getMonth(), today.getDate()); // từ hôm nay
                                                const end = new Date(today.getFullYear(), today.getMonth() + 1, 0);             // đến cuối tháng
                                                start.setHours(0, 0, 0, 0);
                                                end.setHours(0, 0, 0, 0);
                                                selectedStart = start;
                                                selectedEnd = end;
                                            }

                                            // === Tạo text hiển thị trên nút ===
                                            if (selectedStart && selectedEnd) {
                                                const start = formatDateForButton(selectedStart);
                                                const end = formatDateForButton(selectedEnd);
                                                if (selectedStart.getTime() === selectedEnd.getTime()) {
                                                    buttonText = start;
                                                } else {
                                                    buttonText = start + ' - ' + end;
                                                }
                                            } else if (selectedStart) {
                                                buttonText = formatDateForButton(selectedStart);
                                            } else {
                                                buttonText = 'Tất cả các ngày';
                                            }

                                            // === Cập nhật text nút "Tất cả các ngày" ngay lập tức ===
                                            const buttonSpan = document.querySelector('#calendarTrigger span');
                                            if (buttonSpan) {
                                                buttonSpan.textContent = buttonText;
                                                console.log("🟢 Button updated to:", buttonText);
                                            } else {
                                                console.warn("⚠️ Không tìm thấy #calendarTrigger span");
                                            }

                                            // === Đóng popup lịch ===
                                            closeCalendar();
                                            sessionStorage.setItem('selectedDateText', buttonText);
                                            // ✅ Đợi 100ms cho trình duyệt kịp render text, rồi mới gọi servlet
                                            setTimeout(() => {
                                                filterEventsByDate(selectedStart, selectedEnd);
                                            }, 100);
                                        }
                                        function formatDateForButton(date) {
                                            const day = date.getDate();
                                            const month = monthsVi[date.getMonth()];
                                            const year = date.getFullYear();
                                            return day + ' ' + month + ' ' + year;
                                        }

                                        // Hàm filter sự kiện qua servlet
                                        function filterEventsByDate(startDate, endDate) {
                                            const params = new URLSearchParams();

                                            if (startDate) {
                                                params.append('startDate', formatDateForServlet(startDate));
                                            }
                                            if (endDate) {
                                                params.append('endDate', formatDateForServlet(endDate));
                                            }

                                            console.log('🔍 Filtering events:', params.toString());

                                            // Redirect sang servlet với params
                                            window.location.href = 'filter-events?' + params.toString();
                                        }

                                        function formatDateForServlet(date) {
                                            const year = date.getFullYear();
                                            const month = String(date.getMonth() + 1).padStart(2, '0');
                                            const day = String(date.getDate()).padStart(2, '0');
                                            return year + '-' + month + '-' + day;
                                        }
                                        document.addEventListener('DOMContentLoaded', () => {
                                            console.log("✅ DOM loaded - Checking URL params or sessionStorage");

                                            const buttonSpan = document.querySelector('#calendarTrigger span');
                                            if (!buttonSpan) {
                                                console.warn("⚠️ Không tìm thấy #calendarTrigger span");
                                                return;
                                            }

                                            // 🟢 Nếu có text lưu tạm từ trước thì dùng lại luôn
                                            const savedText = sessionStorage.getItem('selectedDateText');
                                            if (savedText) {
                                                buttonSpan.textContent = savedText;
                                                sessionStorage.removeItem('selectedDateText');
                                                console.log("🟢 Hiển thị lại text từ sessionStorage:", savedText);
                                                return;
                                            }

                                            // 🟢 Nếu không có text lưu, kiểm tra query string
                                            const params = new URLSearchParams(window.location.search);
                                            const start = params.get('startDate');
                                            const end = params.get('endDate');

                                            // ✅ Hàm parse YYYY-MM-DD an toàn
                                            function parseYMD(dateStr) {
                                                if (!dateStr)
                                                    return null;
                                                const parts = dateStr.split('-');
                                                return new Date(Number(parts[0]), Number(parts[1]) - 1, Number(parts[2]));
                                            }

                                            if (!start && !end) {
                                                buttonSpan.textContent = 'Tất cả các ngày';
                                            } else if (start && end && start === end) {
                                                const d = parseYMD(start);
                                                buttonSpan.textContent = formatDateForButton(d);
                                            } else if (start && end) {
                                                const startText = formatDateForButton(parseYMD(start));
                                                const endText = formatDateForButton(parseYMD(end));
                                                buttonSpan.textContent = `${startText} - ${endText}`;
                                                        } else if (start) {
                                                            const d = parseYMD(start);
                                                            buttonSpan.textContent = formatDateForButton(d);
                                                        }
                                                    });

        </script>
    </body>
</html>