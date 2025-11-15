<%@page pageEncoding="UTF-8"%> 
<link href="<%= request.getContextPath()%>/css/navigationUI/footer.css" rel="stylesheet" type="text/css"/>
<footer class="footer mt-auto py-5">
    <div class="container">
        <div class="row gy-4">
            <!-- Cột 1 -->
            <div class="col-md-4">
                <h4>Hotline</h4>
                <p>Thứ 2 - Chủ Nhật (8:00 - 22:00)</p>
                <p class="text-pink fw-bold fs-5">070 490 6670</p>

                <h4>Email</h4>
                <p><a href="mailto:hclbfactive1420@gmail.com">hclbfactive1420@gmail.com</a></p>

                <h4>Văn phòng chính</h4>
                <p>600 Nguyễn Văn Cừ Nối Dài, An Bình, Bình Thủy, Cần Thơ</p>
            </div>

            <!-- Cột 2 -->
            <div class="col-md-4">
                <h4>Dành cho Khách hàng</h4>
                <p><a href="${pageContext.request.contextPath}/view-hfs/terms.jsp">Điều khoản sử dụng cho khách hàng</a></p>

                <h4>Dành cho Ban Tổ chức</h4>
                <p><a href="${pageContext.request.contextPath}/view-hfs/termsorganizer.jsp">Điều khoản sử dụng cho ban tổ chức</a></p>
            </div>

            <!-- Cột 3 -->
            <div class="col-md-4">
                <h4>Về công ty chúng tôi</h4>
                <ul class="list-unstyled mb-0">
                    <li><a href="${pageContext.request.contextPath}/view-hfs/regulation.jsp">Quy chế hoạt động</a></li>
                    <li><a href="${pageContext.request.contextPath}/view-hfs/privacy.jsp">Chính sách bảo mật thông tin</a></li>
                    <li><a href="${pageContext.request.contextPath}/view-hfs/complaint.jsp">Cơ chế giải quyết tranh chấp/ khiếu nại</a></li>
                    <li><a href="${pageContext.request.contextPath}/view-hfs/returnpolicy.jsp">Chính sách đổi trả và kiểm hàng</a></li>
                    <li><a href="${pageContext.request.contextPath}/view-hfs/shipping.jsp">Điều kiện vận chuyển và giao nhận</a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>