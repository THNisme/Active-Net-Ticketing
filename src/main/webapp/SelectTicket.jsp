<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chọn vé - ${event.eventName}</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        <style>
            body {
                background: #0f0f0f;
                color: #fff;
                font-family: "Segoe UI", sans-serif;
                margin: 0;
            }

            .container {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                gap: 25px;
                padding: 30px 80px;
            }

            /* LEFT COLUMN */
            .left {
                flex: 3;
            }

            h2 {
                color: #ffb6b6;
                margin-bottom: 10px;
            }

            .ticket {
                border-bottom: 1px dashed #333;
                padding: 15px 0;
            }

            .ticket h3 {
                color:#ffb6b6;
                margin: 0;
            }

            .ticket .price {
                color: #ddd;
                font-size: 18px;
                margin: 5px 0 10px;
            }

            .quantity {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .quantity button {
                background: #ffb6b6;
                border: none;
                color: #fff;
                padding: 5px 10px;
                font-size: 18px;
                border-radius: 6px;
                cursor: pointer;
            }

            .quantity input {
                width: 35px;
                text-align: center;
                font-size: 16px;
                border-radius: 4px;
                border: 1px solid #555;
                background: #222;
                color: #fff;
                padding: 3px;
            }

            .soldout {
                background: #f44336;
                color: white;
                padding: 6px 12px;
                border-radius: 6px;
                display: inline-block;
            }

            /* RIGHT COLUMN */
            .right {
                flex: 2;
                background: #1c1c1c;
                padding: 25px 30px;
                border-radius: 12px;
            }

            .event-summary h3 {
                margin: 0;
                font-size: 18px;
                color: #fff;
            }

            .event-summary p {
                color: #ccc;
                margin: 6px 0;
            }

            .summary-list {
                margin-top: 25px;
                border-top: 1px solid #333;
                padding-top: 15px;
            }

            .summary-item {
                display: flex;
                justify-content: space-between;
                margin-bottom: 10px;
            }
            .summary-item span:first-child {
                white-space: normal !important;
                color: #fff !important;
                display: inline-block;
                line-height: 1.2 !important;
            }
            .summary-item span:last-child {
                color: #ffb6b6;
            }

            .total {
                margin-top: 25px;
                display: flex;
                justify-content: space-between;
                font-weight: bold;
                font-size: 18px;
            }

            .btn-continue {
                display: block;
                width: 100%;
                background: #ffb6b6;
                border: none;
                color: #fff;
                font-size: 18px;
                font-weight: bold;
                padding: 12px 0;
                border-radius: 8px;
                margin-top: 20px;
                cursor: pointer;
                transition: background 0.3s;
            }

            .btn-continue:hover {
                background: #ff9999;
            }
            .active-seat {
                background: #ffb6b6 !important;
                color: #000 !important;
                border-color: #ffb6b6 !important;
                font-weight: bold;
            }
        </style>
    </head>

    <body>

        <div class="container">
            <!-- LEFT: ticket list -->
            <div class="left">
                <h2>${event.eventName}</h2>
                <p><i class="fa-solid fa-location-dot"></i> ${event.placeName} - ${event.address}</p>
                <p><i class="fa-solid fa-clock"></i>
                    <fmt:formatDate value="${event.startDate}" pattern="HH:mm, dd/MM/yyyy"/>
                </p>
                <!-- ZONE SELECTOR -->
                <h3>Loại khu vực (Zone):</h3>

                <div style="display:flex; gap:12px; margin-bottom:20px;">
                    <c:forEach var="z" items="${zones}">
                        <a href="select-ticket?id=${event.eventID}&zone=${z.zoneID}"
                           style="
                           padding:10px 18px;
                           border-radius:8px;
                           text-decoration:none;
                           color:white;
                           background: ${selectedZoneId == z.zoneID ? '#ffb6b6' : '#222'};
                           border:1px solid #555;">
                            ${z.zoneName}
                        </a>
                    </c:forEach>
                </div>
                <h3>Loại vé</h3>

                <c:forEach var="ticket" items="${ticketTypes}">
                    <div class="ticket"
                         data-ticketid="${ticket.ticketTypeID}"
                         data-ticketname="${ticket.typeName}">
                        <h3 class="ticket-name">${ticket.typeName}</h3>
                        <p class="price ticket-price" data-raw-price="${ticket.price}">
                            <fmt:formatNumber value="${ticket.price}" type="number" groupingUsed="true"/> đ
                        </p>

                        <c:choose>
                            <c:when test="${ticket.availableCount > 0}">
                                <c:if test="${!ticket.hasSeat}">
                                    <div class="quantity">
                                        <button class="btn-minus" type="button">-</button>
                                        <input class="qty-input" type="text" value="0"
                                               readonly data-available="${ticket.availableCount}"/>
                                        <button class="btn-plus" type="button">+</button>
                                        <span class="available-count" style="margin-left: 10px; color: #ffb6b6;">
                                            Còn lại: <span class="remaining">${ticket.availableCount}</span>
                                        </span>
                                    </div>
                                </c:if>

                                <c:if test="${ticket.hasSeat}">
                                    <h4 style="color:#ffb6b6; margin:6px 0;">Chọn ghế:</h4>

                                    <div class="seat-list" data-tickettype="${ticket.ticketTypeID}"
                                         style="display:flex; flex-wrap:wrap; gap:8px;">

                                        <c:forEach var="s" items="${ticket.seatList}">
                                            <c:choose>
                                                <c:when test="${s.statusID == 4}">
                                                    <!-- ghế đã bán -->
                                                    <button type="button"
                                                            disabled
                                                            class="seat-tag sold-out-seat"
                                                            style="padding:6px 10px; background:#444; border:1px solid #555;
                                                            border-radius:6px; cursor:not-allowed; opacity:0.4; color:#888;">
                                                        ${s.rowLabel}${s.seatNumber}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- ghế còn -->
                                                    <button type="button"
                                                            class="seat-tag"
                                                            data-seat="${s.seatID}"
                                                            style="padding:6px 10px; background:#222; border:1px solid #444;
                                                            border-radius:6px; cursor:pointer; color:white;">
                                                        ${s.rowLabel}${s.seatNumber}
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <span class="soldout">Hết vé</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>

            <!-- RIGHT: order summary -->
            <div class="right">
                <div class="event-summary">
                    <h3>${event.eventName}</h3>
                    <p><i class="fa-solid fa-calendar-days"></i>
                        <fmt:formatDate value="${event.startDate}" pattern="HH:mm, dd/MM/yyyy"/>
                    </p>
                    <p><i class="fa-solid fa-location-dot"></i> ${event.placeName}</p>
                </div>

                <div class="summary-list" id="summaryList"></div>

                <div class="total">
                    <span>Tổng cộng</span>
                    <span id="total">0 đ</span>
                </div>

                <!-- BỌC TRONG FORM -->
                <form id="checkoutForm" method="post" action="${pageContext.request.contextPath}/checkout">
                    <!-- Gửi thông tin sự kiện -->
                    <input type="hidden" name="eventId" value="${event.eventID}">
                    <input type="hidden" name="eventName" value="${event.eventName}">
                    <input type="hidden" name="placeName" value="${event.placeName}">
                    <input type="hidden" name="startStr"
                           value="<fmt:formatDate value='${event.startDate}' pattern='HH:mm, dd/MM/yyyy'/>">

                    <!-- Gửi dữ liệu vé -->
                    <input type="hidden" name="selectionsJson" id="selectionsJson">
                    <input type="hidden" name="totalAmount" id="totalAmount">
                    <button id="btnContinue" class="btn-continue" type="submit">Tiếp tục</button>
                </form>
            </div> 
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {

                const totalDisplay = document.getElementById("total");
                const summaryList = document.getElementById("summaryList");
                const btnContinue = document.getElementById("btnContinue");

                let totalAmount = 0;
                let selections = {};

                // ==========================
                // 1. XỬ LÝ GHẾ (VÉ CÓ SEAT)
                // ==========================
                document.querySelectorAll(".seat-tag:not([disabled])").forEach(btn => {
                    btn.addEventListener("click", function () {

                        // ĐẾM GHẾ ĐANG CHỌN
                        const selectedSeats = document.querySelectorAll(".active-seat").length;

                        // Nếu user CLICK để chọn ghế (không phải bỏ chọn)
                        if (!this.classList.contains("active-seat") && selectedSeats >= 5) {
                            alert("Bạn chỉ được chọn tối đa 5 ghế.");
                            return; // ❌ không cho chọn thêm
                        }

                        // Toggle trạng thái chọn ghế
                        this.classList.toggle("active-seat");

                        const seatId = this.dataset.seat;
                        const seatLabel = this.textContent.trim();
                        const ticketEl = this.closest(".ticket");
                        const ticketTypeId = ticketEl.dataset.ticketid;
                        const ticketName = ticketEl.dataset.ticketname;
                        const price = parseInt(ticketEl.querySelector(".ticket-price").dataset.rawPrice);

                        const key = "SEAT-" + ticketTypeId + "-" + seatLabel;

                        if (this.classList.contains("active-seat")) {
                            selections[key] = {
                                ticketId: parseInt(ticketTypeId),
                                seatId: parseInt(seatId),
                                name: ticketName + " - " + seatLabel,
                                qty: 1,
                                price: price,
                                isSeat: true
                            };
                        } else {
                            delete selections[key];
                        }

                        updateSummary();
                    });
                });
                /* ======================================================
                 * 2️⃣ XỬ LÝ VÉ KHÔNG GHẾ (+ / -)
                 * ====================================================== */
                document.querySelectorAll(".ticket .quantity").forEach(qtyBox => {

                    const ticket = qtyBox.closest(".ticket");

                    const plusBtn = qtyBox.querySelector(".btn-plus");
                    const minusBtn = qtyBox.querySelector(".btn-minus");
                    const input = qtyBox.querySelector(".qty-input");

                    const name = ticket.dataset.ticketname;
                    const ticketId = ticket.dataset.ticketid;
                    const price = parseInt(ticket.querySelector(".ticket-price").dataset.rawPrice);
                    const available = parseInt(input.dataset.available);

                    const key = "NOSEAT-" + ticketId;

                    // PLUS
                    plusBtn.addEventListener("click", function () {
                        let value = parseInt(input.value);

                        if (value < available && value < 5) {
                            value++;
                            input.value = value;

                            selections[key] = {
                                ticketId: parseInt(ticketId),
                                name: name,
                                qty: value,
                                price: price,
                                isSeat: false
                            };
                        } else if (value >= 5) {
                            alert("Bạn chỉ được chọn tối đa 5 vé.");
                            return;
                        }

                        const remainEl = ticket.querySelector(".remaining");
                        if (remainEl)
                            remainEl.textContent = available - value;

                        updateSummary();
                    });

                    // MINUS
                    minusBtn.addEventListener("click", function () {
                        let value = parseInt(input.value);

                        if (value > 0) {
                            value--;
                            input.value = value;

                            if (value === 0)
                                delete selections[key];
                            else
                                selections[key].qty = value;
                        }

                        const remainEl = ticket.querySelector(".remaining");
                        if (remainEl)
                            remainEl.textContent = available - value;

                        updateSummary();
                    });

                });

                // ==========================
                // 3. UPDATE SUMMARY
                // ==========================
                function updateSummary() {
                    summaryList.innerHTML = "";
                    totalAmount = 0;

                    Object.values(selections).forEach(item => {
                        if (!item || !item.name)
                            return;
                        const lineTotal = item.qty * item.price;
                        totalAmount += lineTotal;

                        const div = document.createElement("div");
                        div.className = "summary-item";

                        const left = document.createElement("span");

                        if (item.isSeat) {
                            // Ví dụ: "Vip - D3"
                            left.textContent = item.name;
                        } else {
                            // Ví dụ: "Không Seat × 2"
                            left.textContent = item.name + " × " + item.qty;
                        }

                        const right = document.createElement("span");
                        right.textContent = lineTotal.toLocaleString("vi-VN") + " đ";

                        div.appendChild(left);
                        div.appendChild(right);
                        summaryList.appendChild(div);
                    });

                    totalDisplay.textContent = totalAmount.toLocaleString("vi-VN") + " đ";
                }

                // ==========================
                // 4. SUBMIT CHECKOUT
                // ==========================
                btnContinue.addEventListener("click", function (e) {
                    if (Object.keys(selections).length === 0) {
                        e.preventDefault();
                        alert("Vui lòng chọn ít nhất 1 vé");
                        return;
                    }

                    document.getElementById("selectionsJson").value = JSON.stringify(selections);
                    document.getElementById("totalAmount").value = totalAmount;
                });

            });
        </script>

    </body>
</html> 