/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//console.log(events);

const eventsPerPage = 3;
let currentPage = 1;
let filteredEvents = [...events]; // mảng đang hiển thị (có thể đã được lọc)
let totalPages = Math.ceil(filteredEvents.length / eventsPerPage);

const fnDelete = (eid, eventName) => {
    document.getElementById("eventName").textContent = eventName;
    document.getElementById("eventID").value = eid;
};

// Cập nhật totalPages mỗi lần dữ liệu lọc thay đổi
function updatePaginationInfo() {
    totalPages = Math.ceil(filteredEvents.length / eventsPerPage);
}

// Render events dựa trên filteredEvents
function renderEvents() {
    const eventList = document.getElementById("event-list");
    eventList.innerHTML = "";
    const start = (currentPage - 1) * eventsPerPage;
    const end = start + eventsPerPage;
    const currentEvents = filteredEvents.slice(start, end);

    if (currentEvents.length === 0) {
        eventList.innerHTML = `<p class="text-center mt-4">Không tìm thấy sự kiện nào.</p>`;
        return;
    }

    currentEvents.forEach(evt => {
        const eventDiv = document.createElement("div");
        eventDiv.className = "event";
        eventDiv.innerHTML = `
            <div class="event-main">
                <img src="${evt.image}" alt="Sự kiện">
                <div class="event-content">
                    <h2>${evt.title}</h2>
                    <p class="time">${evt.time}</p>
                    <p class="location">${evt.location}</p>
                    <p class="address">${evt.address}</p>
                </div>
            </div>
            <div class="actions">
                <a href="statisticsevent?eventId=${evt.id}"><i class="fas fa-chart-pie"></i><span>Doanh thu</span></a>
                <a href="order_management?eventID=${evt.id}"><i class="fas fa-receipt"></i><span>Đơn hàng</span></a>
                <a href="event-form?action=update&eid=${evt.id}"><i class="fas fa-pen"></i><span>Chỉnh sửa</span></a>
                <a href="ticket_management?eventID=${evt.id}"><i class="bi bi-ticket-perforated"></i><span>Quản lí vé</span></a>
                <a class="btn-delete" data-id="${evt.id}" data-title="${evt.title}" data-bs-toggle="modal" data-bs-target="#modalDelete">
                    <i class="fas fa-trash"></i><span>Xóa</span>
                </a>
            </div>
        `;
        eventList.appendChild(eventDiv);
    });

    // Gắn sự kiện xóa
    document.querySelectorAll(".btn-delete").forEach(btn => {
        btn.addEventListener("click", () => {
            fnDelete(btn.dataset.id, btn.dataset.title);
        });
    });
}

// Render phân trang dựa trên filteredEvents
function renderPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";
    updatePaginationInfo();

    if (totalPages === 0)
        return;

    const prevBtn = document.createElement("button");
    prevBtn.textContent = "<";
    prevBtn.disabled = currentPage === 1;
    prevBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            renderEvents();
            renderPagination();
        }
    });
    pagination.appendChild(prevBtn);

    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i;
        if (i === currentPage)
            btn.classList.add("active");
        btn.addEventListener("click", () => {
            currentPage = i;
            renderEvents();
            renderPagination();
        });
        pagination.appendChild(btn);
    }

    const nextBtn = document.createElement("button");
    nextBtn.textContent = ">";
    nextBtn.disabled = currentPage === totalPages;
    nextBtn.addEventListener("click", () => {
        if (currentPage < totalPages) {
            currentPage++;
            renderEvents();
            renderPagination();
        }
    });
    pagination.appendChild(nextBtn);
}

// Xử lý tìm kiếm
const searchInput = document.querySelector(".search-box input");
const searchBtn = document.querySelector(".search-box button");

function performSearch() {
    const keyword = searchInput.value.trim().toLowerCase();
    if (keyword === "") {
        filteredEvents = [...events]; // reset về toàn bộ
    } else {
        filteredEvents = events.filter(evt => evt.title.toLowerCase().includes(keyword));
    }
    currentPage = 1;
    renderEvents();
    renderPagination();
}

searchBtn.addEventListener("click", performSearch);
searchInput.addEventListener("keyup", e => {
    if (e.key === "Enter")
        performSearch();
});

// Render lần đầu
renderEvents();
renderPagination();