package persistence;

import exception.CommandException;
import java.sql.Connection;
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
        return DBConnection.getConnection(isTestmode);
    }

    @Override
    public void createProduct(Product p) throws CommandException {
        try {
            cm.createCategory(p);
        } catch (CommandException e) {
            //If an exception is thrown it means that the category already exits
            //We don't need to forward the message to the user
        }
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
        try {
            cm.createCategory(p);
        } catch (CommandException e) {
            //If an exception is thrown it means that the category already exits
            //We don't need to forward the message to the user
        }
        pm.update(p);
    }

    @Override
    public void deleteProduct(Product p) throws CommandException {
        pm.delete(p);
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CommandException {
        return pm.getProductsByCategory(category);
    }
}
