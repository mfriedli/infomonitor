package ch.friedli.infosystem.servlet;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.misc.BASE64Encoder;

/**
 *
 * @author mfrie_000
 */
@WebServlet(name = "FileDownloadServlet", urlPatterns = {"/app/imageDownload"})
@MultipartConfig
public class FileDownloadServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(FileDownloadServlet.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        StringBuffer sb = new StringBuffer();

        BufferedReader reader = request.getReader();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String filePathUri = sb.toString();
        String fileType = filePathUri.substring(filePathUri.length()-3);
        String[] fileParts = filePathUri.split("/");
        LOGGER.log(Level.FINE, "File name : " + filePathUri);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePathUri));
            
            String filePath = request.getSession().getServletContext().getRealPath("/");
            System.out.println("Server path:" + filePath);
            File fileToCreate = new File(filePath, fileParts[fileParts.length-1]);
 
//            FileUtils.copyFile(this.userImage, fileToCreate);
//            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
//            ImageIO.write(img, fileType, tmp);
//            byte[] imageBytes = tmp.toByteArray();
//            BASE64Encoder encoder = new BASE64Encoder();
//            String imageString = encoder.encode(imageBytes);
            
//            tmp.close();
//            Integer contentLength = imageString.length();
//
//            response.setContentType(getServletContext().getMimeType(filePathUri));
//            response.setContentLength(contentLength);
//            response.setHeader( "Content-Transfer-Encoding", "base64" );
//            OutputStream out = response.getOutputStream();
//            out.write(imageString.getBytes());
//            out.flush();
//            out.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to load image: " + e);
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
