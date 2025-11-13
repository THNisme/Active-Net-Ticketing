<%-- 
    Document   : config-zone
    Created on : Nov 11, 2025, 10:06:43 PM
    Author     : BACH YEN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Zone</title>
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

        <link rel="stylesheet" href="./css/place-page-style/place-form-style/style.css">
    </head>

    <body>
        <div class="container my-5">
            <h3 class="mb-4">Cấu hình zone: ${place.placeName}</h3>
            <!-- Nav tabs -->
            <ul class="nav nav-underline" id="stepTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <a href="placeform?action=update&pid=${place.placeID}" class="nav-link active">
                        <span class="step-number">1</span> Thông tin nơi tổ chức
                    </a>
                </li>
                <span class="mx-3"><i class="bi bi-chevron-right"></i></span>
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="step2-tab" data-bs-toggle="tab" data-bs-target="#step2" type="button">
                        <span class="step-number">2</span> Zone - Vùng
                    </button>
                </li>
            </ul>

            <!-- Tab contents -->
            <div class="tab-content mt-3">
                <!-- Step 1 -->
                <div class="tab-pane fade" id="step1" role="tabpanel">
                    <!-- FORM CREATE PLACE -->
                </div>



                <!-- Step 2 -->
                <div class="tab-pane fade show active" id="step2" role="tabpanel">
                    <div class="mb-3">
                        <h4 for="eventDate" class="form-label"><strong style="color: red;">* </strong>Danh sách Zone</h4>
                        <ul class="ticket-types-list">

                            <c:forEach var="z" items="${zones}">
                                <li class="ticket-types-item mb-3">
                                    <div class="ticket-types-name">
                                        <i class="bi bi-bounding-box-circles"></i>
                                        <p>${z.zoneName}</p>
                                    </div>
                                    <div class="manipulate-btn-group">
                                        <button type="button" class="btn mx-2"
                                                onclick="fnUpdateZone(${z.zoneID}, '${fn:escapeXml(z.zoneName)}', ${z.statusID})"
                                                data-bs-toggle="modal"
                                                data-bs-target="#modalUpdateZone"><i class="bi bi-pencil"></i></button>
                                        <button type="button" class="btn mx-2 delete-btn"
                                                onclick="fnDeleteZone(${z.zoneID}, '${fn:escapeXml(z.zoneName)}')"
                                                data-bs-toggle="modal"
                                                data-bs-target="#modalDeleteZone"><i class="bi bi-x-circle"></i></button>
                                    </div>
                                </li>      
                            </c:forEach>  
                        </ul>
                        <!-- Button trigger modal create ticket type -->
                        <button type="button" class="btn create-tickettypes-btn" data-bs-toggle="modal"
                                data-bs-target="#modalCreateZone">
                            <i class="bi bi-plus-circle"></i>
                            Tạo Zone
                        </button>
                    </div>
                    <c:if test="${not empty globalError}">
                        <p class="text-danger"><c:out value="${globalError}"/></p>
                    </c:if>
                    <a href="placeform?action=update&pid=${place.placeID}" class="btn prev-btn">Sửa nơi tổ chức</a>
                    <a  href="place-overview" type="submit" class="btn">Hoàn tất</a>
                </div>

            </div>
        </div>


        <!-- Modal Delete Ticket Type -->
        <div class="modal fade" id="modalDeleteZone" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalDeleteZoneLable" aria-hidden="true">
            <div class="modal-dialog">
                <form action="config-zone?action=delete" method="POST">
                    <div class="modal-content modal-theme delete">

                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="modalDeleteZoneLable">Xác nhận xóa zone</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <div class="mb-3 row">
                                <label for="zoneID-D" class="col-sm-5 col-form-label">Bạn có chắc muốn xóa zone</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly class="form-control-plaintext fw-bolder text-danger" id="zoneName-D">
                                    <input type="hidden" id="zoneID-D" name="zoneID">
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

        <!-- Modal Create-->
        <div class="modal fade" id="modalCreateZone" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalCreateZoneLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content modal-theme">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modalCreateZoneLabel">Tạo zone</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>


                    <form action="config-zone?action=create" method="POST">

                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="zoneName" class="form-label"><strong style="color: red;">* </strong>Tên zone</label>
                                <p class="text-center text-secondary">Khuyến nghị: Tên zone nên được đặt giống với tên có trong
                                    <a data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
                                       aria-controls="collapseExample">
                                        ảnh sơ đồ nơi tổ chức.
                                    </a>
                                    <br>
                                    Điều này giúp cho việc cấu hình sự kiện dễ dàng hơn !
                                </p>

                                <input type="text" class="form-control" id="zoneName" name="zoneName" required>
                                <input type="hidden" value="${place.placeID}" name="placeID">
                            </div>

                            <div class="collapse mt-3" id="collapseExample">
                                <div class="card card-body">
                                    <div id="modalZoneMapImageWrapper">
                                        <img src="${place.seatMapURL}" alt="" id="modalZoneMapImage" width="400px">
                                        <div class="zoom-control-btn-group">
                                            <button type="button" class="btn" onclick="zoomIn()">Zoom In</button>
                                            <button type="button" class="btn" onclick="zoomOut()">Zoom Out</button>
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
        <!-- Modal Update-->                  
        <div class="modal fade" id="modalUpdateZone" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="modalUpdateZoneLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content modal-theme">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="modalUpdateZoneLabel">Sửa zone</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>


                    <form action="config-zone?action=update" method="POST">

                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="zoneName" class="form-label"><strong style="color: red;">* </strong>Tên zone</label>
                                <p class="text-center text-secondary">Khuyến nghị: Tên zone nên được đặt giống với tên có trong
                                    <a data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
                                       aria-controls="collapseExample">
                                        ảnh sơ đồ nơi tổ chức.
                                    </a>
                                    <br>
                                    Điều này giúp cho việc cấu hình sự kiện dễ dàng hơn !
                                </p>

                                <input type="text" class="form-control" id="zoneName-U" name="zoneName" required>
                                <input type="hidden" value="${place.placeID}" name="placeID">
                                <input type="hidden" value="" id="zoneID-U" name="zoneID">
                                <input type="hidden" value="" id="zoneStatusID-U" name="statusID">
                            </div>

                            <div class="collapse mt-3" id="collapseExample">
                                <div class="card card-body">
                                    <div id="modalZoneMapImageWrapper">
                                        <img src="${place.seatMapURL}" alt="" id="modalZoneMapImage" width="400px">
                                        <div class="zoom-control-btn-group">
                                            <button type="button" class="btn" onclick="zoomIn()">Zoom In</button>
                                            <button type="button" class="btn" onclick="zoomOut()">Zoom Out</button>
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

        <!-- Elfsight Website Translator | Untitled Website Translator -->
        <!-- <script src="https://elfsightcdn.com/platform.js" async></script>
        <div class="elfsight-app-fbb67af7-dc8c-4557-90c3-fc51f6edee01" data-elfsight-app-lazy></div> -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./js/place-form-js/handle-image-input.js"></script>
        <script src="./js/place-form-js/step.js"></script>
        <script src="./js/place-form-js/handle-zoom-image.js"></script>
        <script>
                                                const fnDeleteZone = (id, zoneName) => {
                                                    document.getElementById("zoneID-D").value = id;
                                                    document.getElementById("zoneName-D").value = zoneName;
                                                };
                                                const fnUpdateZone = (zId, zoneName, zStatusId) => {
                                                    document.getElementById("zoneID-U").value = zId;
                                                    document.getElementById("zoneStatusID-U").value = zStatusId;
                                                    document.getElementById("zoneName-U").value = zoneName;
                                                };
        </script>
    </body>

</html>
