package logic;

import exception.CommandException;
import persistence.PersistenceFacade;

/**
 *
 * @author carol
 */
public class LogicController {

    public static Product createProduct(int id, String name, String description, 
            String category) throws CommandException {
        Product p = new Product(id, name, description, category);
        PersistenceFacade.createProduct(p);
        return p;
    }
    
    public static Product updateProduct() throws CommandException {
        Product p = new Product(id, name, description, category);
        PersistenceFacade.updateProduct();
        return p;
    }
    
}
