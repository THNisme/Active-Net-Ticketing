<%-- 
    Document   : event-form-update
    Created on : Nov 6, 2025
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập nhật sự kiện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <link href='https://cdn.jsdelivr.net/npm/froala-editor@latest/css/froala_editor.pkgd.min.css' rel='stylesheet' type='text/css' />
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/event-form-UI/create-event.css">
    </head>

    <body>
        <div class="container my-5">
            <h3 class="mb-4" style="border-bottom: solid 1px rgba(255, 255, 255, 0.3)">Cập nhật sự kiện</h3>

            <form id="eventForm" action="event-form?action=update" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="eventID" value="${event.eventID}" />
                <h5>Hãy nhập đầy đủ thông tin bắt buộc để cập nhật sự kiện (<strong style="color: red;">*</strong>)</h5>
                <div class="row mb-3">
                    <div class="col">
                        <div class="drag-wrap">
                            <label class="dropzone" id="dropzone">
                                <input id="fileInput" type="file" accept="image/*" name="eventImage">
                                <div class="dz-content" id="dzContent">
                                    <i class="bi bi-upload"></i>
                                    <div>
                                        <strong>Nhấp để chọn ảnh mới (nếu muốn thay đổi)</strong>
                                        <p class="dz-hint">Kích thước yêu cầu: <b>1280 × 720</b></p>
                                    </div>
                                    <div class="preview" id="preview" style="display:block;">
                                        <img id="previewImg" src="${event.imageURL}" alt="preview" />
                                    </div>
                                </div>
                                <div class="meta" id="meta" style="display:none;">1280 × 720</div>
                            </label>

                            <div class="controls">
                                <button class="btn" id="chooseBtn" style="display:none;">Chọn ảnh</button>
                                <button class="btn ghost" id="clearBtn" style="display:none;">Xóa</button>
                                <div class="small" id="status" style="margin: auto; color: red; font-size: 0.9em;"  data-is-update="${not empty event}"></div>
                            </div>

                            <div id="errorMsg" class="error" style="display:none; text-align: center; "></div>
                        </div>
                    </div>

                    <div class="col">
                        <div class="mb-3">
                            <label for="eventName" class="form-label"><strong style="color: red;">* </strong>Tên sự kiện</label>
                            <input type="text" class="form-control" id="eventName" name="eventName" required value="${event.eventName}">
                        </div>

                        <div class="mb-3">
                            <label for="eventCategory" class="form-label"><strong style="color: red;">* </strong>Loại sự kiện</label>
                            <select class="form-select" id="eventCategory" required name="eventCategory">
                                <c:forEach var="category" items="${eventCateList}">
                                    <option value="${category.categoryID}" 
                                            <c:if test="${category.categoryID == event.categoryID}">selected</c:if>>
                                        <c:out value="${category.categoryName}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="eventPlace" class="form-label"><strong style="color: red;">* </strong>Nơi tổ chức</label>
                            <a href="placeform" class="float-end">Thêm nơi tổ chức mới.</a>
                            <select class="form-select" id="eventPlace" required name="place">
                                <c:forEach var="place" items="${placeList}">
                                    <option value="${place.placeID}" 
                                            <c:if test="${place.placeID == event.placeID}">selected</c:if>>
                                        <c:out value="${place.placeName}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <div class="row">
                                <div class="col">
                                    <label for="eventDateStart" class="form-label"><strong style="color: red;">* </strong>Ngày bắt đầu</label>
                                    <input type="datetime-local" class="form-control" id="eventDateStart" name="startDate"
                                           value="${event.startDate.toString().substring(0,16)}" required>
                                </div>
                                <div class="col">
                                    <label for="eventDateEnd" class="form-label"><strong style="color: red;">* </strong>Ngày kết thúc</label>
                                    <input type="datetime-local" class="form-control" id="eventDateEnd" name="endDate"
                                           value="${event.endDate.toString().substring(0,16)}" required>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="eventDescription" class="form-label"><strong style="color: red;">* </strong>Thông tin sự kiện</label>
                    <textarea id="eventDescription" name="description">${event.description}</textarea>
                </div>

                <div class="mb-3">
                    <c:if test="${not empty sessionScope.errors}">
                        <c:forEach var="e" items="${errors}">
                            <p class="text-danger"><c:out value="${e.value}"/></p>
                        </c:forEach>
                        <c:remove var="errors" scope="session" />
                    </c:if>
                </div>
                
                <a href="admincenter" type="button" class="btn prev-btn">Quay lại</a>
                <button type="submit" class="btn btn-success">Cập nhật sự kiện</button>
            </form>


        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script type='text/javascript' src='https://cdn.jsdelivr.net/npm/froala-editor@latest/js/froala_editor.pkgd.min.js'></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/custom-wysiwyg.js"></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/handle-image-input.js"></script>
        <script src="${pageContext.request.contextPath}/js/event-form-dashboard/step.js"></script>
    </body>

</html>
