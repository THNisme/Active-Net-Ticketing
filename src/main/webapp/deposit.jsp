<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán VNPay</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
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
                padding: 15px 15px;
                font-size: 1.6rem;
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

    <body class="bg-dark">
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
                            class="form-control-deposit form-control-deposit-lg" 
                            value="100000" 
                            min="50000" 
                            max="5000000" 
                            required>
                        <div class="form-text text-light opacity-90">
                            Tối thiểu 50.000đ - Tối đa 5.000.000đ
                        </div>
                    </div>

                    <div class="mb-4">
                        <input 
                            type="hidden" 
                            id="orderInfo" 
                            name="orderInfo" 
                            class="form-control form-control-lg" 
                            value="Nạp tiền vào ví" 
                            >
                    </div>

                    <button type="submit" class="btn btn-pay w-100 py-2">
                        Thanh toán VNPay
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
