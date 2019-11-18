package logic;

import exception.CommandException;
import java.util.List;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author carol
 */
public class LogicController {
    
    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);

    public static Product createProduct(int id, String name, String description, 
            String category) throws CommandException {
        Product p = new Product(id, name, description, category);
        pf.createProduct(p);
        return p;
    }
    
    public static Product updateProduct(Product p, String name, String description,
            String category) throws CommandException {
        p.setName(name);
        p.setDescription(description);
        p.setCategoryname(category);
        pf.updateProduct(p);
        return p;
    }

    public static void deleteProduct(Product p) throws CommandException{
        pf.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        return pf.getCatalog();
    }

    public static Product getProduct(int id) throws CommandException {
        return pf.getProduct(id);
    }

    public static Category getCategory(String categoryname) throws CommandException {
        return pf.getCategory(categoryname);
    }
    
}
