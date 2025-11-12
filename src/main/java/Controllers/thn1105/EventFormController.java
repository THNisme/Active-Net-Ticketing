/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.thn1105;

import DAOs.thn1105.EventCategoryDAO;
import DAOs.thn1105.PlaceDAO;
import Models.thn1105.EventCategory;
import Models.thn1105.Place;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import Models.thn1105.Event; // We will create this model class next
import DAOs.thn1105.EventDAO; // We will create this DAO next
import DAOs.thn1105.TicketTypeDAO;
import DAOs.thn1105.ZoneDAO;
import Models.thn1105.TicketType;
import Models.thn1105.Zone;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(name = "EventFormController", urlPatterns = {"/event-form"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)

public class EventFormController extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads/event_images";

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
            out.println("<title>Servlet EventFormController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventFormController at " + request.getContextPath() + "</h1>");
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
        EventDAO eDao = new EventDAO();
        EventCategoryDAO eventCateDAO = new EventCategoryDAO();
        PlaceDAO placeDAO = new PlaceDAO();

        String action = request.getParameter("action");

        if (action == null) {
            List<EventCategory> eventCateList = eventCateDAO.getAll();
            List<Place> placeList = placeDAO.getAll();

            request.setAttribute("eventCateList", eventCateList);
            request.setAttribute("placeList", placeList);

            request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            try (PrintWriter out = response.getWriter()) {

                String eIDstr = request.getParameter("eid");
                int eid = Integer.parseInt(eIDstr);

                Event e = eDao.getById(eid);
                List<EventCategory> eventCateList = eventCateDAO.getAll();
                List<Place> placeList = placeDAO.getAll();

                System.out.println("In event-form/update, current EID " + eid);

                request.setAttribute("event", e);
                request.setAttribute("eventCateList", eventCateList);
                request.setAttribute("placeList", placeList);
                request.getRequestDispatcher("/view-thn1105/event-form-update.jsp").forward(request, response);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            handleDeleteEvent(request, response, eDao);
        }
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
        EventDAO edao = new EventDAO();
        String action = request.getParameter("action");

        if (action == null) {
            action = "create";
        }

        if (action.equalsIgnoreCase("create")) {
            handleCreateEvent(request, response, edao);

        } else if (action.equalsIgnoreCase("update")) {
            handleUpdateEvent(request, response, edao);

        } else if (action.equalsIgnoreCase("delete")) {
            handleDeleteEvent(request, response, edao);
        }
    }

    private void handleCreateEvent(HttpServletRequest request, HttpServletResponse response, EventDAO edao)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String dbImagePath = null;

        try {
            // ===== STEP 1: FILE UPLOAD =====
            Part filePart = request.getPart("eventImage");
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            if (originalFileName != null && !originalFileName.isEmpty()) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
                String uniqueFileName = timeStamp + "_" + originalFileName;
                String applicationPath = getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                filePart.write(uploadFilePath + File.separator + uniqueFileName);
                dbImagePath = UPLOAD_DIR + "/" + uniqueFileName;
            } else {
                errors.put("eventImage", "Vui lòng chọn hình ảnh cho sự kiện.");
            }

            // ===== STEP 2: FORM DATA =====
            String eventName = request.getParameter("eventName");
            int categoryId = Integer.parseInt(request.getParameter("eventCategory"));
            int placeId = Integer.parseInt(request.getParameter("place"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String eventDescriptionHTML = request.getParameter("description");

            if (categoryId == 0) {
                errors.put("categoryID", "Vui lòng chọn danh mục cho sự kiện.");
            }

            if (placeId == 0) {
                errors.put("placeID", "Vui lòng chọn nơi tổ chức cho sự kiện.");
            }

            if (eventName == null || eventName.trim().isEmpty()) {
                errors.put("eventName", "Tên sự kiện không được bỏ trống.");
            }
            if (startDateStr == null || startDateStr.trim().isEmpty()) {
                errors.put("startDateStr", "Ngày bắt đầu không được bỏ trống.");
            }
            if (endDateStr == null || endDateStr.trim().isEmpty()) {
                errors.put("endDateStr", "Ngày kết thúc không được bỏ trống.");
            }
            if (eventDescriptionHTML == null || eventDescriptionHTML.trim().isEmpty()) {
                errors.put("eventDescriptionHTML", "Mô tả sự kiện không được bỏ trống.");
            }

            if (!errors.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("event-form");
                return;
            }

            // ===== STEP 3: CONVERT DATE =====
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localStartDateTime = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime localEndDateTime = LocalDateTime.parse(endDateStr, formatter);

            // ===== STEP 3.1: CHECK IF DATES ARE IN THE PAST =====
            LocalDateTime now = LocalDateTime.now();
            if (localStartDateTime.isBefore(now) || localEndDateTime.isBefore(now)) {
                errors.put("invalidDate", "Không thể tạo sự kiện trong quá khứ. Vui lòng chọn ngày hiện tại hoặc tương lai.");
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("event-form");
                return;
            }

            // ===== STEP 4: CHECK FOR OVERLAPPING EVENT =====
            boolean overlap = edao.hasOverlappingEvent(
                    placeId,
                    Timestamp.valueOf(localStartDateTime),
                    Timestamp.valueOf(localEndDateTime),
                    null // vì đây là CREATE, chưa có EventID
            );

            if (overlap) {
                errors.put("timeConflict", "Địa điểm này đã có sự kiện trong khoảng thời gian bạn chọn.");
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("event-form");
                return;
            }

            // ===== STEP 5: SAVE TO DATABASE =====
            Event newEvent = new Event();
            newEvent.setEventName(eventName);
            newEvent.setCategoryID(categoryId);
            newEvent.setPlaceID(placeId);
            newEvent.setImageURL(dbImagePath);
            newEvent.setStartDate(Timestamp.valueOf(localStartDateTime));
            newEvent.setEndDate(Timestamp.valueOf(localEndDateTime));
            newEvent.setDescription(eventDescriptionHTML);

            boolean success = edao.create(newEvent);

            if (success) {
                System.out.println("Create event: " + success);
                HttpSession session = request.getSession();
                session.setAttribute("currentEventID", newEvent.getEventID());
                response.sendRedirect(request.getContextPath() + "/config-ticket");
            } else {
                request.setAttribute("globalError", "Không thể lưu sự kiện vào cơ sở dữ liệu.");
                request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "Đã xảy ra lỗi không mong muốn.");
            request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
        }
    }

//    private void handleUpdateEvent(HttpServletRequest request, HttpServletResponse response, EventDAO eDao)
//            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        Map<String, String> errors = new HashMap<>();
//        String dbImagePath = null;
//
//        try {
//            // ===== STEP 1: LẤY EVENT HIỆN TẠI =====
//            int eventID = Integer.parseInt(request.getParameter("eventID"));
//            Event existingEvent = eDao.getById(eventID);
//            if (existingEvent == null) {
//                request.setAttribute("globalError", "Sự kiện không tồn tại hoặc đã bị xóa.");
//
//                // CÓ LỖI THÌ NHẢY VỀ TỔNG QUAN SỰ KIỆN
//                request.getRequestDispatcher("/view-thn1105/event-list.jsp").forward(request, response);
//                return;
//            }
//
//            // ===== STEP 2: XỬ LÝ FILE ẢNH (NẾU CÓ CHỌN MỚI) =====
//            Part filePart = request.getPart("eventImage");
//            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//
//            if (originalFileName != null && !originalFileName.isEmpty()) {
//                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//                String uniqueFileName = timeStamp + "_" + originalFileName;
//                String applicationPath = getServletContext().getRealPath("");
//                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
//
//                File uploadDir = new File(uploadFilePath);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdirs();
//                }
//
//                filePart.write(uploadFilePath + File.separator + uniqueFileName);
//                dbImagePath = UPLOAD_DIR + "/" + uniqueFileName;
//            } else {
//                // Giữ nguyên ảnh cũ nếu không chọn ảnh mới
//                dbImagePath = existingEvent.getImageURL();
//            }
//
//            // ===== STEP 3: LẤY DỮ LIỆU FORM =====
//            String eventName = request.getParameter("eventName");
//            int categoryId = Integer.parseInt(request.getParameter("eventCategory"));
//            int placeId = Integer.parseInt(request.getParameter("place"));
//            String startDateStr = request.getParameter("startDate");
//            String endDateStr = request.getParameter("endDate");
//            String eventDescriptionHTML = request.getParameter("description");
//
//            // ===== STEP 4: VALIDATE =====
//            if (categoryId == 0) {
//                errors.put("categoryID", "Vui lòng chọn loại sự kiện.");
//            }
//            if (placeId == 0) {
//                errors.put("placeID", "Vui lòng chọn nơi tổ chức.");
//            }
//            if (eventName == null || eventName.trim().isEmpty()) {
//                errors.put("eventName", "Tên sự kiện không bỏ trống.");
//            }
//            if (startDateStr == null || startDateStr.trim().isEmpty()) {
//                errors.put("startDateStr", "Ngày bắt đầu không bỏ trống.");
//            }
//            if (endDateStr == null || endDateStr.trim().isEmpty()) {
//                errors.put("endDateStr", "Ngày kết thúc không bỏ trống.");
//            }
//            if (eventDescriptionHTML == null || eventDescriptionHTML.trim().isEmpty()) {
//                errors.put("eventDescriptionHTML", "Mô tả sự kiện không bỏ trống.");
//            }
//
//            if (!errors.isEmpty()) {
//                HttpSession session = request.getSession();
//                session.setAttribute("errors", errors);
//                response.sendRedirect("event-form?action=update&eid=" + eventID);
//                return;
//            }
//
//            // ===== STEP 5: CHUYỂN ĐỔI NGÀY & UPDATE DB =====
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime localStartDateTime = LocalDateTime.parse(startDateStr, formatter);
//            LocalDateTime localEndDateTime = LocalDateTime.parse(endDateStr, formatter);
//
//            existingEvent.setEventName(eventName);
//            existingEvent.setCategoryID(categoryId);
//            existingEvent.setPlaceID(placeId);
//            existingEvent.setImageURL(dbImagePath);
//            existingEvent.setStartDate(Timestamp.valueOf(localStartDateTime));
//            existingEvent.setEndDate(Timestamp.valueOf(localEndDateTime));
//            existingEvent.setDescription(eventDescriptionHTML);
//
//            boolean success = eDao.update(existingEvent);
//
//            if (success) {
//                System.out.println("Update event: " + success);
//
//                HttpSession session = request.getSession();
//                session.setAttribute("toastMessage", "Cập nhật sự kiện thành công!");
//                session.setAttribute("currentEventID", eventID);
//                response.sendRedirect(request.getContextPath() + "/config-ticket");
//            } else {
//                request.setAttribute("globalError", "Không thể cập nhật sự kiện vào cơ sở dữ liệu.");
//                request.getRequestDispatcher("/view-thn1105/event-list.jsp").forward(request, response);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("globalError", "Đã xảy ra lỗi không mong muốn khi cập nhật sự kiện.");
//            request.getRequestDispatcher("/view-thn1105/event-form-update.jsp").forward(request, response);
//        }
//    }
    private void handleUpdateEvent(HttpServletRequest request, HttpServletResponse response, EventDAO eDao)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String dbImagePath = null;

        try {
            // ===== STEP 1: LẤY EVENT HIỆN TẠI =====
            int eventID = Integer.parseInt(request.getParameter("eventID"));
            Event existingEvent = eDao.getById(eventID);
            if (existingEvent == null) {
                request.setAttribute("globalError", "Sự kiện không tồn tại hoặc đã bị xóa.");
                request.getRequestDispatcher("/view-thn1105/event-list.jsp").forward(request, response);
                return;
            }

            // ===== STEP 2: XỬ LÝ FILE ẢNH (NẾU CÓ CHỌN MỚI) =====
            Part filePart = request.getPart("eventImage");
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            if (originalFileName != null && !originalFileName.isEmpty()) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
                String uniqueFileName = timeStamp + "_" + originalFileName;
                String applicationPath = getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                filePart.write(uploadFilePath + File.separator + uniqueFileName);
                dbImagePath = UPLOAD_DIR + "/" + uniqueFileName;
            } else {
                dbImagePath = existingEvent.getImageURL(); // Giữ nguyên ảnh cũ
            }

            // ===== STEP 3: LẤY DỮ LIỆU FORM =====
            String eventName = request.getParameter("eventName");
            int categoryId = Integer.parseInt(request.getParameter("eventCategory"));
            int placeId = Integer.parseInt(request.getParameter("place"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String eventDescriptionHTML = request.getParameter("description");

            // ===== STEP 4: VALIDATE =====
            if (categoryId == 0) {
                errors.put("categoryID", "Vui lòng chọn loại sự kiện.");
            }
            if (placeId == 0) {
                errors.put("placeID", "Vui lòng chọn nơi tổ chức.");
            }
            if (eventName == null || eventName.trim().isEmpty()) {
                errors.put("eventName", "Tên sự kiện không bỏ trống.");
            }
            if (startDateStr == null || startDateStr.trim().isEmpty()) {
                errors.put("startDateStr", "Ngày bắt đầu không bỏ trống.");
            }
            if (endDateStr == null || endDateStr.trim().isEmpty()) {
                errors.put("endDateStr", "Ngày kết thúc không bỏ trống.");
            }
            if (eventDescriptionHTML == null || eventDescriptionHTML.trim().isEmpty()) {
                errors.put("eventDescriptionHTML", "Mô tả sự kiện không bỏ trống.");
            }

            if (!errors.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("event-form?action=update&eid=" + eventID);
                return;
            }

            // ===== STEP 5: CHUYỂN ĐỔI NGÀY =====
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localStartDateTime = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime localEndDateTime = LocalDateTime.parse(endDateStr, formatter);

            // ===== STEP 5.1: CHECK NGÀY QUÁ KHỨ =====
            LocalDateTime now = LocalDateTime.now();
            if (localStartDateTime.isBefore(now) || localEndDateTime.isBefore(now)) {
                errors.put("invalidDate", "Không thể cập nhật sự kiện có thời gian trong quá khứ.");
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("event-form?action=update&eid=" + eventID);
                return;
            }

            // ===== STEP 6: CHECK FOR OVERLAPPING EVENT =====
            boolean overlap = eDao.hasOverlappingEvent(
                    placeId,
                    Timestamp.valueOf(localStartDateTime),
                    Timestamp.valueOf(localEndDateTime),
                    eventID // KHÁC VỚI CREATE, ở đây truyền eventID để loại trừ chính nó
            );
            if (overlap) {
                errors.put("timeConflict", "Địa điểm này đã có sự kiện khác trong khoảng thời gian bạn chọn.");
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("event-form?action=update&eid=" + eventID);
                return;
            }

            // ===== STEP 7: UPDATE EVENT TRONG DATABASE =====
            existingEvent.setEventName(eventName);
            existingEvent.setCategoryID(categoryId);
            existingEvent.setPlaceID(placeId);
            existingEvent.setImageURL(dbImagePath);
            existingEvent.setStartDate(Timestamp.valueOf(localStartDateTime));
            existingEvent.setEndDate(Timestamp.valueOf(localEndDateTime));
            existingEvent.setDescription(eventDescriptionHTML);

            boolean success = eDao.update(existingEvent);

            if (success) {
                HttpSession session = request.getSession();
                session.setAttribute("toastMessage", "Cập nhật sự kiện thành công!");
                session.setAttribute("currentEventID", eventID);
                response.sendRedirect(request.getContextPath() + "/config-ticket");
            } else {
                request.setAttribute("globalError", "Không thể cập nhật sự kiện vào cơ sở dữ liệu.");
                request.getRequestDispatcher("/view-thn1105/event-list.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "Đã xảy ra lỗi không mong muốn khi cập nhật sự kiện.");
            request.getRequestDispatcher("/view-thn1105/event-form-update.jsp").forward(request, response);
        }
    }

    private void handleDeleteEvent(HttpServletRequest request, HttpServletResponse response, EventDAO edao) throws ServletException, IOException {
        try {
            int eventID = Integer.parseInt(request.getParameter("eid"));
            boolean success = edao.deleteCascade(eventID);
            System.out.println("Delete event: " + success);

            if (success) {
                response.sendRedirect("admincenter");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "Đã xảy ra lỗi không mong muốn khi cập nhật sự kiện.");
            request.getRequestDispatcher("/view-thn1105/event-form-update.jsp").forward(request, response);
        }
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
