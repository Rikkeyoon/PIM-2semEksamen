package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import logic.Product;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author allan
 */
public class TagMapper implements ITagMapper {

    @Override
    public void createTagsAndProductRelation(int productID, List<String> tags) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "INSERT INTO tags_products VALUES ( ? , ?);";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (String tag : tags) {
                try{
                pstmt.setInt(1, getTagIdFromName(tag));
                pstmt.setInt(2, productID);
                pstmt.executeUpdate();
                }catch(SQLException ex){
                    if(ex.getErrorCode() != 1062){
                        throw ex;
                    }
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create tag relation " + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    @Override
    public void createTags(List<String> tags) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String insertSql = "INSERT INTO tags(name) VALUE(?)";
        try{
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (String tag : tags) {
                try{
                pstmt.setString(1, tag);
                pstmt.executeUpdate();

                }catch(SQLException ex){
                    if(ex.getErrorCode() != 1062){
                        throw ex;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new CommandException(ex.getMessage() + " " + ex.getErrorCode());
        }
    }
    
    @Override
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
            throw new CommandException("Could not fetch tags to product: " + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return tags;
    }
    
    @Override
    public List<Integer> getProductsIDFromTagNameSearch(String tagSearch)throws CommandException{
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Integer> productIDs = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT DISTINCT product_id FROM tags_products WHERE tag_id IN (SELECT id FROM tags WHERE name LIKE ?);";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + tagSearch + '%');

            result = pstmt.executeQuery();

            while (result.next()) {
                productIDs.add(result.getInt("product_id"));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch tags to product: " + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return productIDs;
    }

    @Override
    public List<Integer> updateTags(Product p) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "INSERT INTO tags(name) VALUE(?)";
        List<Integer> tagIds = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (String tag : p.getTags()) {
                pstmt.setString(1, tag);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated == 0) {
                   throw new SQLException(); 
                }

                tagIds.add(getLastInsertedId(connection));
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new tags");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return tagIds;
    }

    public int getTagIdFromName(String tag) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        int returnID = 0;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT id FROM tags where name like ?;";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + tag + '%');

            result = pstmt.executeQuery();

            if (result.next()) {
                returnID = result.getInt("id");
            } else {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return returnID;
    }

    private int getLastInsertedId(Connection connection) throws CommandException {
        PreparedStatement pstmt = null;
        ResultSet result = null;

        int id = 0;
        String selectSql = "SELECT last_insert_id() FROM tags LIMIT 1";
        try {
            pstmt = connection.prepareStatement(selectSql);

            result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                id = result.getInt(1);
            }
            if (id == 0) {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not get newest id");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(result);
        }
        return id;
    }


}
