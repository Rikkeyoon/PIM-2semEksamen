package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public int createCategory(Category c) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int id = 0;
        
        String insertSql = "INSERT INTO categories (category_name) VALUES (?);";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, c.getCategoryname());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new category");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
        return id;
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
                try {
                    pstmt.setInt(1, category.getId());
                    pstmt.setInt(2, id);

                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    if (e.getErrorCode() != 1062) {
                        throw e;
                    }
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new attributes");
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
                int id = result.getInt("id");
                String name = result.getString("category_name");

                categories.add(new Category(id, name, getCategoryAttributes(id)));
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
        String selectSql = "SELECT * FROM categories WHERE category_name LIKE ?";

        Category category = null;
        int id = 0;
        try {
            connection = PersistenceFacadeDB.getConnection();

            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, categoryname);

            result = pstmt.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
                String name = result.getString("category_name");

            }
            category = new Category(id, categoryname, getCategoryAttributes(id));
            if(id == 0){
                throw new CommandException("ID was not accepted" + pstmt);
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any category with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return category;
    }
    
    public List<Category> getCategoriesFromSearch(String categoryname) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String selectSql = "SELECT * FROM categories WHERE category_name LIKE ?";

        List<Category> categories = new ArrayList<>();
        int id = 0;
        try {
            connection = PersistenceFacadeDB.getConnection();

            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + categoryname + '%');

            result = pstmt.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
                String name = result.getString("category_name");
                categories.add(new Category(id, categoryname, getCategoryAttributes(id)));
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any category with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return categories;
    }

    public Category getCategory(int categoryid) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String selectSql = "SELECT * FROM categories "
                + "WHERE id = ?";

        Category category = null;
        int id = 0;
        String name = null;
        try {
            connection = PersistenceFacadeDB.getConnection();

            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, categoryid);

            result = pstmt.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("category_name");

            }
            category = new Category(id, name, getCategoryAttributes(id));
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Coulld not find any category with that ID");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return category;
    }

    public List<String> getCategoryAttributes(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        String selectSql = "SELECT attribute_name FROM categories_and_attributes "
                + "WHERE category_id = ?";

        Category category = null;
        List<String> attributes = new ArrayList<>();
        try {
            connection = PersistenceFacadeDB.getConnection();

            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);

            result = pstmt.executeQuery();

            while (result.next()) {
                attributes.add(result.getString(1));

            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not fetch attributes");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return attributes;
    }

    public void deleteCategoryAttribute(int i) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "DELETE FROM category_attributes WHERE attribute_id = ?";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setInt(1, i);

            pstmt.executeUpdate();

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not delete attribute from category");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    public void deleteCategory(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String deleteSQL = "DELETE FROM categories WHERE id = ?;";

        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(deleteSQL);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new CommandException("Could not delete category");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

}
