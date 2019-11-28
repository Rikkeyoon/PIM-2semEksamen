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
 * The purpose of the ImageMapper is to save the images in cloudinary and in the
 * database and to delete the stored images when necessary
 *
 * @author allan
 */
public class ImageMapper {

    private static final String UPLOAD_DIR = "img";
    private static final String WORKING_DIR = System.getProperty("user.dir");
    private static final Cloudinary CLOUDINARY = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dmk5yii3m",
            "api_key", "228872137167968",
            "api_secret", "1IRxrcNuw4zVdlwJBiqAgktyyeU"));

    /**
     * Method to upload the images to cloudinary
     *
     * @param parts
     * @param primaryImage String containing the URL of the primary image
     * @return List of Pair of Strign and boolean
     * @throws CommandException
     */
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

    /**
     * Method to store the images together with the product the images are
     * assign to in the database
     *
     * @param productId
     * @param images
     * @throws CommandException
     */
    public void addPictureURL(int productId, List<Pair<String, Boolean>> images)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String insertSql = "INSERT INTO images VALUES (?, ? , ?)";
            pstmt = connection.prepareStatement(insertSql);
            for (Pair<String, Boolean> p : images) {
                pstmt.setInt(1, productId);
                pstmt.setString(2, p.getKey());
                pstmt.setBoolean(3, p.getValue());
                try {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    if (e.getErrorCode() != 1062) {
                        throw new SQLException();
                    }
                }
            }
        } catch (SQLException | NullPointerException e) {
            throw new CommandException("Could not save URL reference to images");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);

        }
    }

    /**
     * Method to the product's pictures' URLs that are stored in the database by
     * the product's id
     *
     * @param productId
     * @return List of Pair of String and boolean
     * @throws CommandException
     */
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

    /**
     * Method to get the product's primary picture's URL which is stored in the
     * database by getting the product's id
     *
     * @param productId
     * @return Pair of String and boolean
     * @throws CommandException
     */
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

    /**
     * Method to change the primary picture in the database by finding all
     * pictures assigned to the product and setting their primary image boolean
     * to 0 (false) and then changing the correct new primary image's boolean to
     * 1 (true)
     *
     * @param productId
     * @param imageURL
     * @throws CommandException
     */
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

            pstmt.executeUpdate();

            updateSql = "UPDATE images SET primaryImage = 1 "
                    + "WHERE product_id = ? AND url = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setInt(1, productId);
            pstmt.setString(2, imageURL);

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID" + ex);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to delete the images which have the URL from the parameter in the
     * database
     *
     * @param imageUrls
     * @throws CommandException
     */
    public void deleteImages(String[] imageUrls) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            for (String image : imageUrls) {
                connection = PersistenceFacadeDB.getConnection();
                String deleteSql = "DELETE FROM images WHERE url = ?";
                pstmt = connection.prepareStatement(deleteSql);
                pstmt.setString(1, image);

                pstmt.executeUpdate();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the image to be deleted" + ex);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to delete all of a product's pictures from the database
     *
     * @param p Product
     * @throws CommandException
     */
    public void deleteAllImages(Product p) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String deleteSql = "DELETE FROM images WHERE product_id = ?";
            pstmt = connection.prepareStatement(deleteSql);
            pstmt.setInt(1, p.getId());

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the product to be deleted");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to delete a picture from cloudinary
     *
     * @param URL
     * @throws CommandException
     */
    public void removePictureFromCloudinary(String URL) throws CommandException {
        try {
            CLOUDINARY.uploader().destroy(getPublicIDFromURL(URL), ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new CommandException("Could not delete picture");
        }
    }

    /**
     * Private method to get the unique image id in cloudinary based on the
     * image's URL
     *
     * @param url
     * @return String
     */
    private String getPublicIDFromURL(String url) {
        return url.substring(62, url.length() - 4);
    }
}
