<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Models.ttk2008.DepositPromotion"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán VNPay</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            body {
                color: #fff;
                font-family: "Inter", sans-serif;
            }
            .card {
                background-color: #1e1e1e;
                border: 1px solid #ffb6b6;
                border-radius: 12px;
            }
            .form-label {
                font-weight: 600;
                color: #ffb6b6;
            }
            .form-control-deposit {
                background-color: #2a2a2a;
                color: #fff;
                border: 1px solid #555;
                width: 100%;
                padding: 12px;
                font-size: 1rem;
            }
            .form-control-deposit:focus {
                border-color: #ffb6b6;
                box-shadow: 0 0 0 0.2rem rgba(255,184,77,0.12);
                outline: none;
            }
            .btn-pay {
                background-color: #ffb6b6;
                color: #000;
                font-weight: 600;
                transition: all 0.2s;
            }
            .btn-pay:hover {
                background-color: #ff6b6b;
                color: #fff;
            }
            h2 {
                color: #ffb6b6;
                font-weight: bold;
            }
            .bonus-text {
                color: #7CFC00;
                font-weight: bold;
            }
            select.promo-list-select {
                background-color: #2a2a2a !important;
                color: #fff !important;
                border: 1px solid #555 !important;
                cursor: not-allowed;
            }
            option.applied {
                background-color: rgba(124,252,0,0.12);
                font-weight: 600;
            }
            .promo-note {
                font-size: 0.95rem;
                color: #cfcfcf;
                margin-bottom: .5rem;
            }
        </style>
    </head>
    <body class="bg-dark">
        <%@ include file="/view-hfs/header.jsp" %>

        <div class="container d-flex align-items-center justify-content-center min-vh-100">
            <div class="card shadow p-4 w-100" style="max-width: 900px;">
                <h2 class="text-center mb-3">Nạp tiền qua VNPay</h2>

                <form action="payment" method="post" id="depositForm" autocomplete="off">
                    <!-- Số tiền -->
                    <div class="mb-3">
                        <label for="amount" class="form-label">Số tiền (VND)</label>
                        <input type="number" id="amount" name="amount"
                               class="form-control-deposit form-control"
                               value="100000" min="50000" max="5000000" required>
                        <div class="form-text text-light opacity-80">
                            Tối thiểu 50.000đ - Tối đa 5.000.000đ
                        </div>
                    </div>

                    <!-- Danh sách khuyến mãi -->
                    <div class="mb-3">
                        <label class="form-label">Khuyến mãi hiện có</label>
                        <div class="promo-note">Hệ thống sẽ tự áp dụng khuyến mãi phù hợp với số tiền bạn nhập.</div>
                        <select id="promoSelect" class="form-control-deposit promo-list-select form-select" size="5" disabled>
                            <%
                                List<DepositPromotion> promoList = (List<DepositPromotion>) request.getAttribute("promotions");
                                if (promoList != null && !promoList.isEmpty()) {
                                    for (DepositPromotion p : promoList) {
                                        // Chỉ hiển thị promotion active
                                        if ("active".equalsIgnoreCase(p.getStatusCode())) {
                                            String maxLabel = (p.getMaxAmount() != null) ? p.getMaxAmount().toPlainString() : "∞";
                            %>
                            <option 
                                data-min="<%=p.getMinAmount().toPlainString()%>" 
                                data-max="<%= (p.getMaxAmount() != null ? p.getMaxAmount().toPlainString() : "0")%>" 
                                data-discount="<%=p.getDiscountPercent().toPlainString()%>"
                                value="<%=p.getPromotionID()%>">
                                <%= p.getMinAmount().toPlainString()%> - <%= maxLabel%> : +<%= p.getDiscountPercent().toPlainString()%>% bonus
                            </option>
                            <%
                                    }
                                }
                            } else {
                            %>
                            <option>Hiện chưa có khuyến mãi</option>
                            <%
                                }
                            %>
                        </select>
                    </div>

                    <!-- Bonus preview -->
                    <div class="mb-3">
                        <span class="bonus-text" id="bonusPreview">Bonus: 0 VND</span>
                        <input type="hidden" id="promotionAmount" name="promotionAmount" value="0">
                    </div>

                    <input type="hidden" id="orderInfo" name="orderInfo" value="Nạp tiền vào ví">
                    <button type="submit" class="btn btn-pay w-100 py-2">Thanh toán VNPay</button>
                </form>
            </div>
        </div>

        <script>
            (function () {
                const amountInput = document.getElementById('amount');
                const promoSelect = document.getElementById('promoSelect');
                const bonusPreview = document.getElementById('bonusPreview');

                // Lấy danh sách promotion từ select
                function readPromotions() {
                    const promos = [];
                    for (let i = 0; i < promoSelect.options.length; i++) {
                        const opt = promoSelect.options[i];
                        if (opt.dataset && opt.dataset.min !== undefined) {
                            promos.push({
                                min: parseFloat(opt.dataset.min),
                                max: parseFloat(opt.dataset.max) || 0,
                                discount: parseFloat(opt.dataset.discount),
                                index: i
                            });
                        }
                    }
                    return promos;
                }

                const promotions = readPromotions();

                // Tính bonus cao nhất
                function getBestBonus(amount) {
                    let bestBonus = 0, bestIndex = -1;
                    promotions.forEach(p => {
                        if (amount >= p.min && (p.max === 0 || amount <= p.max)) {
                            const candidate = Math.floor(amount * p.discount / 100);
                            if (candidate > bestBonus) {
                                bestBonus = candidate;
                                bestIndex = p.index;
                            }
                        }
                    });
                    return {bonus: bestBonus, index: bestIndex};
                }

                // Cập nhật UI bonus và highlight option
                function updateUI() {
                    const amount = parseInt(amountInput.value) || 0;
                    const res = getBestBonus(amount);

                    // Cập nhật text hiển thị
                    bonusPreview.textContent = "Bonus: " + res.bonus.toLocaleString('vi-VN') + " VND";

                    // Cập nhật data-value để submit
                    bonusPreview.setAttribute('data-value', res.bonus);

                    // highlight promotion option
                    for (let i = 0; i < promoSelect.options.length; i++) {
                        promoSelect.options[i].classList.remove('applied');
                        promoSelect.options[i].selected = false;
                    }
                    if (res.index !== -1) {
                        const appliedOpt = promoSelect.options[res.index];
                        appliedOpt.classList.add('applied');
                        appliedOpt.selected = true;
                        appliedOpt.scrollIntoView({block: 'nearest'});
                    }
                    document.getElementById('promotionAmount').value = res.bonus;
                }

                amountInput.addEventListener('input', updateUI);
                updateUI();

                // Extra guard submit
                document.getElementById('depositForm').addEventListener('submit', function (ev) {
                    const v = parseInt(amountInput.value) || 0;
                    if (v < 50000) {
                        ev.preventDefault();
                        alert('Số tiền nạp tối thiểu là 50.000 VND');
                    }
                });
            })();
        </script>
    </body>
</html>
