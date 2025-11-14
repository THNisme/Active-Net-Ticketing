 let currentDate = new Date();
        let currentMonthOffset = 0;
        let selectedStart = null;
        let selectedEnd = null;
        let currentFilter = 'all';

        const weekdaysVi = ['T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'CN'];
        const monthsVi = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
                          'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];

        function openCalendar() {
            document.getElementById('calendarOverlay').classList.add('active');
            renderCalendars();
        }

        function closeCalendar() {
            document.getElementById('calendarOverlay').classList.remove('active');
        }

        function closeOnOverlay(event) {
            if (event.target === document.getElementById('calendarOverlay')) {
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

            switch(filter) {
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
                    // Calculate days until Friday (5)
                    const daysToFriday = (5 - dayOfWeek + 7) % 7;
                    selectedStart.setDate(today.getDate() + daysToFriday);
                    selectedEnd = new Date(selectedStart);
                    // Set end to Sunday (2 days after Friday)
                    selectedEnd.setDate(selectedEnd.getDate() + 2);
                    break;
                case 'thisMonth':
                    selectedStart = new Date(today);
                    selectedEnd = new Date(today.getFullYear(), today.getMonth() + 1, 0);
                    break;
                case 'all':
                    selectedStart = null;
                    selectedEnd = null;
                    break;
            }
            renderCalendars();
        }

        function renderCalendars() {
            const container = document.getElementById('monthsContainer');
            container.innerHTML = '';

            const firstMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + currentMonthOffset, 1);
            const secondMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + currentMonthOffset + 1, 1);

            container.appendChild(createMonthCalendar(firstMonth));
            container.appendChild(createMonthCalendar(secondMonth));

            document.getElementById('monthsDisplay').textContent = 
                `${monthsVi[firstMonth.getMonth()]}, ${firstMonth.getFullYear()}    |    ${monthsVi[secondMonth.getMonth()]}, ${secondMonth.getFullYear()}`;
        }

        function createMonthCalendar(date) {
            const monthDiv = document.createElement('div');
            monthDiv.className = 'month-calendar';

            const title = document.createElement('div');
            title.className = 'month-title';
            title.textContent = `${monthsVi[date.getMonth()]}, ${date.getFullYear()}`;
            monthDiv.appendChild(title);

            const weekdays = document.createElement('div');
            weekdays.className = 'weekdays';
            weekdaysVi.forEach(day => {
                const dayDiv = document.createElement('div');
                dayDiv.className = 'weekday';
                dayDiv.textContent = day;
                weekdays.appendChild(dayDiv);
            });
            monthDiv.appendChild(weekdays);

            const days = document.createElement('div');
            days.className = 'days';

            const firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
            const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
            
            let startDay = firstDay.getDay();
            startDay = startDay === 0 ? 6 : startDay - 1;

            const prevMonthDays = new Date(date.getFullYear(), date.getMonth(), 0).getDate();
            for (let i = startDay - 1; i >= 0; i--) {
                const dayDiv = createDayElement(prevMonthDays - i, date, true);
                days.appendChild(dayDiv);
            }

            for (let i = 1; i <= lastDay.getDate(); i++) {
                const dayDiv = createDayElement(i, date, false);
                days.appendChild(dayDiv);
            }

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
            // When manually selecting dates, reset filter to 'all'
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
            let buttonText = '📅 Tất cả các ngày';
            
            // If a preset filter is active, show the filter name
            if (currentFilter === 'today') {
                buttonText = '📅 Hôm nay';
            } else if (currentFilter === 'tomorrow') {
                buttonText = '📅 Ngày mai';
            } else if (currentFilter === 'thisWeek') {
                buttonText = '📅 Cuối tuần này';
            } else if (currentFilter === 'thisMonth') {
                buttonText = '📅 Tháng này';
            } else if (currentFilter === 'all' && !selectedStart && !selectedEnd) {
                buttonText = '📅 Tất cả các ngày';
            } else if (selectedStart && selectedEnd) {
                // Custom date range selected
                const start = formatDateForButton(selectedStart);
                const end = formatDateForButton(selectedEnd);
                
                // Check if it's a single day
                if (selectedStart.getTime() === selectedEnd.getTime()) {
                    buttonText = `📅 ${start}`;
                } else {
                    buttonText = `📅 ${start} - ${end}`;
                }
            } else if (selectedStart) {
                buttonText = `📅 ${formatDateForButton(selectedStart)}`;
            }
            
            document.getElementById('calendarTrigger').textContent = buttonText;
            closeCalendar();
        }

        function formatDate(date) {
            return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
        }

        function formatDateForButton(date) {
            const months = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
                          'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];
            return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
        }

        // Initialize on load
        renderCalendars();