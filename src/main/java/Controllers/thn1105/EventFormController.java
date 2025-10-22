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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Date;
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

        String stepStr = request.getParameter("step");
        int currentStep = 1;

        if (stepStr != null) {
            currentStep = Integer.parseInt(stepStr);
        }

        try {
            List<EventCategory> eventCateList = eventCateDAO.getAll();
            List<Place> placeList = placeDAO.getAll();

            request.setAttribute("step", currentStep);
            request.setAttribute("eventCateList", eventCateList);
            request.setAttribute("placeList", placeList);
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();

            // Set an error message to display on the JSP for a better user experience
            request.setAttribute("errorMessage", "Could not load required data. Please try again later.");
        }

        request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);

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
        request.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String dbImagePath = null;

        try {
            // ================== STEP 1: HANDLE FILE UPLOAD ==================
            Part filePart = request.getPart("eventImage"); // 'eventImage' is the name="" of your file input
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            System.out.println("--- STEP 1: FILE UPLOAD DEBUG ---");
            System.out.println("Original Filename: " + originalFileName);
            // Check if a file was actually uploaded
            if (originalFileName != null && !originalFileName.isEmpty()) {
                // Generate a timestamp (e.g., 20251022013005)
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

                // Create a unique filename to avoid collisions
                String uniqueFileName = timeStamp + "_" + originalFileName;

                // Get the absolute path of the web application
                String applicationPath = getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                // Create the upload directory if it does not exist
                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Save the file to the server
                filePart.write(uploadFilePath + File.separator + uniqueFileName);

                // Prepare the relative path to be stored in the database
                dbImagePath = UPLOAD_DIR + "/" + uniqueFileName;

                System.out.println("Unique Filename: " + uniqueFileName);
                System.out.println("Full Server Path to Save: " + uploadFilePath + File.separator + uniqueFileName);
                System.out.println("Path for DB: " + dbImagePath);
                System.out.println("------------------------------------");
            } else {
                // If the file is mandatory, add an error
                errors.put("eventImage", "Event image is required.");
            }

            // ================== STEP 2: GET AND VALIDATE OTHER FORM DATA ==================
            String eventName = request.getParameter("eventName");
            int categoryId = Integer.parseInt(request.getParameter("eventCategory"));
            int placeId = Integer.parseInt(request.getParameter("place"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String eventDescriptionHTML = request.getParameter("description"); // 'description' is the name of your textarea

            // --- Basic Validation ---
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

            // ================== STEP 3: IF ERRORS, FORWARD BACK TO FORM ==================
            if (!errors.isEmpty()) {
                // Repopulate form and show errors (implementation omitted for brevity)
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
                return;
            }

            // ================== STEP 4: PREPARE DATA FOR DATABASE ==================
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localStartDateTime = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime localEndDateTime = LocalDateTime.parse(endDateStr, formatter);

            Timestamp sqlStartDateTimestamp = Timestamp.valueOf(localStartDateTime);
            Timestamp sqlEndDateTimestamp = Timestamp.valueOf(localEndDateTime);

            Event newEvent = new Event();
            newEvent.setEventName(eventName);
            newEvent.setCategoryID(categoryId);
            newEvent.setPlaceID(placeId);
            newEvent.setImageURL(dbImagePath);
            newEvent.setStartDate(sqlStartDateTimestamp);
            newEvent.setEndDate(sqlEndDateTimestamp);
            newEvent.setDescription(eventDescriptionHTML);

            EventDAO edao = new EventDAO();
            boolean success = edao.create(newEvent);

            System.out.println("Success: " + success);

            // ================== STEP 6: REDIRECT BASED ON RESULT (PRG Pattern) ==================
            if (success) {
                // Redirect to a success page or the event list to prevent form resubmission
                response.sendRedirect(request.getContextPath() + "/event-form?step=2");
            } else {
                // If saving fails, forward back to the form with an error message
                request.setAttribute("globalError", "Failed to save the event to the database.");
                request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            request.setAttribute("globalError", "An unexpected error occurred.");
            request.getRequestDispatcher("/view-thn1105/event-form.jsp").forward(request, response);
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
