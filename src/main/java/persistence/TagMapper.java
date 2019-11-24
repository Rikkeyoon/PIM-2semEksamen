package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.Product;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author allan
 */
public class TagMapper implements ITagMapper {

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