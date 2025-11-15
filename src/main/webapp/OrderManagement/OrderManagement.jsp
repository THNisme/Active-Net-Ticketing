<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách đơn hàng - OrderManagement</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>


        <!-- Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

        <!-- Inter Font -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/OrderManagement/order_management.css">
    </head>
    <body>
        <div class="container py-4">
            <!-- Header: Time + Event switch -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <i class="bi bi-calendar-event me-2"></i>
                    <fmt:formatDate value="${currentTime}" pattern="dd/MM/yyyy - HH:mm:ss" />
                </div>

                <div class="d-flex align-items-center gap-2">
                    <!-- Nút quay lại -->
                    <button type="button" class="btn btn-outline-success btn-sm" onclick="history.back()">
                        <i class="bi bi-arrow-left"></i>
                    </button>

                    <!-- Nút đổi sự kiện -->
                    <div class="position-relative">
                        <button id="toggleEventList" type="button" class="btn btn-outline-success btn-sm">
                            <i class="bi bi-arrow-repeat me-1"></i> Đổi sự kiện
                            <i class="bi bi-caret-down-fill ms-1"></i>
                        </button>

                        <!-- Dropdown danh sách sự kiện -->
                        <div id="eventDropdown" class="event-dropdown shadow">
                            <ul class="no-bullets m-0">
                                <c:forEach var="e" items="${otherEvents}">
                                    <li>
                                        <a href="order_management?eventID=${e.eventID}">
                                            ${e.eventName}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <hr>

            <!-- Tiêu đề -->
            <h4 class="section-title mb-5">
                Danh sách đơn hàng 
                <c:if test="${not empty eventName}">
                    của sự kiện: <span class="name">${eventName}</span>
                </c:if>
            </h4>

            <!-- Search + Export -->
            <div class="mb-5">
                <form action="order_management" method="get" class="mb-4 position-relative">
                    <i class="bi bi-search position-absolute top-50 start-0 translate-middle-y ms-3 text-secondary"></i>

                    <input 
                        type="text" 
                        name="keyword"
                        value="${keyword}"
                        autocomplete="off"
                        class="form-control ps-5 search-box"
                        placeholder="Tìm kiếm..."
                        />

                    <!-- Giữ lại eventID khi search -->
                    <c:if test="${not empty eventID}">
                        <input type="hidden" name="eventID" value="${eventID}" />
                    </c:if>
                </form>

                <div class="d-flex gap-2">
                    <a href="export_order?eventID=${param.eventID}" class="btn btn-outline-success btn-sm">
                        <i class="bi bi-download"></i> Xuất báo cáo
                    </a>
                </div>
            </div>

            <!-- Bảng đơn hàng -->
            <div class="card mt-3 p-0">
                <table class="table table-dark table-custom align-middle text-center mb-0">
                    <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Ngày tạo đơn</th>
                            <th>Người mua</th>
                            <th>Email</th>
                            <th>Số điện thoại</th>
                            <th>Giá trị đơn</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty orderList}">
                                <tr>
                                    <td colspan="7" class="text-center">
                                        <div class="no-data">
                                            <i class="bi bi-inbox fs-2"></i>
                                            <div>Không có dữ liệu</div>
                                        </div>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="o" items="${orderList}">
                                    <tr>
                                        <td>${o.orderId}</td>
                                        <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy - HH:mm:ss"/></td>
                                        <td>${o.contactFullname}</td>
                                        <td>${o.contactEmail}</td>
                                        <td>${o.contactPhone}</td>
                                        <td><fmt:formatNumber value="${o.totalAmount}" type="number"/>đ</td>
                                        <td>
                                            <a 
                                                href="order_details?orderId=${o.orderId}" 
                                                class="btn btn-sm btn-outline-primary"
                                                title="Xem chi tiết"
                                                >
                                                <i class="bi bi-eye"></i>
                                            </a>

                                            <a 
                                                href="javascript:void(0)" 
                                                class="btn btn-sm btn-outline-danger"
                                                onclick="confirmDelete(${o.orderId})">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Delete form -->
        <form id="deleteForm" action="order_management" method="post" style="display:none;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="orderID" id="deleteOrderId">
            <input type="hidden" name="eventID" value="${eventID}">
        </form>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content bg-dark text-white">
                    <div class="modal-header border-0">
                        <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Bạn có chắc muốn xóa đơn hàng này?
                    </div>
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-danger" id="confirmDeleteBtn">Xóa</button>
                    </div>
                </div>
            </div>
        </div>




        <!-- External JS -->
        <script src="${pageContext.request.contextPath}/js/OrderManagement/order_management.js"></script>
    </body>
</html>
