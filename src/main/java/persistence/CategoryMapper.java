package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.Category;
import org.apache.commons.dbutils.DbUtils;

/**
 * The purpose of the CategoryMapper is to save the categories in the database
 * and to edit the stored data when necessary
 *
 * @author carol
 */
public class CategoryMapper {

    /**
     * Method to insert the newly created category into the database
     *
     * @param c Category
     * @throws CommandException
     */
    public void createCategory(Category c) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String insertSql = "INSERT INTO categories VALUE(?)";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, c.getCategoryname());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new NullPointerException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new category");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    /**
     * Method to insert category attributes into the database with the
     * category's id and the database ids for the attributes
     *
     * @param category
     * @param attributeIds
     * @throws CommandException
     */
    public void createCategoryAttributes(Category category, List<Integer> attributeIds)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String insertSql = "INSERT INTO category_attributes VALUES(?,?)";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            for (Integer id : attributeIds) {
                pstmt.setString(1, category.getCategoryname());
                pstmt.setInt(2, id);

                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new NullPointerException();
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new category");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    /**
     * Method to get all stored categories
     *
     * @return List of Categories
     * @throws CommandException
     */
    public List<Category> getAllCategories() throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String selectSql = "SELECT * FROM categories";

        List<Category> categories = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(selectSql);

            result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                String name = result.getString(1);

                categories.add(getCategory(name));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any categories");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return categories;
    }

    /**
     * Method to get a single stored category by its name
     *
     * @param categoryname
     * @return Category
     * @throws CommandException
     */
    public Category getCategory(String categoryname) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String selectSql = "SELECT attribute_name FROM categories_and_attributes "
                + "WHERE category_name LIKE ?";

        Category category = null;
        List<String> attributes = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();

            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, categoryname);

            result = pstmt.executeQuery();

            while (result.next()) {
                attributes.add(result.getString(1));

            }
            category = new Category(categoryname, attributes);
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any category with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return category;
    }

}
