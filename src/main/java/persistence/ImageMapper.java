package persistence;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import exception.CommandException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Part;
import logic.Product;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author allan
 */
public class ImageMapper implements IImageMapper {

    private static final String UPLOAD_DIR = "img";
    private static final String WORKING_DIR = System.getProperty("user.dir");
    private static final Cloudinary CLOUDINARY = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dmk5yii3m",
            "api_key", "228872137167968",
            "api_secret", "1IRxrcNuw4zVdlwJBiqAgktyyeU"));

    @Override
    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage)
            throws CommandException {
        List<Pair<String, Boolean>> images = new ArrayList<>();
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
                    if (contentType != null && (contentType.equalsIgnoreCase("image/jpeg") 
                            || contentType.equalsIgnoreCase("image/png"))) {
                        part.write(WORKING_DIR + File.separator + UPLOAD_DIR 
                                + File.separator + fileName);
                        File file = new File(WORKING_DIR + File.separator 
                                + UPLOAD_DIR + File.separator + fileName);

                        Map uploadResult = null;
                        String URL = null;
                        Boolean bool = false;

                        uploadResult = CLOUDINARY.uploader().upload(file, ObjectUtils.emptyMap());

                        URL = (String) uploadResult.get(new String("url"));

                        if (part.getSubmittedFileName().equals(primaryImage.replaceAll("\\s+", ""))) {
                            bool = true;
                        }
                        images.add(new MutablePair(URL, bool));

                        file.delete();
                    }
                }
            }
        } catch (IOException e) {
            throw new CommandException("Could not upload the chosen pictures. "
                    + "Please make sure they are JPEG or PNG and try again.");
        }
        return images;
    }

    @Override
    public void addPictureURL(int productId, List<Pair<String, Boolean>> images) 
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String insertSql = "INSERT INTO images VALUES (?, ? , ?);";
            for (Pair<String, Boolean> p : images) {
                pstmt = connection.prepareStatement(insertSql);
                pstmt.setInt(1, productId);
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
    public List<Pair<String, Boolean>> getPicturesWithId(int productId) 
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Pair<String, Boolean>> images = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM images WHERE product_id = ? "
                    + "ORDER BY primaryImage DESC";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, productId);

            result = pstmt.executeQuery();

            while (result.next()) {
                String URL = result.getString("url");
                Boolean bool = result.getBoolean("primaryImage");

                images.add(new MutablePair(URL, bool));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch URLs to images");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return images;
    }

    @Override
    public Pair<String, Boolean> getPrimaryPictureWithId(int productId) 
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        Pair<String, Boolean> image = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM images WHERE product_id = ? "
                    + "AND primaryImage = 1;";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, productId);

            result = pstmt.executeQuery();
            while (result.next()) {
                String URL = result.getString("url");
                Boolean bool = result.getBoolean("primaryImage");

                image = new MutablePair(URL, bool);
            }

            if (image == null) {
                throw new SQLException();
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch URLs to images");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return image;
    }

    @Override
    public void updatePrimaryPicture(int productId, String imageURL)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String updateSql = "UPDATE images SET primaryImage = 0 "
                    + "WHERE product_id = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setInt(1, productId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated");
            }

            updateSql = "UPDATE images SET primaryImage = 1 "
                    + "WHERE product_id = ? AND url = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setInt(1, productId);
            pstmt.setString(2, imageURL);

            rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public void deleteImages(List<Pair<String, Boolean>> images)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            for (Pair<String, Boolean> image : images) {
                connection = PersistenceFacadeDB.getConnection();
                String deleteSql = "DELETE FROM images WHERE url = ?";
                pstmt = connection.prepareStatement(deleteSql);
                pstmt.setString(1, image.getKey());

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new SQLException("No rows updated");
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the product to be deleted");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public void deleteAllImages(Product p) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String deleteSql = "DELETE FROM images WHERE product_id = ?";
            pstmt = connection.prepareStatement(deleteSql);
            pstmt.setInt(1, p.getId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the product to be deleted");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }
}
