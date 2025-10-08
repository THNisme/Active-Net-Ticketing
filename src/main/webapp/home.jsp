<%-- 
    Document   : home
    Created on : Oct 4, 2025, 5:25:12 PM
    Author     : NguyenDuc
--%>

<%@page import="java.util.List"%>
<%@page import="Models.Event"%>
<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Trang chủ</title>
        <link href="css/header.css" rel="stylesheet" type="text/css"/>
        <link href="css/customer-page/home.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body style="background-color:#0d0d0d; color:#fff;">

        <%@include file="view/header.jsp" %>

        <section class="events-section py-5">
            <div class="container">
                <%
                    List<Event> events = (List<Event>) request.getAttribute("events");
                    Boolean isSearch = (Boolean) request.getAttribute("isSearch");
                %>

                <% if (isSearch != null && isSearch) { %>
                <!-- Khi ở chế độ tìm kiếm -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="text-success">Kết quả tìm kiếm:</h2>
                    <div class="filters">
                        <button class="btn btn-dark border text-white me-2">
                            <i class="bi bi-calendar-event"></i> Tất cả các ngày
                        </button>
                        <button class="btn btn-dark border text-white ">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                 viewBox="0 0 24 24" fill="none"
                                 stroke="currentColor" stroke-width="2"
                                 stroke-linecap="round" stroke-linejoin="round"
                                 class="lucide lucide-funnel-icon lucide-funnel">
                                <path d="M10 20a1 1 0 0 0 .553.895l2 1A1 1 0 0 0 14 21v-7a2 2 0 0 1 .517-1.341L21.74 4.67A1 1 0 0 0 21 3H3a1 1 0 0 0-.742 1.67l7.225 7.989A2 2 0 0 1 10 14z" />
                            </svg>
                            Bộ lọc
                        </button>
                    </div>
                </div>
                <% } else { %>
                <!-- Khi hiển thị mặc định -->
                <h2 class="text-success mb-4">Sự kiện nổi bật</h2>
                <% } %>

                <div class="row g-4">
                    <%
                        if (events == null || events.isEmpty()) {
                    %>
                    <p>Không có sự kiện nào được tìm thấy.</p>
                    <%
                        } else {
                            for (Event e : events) {
                    %>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="h-100 border-0 event-card">
                            <img src="<%= e.getImageURL() != null ? e.getImageURL() : "https://via.placeholder.com/400x250?text=No+Image"%>"
                                 alt="<%= e.getEventName()%>"
                                 loading="lazy"
                                 style="width: 100%; height: auto; aspect-ratio: 16 / 9; border-radius: 12px;">
                            <div class="card-body">
                                <h5 class="card-title"><%= e.getEventName()%></h5>
                                <p class="price text-success">Từ 
                                    <%= (e.getStatusID() == 1) ? "Miễn phí" : "Liên hệ"%>
                                </p>
                                <p class="date">
                                    <i class="bi bi-calendar3"></i>
                                    <%= e.getStartDate() != null ? e.getStartDate().toLocalDateTime().toLocalDate() : "Chưa có ngày"%>
                                </p>
                            </div>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </section>

        <%@include file="view/footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
