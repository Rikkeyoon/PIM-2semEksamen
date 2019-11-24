package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

}