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
    public void createCategory(Product product) throws CommandException {
        connection = DataSourceController.getConnection();
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
        connection = DataSourceController.getConnection();
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

}
