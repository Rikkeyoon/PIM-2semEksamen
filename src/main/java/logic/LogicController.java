package logic;

import exception.CommandException;
import java.util.List;
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
    
    public static Product updateProduct(Product p, String name, String description,
            String category) throws CommandException {
        p.setName(name);
        p.setDescription(description);
        p.setCategoryname(category);
        PersistenceFacade.updateProduct(p);
        return p;
    }

    public static void deleteProduct(Product p) throws CommandException{
        PersistenceFacade.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        return PersistenceFacade.getCatalog();
    }
    
}
