<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <link href="<%= request.getContextPath()%>/css/navigationUI/header.css" rel="stylesheet" type="text/css" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />

  <!-- ✅ Header User + Admin (Fragment) -->
  <header class="header">
    <!-- Logo -->
    <div class="logo">
      <img src="<%= request.getContextPath()%>/img/LogoA_White.png" alt="ACTIVE">
    </div>

    <!-- Ô tìm kiếm -->
    <div class="search-box">
      <form action="<%= request.getContextPath()%>/search" method="get" class="search-form">
        <input type="text" name="keyword" placeholder="Bạn tìm gì hôm nay?" value="<%= request.getAttribute(" keyword")
          !=null ? request.getAttribute("keyword") : "" %>">
        <button type="submit">Tìm kiếm</button>
      </form>
    </div>

    <!-- Khu vực bên phải -->
    <div class="header-right">
      <!-- ➕ Tạo sự kiện -->
      <a href="<%= request.getContextPath()%>/createEvent.jsp" class="create-event-box">
        <i class="fa-solid fa-plus"></i>Tạo sự kiện
      </a>

      <!-- 🎟 Vé của tôi -->
      <a href="<%= request.getContextPath()%>myticket.jsp" class="active-net-box">
        Vé của tôi
      </a>



      <!-- 👤 Tài khoản -->
      <div class="account-box">
        <button class="account-btn">
          <i class="fa-solid fa-user"></i>Tài khoản
        </button>
        <div class="dropdown-menu">
          <a href="<%= request.getContextPath()%>/myticket.jsp">
            <i class="fa-solid fa-ticket"></i> Vé của tôi
          </a>
          <a href="<%= request.getContextPath()%>/myEvents.jsp">
            <i class="fa-regular fa-star"></i>Sự kiện của tôi
          </a>
          <a href="<%= request.getContextPath()%>/myAccount.jsp">
            <i class="fa-solid fa-user"></i>Tài khoản của tôi
          </a>
          <a href="<%= request.getContextPath()%>/logout">
            <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất
          </a>
        </div>
      </div>

      <!-- Cờ Việt Nam -->
      <img src="<%= request.getContextPath()%>/img/VN.png" class="flag" alt="VN">
    </div>
  </header>

  <!-- JS xử lý dropdown menu tài khoản -->
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