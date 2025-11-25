/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Toggle dropdown danh sách sự kiện
const toggleBtn = document.getElementById("toggleEventList");
const dropdown = document.getElementById("eventDropdown");

toggleBtn.addEventListener("click", () => {
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", (e) => {
    if (!toggleBtn.contains(e.target) && !dropdown.contains(e.target)) {
        dropdown.style.display = "none";
    }
});

// Search với phím Enter
const searchInput = document.querySelector("input[name='keyword']");
if (searchInput) {
    searchInput.addEventListener("keydown", function (e) {
        if (e.key === "Enter") {
            e.preventDefault(); // tránh submit form

            const keyword = this.value.trim();
            const eventID = new URLSearchParams(window.location.search).get("eventID");

            let url = "ticket_management?";

            if (eventID) {
                url += "eventID=" + eventID + "&";
            }

            if (keyword.length > 0) {
                url += "keyword=" + encodeURIComponent(keyword);
            }

            window.location.href = url;
        }
    });
}

//// Xác nhận xóa đơn hàng
//function confirmDelete(orderId) {
//    if (confirm("Bạn có chắc muốn xóa đơn hàng này?")) {
//        const deleteOrderInput = document.getElementById("deleteOrderId");
//        const deleteForm = document.getElementById("deleteForm");
//        if (deleteOrderInput && deleteForm) {
//            deleteOrderInput.value = orderId;
//            deleteForm.submit();
//        }
//    }
//}

//let orderToDelete = null;
//
//function confirmDelete(orderId) {
//    orderToDelete = orderId;
//    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
//    deleteModal.show();
//}
//
//document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
//    if (orderToDelete !== null) {
//        const deleteOrderInput = document.getElementById("deleteOrderId");
//        const deleteForm = document.getElementById("deleteForm");
//        deleteOrderInput.value = orderToDelete;
//        deleteForm.submit();
//    }
//});

document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".status-toggle").forEach(btn => {
        btn.addEventListener("click", function () {

            const ticketId = this.dataset.ticketId;
            const newStatus = this.dataset.status; // 1 -> active, 2 -> inactive

            fetch(`${contextPath}/ticket_management?action=toggleStatus`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `ticketId=${ticketId}&status=${newStatus}`
            })
            .then(res => res.json())
            .then(data => {

                if (data.success) {

                    const toggled = newStatus == 1 ? 2 : 1;

                    // Update UI
                    this.innerText = newStatus == 1 ? "ACTIVE" : "INACTIVE";
                    this.dataset.status = toggled;

                    this.classList.toggle("btn-success");
                    this.classList.toggle("btn-secondary");

                } else {
                    alert("Không thể đổi trạng thái!");
                }
            });
        });
    });

});





document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll(".btn-delete").forEach(btn => {
        btn.addEventListener("click", function () {

            if (!confirm("Bạn có chắc muốn xoá vé này?")) {
                return;
            }

            const ticketId = this.getAttribute("data-ticket-id");
            const eventId = document.querySelector("input[name='eventID']")?.value;

            fetch(`ticket_management?action=delete&ticketId=${ticketId}&eventID=${eventId}`, {
                method: "GET"
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    // XÓA DÒNG <tr> KHỎI BẢNG
                    const row = this.closest("tr");
                    row.remove();
                } else {
                    alert("Không thể xóa vé.");
                }
            })
            .catch(err => {
                console.error(err);
                alert("Lỗi xoá vé.");
            });
        });
    });

});


document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll(".btn-edit").forEach(btn => {
        btn.addEventListener("click", function () {
            const ticketId = this.dataset.ticketId;
            const price = this.dataset.price;

            document.getElementById("editTicketId").value = ticketId;

            let formatted = new Intl.NumberFormat("vi-VN").format(price);
            document.getElementById("editPrice").value = formatted;

            new bootstrap.Modal(document.getElementById("editModal")).show();
        });
    });

    document.getElementById("btnSaveEdit").addEventListener("click", function () {

        let id = document.getElementById("editTicketId").value;
        let priceRaw = document.getElementById("editPrice").value;
        let price = priceRaw.replace(/[^\d]/g, "");

        if (price === "" || parseInt(price) <= 0) {
            document.getElementById("priceError").classList.remove("d-none");
            return;
        }

        fetch(`${contextPath}/ticket_management?action=updateRemaining`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `ticketId=${id}&price=${price}`
        })
        .then(r => r.json())
        .then(res => {
            if (res.success) {
                const row = document.querySelector(`tr[data-id='${id}']`);
                row.querySelector(".col-price").innerText =
                    new Intl.NumberFormat("vi-VN").format(price) + "đ";

                bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
            }
        });
    });

});
