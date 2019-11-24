/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.TemporaryProduct;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
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

    @Override
    public List<String> getTagsForProductWithID(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<String> tags = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT name FROM tags WHERE id IN(SELECT tag_id FROM tags_products WHERE product_id = ?);";
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
