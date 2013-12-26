package ch.friedli.infosystem.admin.servlet;

import ch.friedli.secureremoteinterfaceinfomonitor.ContentDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentLoaderRemote;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author mfrie_000
 */
@WebServlet(name = "ContentFileUploadServlet", urlPatterns = {"/app/saveFormData"})
@MultipartConfig
//@Stateless
//@Path("/")
public class ContentFileUploadServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ContentFileUploadServlet.class.getName());
    private static final String CONTENT_FILE_DIR = "c:/infomonitor/content/";
    @EJB
    ContentLoaderRemote contentLoader;

//    @POST
//    @Path("saveFormData")
//    //@Consumes({"application/json", "application/x-www-form-urlencoded"})
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//   // @Produces("text/html")
//    public void createContentEntry(
////            @FormDataParam("fileType") String showInterval,
////            @FormDataParam("fileType") String fileType,
////            @FormDataParam("width") String width,
////            @FormDataParam("height") String height,
////            @FormDataParam("isActive") String isActive,
////            @FormDataParam("selectedWebsiteUrl") String externalWebUrl,
//            @FormDataParam("model") String[] model,
//            @FormDataParam("file") InputStream inputfile,
//            @FormDataParam("file") FormDataContentDisposition fileDisposition
//            ) {
//        ContentDetail detail = new ContentDetail();
//        // Store the message
////        if (showInterval != null && !showInterval.isEmpty()) {
////            detail.setShowInterval(Integer.parseInt(showInterval));
////        } else {
////            detail.setShowInterval(10000); // default is 10 s
////        }
////        if (fileType != null && !fileType.isEmpty()) {
////            detail.setContentType(fileType);
////        }
////        if (width != null && !width.isEmpty()) {
////            detail.setWidth(Integer.parseInt(width));
////        }
////        if (height != null && !height.isEmpty()) {
////            detail.setHeight(Integer.parseInt(height));
////        }
////        if (isActive != null && !isActive.isEmpty() && isActive.equals("true")) {
////            detail.setIsActive(true);
////        }
//
////        if (externalWebUrl != null && !externalWebUrl.isEmpty()) {
////            detail.setExternalWebUrl(externalWebUrl);
////        // file to upload if not an external web url
////        } else {
////            try {
////                for (Part part : request.getParts()) {
////                    String param = part.getName();
////                    LOGGER.log(Level.FINE, param);
////                    if ("uploadFile".equals(param)) {
////                        InputStream is = request.getPart(param).getInputStream();
////                        int i = is.available();
////                        byte[] b = new byte[i];
////                        is.read(b);
////                        LOGGER.log(Level.FINE, "Length : " + b.length);
////                        String fileName = getFileName(part);
////                        LOGGER.log(Level.FINE, "File name : " + fileName);
////                        File directory = new File(CONTENT_FILE_DIR);
////                        
////                        if (!directory.exists()) {
////                            directory.mkdirs();
////                            directory.setWritable(true);
////                            directory.setReadable(true);
////                        }
////                        FileOutputStream os = new FileOutputStream(CONTENT_FILE_DIR + fileName);
////                        os.write(b);
////                        is.close();
////                        detail.setContentUri(fileName);
////                    }
////                }
////            } catch (IOException ex) {
////                Logger.getLogger(ContentFileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (ServletException ex) {
////                Logger.getLogger(ContentFileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        }
//        this.contentLoader.createContentEntityItem(detail);
//    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ContentDetail detail = new ContentDetail();
        String showInterval = request.getParameter("interval");
        String fileType = request.getParameter("fileType");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String isActive = request.getParameter("isActive");
        String protocol = request.getParameter("protocol");
        String externalWebUrl = request.getParameter("websiteUrl");

        if (showInterval != null && !showInterval.isEmpty()) {
            detail.setShowInterval(Integer.parseInt(showInterval));
        } else {
            detail.setShowInterval(10000); // default is 10 s
        }
        if (fileType != null && !fileType.isEmpty()) {
            detail.setContentType(fileType);
        }
        if (width != null && !width.isEmpty()) {
            detail.setWidth(Integer.parseInt(width));
        }
        if (height != null && !height.isEmpty()) {
            detail.setHeight(Integer.parseInt(height));
        }
        if (isActive != null && !isActive.isEmpty() && isActive.equals("on")) {
            detail.setIsActive(true);
        }
        if (protocol != null && !protocol.isEmpty()) {
            detail.setProtocol(protocol);
        }
        if (externalWebUrl != null && !externalWebUrl.isEmpty()) {
            detail.setExternalWebUrl(externalWebUrl);
        // file to upload if not an external web url
        } else {
            for (Part part : request.getParts()) {
                String param = part.getName();
                LOGGER.log(Level.FINE, param);
                if ("fileToBeUploaded".equals(param)) {
                    InputStream is = request.getPart(param).getInputStream();
                    int i = is.available();
                    byte[] b = new byte[i];
                    is.read(b);
                    LOGGER.log(Level.FINE, "Length : " + b.length);
                    String fileName = getFileName(part);
                    LOGGER.log(Level.FINE, "File name : " + fileName);
                    File directory = new File(CONTENT_FILE_DIR);

                    if (!directory.exists()) {
                        directory.mkdirs();
                        directory.setWritable(true);
                        directory.setReadable(true);
                    }
                    FileOutputStream os = new FileOutputStream(CONTENT_FILE_DIR + fileName);
                    os.write(b);
                    is.close();
                    detail.setContentUri(fileName);
                }
            }
        }
        this.contentLoader.createContentEntityItem(detail);
        
        // redirect
        response.sendRedirect("/InfoMonitor-web/app/admin.html#/overview");
    }

    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.FINE, "Part Header = " + partHeader);
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
