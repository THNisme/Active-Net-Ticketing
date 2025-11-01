<%-- 
    Document   : event-form
    Created on : Oct 22, 2025, 12:09:01 AM
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cấu hình vé</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <link href='https://cdn.jsdelivr.net/npm/froala-editor@latest/css/froala_editor.pkgd.min.css' rel='stylesheet'
              type='text/css' />

        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/event-form-UI/create-event.css">
    </head>

    <body>
        <div class="container mt-5">
            <h3 class="mb-4">Sự kiện <c:out value="${event.eventName}"/></h3>
            <!-- Nav tabs -->
            <ul class="nav nav-underline" id="stepTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <a href="event-form?action=update&eid=${event.eventID}" class="nav-link">
                        <span class="step-number">1</span> Thông tin sự kiện
                    </a>
                </li>
                <span class="mx-3"><i class="bi bi-chevron-right"></i></span>
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="step2-tab" data-bs-toggle="tab" data-bs-target="#step2" type="button">
                        <span class="step-number">2</span> Cấu hình vé
                    </button>
                </li>
            </ul>

            <!-- Tab contents -->
            <div class="tab-content mt-3">
                <!-- Step 2 -->
                <div class="tab-pane fade show active" id="step2" role="tabpanel">
                    <div class="mb-3">
                        <h4 for="eventDate" class="form-label"><strong style="color: red;">* </strong> Loại vé</h4>
                        <ul class="ticket-types-list">
                            <c:forEach var="tp" items="${ticketTypes}">
                                <li class="ticket-types-item mb-3">
                                    <div class="ticket-types-name">
                                        <i class="bi bi-ticket-perforated"></i>
                                        <p>${tp.typeName}</p>
                                    </div>
                                    <div class="manipulate-btn-group">
                                        <button type="button" class="btn mx-2"><i class="bi bi-pencil"></i></button>
                                        <button type="button" class="btn mx-2 delete-btn"><i class="bi bi-x-circle"></i></button>
                                    </div>
                                </li>
                            </c:forEach> 
                        </ul>
                        <!-- Button trigger modal create ticket type -->
                        <button type="button" class="btn create-tickettypes-btn" data-bs-toggle="modal"
                                data-bs-target="#modalCreateTicketType">
                            <i class="bi bi-plus-circle"></i>
                            Tạo loại vé mới
                        </button>
                    </div>
                    <a href="event-form?action=update&eid=${event.eventID}" type="button" class="btn prev-btn">Quay lại</a>
                    <a href="#" type="submit" class="btn">Hoàn tất</a>
                </div>
            </div>
        </div>


        <!-- Modal Delete Ticket Type -->
        <div class="modal fade" id="modalDeleteTicketType" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalDeleteTicketTypeLable" aria-hidden="true">
            <div class="modal-dialog">
                <form action="">
                    <div class="modal-content modal-theme delete">

                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="modalDeleteTicketTypeLable">Xác nhận xóa loại vé</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <div class="mb-3 row">
                                <label for="staticEmail" class="col-sm-5 col-form-label">Bạn có chắc muốn xóa hạng vé </label>
                                <div class="col-sm-4">
                                    <input type="text" readonly class="form-control-plaintext fw-bolder text-danger" id="staticEmail"
                                           value="VIP">
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary d-flex justify-content-end">Xóa</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal Create Ticket Type -->
        <div class="modal fade" id="modalCreateTicketType" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalCreateTicketTypeLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <form action="event-form?action=config-ticket" method="POST">
                    <div class="modal-content modal-theme">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="modalCreateTicketTypeLabel">Tạo loại vé</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="ticketTypeName" class="form-label"><strong style="color: red;">* </strong>Tên loại vé</label>
                                <input type="text" class="form-control" id="ticketTypeName" name="ticketTypeName" required>
                            </div>

                            <div class="mb-3">
                                <div class="row">
                                    <div class="col">

                                        <label for="ticketTypePrice" class="form-label"><strong style="color: red;">* </strong>Giá
                                            vé</label>
                                        <div class="modal-price-input-wrapper">
                                            <div>
                                                <input type="number" class="form-control" id="ticketTypePrice" name="ticketTypePrice" min="1"
                                                       required>
                                            </div>

                                            <div class="form-check mx-2">
                                                <input class="form-check-input" type="checkbox" value="" id="checkFreeTicket">
                                                <label class="form-check-label" for="checkFreeTicket">
                                                    Miễn phí
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <label for="ticketTotal" class="form-label"><strong style="color: red;">* </strong>Tổng số
                                            vé</label>
                                        <input type="number" class="form-control" id="ticketTotal" name="ticketTotal" min="1"
                                               required>
                                    </div>
                                    <div class="col">
                                        <label for="ticketTypeZone" class="form-label"><strong style="color: red;">* </strong>Chọn zone vé</label>
                                        <a href="#" class="float-end">Tạo zone</a>


                                        <c:forEach var="place" items="${placeList}">
                                            <option value="${place.placeID} " 
                                                    <c:out value="${place.placeName}"/>
                                        </option>
                                    </c:forEach>

                                    <select class="form-select" aria-label="Default select example" id="ticketTypeZone" required>
                                        <c:forEach var="z" items="${zones}">
                                            <option value="${z.zoneID} <c:if test="${z.statusID == 2}">disabled</c:if>">
                                                <c:out value="${z.zoneName}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="row">
                                <div class="col">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="" id="checkIdentityTicket">
                                        <label class="form-check-label" for="checkIdentityTicket" data-bs-toggle="tooltip"
                                               data-bs-placement="right" data-bs-title="Chọn để tạo loại vé có vị trí chỗ ngồi cụ thể.">
                                            Khởi tạo vé có định danh <span style="opacity: 0.8;">(tùy chọn)</span>
                                        </label>
                                    </div>
                                </div>
                                <div class="col">
                                    <a class="float-end" data-bs-toggle="collapse" href="#collapseExample" role="button"
                                       aria-expanded="false" aria-controls="collapseExample">
                                        Xem sơ đồ nơi tổ chức</a>
                                </div>
                                <div class="collapse mt-3" id="collapseExample">
                                    <div class="card card-body">
                                        <div id="modalZoneMapImageWrapper">
                                            <img src="${place.seatMapURL}" alt="" id="modalZoneMapImage"
                                                 width="600px">
                                            <div class="zoom-control-btn-group">
                                                <button type="button" class="btn" onclick="zoomIn()">Zoom In</button>
                                                <button type="button" class="btn" onclick="zoomOut()">Zoom Out</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3 seat-init disable" id="init-seat-container">
                            <div class="row controls-init-seat">

                                <div class="col">
                                    <label for="rowsSeatInput" class="form-label"><strong style="color: red;">* </strong> Dãy ghế:</label>
                                    <input type="text" class="form-control" id="rowsSeatInput" placeholder="Ví dụ: A-D hoặc ABCD"
                                           name="rowsSeatInput" required>
                                </div>

                                <!-- <div class="col">
                                  <label for="rowsSeatInput" class="form-label"><strong style="color: red;">* </strong> Số ghế mỗi
                                    dãy:</label>
                                  <input type="number" class="form-control" id="colsSeatInput" placeholder="Ví dụ: 10"
                                    name="colsSeatInput" min="1" required disabled>
                                </div> -->

                                <div class="col d-flex flex-column justify-content-end">
                                    <div class="d-flex">
                                        <button type="button" class="btn mx-2" onclick="generateSeatInputs()" style="width: fit-content;">
                                            Tạo dãy ghế
                                        </button>
                                        <button type="button" class="btn mx-2" onclick="generateSeatGrid()" data-bs-toggle="tooltip"
                                                data-bs-placement="right"
                                                data-bs-title="Mô hình vé mang tính chất minh họa cho lượng vé được tạo ra, không phải vị trí thực"
                                                style="width: fit-content;">
                                            Tạo mô hình ghế
                                        </button>
                                    </div>
                                </div>

                                <div class="mb-4 mt-4">
                                    <div id="seatInputs"></div>
                                </div>
                            </div>


                            <div id="seatModelGridContainer"></div>
                        </div>
                    </div>

                    <div class="modal-footer ticketType-modal-footer">
                        <div class="row">
                            <div class="col">
                                <p id="message" style="color: #f85c51; font-weight: bold;"></p>
                            </div>
                            <div class="col">
                                <button type="submit" class="btn btn-primary float-end">Lưu</button>
                            </div>
                        </div>
                    </div>
            </form>
        </div>
    </div>
</div>









<!-- Elfsight Website Translator | Untitled Website Translator -->
<!-- <script src="https://elfsightcdn.com/platform.js" async></script>
<div class="elfsight-app-fbb67af7-dc8c-4557-90c3-fc51f6edee01" data-elfsight-app-lazy></div> -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-image-input.js"></script>
<c:if test="${empty event.imageURL}">
    <script>
                                            clearAll();
    </script>
</c:if>

<script src="${pageContext.request.contextPath}/js/event-form-dashboard/step.js"></script>
<script type='text/javascript'
src='https://cdn.jsdelivr.net/npm/froala-editor@latest/js/froala_editor.pkgd.min.js'></script>
<script src="${pageContext.request.contextPath}/js/event-form-dashboard/custom-wysiwyg.js"></script>
<script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-seat-model.js"></script>
<script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-zoom-image.js"></script>
<script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-input-tickettype-modal.js"></script>

<!--DATA FILLING-->
<script>
                                            function fnEditTicketType(id, eventID, zoneID, typeName, price, status) {
//                document.getElementById("idDelete").value = id;
//                document.getElementById("accountDelete").value = account;
//                document.getElementById("passwordDelete").value = password;
//                document.getElementById("platformDelete").value = platform;
                                            }


</script>
</body>

</html>
