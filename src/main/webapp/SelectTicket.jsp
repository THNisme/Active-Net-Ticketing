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
                color: #4caf50;
                margin-bottom: 10px;
            }

            .ticket {
                border-bottom: 1px dashed #333;
                padding: 15px 0;
            }

            .ticket h3 {
                color: #4caf50;
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
                background: #4caf50;
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

            .summary-item span:last-child {
                color: #4caf50;
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
                background: #4caf50;
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
                background: #45a049;
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

                <h3>Loại vé</h3>

                <c:forEach var="ticket" items="${ticketTypes}">
                    <div class="ticket">
                        <h3 class="ticket-name">${ticket.typeName}</h3>
                        <p class="price ticket-price" data-raw-price="${ticket.price}">
                            <fmt:formatNumber value="${ticket.price}" type="number" groupingUsed="true"/> đ
                        </p>

                        <c:choose>
                            <c:when test="${ticket.availableCount > 0}">
                                <div class="quantity">
                                    <button class="btn-minus" type="button">-</button>
                                    <input class="qty-input" type="text" value="0" readonly data-available="${ticket.availableCount}"/>
                                    <button class="btn-plus" type="button">+</button>
                                </div>
                                <p class="remain">Còn ${ticket.availableCount} vé</p>
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

                <div class="summary-list"></div>

                <div class="total">
                    <span>Tổng cộng</span>
                    <span id="total">0 đ</span>
                </div>

                <button class="btn-continue" type="button">Tiếp tục</button>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const tickets = document.querySelectorAll(".ticket");
                const totalDisplay = document.getElementById("total");
                const summaryList = document.querySelector(".summary-list");

                let totalAmount = 0;
                let selections = {};

                tickets.forEach(ticket => {
                    const minusBtn = ticket.querySelector(".btn-minus");
                    const plusBtn = ticket.querySelector(".btn-plus");
                    const input = ticket.querySelector(".qty-input");
                    
                    // Lấy dữ liệu từ các element con
                    const nameElement = ticket.querySelector(".ticket-name");
                    const priceElement = ticket.querySelector(".ticket-price");
                    
                    if (!nameElement || !priceElement || !input) return;
                    
                    const name = nameElement.textContent.trim();
                    const price = parseFloat(priceElement.getAttribute('data-raw-price'));
                    const available = parseInt(input.getAttribute('data-available'));

                    if (!plusBtn || !minusBtn) return;

                    plusBtn.addEventListener("click", function() {
                        let value = parseInt(input.value, 10);
                        if (value < available) {
                            value++;
                            input.value = value;
                            selections[name] = {qty: value, price: price};
                            updateSummary();
                        } else {
                            alert("Chỉ còn " + available + " vé cho " + name);
                        }
                    });

                    minusBtn.addEventListener("click", function() {
                        let value = parseInt(input.value, 10);
                        if (value > 0) {
                            value--;
                            input.value = value;
                            if (value === 0) {
                                delete selections[name];
                            } else {
                                selections[name].qty = value;
                            }
                            updateSummary();
                        }
                    });
                });

                function updateSummary() {
                    summaryList.innerHTML = "";
                    totalAmount = 0;

                    for (let name in selections) {
                        if (selections.hasOwnProperty(name)) {
                            const item = selections[name];
                            const lineTotal = item.qty * item.price;
                            totalAmount += lineTotal;

                            const div = document.createElement("div");
                            div.className = "summary-item";

                            const leftSpan = document.createElement("span");
                            leftSpan.textContent = name + " × " + item.qty;

                            const rightSpan = document.createElement("span");
                            rightSpan.textContent = lineTotal.toLocaleString("vi-VN") + " đ";

                            div.appendChild(leftSpan);
                            div.appendChild(rightSpan);
                            summaryList.appendChild(div);
                        }
                    }

                    totalDisplay.textContent = totalAmount.toLocaleString("vi-VN") + " đ";
                }
            });
        </script>

    </body>
</html>