package logic;

import exception.CommandException;

/**
 *
 * @author carol
 */
public class LogicFacade {

    public static Product createProduct(int id, String name, String description, 
            String category) throws CommandException {
        return LogicController.createProduct(id, name, description, category);
    }
    
    public static Product updateProduct(Product p, String name, String description,
            String category) throws CommandException {
        return LogicController.updateProduct(p, name, description, category);
    }

    public static void deleteProduct(Product p) throws CommandException {
        LogicController.deleteProduct(p);
    }
    
}
