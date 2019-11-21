package logic;

import exception.CommandException;
import java.util.List;
import java.util.Map;
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

    public static Product updateProduct(Product p, Map<String, String[]> parameterMap)
            throws CommandException {
        return LogicController.updateProduct(p, parameterMap);
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

    public static List<Product> getProductsByName(String name) 
            throws CommandException {
        return LogicController.getProductsByName(name);
    }

    public static List<Product> getProductsByCategory(String category)
            throws CommandException {
        return LogicController.getProductsByCategory(category);
    }

    public static Category createCategory(String categoryname, String[] attributes)
            throws CommandException {
        return LogicController.createCategory(categoryname, attributes);
    }


    public static Category editCategory(String categoryname, String[] attributes)
            throws CommandException {
        return LogicController.editCategory(categoryname, attributes);
    }

    public static List<Category> getCategories() throws CommandException {
        return LogicController.getCategories();
    }

    public static List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException{
        return LogicController.uploadImages(parts, primaryImage);
    }

}
