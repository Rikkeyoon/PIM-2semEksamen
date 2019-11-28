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
 * The purpose of the AttributeMapper is to save the categories attributes in
 * the database and to edit the stored data when necessary
 *
 * @author carol
 */
public class AttributeMapper {

    /**
     * Method to get the unique storage id for a specific attribute based on the
     * name of the attribute
     *
     * @param attributename
     * @return int
     * @throws CommandException
     */
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

    /**
     * Method to insert the new attributes into the database
     *
     * @param attributeNames
     * @return List of ints The attributes unique storage id, which are auto
     * incremented
     * @throws CommandException
     */
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
            throw new CommandException("Could not create new category attributes");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return attributeIds;
    }

    /**
     * Private method to get the last inserted id in the database, since the id
     * is auto incremented
     *
     * @param connection It uses the same connection, as the method which calls
     * this method so it can get the correct id
     * @return int The unique database id
     * @throws CommandException
     */
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

}
