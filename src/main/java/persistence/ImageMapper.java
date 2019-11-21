/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import exception.CommandException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.servlet.http.Part;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author allan
 */
public class ImageMapper implements IImageMapper {

    private static final String UPLOAD_DIR = "img";
    private static final String WORKING_DIR = System.getProperty("user.dir");
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dmk5yii3m",
            "api_key", "228872137167968",
            "api_secret", "1IRxrcNuw4zVdlwJBiqAgktyyeU"));

    @Override
    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException {
        List<Pair<String, Boolean>> images = new ArrayList<Pair<String, Boolean>>();
        try {
            //Creates img folder if none exist(temporary storage for image before uploaded to cloudinary)
            File uploadFolder = new File(WORKING_DIR + File.separator + UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            for (Part part : parts) {
                if (part.getContentType() != null && part.getSize() > 0) {
                    String fileName = part.getSubmittedFileName();
                    String contentType = part.getContentType();

                    // allows JPEG & PNG files to be uploaded
                    if (contentType != null && (contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/png"))) {
                        part.write(WORKING_DIR + File.separator + UPLOAD_DIR + File.separator + fileName);
                        File file = new File(WORKING_DIR + File.separator + UPLOAD_DIR + File.separator + fileName);

                        Map uploadResult = null;
                        String URL = null;
                        Boolean bool = false;

                        uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

                        URL = (String) uploadResult.get(new String("url"));

                        if (part.getSubmittedFileName().equals(primaryImage.replaceAll("\\s+", ""))) {
                            bool = true;
                        }
                        images.add(new Pair(URL, bool));

                        file.delete();
                    }
                }
            }

        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return images;
    }

    @Override
    public void addPictureURL(int id, List<Pair<String, Boolean>> images) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String insertSql = "INSERT INTO images VALUES (?, ? , ?);";
            for (Pair<String, Boolean> p : images) {
                pstmt = connection.prepareStatement(insertSql);
                pstmt.setInt(1, id);
                pstmt.setString(2, p.getKey());
                pstmt.setBoolean(3, p.getValue());
                pstmt.executeUpdate();
            }

        } catch (SQLException | NullPointerException e) {
            throw new CommandException("Could not save URL reference to images");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);

        }
    }

    @Override
    public List<Pair<String, Boolean>> getPicturesWithId(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Pair<String, Boolean>> images = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM images WHERE product_id = " + id + ";";
            pstmt = connection.prepareStatement(selectSql);
            //pstmt.setInt(1, id);

            result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                String URL = result.getString("url");
                Boolean bool = result.getBoolean("primaryImage");

                images.add(new Pair(URL, bool));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch URLs to images" + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return images;
    }

    @Override
    public List<Pair<String, Boolean>> getPrimaryPictureWithId(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Pair<String, Boolean>> images = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM images WHERE product_id = ? AND primaryImage = 1;";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);

            result = pstmt.executeQuery(selectSql);
            result.next();
            String URL = result.getString("url");
            Boolean bool = result.getBoolean("primaryImage");
            images.add(new Pair(URL, bool));

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch URLs to images" + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return images;
    }
}
