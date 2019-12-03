package presentation;

import exception.CommandException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The purpose of DownloadServlet is to create a downloadable (json) file the
 * user can save on their local computer
 *
 * @author carol
 */
@WebServlet(name = "DownloadServlet", urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {

    private static final String DOWNLOAD_DIR = "json";
    private static final String WORKING_DIR = System.getProperty("user.dir");

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
        String fileName = (String) request.getSession().getAttribute("file_name");
        response.setContentType("application/json");
        //the default Content-Disposition is “inline”, but we need “attachment” for a downloadable file.
        response.setHeader("Content-disposition", "attachment; filename=" 
                + fileName);

        String filePlacement = WORKING_DIR + File.separator + DOWNLOAD_DIR
                + File.separator + fileName;
        File file = new File(filePlacement);

        response.setContentLength((int) file.length());
        /*Using a try-with-resources statement, the application will automatically
         *close any AutoCloseable instances defined as part of the try statement
         */
        try (FileInputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream()) {
            int bytes;
            while ((bytes = in.read()) != -1) {
                out.write(bytes);
            }
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
