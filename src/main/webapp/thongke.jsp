<%-- 
    Document   : thongke
    Created on : Oct 2, 2025, 7:56:04 AM
    Author     : NGUYEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Dashboard - Doanh thu</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="./css/style.css">
  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body>
  <div class="container py-4">

    <!-- Thông tin sự kiện -->
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div>
        <i class="bi bi-calendar-event"></i> 30 Tháng 09, 2025 - 09:00
      </div>
      <button class="btn btn-outline-success btn-sm">
        <i class="bi bi-arrow-repeat"></i> Đổi suất diễn
      </button>
    </div>

    <!-- Doanh thu -->
    <h5>Doanh thu</h5>

    <!-- Tổng quan -->
    <div class="section-title">Tổng quan</div>
    <div class="row g-3">
      <!-- Box Doanh thu -->
      <div class="col-md-6">
        <div class="card d-flex flex-row align-items-center">
          <div>
            <div class="fw-bold">Doanh thu</div>
            <div>0đ</div>
            <div>Tổng: 0đ</div>
          </div>
          <div class="circle">0%</div>
        </div>
      </div>

      <!-- Box Số vé đã bán -->
      <div class="col-md-6">
        <div class="card d-flex flex-row align-items-center">
          <div>
            <div class="fw-bold">Số vé đã bán</div>
            <div>0 vé</div>
            <div>Tổng: 30 vé</div>
          </div>
          <div class="circle">0%</div>
        </div>
      </div>
    </div>

    <!-- Chi tiết -->
    <div class="section-title">Chi tiết</div>
    <h6>Vé đã bán</h6>

    <div class="table-responsive">
      <table class="table table-dark table-bordered align-middle text-center">
        <thead>
          <tr>
            <th>Loại vé</th>
            <th>Giá bán</th>
            <th>Đã bán</th>
            <th>Bị khoá</th>
            <th>Tỉ lệ bán</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Standard</td>
            <td>0đ</td>
            <td>0 / 30</td>
            <td>0</td>
            <td>
              <div class="d-flex align-items-center">
                <div class="progress flex-grow-1 me-2">
                  <div class="progress-bar" style="width: 0%"></div>
                </div>
                <span>0%</span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
