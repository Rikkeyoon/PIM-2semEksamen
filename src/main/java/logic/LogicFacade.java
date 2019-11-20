package logic;

import exception.CommandException;
import java.util.List;

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
            String category, String[] attributeValues) throws CommandException {
        return LogicController.updateProduct(p, name, description, category,
                attributeValues);
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

    public static List<Product> getProductsByName(String name) throws CommandException {
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

}
