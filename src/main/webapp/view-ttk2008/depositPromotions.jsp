<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="promotions" value="${requestScope.promotions}" />
<c:if test="${promotions == null}">
    <c:set var="promotions" value="${[]}" />
</c:if>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Khuyến mãi Nạp tiền</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <style>
            body {
                background:#0b1220;
                color:#fff;
                font-family:'Inter',sans-serif;
                padding:20px;
            }
            .card {
                background:#111827;
                border: none;
            }
            .table thead th {
                background:#1f2937;
                color:#fff;
            }
            .btn-pink {
                background:#22C55E;
                color:white;
            }
            .badge-active {
                background:#16a34a;
                color:white;
                padding:6px 8px;
                border-radius:6px;
            }
            .badge-inactive {
                background:#6b7280;
                color:white;
                padding:6px 8px;
                border-radius:6px;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3>Quản lý Khuyến mãi Nạp tiền</h3>
                <div class="mt-5 d-flex" style="justify-content: flex-end; gap: 15px;">
                    <a href="${pageContext.request.contextPath}/admincenter" class="btn btn-pink">Về trang chủ</a>
                    <a href="${pageContext.request.contextPath}/promotions?action=create" class="btn btn-pink">Thêm khuyến mãi mới</a>
                </div>
            </div>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">${requestScope.error}</div>
            </c:if>

            <div class="card p-3">
                <div class="table-responsive">
                    <table class="table table-hover text-white align-middle">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Số tiền tối thiểu</th>
                                <th>Số tiền tối đa</th>
                                <th>Phần trăm thưởng (%)</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Trạng thái</th>
                                <th style="width:160px">Hành động</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="p" items="${promotions}">
                                <tr>
                                    <td>${p.promotionID}</td>
                                    <td>
                                        <fmt:formatNumber value="${p.minAmount}" type="number" groupingUsed="true" maxFractionDigits="0"/> ₫
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${p.maxAmount != null}">
                                                <fmt:formatNumber value="${p.maxAmount}" type="number" groupingUsed="true" maxFractionDigits="0"/> ₫
                                            </c:when>
                                            <c:otherwise>—</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>${p.discountPercent}%</td>
                                    <td><fmt:formatDate value="${p.startDate}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${p.endDate}" pattern="dd/MM/yyyy"/></td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${p.statusCode == 'ACTIVE'}">
                                                <span class="badge-active">Hoạt động</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-inactive">Ngừng hoạt động</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <a class="btn btn-sm btn-warning" 
                                           href="${pageContext.request.contextPath}/promotions?action=edit&id=${p.promotionID}">
                                            Sửa
                                        </a>

                                        <button class="btn btn-sm btn-danger"
                                                onclick="deletePromotion(${p.promotionID})">
                                            Xóa
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty promotions}">
                                <tr><td colspan="8" class="text-center">Không có khuyến mãi</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- SweetAlert2 Delete -->
        <script>
            function deletePromotion(id) {
                Swal.fire({
                    title: "Xác nhận vô hiệu hóa?",
                    text: "Bạn có chắc muốn vô hiệu hóa khuyến mãi này không?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Xóa",
                    cancelButtonText: "Hủy"
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href =
                                "${pageContext.request.contextPath}/promotions?action=delete&id=" + id;
                    }
                });
            }
        </script>

    </body>
</html>
