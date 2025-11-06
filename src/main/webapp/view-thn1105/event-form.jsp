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
        <title>Tạo sự kiện</title>
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
            <h3 class="mb-4" style="border-bottom: solid 1px rgba(255, 255, 255, 0.3)">Thêm sự kiện mới</h3>
            <!-- Nav tabs -->
            <ul class="nav nav-underline" id="stepTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="step1-tab" data-bs-toggle="tab" data-bs-target="#step1" type="button">
                        <span class="step-number">1</span> Thông tin sự kiện
                    </button>
                </li>
                <span class="mx-3"><i class="bi bi-chevron-right"></i></span>
                <li class="nav-item" role="presentation">
                    <button class="nav-link disabled" id="step2-tab" data-bs-toggle="tab" data-bs-target="#step2" type="button">
                        <span class="step-number">2</span> Cấu hình vé
                    </button>
                </li>
            </ul>

            <!-- Tab contents -->
            <div class="tab-content mt-3">
                <!-- Step 1 -->
                <div class="tab-pane fade show active" id="step1" role="tabpanel">
                    <!-- FORM CREATE EVENT -->
                    <form id="eventForm" action="${pageContext.request.contextPath}/event-form" method="POST" enctype="multipart/form-data">
                        <h5>Hãy nhập đầy đủ thông tin bắt buộc để tạo sự kiện mới (<strong style="color: red;">*</strong>)</h5>
                        <div class="row mb-3">
                            <div class="col">
                                <div class="drag-wrap">
                                    <label class="dropzone" id="dropzone">
                                        <input id="fileInput" type="file" accept="image/* " required name="eventImage">
                                        <div class="dz-content" id="dzContent">
                                            <i class="bi bi-upload"></i>
                                            <div>
                                                <strong>Nhấp nút để chọn ảnh</strong>
                                                <p class="dz-hint">Kích thước yêu cầu: <b>1280 × 720</b></p>
                                            </div>
                                            <div class="preview" id="preview" style="display:none;">
                                                <img id="previewImg" alt="preview" />
                                            </div>
                                        </div>
                                        <div class="meta" id="meta" style="display:none;">1280 × 720</div>
                                    </label>

                                    <div class="controls">
                                        <button class="btn" id="chooseBtn" style="display:none;">Chọn ảnh</button>
                                        <button class="btn ghost" id="clearBtn" style="display:none;">Xóa</button>
                                        <div class="small" id="status" style="margin: auto; color: red; font-size: 0.9em;">Vui lòng chọn ảnh</div>
                                    </div>

                                    <div id="errorMsg" class="error" style="display:none; text-align: center;"></div>
                                </div>
                            </div>
                            <div class="col">
                                <div class="mb-3">
                                    <label for="eventName" class="form-label"><strong style="color: red;">* </strong>Tên sự kiện</label>
                                    <input type="text" class="form-control" id="eventName" name="eventName" required>
                                </div>

                                <div class="mb-3">

                                    <label for="eventCategory" class="form-label"><strong style="color: red;">* </strong>Loại sự
                                        kiện</label>
                                    <select class="form-select" aria-label="Default select example" id="eventCategory" required name="eventCategory">
                                        <option value="0" selected>Open this select menu</option>
                                        <c:forEach var="category" items="${eventCateList}">
                                            <option value="${category.categoryID}">
                                                <c:out value="${category.categoryName}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="eventPlace" class="form-label"><strong style="color: red;">* </strong>Nơi tổ chức</label>
                                    <a href="#" class="float-end">Thêm nơi tổ chức mới.</a>
                                    <select class="form-select" aria-label="Default select example" id="eventPlace" required name="place">
                                        <option value="0" selected>Open this select menu</option>
                                        <c:forEach var="place" items="${placeList}">
                                            <option value="${place.placeID}">
                                                <c:out value="${place.placeName}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="mb-3">
                                    <div class="row">
                                        <div class="col">
                                            <label for="eventDateStart" class="form-label"><strong style="color: red;">* </strong>Ngày diễn
                                                ra</label>
                                            <input type="datetime-local" class="form-control" id="eventDateStart" name="startDate"
                                                   required>
                                        </div>
                                        <div class="col">
                                            <label for="eventDateEnd" class="form-label"><strong style="color: red;">* </strong>Ngày kết
                                                thúc</label>
                                            <input type="datetime-local" class="form-control" id="eventDateEnd" name="endDate" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="eventDescription" class="form-label"><strong style="color: red;">* </strong>Thông tin sự
                                kiện</label>
                            <textarea id="eventDescription" name="description"></textarea>
                        </div>

                        <div class="mb-3">
                            <c:if test="${not empty sessionScope.errors}">
                                <c:forEach var="e" items="${errors}">
                                    <p class="text-danger"><c:out value="${e.value}"/></p>
                                </c:forEach>
                                <c:remove var="errors" scope="session" />
                            </c:if>
                        </div>

                        <button type="submit" class="btn btn-primary next-btn">Tạo sự kiện</button>
                    </form>
                </div>



                <!-- Step 2 -->
                <div class="tab-pane fade" id="step2" role="tabpanel">
                    <!--After submit event will go to event-form-update and step 2 will be opened in that time-->
                </div>

            </div>
        </div>


        <!-- Elfsight Website Translator | Untitled Website Translator -->
        <!-- <script src="https://elfsightcdn.com/platform.js" async></script>
        <div class="elfsight-app-fbb67af7-dc8c-4557-90c3-fc51f6edee01" data-elfsight-app-lazy></div> -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-image-input.js"></script>
        <script>
            clearAll();
        </script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/step.js"></script>
        <script type='text/javascript'
        src='https://cdn.jsdelivr.net/npm/froala-editor@latest/js/froala_editor.pkgd.min.js'></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/custom-wysiwyg.js"></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-seat-model.js"></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-zoom-image.js"></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-input-tickettype-modal.js"></script>
    </body>

</html>
