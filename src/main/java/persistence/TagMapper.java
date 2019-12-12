package persistence;

import com.cloudinary.utils.StringUtils;
import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logic.Product;
import org.apache.commons.dbutils.DbUtils;

/**
 * The purpose of the TagMapper is to save the tags in the database and to edit
 * the stored data when necessary
 *
 * @author allan
 */
public class TagMapper {

    /**
     * Method to store new tags for a specific product in the database
     *
     * @param productId
     * @param tags
     * @throws CommandException
     */
    public void createTagsAndProductRelation(int productId, List<String> tags)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "INSERT INTO product_tags VALUES (?, ?);";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (String tag : tags) {
                try {
                    pstmt.setInt(1, getTagIdFromName(tag));
                    pstmt.setInt(2, productId);
                    pstmt.executeUpdate();
                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 1062) {
                        throw ex;
                    }
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create tag relation");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    /**
     * Method for storing new tags in the database
     *
     * @param tags
     * @throws CommandException
     */
    public void createTags(List<String> tags) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String insertSql = "INSERT INTO tags(name) VALUE(?)";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (String tag : tags) {
                try {
                    pstmt.setString(1, tag);
                    pstmt.executeUpdate();

                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 1062) {
                        throw ex;
                    }
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("could not create tags");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    /**
     * Method to get all tags for a specific product based on the product's
     * unique database id
     *
     * @param id
     * @return List of Strings
     * @throws CommandException
     */
    public List<String> getTagsForProductWithID(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<String> tags = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT name FROM tags WHERE id IN"
                    + "(SELECT tag_id FROM product_tags WHERE product_id = ?)";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);

            result = pstmt.executeQuery();

            while (result.next()) {
                tags.add(result.getString("name"));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch tags to product");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return tags;
    }

    /**
     * Method to get the unique database ids for products that share a tag or
     * share the String as part of their tags' names
     *
     * @param tagSearch
     * @return List of ints
     * @throws CommandException
     */
    public List<Integer> getProductsIDFromTagNameSearch(String tagSearch)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Integer> productIDs = new ArrayList<>();
        try {
            if(StringUtils.isBlank(tagSearch)|| tagSearch == null) throw new NullPointerException();
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT DISTINCT product_id FROM product_tags "
                    + "WHERE tag_id IN (SELECT id FROM tags WHERE name LIKE ?);";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + tagSearch + '%');

            result = pstmt.executeQuery();

            while (result.next()) {
                productIDs.add(result.getInt("product_id"));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch productIds for tag");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return productIDs;
    }


    /**
     * Method to get the unique database id for a specific tag
     *
     * @param tag
     * @return int
     * @throws CommandException
     */
    public int getTagIdFromName(String tag) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        int returnID = 0;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT id FROM tags where name like ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + tag + '%');

            result = pstmt.executeQuery();

            if (result.next()) {
                returnID = result.getInt("id");
            } else {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("could not fetch tag id from tag name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return returnID;
    }

    /**
     * Method to delete all stored tags for a specific product from the database
     *
     * @param id Product id
     * @throws CommandException
     */
    public void deleteTagsForProduct(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            if(id <= 0) throw new NullPointerException();
            connection = PersistenceFacadeDB.getConnection();
            String deleteSql = "DELETE FROM product_tags WHERE product_id = ?";
            pstmt = connection.prepareStatement(deleteSql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not delete tags for product");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to get all stored tags from the database
     *
     * @return List of ints
     * @throws CommandException
     */
    public List<Integer> getAllTagIds() throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Integer> tags = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT id FROM tags;";
            pstmt = connection.prepareStatement(selectSql);

            result = pstmt.executeQuery();

            while (result.next()) {
                tags.add(result.getInt("id"));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch tags to product");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return tags;
    }

    /**
     * Method to delete tags that aren't used by any products stored in the
     * database
     *
     * @throws CommandException
     */
    public void deleteUnusedTags() throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        List<Integer> tagids = getAllTagIds();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String deleteSql = "DELETE FROM tags WHERE id = ?";
            pstmt = connection.prepareStatement(deleteSql);
            for (Integer i : tagids) {
                try {
                    pstmt.setInt(1, i);
                    pstmt.executeUpdate();
                } catch (SQLException ex) {
                    if (ex.getErrorCode() != 1451) {
                        throw ex;
                    }
                }
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("could not delete");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

}
