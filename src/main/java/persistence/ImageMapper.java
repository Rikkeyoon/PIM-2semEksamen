package persistence;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import exception.CommandException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.Part;
import logic.Image;
import org.apache.commons.dbutils.DbUtils;

/**
 * The purpose of the ImageMapper is to save the images in cloudinary and in the
 * database and to delete the stored images when necessary
 *
 * @author allan
 */
public class ImageMapper {

    private static final String UPLOAD_DIR = "img";
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static String working_dir = null;
    private static Cloudinary cloudinary = null;

   
    public ImageMapper() {
        if(OS.contains("win") || OS.contains("mac")){
            working_dir = System.getProperty("user.dir");
        }else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix")){
            working_dir = System.getProperty("catalina.base");
        } else {
            working_dir = "";
        }
    }

    /**
     * Method to upload the images to cloudinary
     *
     * @param parts
     * @param primaryImage String containing the URL of the primary image
     * @return List of Pair of Strign and boolean
     * @throws CommandException
     */
    public List<Image> uploadImages(List<Part> parts, String primaryImage)
            throws CommandException {
        List<Image> images = new ArrayList<>();
        try {
            //Creates img folder if none exist(temporary storage for image before uploaded to cloudinary)
            File uploadFolder = new File(working_dir + File.separator + UPLOAD_DIR);
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
                        part.write(working_dir + File.separator + UPLOAD_DIR
                                + File.separator + fileName);
                        File file = new File(working_dir + File.separator
                                + UPLOAD_DIR + File.separator + fileName);

                        Map uploadResult = null;
                        String URL = null;
                        Boolean bool = false;

                        uploadResult = getCloudinaryConnection().uploader().upload(file, ObjectUtils.emptyMap());

                        URL = (String) uploadResult.get(new String("url"));

                        if (part.getSubmittedFileName().equals(primaryImage.replaceAll("\\s+", ""))) {
                            bool = true;
                        }
                        images.add(new Image(URL, bool));

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
    public void addPictureURL(int productId, List<Image> images)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String insertSql = "INSERT INTO images VALUES (?, ? , ?)";
            pstmt = connection.prepareStatement(insertSql);
            for (Image image : images) {
                pstmt.setInt(1, productId);
                pstmt.setString(2, image.getUrl());
                pstmt.setBoolean(3, image.isPrimary());
                try {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    if (e.getErrorCode() != 1062) {
                        throw e;
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
    public List<Image> getPicturesWithId(int productId)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Image> images = new ArrayList<>();
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

                images.add(new Image(URL, bool));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("get pictures withID Could not fetch URLs to images" + ex.getMessage());
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
    public Image getPrimaryPictureWithId(int productId)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        Image image = null;
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

                image = new Image(URL, bool);
            }

            

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch URLs to images");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return image;
    }
    
     public List<Image> getPicturesForProduct(int productId)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Image> images = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM images WHERE product_id = ?;";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, productId);

            result = pstmt.executeQuery();
            while (result.next()) {
                String URL = result.getString("url");
                Boolean bool = result.getBoolean("primaryImage");

                images.add(new Image(URL, bool));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch URLs to images");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return images;
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
            pstmt.setString(2, '%' + imageURL + '%');

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not update primary picture");
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
            throw new CommandException("Could not find the image to be deleted");
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
    public void deleteAllImages(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String deleteSql = "DELETE FROM images WHERE product_id = ?";
            pstmt = connection.prepareStatement(deleteSql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("could not delete images");
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
            getCloudinaryConnection().uploader().destroy(getPublicIDFromURL(URL), ObjectUtils.emptyMap());
        } catch (IOException e) {
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
        return url.substring(61, url.length() - 4);
    }
    
    private Cloudinary getCloudinaryConnection()throws CommandException{
        if(cloudinary == null){
            try (InputStream prob = ImageMapper.class.getResourceAsStream("/cloudinary.properties");) {
            Properties pros = new Properties();
            pros.load(prob);
            
            cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", pros.getProperty("cloud_name"),
            "api_key", pros.getProperty("api_key"),
            "api_secret", pros.getProperty("api_secret")));
            }catch(IOException ex){
                throw new CommandException("Could not read Cloudinary Properties.");
            }
        }    
        return cloudinary;
    }
}
