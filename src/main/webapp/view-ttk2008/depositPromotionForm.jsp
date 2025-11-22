<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Models.ttk2008.DepositPromotion promotion = (Models.ttk2008.DepositPromotion) request.getAttribute("promotion");
    boolean isEdit = (promotion != null);
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Sửa Khuyến mãi Nạp tiền" : "Thêm Khuyến mãi Nạp tiền"%></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: #0b1220;
                color: #e5e7eb;
                font-family: Arial, sans-serif;
                padding: 20px;
            }
            h3 {
                text-align: center;
                margin-bottom: 20px;
            }
            .card {
                background: #1f2937;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 0 15px rgba(0,0,0,0.4);
            }
            .form-label {
                font-weight: 500;
                color: #d1d5db;
            }
            .btn-green {
                background: #22C55E;
                color: #fff;
            }
            .btn-green:hover {
                background: #16a34a;
                color: #fff;
            }
            .row > .col-md-6 {
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>

        <div class="col-md-6 mx-auto mt-5">
            <h3><%= isEdit ? "Sửa Khuyến mãi Nạp tiền" : "Thêm Khuyến mãi Nạp tiền"%></h3>

            <!-- Container hiển thị lỗi -->
            <div id="errorContainer"></div>

            <div class="card">
                <form action="<%= request.getContextPath()%>/promotions" method="post">
                    <% if (isEdit) {%>
                    <input type="hidden" name="promotionID" value="<%= promotion.getPromotionID()%>">
                    <% }%>

                    <div class="row">
                        <div class="col-md-6">
                            <label class="form-label">Số tiền tối thiểu</label>
                            <input type="number" name="minAmount" class="form-control" min="0" step="1000" id="minAmount">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Số tiền tối đa</label>
                            <input type="number" name="maxAmount" class="form-control" min="0" max="5000000" step="1000" id="maxAmount" placeholder="Để trống nếu không giới hạn">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label class="form-label">Phần trăm thưởng (%)</label>
                            <input type="number" name="discountPercent" class="form-control" min="0" max="50" id="discountPercent">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Trạng thái</label>
                            <select name="statusCode" class="form-select" id="statusCode">
                                <option value="">-- Chọn trạng thái --</option>
                                <option value="ACTIVE">Hoạt động</option>
                                <option value="INACTIVE">Ngừng hoạt động</option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label class="form-label">Ngày bắt đầu</label>
                            <input type="date" name="startDate" class="form-control" id="startDate">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Ngày kết thúc</label>
                            <input type="date" name="endDate" class="form-control" id="endDate">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-green w-100 mt-4 mb-2">
                        <%= isEdit ? "Cập nhật" : "Thêm mới"%>
                    </button>
                    <a href="<%= request.getContextPath()%>/promotions?action=list" class="btn btn-secondary w-100 mt-2">Quay lại</a>
                </form>
            </div>
        </div>

        <script>
            // Fill dữ liệu nếu đang edit
            <% if (isEdit) {%>
            document.getElementById('minAmount').value = "<%= promotion.getMinAmount()%>";
            document.getElementById('maxAmount').value = "<%= promotion.getMaxAmount() != null ? promotion.getMaxAmount() : ""%>";
            document.getElementById('discountPercent').value = "<%= promotion.getDiscountPercent()%>";
            document.getElementById('statusCode').value = "<%= promotion.getStatusCode()%>";
            document.getElementById('startDate').value = "<%= promotion.getStartDate().toLocalDateTime().toLocalDate()%>";
            document.getElementById('endDate').value = "<%= promotion.getEndDate().toLocalDateTime().toLocalDate()%>";
            <% }%>

            // Validation trước submit
            document.querySelector('form').addEventListener('submit', function (ev) {
                const minAmount = document.getElementById('minAmount').value;
                const maxAmountInput = document.getElementById('maxAmount').value;
                const discount = document.getElementById('discountPercent').value;
                const statusCode = document.getElementById('statusCode').value;
                const startDate = document.getElementById('startDate').value;
                const endDate = document.getElementById('endDate').value;

                const errors = [];

                // Không được để trống
                if (!minAmount)
                    errors.push("Số tiền tối thiểu không được để trống");
                if (!discount)
                    errors.push("Phần trăm thưởng không được để trống");
                if (!statusCode)
                    errors.push("Trạng thái phải chọn");
                if (!startDate)
                    errors.push("Ngày bắt đầu không được để trống");
                if (!endDate)
                    errors.push("Ngày kết thúc không được để trống");

                // Parse số nếu có
                const min = parseFloat(minAmount) || 0;
                const max = maxAmountInput ? parseFloat(maxAmountInput) : 0;
                const disc = parseFloat(discount) || 0;

                if (maxAmountInput && max < min)
                    errors.push("Số tiền tối đa phải >= số tiền tối thiểu");
                if (max > 5000000)
                    errors.push("Số tiền tối đa không được vượt quá 5.000.000");
                if (disc < 0 || disc > 50)
                    errors.push("Phần trăm thưởng phải từ 0 đến 50");
                if (startDate && endDate && startDate > endDate)
                    errors.push("Ngày bắt đầu phải trước hoặc bằng ngày kết thúc");

                // Hiển thị lỗi dạng bảng
                const errorContainer = document.getElementById('errorContainer');
                errorContainer.innerHTML = "";
                if (errors.length > 0) {
                    ev.preventDefault();
                    const ul = document.createElement('ul');
                    ul.classList.add('alert', 'alert-danger');
                    errors.forEach(e => {
                        const li = document.createElement('li');
                        li.textContent = e;
                        ul.appendChild(li);
                    });
                    errorContainer.appendChild(ul);
                    window.scrollTo({top: 0, behavior: 'smooth'});
                    return false;
                }
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
