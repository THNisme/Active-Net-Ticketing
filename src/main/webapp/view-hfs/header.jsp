<%@page contentType="text/html" pageEncoding="UTF-8"%> 

<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
<!--Inter font - Google Fonts-->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
    href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
    rel="stylesheet">

<link href="<%= request.getContextPath()%>/css/navigationUI/header.css" rel="stylesheet" type="text/css"/>

<header class="header"> 
    <div class="logo">
        <img src="<%= request.getContextPath()%>/img/LogoA_White.png" alt="ACTIVE">
    </div>

    <div class="search-box">
        <input type="text" placeholder="Bạn tìm gì hôm nay?">
        <button>Tìm kiếm</button>
    </div>

    <div class="header-right">
        <a href="<%= request.getContextPath()%>/createEvent.jsp" class="create-event-box">
            <i class="fa-solid fa-plus"></i>Tạo sự kiện
        </a>

        <a href="<%= request.getContextPath()%>/myticket" class="active-net-box">
            Vé của tôi
        </a>



        <div class="account-box">
            <button class="account-btn">
                <i class="fa-solid fa-user"></i>Tài khoản 
            </button>
            <div class="dropdown-menu">
                <a href="<%= request.getContextPath()%>/myticket">
                    <i class="fa-solid fa-ticket"></i> Vé của tôi
                </a>
                <a href="<%= request.getContextPath()%>/myEvents.jsp">
                    <i class="fa-regular fa-star"></i>Sự kiện của tôi
                </a>
                <a href="<%= request.getContextPath()%>/userinfo">
                    <i class="fa-solid fa-user"></i>Tài khoản
                </a>
                <a href="<%= request.getContextPath()%>/logout">
                    <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
                </a>
            </div>
        </div>

        <img src="<%= request.getContextPath()%>/img/VN.png" class="flag" alt="VN">
    </div>
</header>

<script>
    document.querySelector('.account-btn').addEventListener('click', function (e) {
        e.stopPropagation();
        document.querySelector('.account-box').classList.toggle('show');
    });
    document.addEventListener('click', function (e) {
        const accountBox = document.querySelector('.account-box');
        if (!accountBox.contains(e.target)) {
            accountBox.classList.remove('show');
        }
    });
</script>
