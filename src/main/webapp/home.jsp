<%-- 
    Document   : home
    Created on : Oct 4, 2025, 5:25:12 PM
    Author     : NguyenDuc
--%>

<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<link href="css/header.css" rel="stylesheet" type="text/css"/>
<link href="css/customer-page/home.css" rel="stylesheet" type="text/css"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


<html>
    <head>
        <title>Trang chủ</title>
    </head>
    <body>
        <%@include file="view/header.jsp" %>
        <section class="events-section py-5" style="background-color:#0d0d0d; color:#fff;">
            <div class="container">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="text-success">Kết quả tìm kiếm:</h2>
                    <div class="filters">
                        <button class="btn btn-dark border text-white me-2">
                            <i class="bi bi-calendar-event"></i> Tất cả các ngày
                        </button>
                        <button class="btn btn-dark border text-white ">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                                 class="lucide lucide-funnel-icon lucide-funnel">
                            <path
                                d="M10 20a1 1 0 0 0 .553.895l2 1A1 1 0 0 0 14 21v-7a2 2 0 0 1 .517-1.341L21.74 4.67A1 1 0 0 0 21 3H3a1 1 0 0 0-.742 1.67l7.225 7.989A2 2 0 0 1 10 14z" />
                            </svg>
                            <i class="bi bi-filter"></i> Bộ lọc
                        </button>
                    </div>
                </div>

                <div class="row g-4">
                    <!-- Event 1 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/34/29/ef/e1a48969ffb2cec789e517a259f74c59.png"
                                 alt="[WORKSHOP] LÀM NẾM ĐÍNH ĐÁ" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">ART WORKSHOP "SNICKERS MOUSSE STICK"</h5>
                                <p class="price text-success">Từ 390.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 19 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 2 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/37/59/50/1f63cb2144e4f3008e4e6ba2f783ef4b.jpg"
                                 alt="Trải nghiệm bắn cung tại AFC Hà Nội" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Cocktail de Rentrée 2025 - HCM</h5>
                                <p class="price text-success">Từ 750.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 19 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 3 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/c2/59/1d/e3bad2a9a09ffc1c2d3a929fee10c06b.png"
                                 alt="[CAT&amp;MOUSE] UnlimiteD Band - Đêm nhạc vượt giới hạn" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">The “Traditional Water Puppet Show” - Múa rối nước</h5>
                                <p class="price text-success">Từ 330.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 19 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 4 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/fb/eb/66/1d976574a7ad259eb46ec5c6cfeaf63e.png"
                                 alt="SUPERFEST 2025 - Concert Mùa Hè Rực Sáng" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">[ĐÊ GARDEN] Terrarium Workshop</h5>
                                <p class="price text-success">Từ 445.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 19 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 5 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/51/4a/63/390a98adc7c30abd1c1e1be13ddc06f7.jpg"
                                 alt="SÂN KHẤU THIÊN ĐĂNG : CHUYẾN ĐÒ ĐỊNH MỆNH" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Yoga & Mindfulness Retreat</h5>
                                <p class="price text-success">Từ 500.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 20 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 6 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/71/ee/a6/e8afda43ecb70a803693813577f2dff3.jpg"
                                 alt="ART WORKSHOP &quot; UJI MATCHA CHEESECAKE TARTE&quot;" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Jazz Night at Rooftop</h5>
                                <p class="price text-success">Từ 650.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 21 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 7 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/a5/4a/d2/12722ab235b097941a4b06fe704f40c8.png"
                                 alt="[FLOWER 1969’s] WORKSHOP CANDLE - HỌC LÀM NẾN THƠM" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Food & Wine Festival 2025</h5>
                                <p class="price text-success">Từ 1.000.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 22 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 8 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/93/a9/2e/1e7b7ab361a0ff0f07df7bcd721cd8d1.jpg"
                                 alt="Colorful China" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Photography Workshop</h5>
                                <p class="price text-success">Từ 400.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 23 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 9 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/93/7b/8e/acad6821814c38a8be886b65b6a53737.jpeg"
                                 alt="SÂN KHẤU THIÊN ĐĂNG : DUYÊN THỆ" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Live Cooking Demo</h5>
                                <p class="price text-success">Từ 350.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 24 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 10 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/50/e6/51/cbc19a1cb78e264bc6c3e9fcd333b510.jpg"
                                 alt="ART WORKSHOP &quot;RABBIT &amp; MOON PINEAPPLE SHORTCAKE&quot;" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Craft Beer Tasting</h5>
                                <p class="price text-success">Từ 290.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 25 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 11 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/1a/2c/a1/8d41e6a6d325f907b7e14b4582428461.jpg"
                                 alt="SÂN KHẤU THIÊN ĐĂNG: XÓM VỊT TRỜI" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Art Exhibition: Modern Visions</h5>
                                <p class="price text-success">Từ 600.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 26 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                    <!-- Event 12 -->
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="  h-100 border-0 event-card">
                            <img src="https://images.tkbcdn.com/2/608/332/ts/ds/9f/ba/38/ea842f411e9dd7d7d151912d47683bf9.jpg"
                                 alt="HCM Networking &amp; Pitching: The 1-minute Pitch for Retail and F&amp;B" lazy="lazy" loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title">Classical Music Evening</h5>
                                <p class="price text-success">Từ 800.000đ</p>
                                <p class="date"><i class="bi bi-calendar3"></i> 27 tháng 09, 2025</p>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </section>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    <%@include file="view/footer.jsp" %>
</html>