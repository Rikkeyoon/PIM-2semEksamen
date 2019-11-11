package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.Product;

/**
 *
 * @author carol
 */
public class CategoryMapper implements ICategoryMapper {

    private Connection connection;

    @Override
    public void create(Product product) throws CommandException {
        if (!isExistingCategory(product.getCategoryname())) {
            connection = DataSourceController.getConnection();
            try {
            String selectSql = "INSERT INTO categories VALUE(?)";

            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, product.getCategoryname());

            pstmt.executeUpdate();
            } catch (SQLException | NullPointerException ex) {
                throw new CommandException("Could not create new category");
            }
        }
    }

    public List<String> getAllCategories() throws CommandException {
        connection = DataSourceController.getConnection();
        List<String> categories = new ArrayList();

        try {
            String selectSql = "SELECT * FROM categories";
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

    private boolean isExistingCategory(String categoryname) throws CommandException {
        List<String> categories = getAllCategories();
        for (String category : categories) {
            if (category.equals(categoryname)) {
                return true;
            }
        }
        return false;
    }

}
