<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch sử giao dịch</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>        <style>
            body {
                background-color: #121212;
                color: #fff;
            }

            h2 {
                color: #ffb6b6;
                font-weight: bold;
            }

            .card {
                background-color: #1e1e1e;
                border: 1px solid #ffb84d;
                border-radius: 12px;
            }

            .table-custom {
                color: #fff;
                margin-bottom: 0;
            }

            .table-custom thead th {
                background-color: #ffb6b6;
                color: #000;
                font-weight: bold;
                text-align: center;
            }

            .table-custom tbody tr:hover {
                background-color: rgba(255, 184, 77, 0.1);
            }

            .table-custom td,
            .table-custom th {
                vertical-align: middle;
                text-align: center;
                border-color: #ffb6b6 !important;
            }

            .badge {
                font-size: 0.9rem;
                padding: 0.5em 0.75em;
            }

            .badge-deposit {
                background-color: #28a745;
            }
            .badge-payment {
                background-color: #dc3545;
            }
            .badge-refund {
                background-color: #17a2b8;
            }
            .badge-other {
                background-color: #6c757d;
            }
        </style>
    </head>
    <%@include file="/view-hfs/header.jsp" %>
    <%@include file="/view-ttk2008/sidebar-for-user.jsp" %>
    <body class="bg-dark">

        <div class="container my-5">
            <%%>
            <h2 class="text-center mb-4">Lịch sử giao dịch</h2>

            <div class="card shadow" style="border: none;">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <div style="padding: 10px; background-color: #fff; border-radius: 10px">
                            <table class="table table-hover mb-0">
                                <thead>
                                    <tr>
                                        <th>Mã giao dịch</th>
                                        <th>Loại giao dịch</th>
                                        <th>Số tiền</th>
                                        <th>Số dư còn lại</th>
                                        <th>Ngày giao dịch</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${transactions}">
                                        <tr>
                                            <td>${t.transactionID}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${t.transactionTypeID == 1}">
                                                        <span class="badge badge-deposit">Nạp tiền</span>
                                                    </c:when>
                                                    <c:when test="${t.transactionTypeID == 2}">
                                                        <span class="badge badge-payment">Thanh toán</span>
                                                    </c:when>
                                                    <c:when test="${t.transactionTypeID == 3}">
                                                        <span class="badge badge-refund">Hoàn tiền</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-other">Khác</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatNumber value="${t.amount}" type="currency" currencySymbol="" maxFractionDigits="0"/> ₫
                                            </td>
                                            <td><fmt:formatNumber value="${t.remain}" type="currency" currencySymbol="" maxFractionDigits="0"/> ₫</td>
                                            <td>${t.createdAt}</td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty transactions}">
                                        <tr>
                                            <td colspan="5" class="text-center text-muted py-4">
                                                <i>Không có giao dịch nào để hiển thị.</i>
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
