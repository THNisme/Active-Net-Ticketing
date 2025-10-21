/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.thn1105;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
@WebServlet(name = "FroalaImageUploadServlet", urlPatterns = {"/froala-upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class FroalaImageUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads/froala_media";

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
            out.println("<title>Servlet FroalaImageUploadServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FroalaImageUploadServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        // --- 1. Get the uploaded file ---
        Part filePart = request.getPart("file"); // Froala sends the file with the name "file"
        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String jsonResponse;

        if (originalFileName != null && !originalFileName.isEmpty()) {
            try {
                // --- 2. Create a unique filename ---
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
                String uniqueFileName = timeStamp + "_" + originalFileName;

                // --- 3. Save the file to the server ---
                String applicationPath = getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                filePart.write(uploadFilePath + File.separator + uniqueFileName);

                // --- 4. Construct the public URL for the file ---
                String contextPath = request.getContextPath();
                String publicUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/" + UPLOAD_DIR + "/" + uniqueFileName;

                // --- 5. Create the JSON response that Froala expects ---
                // Format: {"link": "http://your_url/image.jpg"}
                jsonResponse = "{\"link\": \"" + publicUrl + "\"}";

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse = "{\"error\": \"File upload failed.\"}";
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = "{\"error\": \"No file provided.\"}";
        }

        // --- 6. Send the JSON response back to Froala ---
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
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
