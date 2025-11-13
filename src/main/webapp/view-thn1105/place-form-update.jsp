<%-- 
    Document   : place-form-update
    Created on : Nov 11, 2025, 11:22:54 AM
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập nhật nơi tổ chức</title>
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
        <div class="container mt-5">
            <h3 class="mb-4">Cập nhật nơi tổ chức ${place.placeName}:</h3>
            <!-- Nav tabs -->
            <ul class="nav nav-underline" id="stepTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="step1-tab" data-bs-toggle="tab" data-bs-target="#step1" type="button">
                        <span class="step-number">1</span> Thông tin nơi tổ chức
                    </button>
                </li>
                <span class="mx-3"><i class="bi bi-chevron-right"></i></span>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="step2-tab" type="button">
                        <span class="step-number">2</span> Zone - Vùng
                    </button>
                </li>
            </ul>

            <!-- Tab contents -->
            <div class="tab-content my-5">
                <!-- Step 1 -->
                <div class="tab-pane fade show active" id="step1" role="tabpanel">
                    <!-- FORM UPDATE PLACE -->
                    <form id="placeForm" action="placeform?action=update" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="placeID" value="${place.placeID}" />
                        <div class="row mb-3">
                            <div class="col">
                                <div class="drag-wrap">
                                    <label class="dropzone" id="dropzone">
                                        <input id="fileInput" type="file" accept="image/*" name="zoneImage">
                                        <div class="dz-content" id="dzContent">
                                            <i class="bi bi-upload"></i>
                                            <div>
                                                <strong>Nhấp để chọn ảnh mới (nếu muốn thay đổi)</strong>
                                            </div>
                                            <div class="preview" id="preview" style="display:block;">
                                                <img id="previewImg" src="${place.seatMapURL}" alt="preview" />
                                            </div>
                                        </div>
                                    </label>

                                    <div class="controls">
                                        <button class="btn" id="chooseBtn" style="display:none;">Chọn ảnh</button>
                                        <button class="btn ghost" id="clearBtn" style="display:none;">Xóa</button>
                                        <div class="small" id="status" style="margin: auto; color: red; font-size: 0.9em;"  data-is-update="${not empty place}"></div>
                                    </div>

                                    <div id="errorMsg" class="error" style="display:none; text-align: center; "></div>
                                </div>
                            </div>

                            <div class="col">
                                <div class="mb-3">
                                    <label for="placeName" class="form-label"><strong style="color: red;">* </strong>Tên địa điểm</label>
                                    <input type="text" class="form-control" id="eventName" name="placeName" value="${place.placeName}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="placeAddress" class="form-label"><strong style="color: red;">* </strong>Địa chỉ</label>
                                    <input type="text" class="form-control" id="placeAddress" name="placeAddress" required value="${place.address}">
                                </div>

                                <div class="mb-3">
                                    <label for="placeDescription" class="form-label"><strong style="color: red;">* </strong>Mô tả</label>
                                    <input type="text" class="form-control" id="placeDescription" name="placeDescription" required value="${place.description}">
                                </div>
                            </div>
                        </div>
                        <a href="place-overview" type="button" class="btn prev-btn">Quay lại</a>
                        <button type="submit" class="btn btn-primary next-btn">Tiếp theo</button>
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
    </body>

</html>
