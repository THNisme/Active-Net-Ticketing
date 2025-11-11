<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>${event.eventName}</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        <style>
            body {
                font-family: "Segoe UI", sans-serif;
                background: #38383D;
                color: #fff;
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }

            /* ===== Header menu (optional) ===== */
            .menu {
                background: #000;
                padding: 25px 75px;
                display: flex;
                align-items: center;
                gap: 30px;
            }

            .menu a {
                color: #fff;
                text-decoration: none;
                font-size: 20px;
                font-weight: 500;
            }

            .menu a:hover {
                color: #00cc66;
            }

            /* ===== Event container ===== */
            .event-container {
                display: flex;
                justify-content: center;
                align-items: stretch;
                margin: 60px auto;
                background: #1f1f1f;
                border-radius: 40px;
                overflow: hidden;
                width: 85%;
                min-height: 550px;
                position: relative;
            }

            .event-divider {
                position: absolute;
                top: 30px;
                bottom: 30px;
                left: 50%;
                transform: translateX(-50%);
                border-left: 5px dashed #38383D;
                opacity: 0.9;
                z-index: 3;
            }

            .event-info {
                flex: 1;
                padding: 40px 50px;
                position: relative;
            }

            .event-info h2 {
                margin-top: 0;
                font-size: 26px;
                font-weight: bold;
                line-height: 1.4;
            }

            .event-info .datetime {
                color: #9ef59b;
                font-weight: bold;
                margin: 12px 0;
                font-size: 15px;
            }

            .event-info .location {
                color: #ccc;
                margin-bottom: 20px;
                font-size: 15px;
            }

            .price {
                padding-top: 140px;
                font-size: 32px;
                font-weight: bold;
            }

            .price span {
                color: #00cc66;
            }

            .btn-buy {
                position: absolute;
                bottom: 50px;
                left: 50px;
                background: #00cc66;
                color: #fff;
                padding: 14px 190px;
                border-radius: 8px;
                font-weight: bold;
                text-decoration: none;
                transition: background 0.3s;
            }

            .btn-buy:hover {
                background: #00b35a;
            }

            .event-image {
                flex: 1.2;
                background: #000;
                position: relative;
                overflow: hidden;
            }

            .event-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            /* ===== Circle decorations ===== */
            .circle {
                position: absolute;
                width: 80px;
                height: 80px;
                background-color: #38383D;
                border-radius: 50%;
                z-index: 5;
            }

            #circle1 {
                top: 85px;
                left: 50%;
                transform: translateX(-50%);
            }
            #circle2 {
                bottom: 40px;
                left: 50%;
                transform: translateX(-50%);
            }

            /* ===== Intro section ===== */
            .intro {
                background: #f6f7fb;
                color: #222;
                padding: 40px 80px;
                border-radius: 8px;
                width: 91.5%;
                margin: 0 auto 80px auto;
            }

            .intro h3 {
                margin-bottom: 15px;
                font-size: 22px;
            }

            .intro p {
                line-height: 1.7;
                font-size: 16px;
            }

            hr {
                border: none;
                border-top: 2px solid #ddd;
                margin: 10px 0 20px 0;
            }

            /* ===== Responsive ===== */
            @media (max-width: 992px) {
                .event-container {
                    flex-direction: column;
                    border-radius: 20px;
                    width: 90%;
                }
                .event-divider,
                .circle {
                    display: none;
                }
                .event-info {
                    order: 2;
                    padding: 30px;
                }
                .event-image {
                    order: 1;
                    height: 300px;
                }
                .btn-buy {
                    position: static;
                    display: inline-block;
                    margin-top: 30px;
                    padding: 12px 40px;
                }
            }
        </style>
    </head>

    <body>

        <!-- Main event container -->
        <section class="event-container">
            <div class="event-info">
                <h2>${event.eventName}</h2>

                <p class="datetime">
                    <i class="fa-solid fa-clock"></i>
                    <fmt:formatDate value="${event.startDate}" pattern="HH:mm - dd 'Tháng' MM, yyyy"/>
                </p>

                <p><i class="fa-solid fa-location-dot"></i> ${event.placeName}</p>
                <p class="location">${event.address}</p>

                <hr>

                <div class="price">
                    Giá từ 
                    <span>
                        <c:choose>
                            <c:when test="${not empty event.lowestPrice}">
                                <fmt:formatNumber value="${event.lowestPrice}" type="number" groupingUsed="true"/> đ
                            </c:when>
                            <c:otherwise>Liên hệ</c:otherwise>
                        </c:choose>
                    </span>
                </div>

                <a href="select-ticket?id=${event.eventID}" class="btn-buy">Mua vé ngay</a>
            </div>

            <div class="event-image">
                <img src="${event.imageURL}" alt="${event.eventName}">
            </div>

            <div class="event-divider"></div>
        </section>

        <div id="circle1" class="circle"></div>
        <div id="circle2" class="circle"></div>

        <section class="intro">
            <h3>Giới thiệu</h3>
            <hr>
            <p>${event.description}</p>
        </section>
    </body>
</html>
