package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */

public class PersistenceFacade {

    //private static IDataSourceController DSController = new DataSourceController(false);
    //Til test
    private static IDataSourceController DSController = new DataSourceController(true);

    public static List<Product> getCatalog() throws CommandException {
        return DSController.getProducts();
    }
    
    public static List<Product> getAllProductsWithCategoryAttributes() throws CommandException {
        return DSController.getAllProductsWithCategoryAttributes();
    }

    public static Product getProduct(int id) throws CommandException {
        return DSController.getProduct(id);
    }

    public static void createProduct(Product p) throws CommandException{
        DSController.createProduct(p);
    }

    public static void updateProduct(Product p) throws CommandException {
        DSController.updateProduct(p);
    }

    public static void deleteProduct(Product p) throws CommandException {
        DSController.deleteProduct(p);
    }

}