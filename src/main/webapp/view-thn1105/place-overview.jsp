<%-- 
    Document   : place-overview
    Created on : Nov 11, 2025, 10:47:15 AM
    Author     : BACH YEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lí nơi tổ chức</title>


        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/place-page-style/style.css">

    </head>

    <body>

        <div class="container">

            <h2 class="mt-5 text-center">
                DANH SÁCH NƠI TỔ CHỨC
            </h2>
            <p class="text-center">
                <a href="admincenter" class="btn prev-btn">Về trung tâm</a>
                <a href="event-form" class="btn btn-primary mx-3">Tạo sự kiện</a>
                <a href="placeform" class="btn btn-primary"> Tạo nơi tổ chức</a>
            </p>

            <div class="table-wrapper">
                <table class="table table-dark table-striped align-middle">
                    <thead>
                        <tr>
                            <th scope="col" class="text-center">ID</th>
                            <th scope="col">Địa điểm</th>
                            <th scope="col">Địa chỉ</th>
                            <th scope="col" class="text-center">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${places}">
                            <tr>
                                <th scope="row" class="text-center">${p.placeID}</th>
                                <td class="text-truncate">
                                    <a href="place-overview?action=detail&pid=${p.placeID}">
                                        <p class="truncate-cell">${p.placeName}</p>
                                    </a>
                                </td>
                                <td class="text-truncate">
                                    <p class="truncate-cell">${p.address}</p>
                                </td>
                                <td>
                                    <div class="text-center">
                                        <a href="placeform?action=update&pid=${p.placeID}" role="button" class="btn btn-warning">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        <a role="button" class="btn delete-btn"
                                           onclick="fnDeletePlace(${p.placeID}, '${fn:escapeXml(p.placeName)}')"
                                           data-bs-toggle="modal"
                                           data-bs-target="#deleteModal">
                                            <i class="bi bi-trash2"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>

        <!--DELETE MODAL-->
        <div class="modal fade" id="deleteModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="deleteModalLabel">Xóa nơi tổ chức</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="place-overview?action=delete" method="POST">
                        <div class="modal-body">
                            <input type="hidden" id="placeID-D" name="placeID"/>
                            <p>Xác nhận xóa nơi tổ chức: <span id="placeName-D"></span></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Xóa</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>

        <script>
                                               function fnDeletePlace(placeID, placeName) {
                                                   document.getElementById("placeID-D").value = placeID;
                                                   document.getElementById("placeName-D").textContent = placeName;
                                               }
        </script>
    </body>

</html>
