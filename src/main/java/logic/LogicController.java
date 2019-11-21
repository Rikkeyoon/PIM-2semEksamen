package logic;

import exception.CommandException;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.http.Part;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author carol
 */
public class LogicController {
    
    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);

    public static Product createProduct(int id, String name, String description, 
            String category, List<Pair<String, Boolean>> images) throws CommandException {
        Product p = new Product(id, name, description, category, images);
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

    public static List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException{
        return pf.uploadImages(parts, primaryImage);
    }
    
}
