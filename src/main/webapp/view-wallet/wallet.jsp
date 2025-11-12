<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Models.ttk2008.Wallet, java.text.NumberFormat, java.util.Locale" %>
<%
    Wallet wallet = (Wallet) request.getAttribute("wallet");
    String error = (String) request.getAttribute("error");
    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Ví của tôi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <link href="<%= request.getContextPath()%>/css/wallet-page/wallet.css" rel="stylesheet" type="text/css"/>
       
    </head>

    <%@include file="/view-hfs/header.jsp" %>

    <body>
        <div class="container my-5">
            <div >
             
                <!-- Content -->
                <div >
                    <div class="wallet-card">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <div>
                                <div class="wallet-title">Ví của tôi</div>
                                <div class="meta-small">Quản lý số dư & lịch sử giao dịch</div>
                            </div>
                            <div>
                                <%-- optional quick action --%>
                                <a href="<%= request.getContextPath() %>/deposit.jsp" class="btn btn-sm btn-accent">
                                    <i class="fa-solid fa-plus me-1"></i> Nạp tiền
                                </a>
                            </div>
                        </div>

                        <% if (error != null) { %>
                        <div class="alert alert-danger"><%= error %></div>
                        <% } else if (wallet != null) { %>
                        <div class="wallet-info text-center">
                            <p class="meta-small"><strong>Mã ví:</strong> <%= wallet.getWalletID() %></p>

                            <p class="balance"><strong>Số dư hiện tại: </strong>
                               <span style="color: #00ff00;"><%= currencyFormat.format(wallet.getBalance()) %> ₫</span>
                            </p>

                            <p class="meta-small"><strong>Cập nhật lần cuối:</strong> <%= wallet.getLastUpdated() %></p>

                            <div class="d-flex justify-content-center gap-3 mt-4">
                                <a href="<%= request.getContextPath() %>/deposit.jsp" class="btn btn-accent">
                                    <i class="fa-solid fa-arrow-up-from-bracket me-2"></i> Nạp thêm tiền
                                </a>
                                <a href="<%= request.getContextPath() %>/transactions" class="btn btn-outline-accent">
                                    <i class="fa-solid fa-clock-rotate-left me-2"></i> Xem lịch sử
                                </a>
                            </div>
                        </div>
                        <% } else { %>
                        <div class="alert alert-warning text-center">
                            Không tìm thấy thông tin ví.
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
