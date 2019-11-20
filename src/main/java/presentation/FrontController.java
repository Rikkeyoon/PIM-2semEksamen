package presentation;

import exception.CommandException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

/**
 * The purpose of FrontController is to handle all request for the web site It
 * consists of two parts: Web Handler and Command hierarchy As a Web Handler it
 * receives post or get requests, delegates to a command to carry out the
 * action, and forwards the result to a view
 *
 * @author carol
 */
@WebServlet(name = "FrontController", urlPatterns = {"/FrontController"})
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
        maxFileSize = 10485760L, // 10 MB
        maxRequestSize = 20971520L // 20 MB
)

public class FrontController extends HttpServlet {

    private static final String UPLOAD_DIR = "img";
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
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            Command cmd = Command.from(request);
//            String view = cmd.execute(request, response);
//            if (view.equals("index")) {
//                request.getRequestDispatcher(view + ".jsp").forward(request, response);
//            } else {
//                request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
//            }
//        } catch (CommandException ex) {
//            request.setAttribute("error", ex.getMessage());
//            //TODO: Forward to a view or   perhaps an error page instead of index
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//        }
//    }
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        // creates upload folder if it does not exists
        File uploadFolder = new File(WORKING_DIR + File.separator + UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        PrintWriter writer = response.getWriter();
        // write all files in upload folder
        for (Part part : request.getParts()) {
            if (part.getContentType() != null && part.getSize() > 0) {
                String fileName = part.getSubmittedFileName();
                String contentType = part.getContentType();

                // allows only JPEG files to be uploaded
                if (contentType != null && !contentType.equalsIgnoreCase("image/jpeg")) {
                    continue;
                }

                part.write(WORKING_DIR + File.separator + UPLOAD_DIR + File.separator + fileName);
                File file = new File(WORKING_DIR + File.separator + UPLOAD_DIR + File.separator + fileName);
                Map uploadResult = null;
                String s = null;
                try{
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                        
                        "cloud_name", "dmk5yii3m",
                        "api_key", "228872137167968",
                        "api_secret", "1IRxrcNuw4zVdlwJBiqAgktyyeU"));                
                uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                s = (String) uploadResult.get(new String("url"));
                file.delete();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.println("hallo" + file.getAbsoluteFile());
                writer.append("File successfully uploaded to "
                        + uploadFolder.getAbsolutePath()
                        + File.separator
                        + fileName
                        + "<br>\r\n");
                writer.append(file.getAbsolutePath());
                writer.append("<img src ='" + s + "'  >");
            }
        }
    }

    /* response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        // gets absolute path of the web application
      //String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
      //String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        // creates upload folder if it does not exists
      //File uploadFolder = new File(uploadFilePath);
     //  if (!uploadFolder.exists()) {
     //       uploadFolder.mkdirs();
      //  }
       PrintWriter writer = response.getWriter();
        // write all files in upload folder
      //  System.out.println(request.getParts().size());
      //  for (Part part : request.getParts()) {
      //      if (part != null && part.getSize() > 0) {
      //          String fileName = part.getSubmittedFileName();
      //          String contentType = part.getContentType();
      //          System.out.println(contentType);
                // allows only JPEG files to be uploaded
      //          if (contentType != null && !contentType.equalsIgnoreCase("image/jpeg")) {
      //              continue;  
      //          }
              Part part = request.getPart("file");
              
//        part.write(filename);
        String fileName = getFileName(part);
        File uploads = new File("");
        System.out.println(fileName);
        System.out.println(new File("").getAbsolutePath());
        File file = new File(fileName);
               
                                
                //part.write(uploadFilePath + File.separator + fileName);

                writer.append("File successfully uploaded to "
                        + uploadResult.get("url")
                        + "<br>\r\n");
            //}
        //}
     */
    //}
//    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "dmk5yii3m",
//                "api_key", "228872137167968",
//                "api_secret", "1IRxrcNuw4zVdlwJBiqAgktyyeU"));
//  //              part.write(uploadFilePath + File.separator + fileName);;
////                File file = new File(uploadFilePath + File.separator + fileName);
//                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    private String getFileName(Part part) {
        for (String token : part.getHeader("content-disposition").split(";")) {
            if (token.trim().startsWith("filename")) {
                String file = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                String[] fileSplit = file.split("\\\\");
                return fileSplit[fileSplit.length - 1];
            }
        }
        return null;
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
