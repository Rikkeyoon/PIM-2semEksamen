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
 * @author carol
 */
public class AttributeMapper {

    public List<Integer> createAttributes(List<String> attributeNames) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "INSERT INTO attributes(attribute_name) VALUE(?)";
        List<Integer> attributeIds = new ArrayList<>();
        try {
            for (String name : attributeNames) {
                connection = PersistenceFacadeDB.getConnection();
                pstmt = connection.prepareStatement(insertSql);
                pstmt.setString(1, name);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new NullPointerException();
                }
                
                attributeIds.add(getLastInsertedId());
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new category attributes");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return attributeIds;
    }

    public int getLastInsertedId() throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        
        int id = 0;
        String selectSql = "SELECT LAST_INSERT_ID() FROM attributes LIMIT 1";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(selectSql);

            result = pstmt.executeQuery(selectSql);
            
            while (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not get newest id");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return id;
    }
}
