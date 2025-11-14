/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.nttn0902;

import DAOs.nttn0902.OrderManagementDAO;
import Models.nttn0902.OrderManagement;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// ====== Apache POI ======
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author NGUYEN
 */
@WebServlet(name = "ExportOrderExcelServlet", urlPatterns = {"/export_order"})
public class ExportOrderExcelServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExportOrderExcelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportOrderExcelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setHeader("Content-Disposition", "attachment; filename=BaoCaoDonHang.xlsx");

    OrderManagementDAO dao = new OrderManagementDAO();
    List<OrderManagement> orders;

    // ️ ĐÚNG THEO SERVLET TRƯỚC: dùng "eventID"
    String eventIdParam = request.getParameter("eventID");

    int eventId = -1;
    if (eventIdParam != null && !eventIdParam.isEmpty()) {
        try {
            eventId = Integer.parseInt(eventIdParam);

            // ️ giống y hệt servlet trước:
            orders = dao.getAllOrdersByEventId(eventId);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            orders = dao.getAllOrders(); // fallback giống servlet trước
        }
    } else {
        // ⚠️ không có eventID → lấy tất cả
        orders = dao.getAllOrders();
    }

    // ⚠️ Lấy tên sự kiện giống servlet OrderManagementServlet
    String eventName = dao.getEventNameById(eventId);
    if (eventName == null || eventName.trim().isEmpty()) {
        eventName = "TẤT CẢ SỰ KIỆN";
    }

    // === PHẦN TẠO EXCEL GIỮ NGUYÊN ===
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Báo cáo đơn hàng");

    // ====== STYLE ======
    CellStyle titleStyle = workbook.createCellStyle();
    Font titleFont = workbook.createFont();
    titleFont.setBold(true);
    titleFont.setFontHeightInPoints((short) 16);
    titleStyle.setFont(titleFont);
    titleStyle.setAlignment(HorizontalAlignment.CENTER);

    CellStyle dateStyle = workbook.createCellStyle();
    Font dateFont = workbook.createFont();
    dateFont.setItalic(true);
    dateFont.setFontHeightInPoints((short) 11);
    dateStyle.setFont(dateFont);
    dateStyle.setAlignment(HorizontalAlignment.CENTER);

    CellStyle headerStyle = workbook.createCellStyle();
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setColor(IndexedColors.WHITE.getIndex());
    headerStyle.setFont(headerFont);
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setBorderBottom(BorderStyle.THIN);
    headerStyle.setBorderTop(BorderStyle.THIN);
    headerStyle.setBorderLeft(BorderStyle.THIN);
    headerStyle.setBorderRight(BorderStyle.THIN);
    headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    CellStyle dataStyle = workbook.createCellStyle();
    dataStyle.setAlignment(HorizontalAlignment.CENTER);
    dataStyle.setBorderBottom(BorderStyle.THIN);
    dataStyle.setBorderTop(BorderStyle.THIN);
    dataStyle.setBorderLeft(BorderStyle.THIN);
    dataStyle.setBorderRight(BorderStyle.THIN);

    CellStyle currencyStyle = workbook.createCellStyle();
    currencyStyle.cloneStyleFrom(dataStyle);
    DataFormat format = workbook.createDataFormat();
    currencyStyle.setDataFormat(format.getFormat("#,##0 \"₫\""));

    // ====== TIÊU ĐỀ BÁO CÁO ======
    Row titleRow = sheet.createRow(0);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue("BÁO CÁO ĐƠN HÀNG CỦA " + "eventName".toUpperCase());
    titleCell.setCellStyle(titleStyle);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

    // ====== NGÀY GIỜ XUẤT ======
    Row dateRow = sheet.createRow(1);
    Cell dateCell = dateRow.createCell(0);
    String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    dateCell.setCellValue("Ngày xuất báo cáo: " + currentDate);
    dateCell.setCellStyle(dateStyle);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

    // ====== HEADER CỘT ======
    int startRow = 3;
    String[] columns = {"Mã đơn hàng", "Ngày tạo đơn", "Người mua", "Email", "Số điện thoại", "Giá trị đơn"};
    Row headerRow = sheet.createRow(startRow);
    for (int i = 0; i < columns.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(columns[i]);
        cell.setCellStyle(headerStyle);
    }

    // ====== ĐỔ DỮ LIỆU ======
    int rowIdx = startRow + 1;
    for (OrderManagement o : orders) {
        Row row = sheet.createRow(rowIdx++);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue(o.getOrderId());
        cell0.setCellStyle(dataStyle);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue(o.getOrderDate() != null ? o.getOrderDate().toString() : "");
        cell1.setCellStyle(dataStyle);

        Cell cell2 = row.createCell(2);
        cell2.setCellValue(o.getContactFullname());
        cell2.setCellStyle(dataStyle);

        Cell cell3 = row.createCell(3);
        cell3.setCellValue(o.getContactEmail());
        cell3.setCellStyle(dataStyle);

        Cell cell4 = row.createCell(4);
        cell4.setCellValue(o.getContactPhone());
        cell4.setCellStyle(dataStyle);

        Cell cell5 = row.createCell(5);
        try {
            cell5.setCellValue(o.getTotalAmount().doubleValue());
        } catch (Exception e) {
            cell5.setCellValue(0);
        }
        cell5.setCellStyle(currencyStyle);
    }

    for (int i = 0; i < columns.length; i++) {
        sheet.autoSizeColumn(i);
    }

    try (OutputStream out = response.getOutputStream()) {
        workbook.write(out);
    }
    workbook.close();
}
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
