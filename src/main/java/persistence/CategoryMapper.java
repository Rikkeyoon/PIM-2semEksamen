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

/**
 *
 * @author carol
 */
public class CategoryMapper implements ICategoryMapper {

    private Connection connection;

    @Override
    public void createCategory(Product product) throws CommandException {
        connection = PersistenceFacadeDB.getConnection();
        String insertSql = "INSERT INTO categories VALUE(?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, product.getCategoryname());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new NullPointerException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not create new category");
        }
    }

    @Override
    public List<String> getAllCategories() throws CommandException {
        connection = PersistenceFacadeDB.getConnection();
        List<String> categories = new ArrayList();
        String selectSql = "SELECT * FROM categories";
        try {
            PreparedStatement pstmt = connection.prepareStatement(selectSql);

            ResultSet result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                categories.add(result.getString(1));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any categories");
        }
        return categories;
    }

    @Override
    public Category getCategory(String categoryname) throws CommandException {
        connection = PersistenceFacadeDB.getConnection();
        Category category = null;
        try {
            String selectSql = "SELECT * FROM category WHERE category_name LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + categoryname + '%');

            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                String categoryName = result.getString(1);

                category = new Category(categoryname);
            }

            if (category == null) {
                throw new SQLException();
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        }
        return category;
    }

}
