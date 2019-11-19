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

    public String getAttribute(String attributename) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;

        String selectSql = "SELECT attribute_name FROM attributes "
                + "WHERE attribute_name = ?";
        String attribute = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, attributename);

            result = pstmt.executeQuery();

            while (result.next()) {
                attribute = result.getString(1);

            }
            if (attribute == null) {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find category attribute");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return attribute;
    }

    public List<Integer> createAttributes(List<String> attributeNames)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "INSERT INTO attributes(attribute_name) VALUE(?)";
        List<Integer> attributeIds = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (String name : attributeNames) {
                pstmt.setString(1, name);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new NullPointerException();
                }

                attributeIds.add(getLastInsertedId(connection));
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new category attributes");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return attributeIds;
    }

    public int getLastInsertedId(Connection connection) throws CommandException {
        PreparedStatement pstmt = null;
        ResultSet result = null;

        int id = 0;
        String selectSql = "SELECT last_insert_id() FROM attributes LIMIT 1";
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
