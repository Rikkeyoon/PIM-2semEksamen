package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * The purpose of the AttributeMapper is to save the categories attributes in
 * the database and to edit the stored data when necessary
 *
 * @author carol, Allan, Nina
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
                + "WHERE attribute_name = ?;";
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
                throw new SQLException("id = 0");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find category attribute" + ex.getMessage());
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
        int id = 0;
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            for (String name : attributeNames) {
                pstmt.setString(1, name);
                try{
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                }catch(SQLException ex){
                    if(ex.getErrorCode() != 1062){
                        throw ex;
                    }else{
                        id = getAttributeId(name);
                    }
                }

                attributeIds.add(id);
            }
        } catch (SQLException ex) {
            throw new CommandException("Could not create new category attributes");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return attributeIds;
    }

    public void updateAttributeName(String oldAttr, String newAttr) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "UPDATE attributes SET attribute_name = ? WHERE attribute_name = ?;";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setString(1, newAttr);
            pstmt.setString(2, oldAttr);

            pstmt.executeUpdate();

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not update name on attribute");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    public void deleteAttribute(int i) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "DELETE FROM attributes WHERE id = ?";
        try {
            if(i <= 0) throw new CommandException("");
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setInt(1, i);

            pstmt.executeUpdate();

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not delete attribute from attributes");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    public void deleteAttribute(String name) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "DELETE FROM attributes WHERE attribute_name = ?";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setString(1, name);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getErrorCode() != 1451) {
                throw new CommandException("Could not delete attribute from attributes");
            }
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

}
