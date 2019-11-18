package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.util.List;
import logic.Product;
import logic.Category;
import logic.TemporaryProduct;

/**
 *
 * @author allan
 */
public class PersistenceFacadeDB implements IPersistenceFacade {

    private static IDatabaseConnection DBC;
    private IProductMapper pm = new ProductMapper();
    private ICategoryMapper cm = new CategoryMapper();

    public PersistenceFacadeDB(Boolean testmode) {
        try {
            DBC = new DatabaseConnection(testmode);
        } catch (CommandException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Connection getConnection() throws CommandException {
        return DBC.getConnection();
    }

    @Override
    public List<TemporaryProduct> getCatalog() throws CommandException {
        return pm.getAllProducts();
    }

    @Override
    public TemporaryProduct getProduct(int id) throws CommandException {
        return pm.getProduct(id);
    }

    @Override
    public TemporaryProduct getProduct(String name) throws CommandException {
        return pm.getProduct(name);
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
    public List<TemporaryProduct> getProductsByCategory(String category)
            throws CommandException {
        return pm.getProductsByCategory(category);

    }

    @Override
    public Category getCategory(String categoryname) throws CommandException {
        return cm.getCategory(categoryname);
    }

    @Override
    public TemporaryProduct getProductWithCategoryAttributes(int id) 
            throws CommandException {
        return pm.getProductWithCategoryAttributes(id);
    }

}
