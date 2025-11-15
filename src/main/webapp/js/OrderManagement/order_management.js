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

            let url = "order_management?";

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

let orderToDelete = null;

function confirmDelete(orderId) {
    orderToDelete = orderId;
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    deleteModal.show();
}

document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
    if (orderToDelete !== null) {
        const deleteOrderInput = document.getElementById("deleteOrderId");
        const deleteForm = document.getElementById("deleteForm");
        deleteOrderInput.value = orderToDelete;
        deleteForm.submit();
    }
});




