<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String currentPage = request.getRequestURI();
%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

<nav class="navbar navbar-dark bg-dark">
  <div class="container-fluid">

    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
      aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="offcanvas offcanvas-start text-bg-dark" tabindex="-1" id="offcanvasNavbar"
      aria-labelledby="offcanvasNavbarLabel">
      <div class="offcanvas-header border-bottom">
        <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Menu</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>

      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-start flex-grow-1 pe-3">
          <li class="nav-item">
              <a class="nav-link <%= currentPage.contains("/userinfo") ? "active" : "" %>" href="<%= request.getContextPath()%>/userinfo">
              <i class="fa-solid fa-user me-2"></i> Thông tin tài khoản
            </a>
          </li>
          <li class="nav-item">
              <a class="nav-link <%= currentPage.contains("/myticket") ? "active" : "" %>" href="<%= request.getContextPath()%>/myticket">
              <i class="fa-solid fa-ticket me-2 mt-3"></i> Vé của tôi
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link <%= currentPage.contains("#") ? "active" : "" %>" href="#">
              <i class="fa-solid fa-wallet me-2 mt-3"></i> Ví của tôi
            </a>
          </li>
          
        </ul>
      </div>
    </div>
  </div>
</nav>