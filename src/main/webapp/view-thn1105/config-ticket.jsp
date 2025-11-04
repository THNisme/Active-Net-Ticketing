<%-- 
    Document   : event-form
    Created on : Oct 22, 2025, 12:09:01 AM
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
            <div class="row mb-3">
                <div class="col">
                    <h3 class="mb-4">Sự kiện <c:out value="${event.eventName}"/></h3>
                </div>
                <div class="col">
                    <div class="d-flex justify-content-end">
                        <!-- Button trigger modal create ticket type -->
                        <button type="button" class="btn create-tickettypes-btn" data-bs-toggle="modal"
                                data-bs-target="#modalCreateTicketType">
                            <i class="bi bi-plus-circle"></i>
                            Cấu hình loại vé mới
                        </button>
                        <!-- Button go to mange ticket-->
                        <button type="button" class="btn create-tickettypes-btn" data-bs-toggle="modal"
                                data-bs-target="#modalGenerateTicketType">
                            <i class="bi bi-plus-circle"></i>
                            Tạo vé
                        </button>    
                    </div>
                </div>
            </div>
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
                                        <c:choose>
                                            <c:when test="${tp.statusID == 10}">
                                                <span class="text-success">Đã có vé</span>
                                            </c:when>
                                            <c:otherwise>
                                                <a type="button" class="text-warning" data-bs-toggle="modal"
                                                   data-bs-target="#modalGenerateTicketType">⚠️ Chưa được tạo vé</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <button type="button" class="btn mx-2"
                                                onclick="fnUpdateTicketType(
                                                ${tp.ticketTypeID},
                                                ${tp.eventID},
                                                ${tp.zoneID},
                                                                '${fn:escapeXml(tp.typeName)}',
                                                ${tp.price},
                                                ${tp.statusID})"
                                                data-bs-toggle="modal"
                                                data-bs-target="#modalUpdateTicketType">
                                            <i class="bi bi-pencil"></i>
                                        </button>
                                        <button type="button" class="btn mx-2 delete-btn"
                                                onclick="fnDeleteTicketType(
                                                ${tp.ticketTypeID},
                                                                '${fn:escapeXml(tp.typeName)}')"
                                                data-bs-toggle="modal"
                                                data-bs-target="#modalDeleteTicketType">
                                            <i class="bi bi-x-circle"></i>
                                        </button>
                                    </div>
                                </li>
                            </c:forEach> 
                        </ul>
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
                <form action="config-ticket?action=delete" method="POST">
                    <div class="modal-content modal-theme delete">

                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="modalDeleteTicketTypeLable">Xác nhận xóa loại vé</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="event"></form>
                        <div class="modal-body">
                            <div class="mb-3 row">
                                <label for="staticEmail" class="col-sm-5 col-form-label">Bạn có chắc muốn xóa </label>
                                <div class="col-sm-4">
                                    <input type="text" readonly class="form-control-plaintext fw-bolder text-danger" id="typeName-D">
                                    <input type="hidden" id="ticketTypeID-D" name="ticketTypeID-D">
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
            <div class="modal-dialog">
                <div class="modal-content modal-theme">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modalCreateTicketTypeLabel">Cấu hình loại vé mới</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <form action="config-ticket?action=create" method="POST">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="ticketTypeName" class="form-label"><strong style="color: red;">* </strong>Tên loại vé</label>
                                <input type="text" class="form-control" id="ticketTypeName" name="ticketTypeName" required>
                                <!--EVENT ID HIDDEN-->
                                <input type="hidden" name="eventID" value="${event.eventID}">
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
                                        <label for="ticketTypeZone" class="form-label"><strong style="color: red;">* </strong>Chọn zone vé</label>
                                        <a href="#" class="float-end">Tạo zone</a>

                                        <select class="form-select" aria-label="Default select example" id="ticketTypeZone" name="zoneID" required>
                                            <c:forEach var="z" items="${zones}">
                                                <option value="${z.zoneID}"  <c:if test="${z.statusID == 2}">disabled</c:if>>
                                                    <c:out value="${z.zoneName}"/>
                                                </option>
                                            </c:forEach>
                                        </select>

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


        <!-- Modal Update Ticket Type -->
        <div class="modal fade" id="modalUpdateTicketType" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalUpdateTicketTypeLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content modal-theme">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modalUpdateTicketTypeLabel">Cập nhật loại vé: </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <form action="config-ticket?action=update" method="POST">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="ticketTypeName-U" class="form-label"><strong style="color: red;">* </strong>Tên loại vé</label>
                                <input type="text" class="form-control" id="ticketTypeName-U" name="ticketTypeName-U" required>
                                <!--EVENT & STATUS & TICKET TYPE ID HIDDEN-->
                                <input type="hidden" name="eventID-U" id="tickUpdateEID-U" value="${event.eventID}">
                                <input type="hidden" name="statusID-U" id="tickUpdateSID-U">
                                <input type="hidden" name="ticketTypeID-U" id="tickUpdateTID-U">

                            </div>

                            <div class="mb-3">
                                <div class="row">
                                    <div class="col">
                                        <label for="ticketTypePrice-U" class="form-label"><strong style="color: red;">* </strong>Giá
                                            vé</label>
                                        <div class="modal-price-input-wrapper">
                                            <div>
                                                <input type="number" class="form-control" id="ticketTypePrice-U" name="ticketTypePrice-U" min="1"
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
                                        <label for="ticketTypeZone-U" class="form-label"><strong style="color: red;">* </strong>Chọn zone vé</label>
                                        <a href="#" class="float-end">Tạo zone</a>

                                        <select class="form-select" aria-label="Default select example" id="ticketTypeZone-U" name="zoneID-U" required>
                                            <c:forEach var="z" items="${zones}">
                                                <option value="${z.zoneID}"  <c:if test="${z.statusID == 2}">disabled</c:if>>
                                                    <c:out value="${z.zoneName}"/>
                                                </option>
                                            </c:forEach>
                                        </select>

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

        <!-- Modal Generate Ticket -->
        <div class="modal fade" id="modalGenerateTicketType" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalGenerateTicketTypeLable" aria-hidden="true">
            <div class="modal-dialog">
                <form action="config-ticket?action=generate-ticket" method="POST">
                    <div class="modal-content modal-theme">

                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="modalDeleteTicketTypeLable">Tạo vé</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="config-ticket?action=generate-ticket">

                            <div class="modal-body">
                                <div class="mb-3">
                                    <div class="row">
                                        <div class="col">
                                            <label for="ticketTypeID-GenTicket" class="form-label">Loại vé</label>
                                            <select id="ticketTypeID-GenTicket" name="ticketTypeID-GenTicket" class="form-select" required>
                                                <c:forEach var="tp" items="${ticketTypes}">
                                                    <option value="${tp.ticketTypeID}" ${tp.statusID == 10 ? 'disabled' : ''}>${tp.typeName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col">
                                            <label for="ticketQuantity" class="form-label">Tổng số vé</label>
                                            <input type="number" id="ticketQuantity" name="ticketQuantity" class="form-control" min="1" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-check mb-3">
                                    <input class="form-check-input" type="checkbox" id="hasSeat" name="hasSeat">
                                    <label class="form-check-label" for="hasSeat">
                                        Tạo vé có định danh (có chỗ ngồi)
                                    </label>
                                </div>
                                <div id="seatConfigSection" style="display: none;">
                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col">
                                                <label for="rowInput" class="form-label">Hàng ghế (VD: A-D hoặc ABCD)</label>
                                                <input type="text" id="rowInput" class="form-control" placeholder="A-D hoặc ABCD">
                                            </div>
                                            <div class="col d-flex flex-column-reverse">
                                                <button type="button" id="generateSeatRows" class="btn">Sinh hàng ghế</button>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="seatRowsContainer"></div>

                                    <div id="seatTotalCheck" class="text-danger fw-bold mt-3 mb-3"></div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <div class="row">
                                    <div class="col">
                                        <p id="message" style="color: #f85c51; font-weight: bold;"></p>
                                    </div>
                                    <div class="col">
                                        <button type="submit" class="btn float-end" id="generateTicketBtn">Tạo vé</button>
                                    </div>
                                </div>
                            </div>

                        </form>
                    </div>
                </form>
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
        <script src="${pageContext.request.contextPath}/js/config-ticket/handle-generate-ticket.js"></script>

        <!--DATA FILLING-->
        <script>
                                                        function fnUpdateTicketType(ticketTypeid, eventID, zoneID, typeName, price, statusID) {
                                                            document.getElementById("modalUpdateTicketTypeLabel").textContent = "Cập nhật loại vé: " + typeName;
                                                            document.getElementById("ticketTypeName-U").value = typeName;
                                                            document.getElementById("tickUpdateEID-U").value = eventID;
                                                            document.getElementById("ticketTypePrice-U").value = price;
                                                            document.getElementById("ticketTypeZone-U").value = zoneID;
                                                            document.getElementById("tickUpdateSID-U").value = statusID;
                                                            document.getElementById("tickUpdateTID-U").value = ticketTypeid;
                                                        }

                                                        function fnDeleteTicketType(ticketTypeId, typeName) {
                                                            document.getElementById("ticketTypeID-D").value = ticketTypeId;
                                                            document.getElementById("typeName-D").value = typeName;
                                                        }

        </script>
    </body>

</html>
