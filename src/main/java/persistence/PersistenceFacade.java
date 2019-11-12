package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */

public class PersistenceFacade {

    private static IDataSourceController DSController = new DataSourceController(false);

    public static List<Product> getCatalog() throws CommandException {
        return DSController.getProducts();
    }

    public static void getProduct() throws CommandException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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