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
        EventCategoryDAO eventCateDAO = new EventCategoryDAO();
        PlaceDAO placeDAO = new PlaceDAO();
        ZoneDAO zDao = new ZoneDAO();
        EventDAO eDao = new EventDAO();
        TicketTypeDAO ticketTypeDao = new TicketTypeDAO();

        String action = request.getParameter("action");

        if (action == null) {
            List<EventCategory> eventCateList = eventCateDAO.getAll();
            List<Place> placeList = placeDAO.getAll();

            request.setAttribute("eventCateList", eventCateList);
            request.setAttribute("placeList", placeList);

            request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("config-ticket")) {
//            HttpSession session = request.getSession();
//            int currentEID = (int) session.getAttribute("currentEventID");

//            TEST EID
            int currentEID = 1030;
            System.out.println("currentEID: " + currentEID);
            Event e = eDao.getById(currentEID);
            Place p = placeDAO.getById(e.getPlaceID());
            List<TicketType> ticketTypeList = ticketTypeDao.getAllByEventID(currentEID);
            List<Zone> zoneList = zDao.getAllZoneOfPlace(e.getPlaceID());

            request.setAttribute("event", e);
            request.setAttribute("place", p);
            request.setAttribute("ticketTypes", ticketTypeList);
            request.setAttribute("zones", zoneList);
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet EventFormController</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Đã vào Update Event" + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
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
        TicketTypeDAO ticketTypeDao = new TicketTypeDAO();
        String action = request.getParameter("action");

        if (action == null) {
            action = "create";
        }

        if (action.equalsIgnoreCase("create")) {
            handleCreateEvent(request, response, edao);

        } else if (action.equalsIgnoreCase("update")) {
            // TODO: thêm handleUpdateEvent() sau
//            handleUpdateEvent(request, response, edao);

        } else if (action.equalsIgnoreCase("delete")) {
            // TODO: thêm handleDeleteEvent() sau
//            handleDeleteEvent(request, response, edao);
        } else if (action.equalsIgnoreCase("config-ticket")) {
            handleConfigTicketType(request, response, ticketTypeDao);
        } else if (action.equalsIgnoreCase("config-ticket-update")) {
            handleUpdateTicketType(request, response, ticketTypeDao);
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
                errors.put("eventImage", "Event image is required.");
            }

            // ===== STEP 2: FORM DATA =====
            String eventName = request.getParameter("eventName");
            int categoryId = Integer.parseInt(request.getParameter("eventCategory"));
            int placeId = Integer.parseInt(request.getParameter("place"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String eventDescriptionHTML = request.getParameter("description");

            if (eventName == null || eventName.trim().isEmpty()) {
                errors.put("eventName", "Event name cannot be empty.");
            }
            if (startDateStr == null || startDateStr.trim().isEmpty()) {
                errors.put("startDateStr", "Date start cannot be empty.");
            }
            if (endDateStr == null || endDateStr.trim().isEmpty()) {
                errors.put("endDateStr", "Date end cannot be empty.");
            }
            if (eventDescriptionHTML == null || eventDescriptionHTML.trim().isEmpty()) {
                errors.put("eventDescriptionHTML", "Description cannot be empty.");
            }

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
                return;
            }

            // ===== STEP 3: CONVERT DATE & SAVE =====
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localStartDateTime = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime localEndDateTime = LocalDateTime.parse(endDateStr, formatter);

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
                HttpSession session = request.getSession();
                session.setAttribute("currentEventID", newEvent.getEventID());
                response.sendRedirect(request.getContextPath() + "/event-form?action=config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the event to the database.");
                request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
        }
    }

//    private void handleUpdateEvent(HttpServletRequest request, HttpServletResponse response, EventDAO edao)
//            throws ServletException, IOException {
//        // TODO: xử lý update
//    }
//
//    private void handleDeleteEvent(HttpServletRequest request, HttpServletResponse response, EventDAO edao)
//            throws ServletException, IOException {
//        // TODO: xử lý delete
//    }
    private void handleConfigTicketType(HttpServletRequest request, HttpServletResponse response, TicketTypeDAO tickTypeDao)
            throws ServletException, IOException {
        try {
            String eidStr = request.getParameter("eventID");
            String ticketTypeName = request.getParameter("ticketTypeName");
            String ticketTypePriceStr = request.getParameter("ticketTypePrice");
            String zoneIDStr = request.getParameter("zoneID");

            int eID = Integer.parseInt(eidStr.trim());
            int zID = Integer.parseInt(zoneIDStr.trim());

            BigDecimal tickPrice = null;
            if (ticketTypePriceStr != null && !ticketTypePriceStr.isEmpty()) {
                tickPrice = new BigDecimal(ticketTypePriceStr);
            }

            TicketType newTicketType = new TicketType();
            newTicketType.setEventID(eID);
            newTicketType.setTypeName(ticketTypeName);
            newTicketType.setPrice(tickPrice);
            newTicketType.setZoneID(zID);

            boolean success = tickTypeDao.create(newTicketType);
            System.out.println("Tạo thành công: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/event-form?action=config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
        }
    }

    private void handleUpdateTicketType(HttpServletRequest request, HttpServletResponse response, TicketTypeDAO tickTypeDao)
            throws ServletException, IOException {
        try {
            String eidStr = request.getParameter("eventID-U");
            String ticketTypeIdStr = request.getParameter("ticketTypeID-U");
            String ticketTypeName = request.getParameter("ticketTypeName-U");
            String ticketTypePriceStr = request.getParameter("ticketTypePrice-U");
            String zoneIDStr = request.getParameter("zoneID-U");
            String statusStr = request.getParameter("statusID-U");

            int eID = Integer.parseInt(eidStr.trim());
            int zID = Integer.parseInt(zoneIDStr.trim());
            int tpID = Integer.parseInt(ticketTypeIdStr.trim());
            int statusID = Integer.parseInt(statusStr.trim());

            BigDecimal tickPrice = null;
            if (ticketTypePriceStr != null && !ticketTypePriceStr.isEmpty()) {
                tickPrice = new BigDecimal(ticketTypePriceStr);
            }

            System.out.println("TTID: " + tpID);
            System.out.println("EID: " + eID);
            System.out.println("TName: " + ticketTypeName);
            System.out.println("TPrice: " + tickPrice);
            System.out.println("ZID: " + zID);
            System.out.println("SID: " + statusID);

            TicketType updateTicketType = new TicketType();
            updateTicketType.setTicketTypeID(tpID);
            updateTicketType.setEventID(eID);
            updateTicketType.setTypeName(ticketTypeName);
            updateTicketType.setPrice(tickPrice);
            updateTicketType.setZoneID(zID);
            updateTicketType.setStatusID(statusID);

            boolean success = tickTypeDao.update(updateTicketType);
            System.out.println("Cập nhật thành công: " + success);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/event-form?action=config-ticket");
            } else {
                request.setAttribute("globalError", "Failed to save the ticket type to the database.");
                request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/config-ticket.jsp").forward(request, response);
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
