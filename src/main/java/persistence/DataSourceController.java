package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */
public class DataSourceController implements IDataSourceController {

    private static Boolean isTestmode;
    private IProductMapper pm = new ProductMapper();
    private ICategoryMapper cm = new CategoryMapper();

    public DataSourceController(boolean isTestmode) {
        this.isTestmode = isTestmode;
    }

    public static Connection getConnection() throws CommandException {
        try {
            return DBConnection.getInstance(isTestmode).getBasicDS().getConnection();
        } catch (SQLException ex) {
            throw new CommandException("Could not get establish connection. " + ex.getMessage());
        }
    }

    @Override
    public void createProduct(Product p) throws CommandException {
        cm.createCategory(p);
        pm.create(p);
    }

    @Override
    public Product getProduct(int id) throws CommandException {
        return pm.getProduct(id);
    }

    @Override
    public Product getProduct(String name) throws CommandException {
        return pm.getProduct(name);
    }

    @Override
    public List<Product> getProducts() throws CommandException {
        return pm.getAllProducts();

    }

    @Override
    public void updateProduct(Product p) throws CommandException {
        cm.createCategory(p);
        pm.update(p);
    }

    @Override
    public void deleteProduct(Product p) throws CommandException {
        pm.delete(p);
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CommandException {
        //return pm.getProductsByCategory(category);
        throw new UnsupportedOperationException();
    }
}
