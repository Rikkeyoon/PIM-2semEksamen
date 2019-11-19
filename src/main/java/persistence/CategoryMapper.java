package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.Category;
import logic.Product;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author carol
 */
public class CategoryMapper implements ICategoryMapper {

    @Override
    public void createCategory(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String insertSql = "INSERT INTO categories VALUE(?)";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, product.getCategory().getCategoryname());

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

    @Override
    public void createCategoryAttributes(Category category, List<Integer> attributeIds)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String insertSql = "INSERT INTO category_attributes VALUES(?,?)";
        try {
            for (Integer id : attributeIds) {
                connection = PersistenceFacadeDB.getConnection();
                pstmt = connection.prepareStatement(insertSql);
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

    @Override
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

    @Override
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
            pstmt.setString(1, '%' + categoryname + '%');

            result = pstmt.executeQuery();

            while (result.next()) {
                attributes.add(result.getString(1));
            }

            category = new Category(categoryname, attributes);

            if (category == null) {
                throw new SQLException();
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any category with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return category;
    }
}
