package logic;

import exception.CommandException;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.http.Part;

/**
 *
 * @author carol
 */
public class LogicFacade {

    public static Product createProduct(int id, String name, String description, 
            String category, List<Pair<String, Boolean>> images) throws CommandException {
        return LogicController.createProduct(id, name, description, category, images);
    }
    
    public static Product updateProduct(Product p, String name, String description,
            String category) throws CommandException {
        return LogicController.updateProduct(p, name, description, category);
    }

    public static void deleteProduct(Product p) throws CommandException {
        LogicController.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        return LogicController.getCatalog();
    }

    public static Product getProduct(int id) throws CommandException {
        return LogicController.getProduct(id);
    }

    public static Category createCategory(String categoryname) throws CommandException {
        return LogicController.getCategory(categoryname);
    }

    public static List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException{
        return LogicController.uploadImages(parts, primaryImage);
    }
}
