<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán VNPay</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background-color: #121212;
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

            .form-control {
                background-color: #2a2a2a;
                color: #fff;
                border: 1px solid #555;
            }

            .form-control:focus {
                border-color: #ffb6b6;
                box-shadow: 0 0 0 0.2rem rgba(255, 184, 77, 0.25);
            }

            .btn-pay {
                background-color: #ffb6b6;
                color: #000;
                font-weight: 600;
                transition: all 0.3s;
            }

            .btn-pay:hover {
                background-color: #ff6b6b;
                color: #fff;
            }

            h2 {
                color: #ffb6b6;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <%@include file="/view-hfs/header.jsp" %>

        <div class="container d-flex align-items-center justify-content-center min-vh-100">
            <div class="card shadow p-5 w-100" style="max-width: 900px;">
                <h2 class="text-center mb-4">Nạp tiền qua VNPay</h2>

                <form action="payment" method="post">
                    <div class="mb-4">
                        <label for="amount" class="form-label">Số tiền (VND)</label>
                        <input 
                            type="number" 
                            id="amount" 
                            name="amount" 
                            class="form-control form-control-lg" 
                            value="100000" 
                            min="50000" 
                            max="5000000" 
                            required>
                        <div class="form-text text-light opacity-90">
                            Tối thiểu 50.000đ - Tối đa 5.000.000đ
                        </div>
                    </div>

                    <div class="mb-4">
                        <label for="orderInfo" class="form-label">Thông tin đơn hàng</label>
                        <input 
                            type="text" 
                            id="orderInfo" 
                            name="orderInfo" 
                            class="form-control form-control-lg" 
                            value="Nạp tiền vào ví" 
                            required>
                    </div>

                    <button type="submit" class="btn btn-pay w-100 py-2">
                        Thanh toán VNPay
                    </button>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
