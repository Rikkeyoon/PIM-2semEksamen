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
    
    public static Product updateProduct() throws CommandException {
        return LogicController.updateProduct();
    }
    
}
