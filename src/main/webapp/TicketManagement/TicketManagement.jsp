<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách vé - TicketManagement</title>

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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/TicketManagement/ticket_management.css">
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
                    <a type="button" class="btn btn-outline-success btn-sm" href="admincenter">
                        <i class="bi bi-arrow-left"></i>
                    </a>

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
                                        <a href="ticket_management?eventID=${e.eventID}">
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
            <!-- Title -->
            <h4 class="section-title mb-5">
                Quản lí vé
                <c:if test="${not empty eventName}">
                    của sự kiện: <span class="name">${eventName}</span>
                </c:if>
            </h4>

            <!-- Search -->
<!--            <form action="ticket_management" method="get" class="mb-4 position-relative">
    <i class="bi bi-search position-absolute top-50 start-0 translate-middle-y ms-3 text-secondary"></i>

    <input type="text"
           name="keyword"
           value="${keyword}"
           autocomplete="off"
           class="form-control ps-5 search-box"
           placeholder="Tìm kiếm..." />

    <c:if test="${not empty eventID}">
        <input type="hidden" name="eventID" value="${eventID}" />
    </c:if>

     GIỮ LẠI CHẾ ĐỘ FILER 
    <input type="hidden" name="filter" value="${filter}" />
</form>-->


            <!-- Buttons -->
            <div class="d-flex justify-content-between align-items-center mb-4">

                <div class="d-flex gap-2">
                    <a href="ticket_management?eventID=${eventID}&filter=sold"
                       class="btn ${filter == 'sold' ? 'btn-success' : 'btn-outline-success'} btn-sm">
                        <i class="bi bi-ticket-perforated me-1"></i> Vé đã bán
                    </a>

                    <a href="ticket_management?eventID=${eventID}&filter=remaining"
                       class="btn ${filter == 'remaining' ? 'btn-success' : 'btn-outline-success'} btn-sm">
                        <i class="bi bi-ticket me-1"></i> Vé còn lại
                    </a>
                </div>
            </div>

            <!-- Table -->
            <div class="card mt-3 p-0">
                <table class="table table-dark table-custom align-middle text-center mb-0">
                    <thead>
                        <tr>
                            <c:choose>
                                <c:when test="${filter == 'sold'}">
                                    <th>Mã đơn</th>
                                    <th>ID vé</th>
                                    <th>Mã vé</th>
                                    <th>Loại vé</th>
                                    <th>Ngày tạo đơn</th>
                                    <th>Giá trị</th>
                                    <th>Trạng thái</th>
                                    </c:when>

                                <c:otherwise>
                                    <th>ID vé</th>
                                    <th>Mã vé</th>
                                    <th>Loại vé</th>
                                    <th>Zone</th>
                                    <th>Seat</th>
                                    <th>Giá vé</th>
                                    <th>Hành động</th>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                    </thead>

                    <tbody>
                        <c:choose>

                            <c:when test="${empty ticketList}">
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
                                <c:forEach var="o" items="${ticketList}">
                                    <tr data-id="${o.ticketId}">

                                        <c:choose>


                                            <c:when test="${filter == 'sold'}">
                                                <td>${o.orderId}</td>
                                                <td>${o.ticketId}</td>
                                                <td>${o.serialNumber}</td>
                                                <td>${o.typeName}</td>
                                                <td>
                                                    <fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy - HH:mm:ss"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${o.unitPrice}" type="number"/>đ
                                                </td>
                                                <td>
                                                    <button class="btn btn-sm status-toggle 
                                                            ${o.statusId == 1 ? 'btn-success' : 'btn-secondary'}"
                                                            data-ticket-id="${o.ticketId}"
                                                            data-status="${o.statusId == 1 ? 2 : 1}"
                                                            data-event-id="${eventID}">
                                                        ${o.statusId == 1 ? "ACTIVE" : "INACTIVE"}
                                                    </button>


                                                </td>





                                            </c:when>


                                            <c:otherwise>
                                                <td>${o.ticketId}</td>
                                                <td>${o.serialNumber}</td>
                                                <td>${o.typeName}</td>
                                                <td>${o.zoneName}</td>
                                                <td>${o.seat}</td>
                                                <td class="col-price">
                                                    <fmt:formatNumber value="${o.price}" type="number"/>đ
                                                </td>



                                                <td class="d-flex justify-content-center gap-2">

                                                    <!-- Nút chỉnh sửa -->
                                                    <button class="btn btn-sm btn-outline-warning btn-edit"
                                                            data-ticket-id="${o.ticketId}"
                                                            data-price="${o.price}">
                                                        <i class="bi bi-pencil-square"></i>
                                                    </button>



                                                    <!-- Nút xóa (đổi status sang 3) -->
                                                    <button class="btn btn-sm btn-outline-danger btn-delete"
                                                            data-ticket-id="${o.ticketId}"
                                                            title="Xóa vé">
                                                        <i class="bi bi-trash3"></i>
                                                    </button>


                                                </td>
                                            </c:otherwise>

                                        </c:choose>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>

                        </c:choose>
                    </tbody>
                </table>
            </div>

        </div>

        <!-- EDIT MODAL -->
        <div class="modal fade" id="editModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">

                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title">Chỉnh sửa vé</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <div class="modal-body">
                        <input type="hidden" id="editTicketId">

                        <div class="mb-3">
                            <label class="form-label">Giá vé</label>
                            <input type="text" id="editPrice" class="form-control" />
                            <small class="text-danger d-none" id="priceError">
                                Giá không hợp lệ!
                            </small>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button id="btnSaveEdit" class="btn btn-success">Update</button>
                    </div>

                </div>
            </div>
        </div>


        <script>
            const contextPath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/js/TicketManagement/ticket_management.js"></script>

    </body>
</html>
