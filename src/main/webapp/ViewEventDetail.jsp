<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>${event.eventName}</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            * {
                box-sizing: border-box;
            }

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
                overflow: hidden;        /* CẮT mọi thứ thò ra ngoài */
                width: 85%;
                max-width: 1400px;
                min-height: 550px;
                position: relative;      /* ĐỂ divider + circle bám theo card */
            }

            /* Divider đúng chuẩn */
            /* Đặt trục xé vé chung */
            .event-divider,
            .circle {
                left: 38%;                 /* chỗ này bạn chỉnh: 45, 46, 47 tuỳ mắt */
                transform: translateX(-50%);
            }

            /* Divider */
            .event-divider {
                position: absolute;
                top: 50%;
                transform: translate(-50%, -50%);
                height: calc(100% - 80px);
                border-left: 5px dashed #2e2e2e;
                z-index: 3;
            }

            .event-info {
                flex: 0.8;              /* NHỎ HƠN */
                padding: 50px;
                position: relative;
                display: flex;
                flex-direction: column;
                min-width: 0;
            }

            .event-info h2 {
                margin-top: 0;
                font-size: 26px;
                font-weight: bold;
                line-height: 1.4;
            }

            .event-info .datetime {
                color: #ffb6b6;
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
                margin-top: auto;
                padding-top: 40px;
                font-size: 32px;
                font-weight: bold;
            }

            .price span {
                color:#ffb6b6;
            }

            .btn-buy {
                margin-top: 30px;
                background: #ffb6b6;
                color: #fff;
                padding: 14px 40px;
                border-radius: 8px;
                font-weight: bold;
                text-decoration: none;
                transition: background 0.3s;
                text-align: center;
                display: inline-block;
                align-self: flex-start;
            }

            .btn-buy:hover {
                background: #ff9999;
            }

            /* Ảnh chuẩn Ticketbox */
            .event-image {
                flex: 1.6;              /* RỘNG HƠN */
                position: relative;
                overflow: hidden;
                border-radius: 0 40px 40px 0;
                background: #000;
                min-height: 550px;
            }

            /* Giống 100% Ticketbox */
            .event-image img {
                position: absolute;
                inset: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;   /* Crop đẹp */
                object-position: center;
            }


            /* ===== Circle decorations ===== */
            /* Vòng tròn xé vé */

            .circle {
                position: absolute;
                width: 80px;
                height: 80px;
                border-radius: 50%;
                background: #38383D;
                z-index: 5;
            }

            #circle1 {
                top: -40px;
            }
            #circle2 {
                bottom: -40px;
            }
            /* ===== Intro section ===== */
            .intro {
                background: #f6f7fb;
                color: #222;
                padding: 40px 80px;
                border-radius: 8px;
                width: 85%;
                max-width: 1400px;
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
                    height: 350px;
                    border-radius: 20px 20px 0 0;
                }

                .btn-buy {
                    align-self: stretch;
                    padding: 12px 20px;
                }

                .intro {
                    width: 90%;
                    padding: 30px;
                }
            }
        </style>
    </head>

    <body>
        <%@include file="view-hfs/header.jsp" %>
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
            <div id="circle1" class="circle"></div>
            <div id="circle2" class="circle"></div>
        </section>

        <section class="intro">
            <h3>Giới thiệu</h3>
            <hr>
            <p>${event.description}</p>
        </section>

        <%@include file="view-hfs/footer.jsp" %>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>