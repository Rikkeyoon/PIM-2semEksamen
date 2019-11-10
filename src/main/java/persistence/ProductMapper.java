package persistence;

/**
 * @author Nina Lisakowski
 */
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

    @Override
    public void create(Product product) throws CommandException {
        try {
            this.connection = DataSourceFacade.getConnection();
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
            /*} catch (ClassNotFoundException | IOException ex) {
            throw new CommandException("Could not connect to database");
             */
        }
    }

    @Override
    public Product getProduct(String name) throws CommandException {
        Product product = null;
        try {
            this.connection = PersistenceFacade.getConnection();
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
            /*} catch (ClassNotFoundException | IOException ex) {
            throw new CommandException("Could not connect to database");
             */
        }
        return product;
    }

    @Override
    public List<Product> getProductsByCategory(List<String> names) throws CommandException {
        List<Product> products = new ArrayList();

        try {
            this.connection = DataSourceFacade.getConnection();
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
            /*} catch (ClassNotFoundException | IOException ex) {
            throw new CommandException("Could not connect to database");
             */
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts() throws CommandException {
        List<Product> products = new ArrayList();

        try {
            this.connection = PersistenceFacade.getConnection();
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
            /*} catch (ClassNotFoundException | IOException ex) {
            throw new CommandException("Could not connect to database");
             */
        }
        return products;
    }

}
