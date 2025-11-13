/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.thn1105;

import DAOs.thn1105.PlaceDAO;
import Models.thn1105.Place;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(name = "PlaceFormController", urlPatterns = {"/placeform"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB: tạm giữ trong RAM trước khi ghi ra đĩa
        maxFileSize = 1024 * 1024 * 10, // 10MB: file tối đa
        maxRequestSize = 1024 * 1024 * 50 // 50MB: tổng request tối đa
)
public class PlaceFormController extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads/zone-maps";

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
            out.println("<title>Servlet PlaceFormController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlaceFormController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        PlaceDAO pDao = new PlaceDAO();

        if (action == null) {
            
            request.getRequestDispatcher("view-thn1105/place-form.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            String pIdStr = request.getParameter("pid");
            int pID = Integer.parseInt(pIdStr);
            Place p = pDao.getById(pID);

            request.setAttribute("place", p);
            request.getRequestDispatcher("view-thn1105/place-form-update.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        PlaceDAO pDao = new PlaceDAO();

        if (action.equalsIgnoreCase("create")) {
            handleCreatePlace(request, response, pDao);
        } else if (action.equalsIgnoreCase("update")) {
            handleUpdatePlace(request, response, pDao);
        }
    }

    private void handleCreatePlace(HttpServletRequest request, HttpServletResponse response, PlaceDAO pdao)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String dbImagePath = null;

        try {
            // ===== STEP 1: FILE UPLOAD =====
            Part filePart = request.getPart("zoneImage"); // tên input file phải là eventImage
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
                errors.put("eventImage", "Vui lòng chọn hình ảnh cho địa điểm.");
            }

            // ===== STEP 2: FORM DATA =====
            String placeName = request.getParameter("placeName");
            String placeAddress = request.getParameter("placeAddress");
            String placeDescription = request.getParameter("placeDescription");

            if (placeName == null || placeName.trim().isEmpty()) {
                errors.put("placeName", "Tên địa điểm không được bỏ trống.");
            }
            if (placeAddress == null || placeAddress.trim().isEmpty()) {
                errors.put("placeAddress", "Địa chỉ không được bỏ trống.");
            }
            if (placeDescription == null || placeDescription.trim().isEmpty()) {
                errors.put("placeDescription", "Mô tả không được bỏ trống.");
            }

            if (!errors.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("place-form");
                return;
            }

            // ===== STEP 3: SAVE TO DATABASE =====
            Place newPlace = new Place();
            newPlace.setPlaceName(placeName);
            newPlace.setAddress(placeAddress);
            newPlace.setDescription(placeDescription);
            newPlace.setSeatMapURL(dbImagePath);

            boolean success = pdao.create(newPlace);

            if (success) {
                HttpSession session = request.getSession();
                session.setAttribute("currentPlaceID", newPlace.getPlaceID());
                System.out.println("Create place success: " + newPlace.getPlaceName());
                response.sendRedirect(request.getContextPath() + "/config-zone");
            } else {
                request.setAttribute("globalError", "Không thể lưu địa điểm vào cơ sở dữ liệu.");
                request.getRequestDispatcher("/view-thn1105/place-form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "Đã xảy ra lỗi không mong muốn.");
            request.getRequestDispatcher("/view-thn1105/place-form.jsp").forward(request, response);
        }
    }

    private void handleUpdatePlace(HttpServletRequest request, HttpServletResponse response, PlaceDAO pdao)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();
        String dbImagePath = null;

        try {
            // ===== STEP 1: FILE UPLOAD (nếu người dùng chọn ảnh mới) =====
            Part filePart = request.getPart("zoneImage");
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            if (originalFileName != null && !originalFileName.isEmpty()) {
                // Tạo tên file duy nhất
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
                String uniqueFileName = timeStamp + "_" + originalFileName;
                String applicationPath = getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Ghi file ảnh vào thư mục upload
                filePart.write(uploadFilePath + File.separator + uniqueFileName);
                dbImagePath = UPLOAD_DIR + "/" + uniqueFileName;
            }

            // ===== STEP 2: FORM DATA =====
            int placeID = Integer.parseInt(request.getParameter("placeID"));
            String placeName = request.getParameter("placeName");
            String placeAddress = request.getParameter("placeAddress");
            String placeDescription = request.getParameter("placeDescription");

            if (placeName == null || placeName.trim().isEmpty()) {
                errors.put("placeName", "Tên địa điểm không được bỏ trống.");
            }
            if (placeAddress == null || placeAddress.trim().isEmpty()) {
                errors.put("placeAddress", "Địa chỉ không được bỏ trống.");
            }
            if (placeDescription == null || placeDescription.trim().isEmpty()) {
                errors.put("placeDescription", "Mô tả không được bỏ trống.");
            }

            if (!errors.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errors", errors);
                response.sendRedirect("placeform?action=edit&placeID=" + placeID);
                return;
            }

            // ===== STEP 3: LẤY PLACE HIỆN TẠI =====
            Place existingPlace = pdao.getById(placeID);
            if (existingPlace == null) {
                request.setAttribute("globalError", "Không tìm thấy địa điểm cần cập nhật.");
                request.getRequestDispatcher("/view-thn1105/place-form-update.jsp").forward(request, response);
                return;
            }

            // ===== STEP 4: CẬP NHẬT DỮ LIỆU =====
            existingPlace.setPlaceName(placeName);
            existingPlace.setAddress(placeAddress);
            existingPlace.setDescription(placeDescription);

            // Nếu có ảnh mới thì cập nhật, nếu không thì giữ ảnh cũ
            if (dbImagePath != null) {
                existingPlace.setSeatMapURL(dbImagePath);
            }

            boolean success = pdao.update(existingPlace);

            // ===== STEP 5: PHẢN HỒI =====
            if (success) {
                HttpSession session = request.getSession();
                session.setAttribute("currentPlaceID", existingPlace.getPlaceID());
                System.out.println("Update place success: " + existingPlace.getPlaceName());
                response.sendRedirect(request.getContextPath() + "/config-zone?placeID=" + placeID);
            } else {
                request.setAttribute("globalError", "Không thể cập nhật địa điểm.");
                request.getRequestDispatcher("/view-thn1105/place-form-update.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalError", "Đã xảy ra lỗi trong quá trình cập nhật địa điểm.");
            request.getRequestDispatcher("/view-thn1105/place-form-update.jsp").forward(request, response);
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
