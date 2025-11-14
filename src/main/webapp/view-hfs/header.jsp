<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!-- Bootstrap 5 -->

<!-- Font Awesome -->
<!--<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>-->
<!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>-->

<!-- Inter Font -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link href="<%= request.getContextPath()%>/css/navigationUI/header.css" rel="stylesheet" type="text/css"/>

<header class="header bg-pink py-2">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-auto">
                <a href="<%= request.getContextPath()%>/home">
                    <img src="<%= request.getContextPath()%>/img/LogoA_White.png" alt="ACTIVE" style="width:50px; border-radius:8px;">
                </a>
            </div>
            <div class="col">
                <form action="search" method="get" class="d-flex justify-content-center">
                    <input class="form-control me-2 w-50" 
                           type="text" 
                           name="keyword" 
                           placeholder="Bạn tìm gì hôm nay?" 
                           value="${keyword}">
                    <button class="btn btn-danger">Tìm kiếm</button>
                </form>
            </div>
            <div class="col-auto d-flex align-items-center gap-3">
                <a href="<%= request.getContextPath()%>/admincenter" class="btn btn-outline-light rounded-pill">
                    <i class="fa-solid fa-plus me-1"></i>Tạo sự kiện
                </a>

                <a href="<%= request.getContextPath()%>/myticket" class="btn btn-outline-light rounded-pill">
                    Vé của tôi
                </a>
                <div class="dropdown">
                    <button class="btn btn-outline-light rounded-pill dropdown-toggle" data-bs-toggle="dropdown">
                        <i class="fa-solid fa-user me-1"></i> Tài khoản
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/myticket"><i class="fa-solid fa-ticket me-2"></i>Vé của tôi</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/admincenter"><i class="fa-regular fa-star me-2"></i>Sự kiện của tôi</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/userinfo"><i class="fa-solid fa-user me-2"></i>Tài khoản</a></li>
                        <li><a class="dropdown-item" href="<%= request.getContextPath()%>/wallet"><i class="fa-solid fa-wallet me-2"></i>Ví</a></li>
                        <li><a class="dropdown-item text-danger" href="<%= request.getContextPath()%>/logout"><i class="fa-solid fa-right-from-bracket me-2"></i>Đăng xuất</a></li>
                    </ul>
                </div>

                <img src="<%= request.getContextPath()%>/img/VN.png" class="rounded" alt="VN" style="width:28px; height:20px;">
            </div>
        </div>
    </div>
</header>
