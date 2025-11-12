/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

console.log(events);

const eventsPerPage = 3;
let currentPage = 1;
const totalPages = Math.ceil(events.length / eventsPerPage);

const fnDelete = (eid, eventName) => {
    document.getElementById("eventName").textContent = eventName;
    document.getElementById("eventID").value = eid;
};

function renderEvents() {
    const eventList = document.getElementById("event-list");
    eventList.innerHTML = "";
    const start = (currentPage - 1) * eventsPerPage;
    const end = start + eventsPerPage;
    const currentEvents = events.slice(start, end);
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
                  <a href="#"><i class="fas fa-chart-pie"></i><span>Doanh thu</span></a>
                  <a href="#"><i class="fas fa-receipt"></i><span>Đơn hàng</span></a>
                  <a href="event-form?action=update&eid=${evt.id}"><i class="fas fa-pen"></i><span>Chỉnh sửa</span></a>
                  <a
                    class="btn-delete"
                    data-id="${evt.id}" data-title="${evt.title}"
                    data-bs-toggle="modal"
                    data-bs-target="#modalDelete"
                    >
                        <i class="fas fa-trash"></i><span>Xóa</span>
                </a>
                </div>
              `;
        eventList.appendChild(eventDiv);
    });

    // Gắn sự kiện sau khi render xong
    document.querySelectorAll(".btn-delete").forEach(btn => {
        btn.addEventListener("click", () => {
            fnDelete(btn.dataset.id, btn.dataset.title);
        });
    });
}


function renderPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";
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

renderEvents();
renderPagination();

