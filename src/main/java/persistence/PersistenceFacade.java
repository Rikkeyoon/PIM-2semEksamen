package persistence;

import exception.CommandException;
import logic.Product;/**
 *
 * @author allan
 */

public class PersistenceFacade {

    private static IDataSourceController DSController = new DataSourceController(false);

    public static void getProducts() throws CommandException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void getProduct() throws CommandException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void createProduct(Product p) throws CommandException{
        DSController.createProduct(p);
    }

    public static void updateProduct() throws CommandException {
        DSController.updateProduct();
    }

}