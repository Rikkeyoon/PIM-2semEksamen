package persistence;

/**
 * @author Nina Lisakowski
 */
import persistence.interfaces.IProductMapper;
import logic.Product;
import exception.CommandException;
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

    @Override
    public void create(Product product) throws CommandException {
        connection = DataSourceController.getConnection();
        try {
            String selectSql = "INSERT INTO products "
                    + "(id, name, description, category_name) VALUES"
                    + "(?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getCategoryname());

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new CommandException("Something went wrong. Try again!");
        }
    }

    @Override
    public Product getProduct(String name) throws CommandException {
        connection = DataSourceController.getConnection();
        Product product = null;
        try {
            String selectSql = "SELECT * FROM products WHERE name LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + name + '%');

            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String description = result.getString(3);
                String categoryname = result.getString(4);

                product = new Product(id, name, description, categoryname);
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        }
        return product;
    }

    @Override
    public List<Product> getProductsByCategory(List<String> names) 
            throws CommandException {
        connection = DataSourceController.getConnection();
        List<Product> products = new ArrayList();

        try {
            for (String name : names) {
                String selectSql = "SELECT * FROM products WHERE name LIKE ?";
                PreparedStatement pstmt = connection.prepareStatement(selectSql);
                pstmt.setString(1, '%' + name + '%');

                ResultSet result = pstmt.executeQuery();

                while (result.next()) {
                    int id = result.getInt(1);
                    String description = result.getString(3);
                    String categoryname = result.getString(4);

                    products.add(new Product(id, name, description, categoryname));
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the products with the chosen name");
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts() throws CommandException {
        connection = DataSourceController.getConnection();
        List<Product> products = new ArrayList();

        try {

            String selectSql = "SELECT * FROM products";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);

            ResultSet result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);
                String categoryname = result.getString(4);

                products.add(new Product(id, name, description, categoryname));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products");
        }
        return products;
    }

    public void update(Product product) throws CommandException {
        connection = DataSourceController.getConnection();
        try {
            String updateSql = "UPDATE products SET name = ?, description = ?, "
                    + "category_name = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(updateSql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setString(3, product.getCategoryname());
            pstmt.setInt(4, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID");
        }
    }
}
