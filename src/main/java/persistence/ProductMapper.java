package persistence;

import persistence.interfaces.IProductMapper;
import logic.Product;
import exception.CommandException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nina Lisakowski
 */
public class ProductMapper implements IProductMapper {

    private Connection connection;

    public Product getProduct(String name) throws CommandException {
        Product product = null;
        try {
            this.connection = DataSourceFacade.getConnection();
            String selectSql = "SELECT * FROM products WHERE name LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(2, '%' + name + '%');

            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String description = result.getString(3);

                product = new Product(id, name, description);
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        } catch (ClassNotFoundException | IOException ex) {
            throw new CommandException("Could not connect to database");
        }

    }

    @Override
    public List<Product> getProductsByCategory(String name) throws CommandException {

    }

    public List<Product> getAllProducts() throws CommandException {
        List<Product> products = new ArrayList();

        try {
            this.connection = DataSourceFacade.getConnection();
            String selectSql = "SELECT * FROM products";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);

            ResultSet result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);

                products.add(new Product(id, name, description));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products");
        } catch (ClassNotFoundException | IOException ex) {
            throw new CommandException("Could not connect to database");
        }
        return products;
    }

}
