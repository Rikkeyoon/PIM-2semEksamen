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
 * @author carol, Allan, Nina
 */
public class AttributeMapper implements IAttributeMapper {

    @Override
    public int getAttributeId(String attributename) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;

        String selectSql = "SELECT id FROM attributes "
                + "WHERE attribute_name = ?";
        int id = 0;
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, attributename);

            result = pstmt.executeQuery();

            while (result.next()) {
                id = result.getInt(1);

            }
            if (id == 0) {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find category attribute");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return id;
    }

    @Override
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
                    throw new SQLException();
                }

                attributeIds.add(getLastInsertedId(connection));
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("createAttributes Could not create new category attributes" + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return attributeIds;
    }

    private int getLastInsertedId(Connection connection) throws CommandException {
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

    @Override
    public void updateCategoryAttributename(String oldAttr, String newAttr) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "UPDATE attributes SET attribute_name = ? WHERE attribute_name = ?;";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setString(1, newAttr);
            pstmt.setString(2, oldAttr);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException();

            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not update name on attribute");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    @Override
    public void deleteAttribute(int i) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "DELETE FROM attributes WHERE id = ?";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setInt(1, i);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException();

            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not delete attribute from attributes");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

}
