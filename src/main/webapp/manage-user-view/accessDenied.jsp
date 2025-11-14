<%-- 
    Document   : accessDenied
    Created on : 13 Nov 2025, 10.48.41
    Author     : Acer
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Access Denied</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex vh-100 justify-content-center align-items-center">

    <div class="text-center">
        <h1 class="text-danger fw-semibold display-3 mb-3">⚠️ Access Denied</h1>
        <p class="fs-5 mb-4">Bạn không có quyền truy cập trang này.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-danger btn-sm ">Quay về Home</a>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>



