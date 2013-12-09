package ch.friedli.infosystem.admin.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mfrie_000
 */
@WebServlet(name = "FileDownloadServlet", urlPatterns = {"/app/fileDownload"})
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
        
        JsonReader reader = Json.createReader(request.getInputStream());
        JsonObject jsonObj = reader.readObject();       
        reader.close();
        String filePath = jsonObj.getString("filePath");
        LOGGER.log(Level.FINE, "File name : " + filePath);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));

            String[] fileParts = filePath.split(".");
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            ImageIO.write(img, fileParts[1], tmp);
            tmp.close();
            Integer contentLength = tmp.size();

            response.setContentType("image/" + fileParts[1]);
            response.setHeader("Content-Length", contentLength.toString());
            OutputStream out = response.getOutputStream();
            out.write(tmp.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to load image: " + e);
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
