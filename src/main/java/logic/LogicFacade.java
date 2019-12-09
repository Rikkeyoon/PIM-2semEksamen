package logic;

import exception.CommandException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Part;

/**
 * The purpose of LogicFacade is to keep low coupling between the presentation
 * layer and the domainmodel/logic layer by being the first object beyond the
 * presentation layer to receive and coordinate a system operation
 *
 * @author carol
 */
public class LogicFacade {

    /**
     * A method for creating a Product The LogicFacade receives the new
     * product's information from the presentation layer and passes that
     * information on to the LogicController
     *
     * @param p Product
     * @param category
     * @return Product
     * @throws CommandException
     */
    public static Product createProduct(Product p, String category,
            String fileSelected, List<Part> parts) throws CommandException {
        return LogicController.createProduct(p, category, fileSelected, parts);
    }

    /**
     * A method to update a product The LogicFacade receives the product's
     * information from the presentation layer and passes that information on to
     * the LogicController
     *
     * @param p Product to be updated
     * @param parameterMap The various information from the presentation layer
     * @param imageURLs
     * @return Product
     * @throws CommandException
     */
    public static Product updateProduct(Product p, String category, String[] picsToDelete,
            String fileSelected, List<Part> parts) throws CommandException {
        return LogicController.updateProduct(p, category, picsToDelete, fileSelected, parts);
    }

    /**
     * A method to delete a product The LogicFacade receives the product's
     * information from the presentation layer and passes that information on to
     * the LogicController
     *
     * @param p Poduct
     * @throws CommandException
     */
    public static void deleteProduct(Product p) throws CommandException {
        LogicController.deleteProduct(p);
    }

    /**
     * A method to get all products that are in the catalog
     *
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getCatalog() throws CommandException {
        return LogicController.getCatalog();
    }

    /**
     * A method for getting a specific product by the product's database id The
     * LogicFacade receives the product's database id from the presentation
     * layer and passes that information on to the LogicController
     *
     * @param id
     * @return Product
     * @throws CommandException
     */
    public static Product getProduct(int id) throws CommandException {
        return LogicController.getProduct(id);
    }

    /**
     * A method for getting products that has the same name or that contains the
     * String in their names The LogicFacade receives the product's information
     * from the presentation layer and passes that information on to the
     * LogicController
     *
     * @param name String Part of name or entire name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByName(String name)
            throws CommandException {
        return LogicController.getProductsByName(name);
    }

    /**
     * A method for getting products that have the same category or products
     * which have a category that contains the String in the name The
     * LogicFacade receives the product's information from the presentation
     * layer and passes that information on to the LogicController
     *
     * @param category Part of a category name or entire name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByCategory(String category)
            throws CommandException {
        return LogicController.getProductsByCategory(category);
    }

    /**
     * A method for getting products that have the same tag or products which
     * have a tag which name contains the string The LogicFacade receives the
     * product's information from the presentation layer and passes that
     * information on to the LogicController
     *
     * @param tag Part of a tag name or the entire name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByTag(String tag)
            throws CommandException {
        return LogicController.getProductsByTag(tag);
    }

    /**
     * A method for creating a new category The LogicFacade receives the
     * category's name and attributes from the presentation layer and passes
     * that information on to the LogicController
     *
     * @param categoryname
     * @param attributes
     * @return Category
     * @throws CommandException
     */
    public static Category createCategory(String categoryname, String[] attributes)
            throws CommandException {
        return LogicController.createCategory(categoryname, attributes);
    }

    /**
     * A method for editing the category The LogicFacade receives the category's
     * new information from the presentation layer and passes that information
     * on to the LogicController
     *
     * @param categoryname
     * @param attributes
     * @return Category
     * @throws CommandException
     */
    public static Category editCategory(String categoryname, String[] attributes)
            throws CommandException {
        return LogicController.editCategory(categoryname, attributes);
    }

    /**
     * Method for getting all categories
     *
     * @return List of Categories
     * @throws CommandException
     */
    public static List<Category> getCategories() throws CommandException {
        return LogicController.getCategories();
    }

    /**
     * A method for uploading images The LogicFacade receives the product's
     * images' information from the presentation layer and passes that
     * information on to the LogicController
     *
     * @param parts
     * @param primaryImage
     * @return List of Pair with String and boolean
     * @throws CommandException
     */
    public static List<Image> uploadImages(List<Part> parts, String primaryImage)
            throws CommandException {
        return LogicController.uploadImages(parts, primaryImage);
    }

    public static void updateCategoryAttributename(String oldAttr, String newAttr) throws CommandException {
        LogicController.updateCategoryAttributename(oldAttr, newAttr);
    }

    public static void deleteAttributeFromCategory(List<String> removeAttr) throws CommandException {
        LogicController.deleteAttributeFromCategory(removeAttr);
    }

    /**
     * Method for converting a List of Products into a JSON String
     *
     * @param catalog List Product
     * @throws CommandException
     */
    public static void convertProductsToJSON(List<Product> catalog) throws CommandException {
        JSONConverter.convertProductsToJSON(catalog);
    }
    
    /**
     * Method for converting a List of Categories into a JSON String
     *
     * @param categories List Category
     * @throws CommandException
     */
    public static void convertCategoriesToJSON(List<Category> categories) throws CommandException {
        JSONConverter.convertCategoriesToJSON(categories);
    }

    /**
     * Method for converting a Java Object into a JSON String
     *
     * @param p Product
     * @throws CommandException
     */
    public static void convertObjectToJSON(Product p) throws CommandException {
        JSONConverter.convertObjectToJSON(p);
    }

    /**
     * Method for uploading JSON file and converting it to Java Objects
     *
     * @param parts List of Part
     * @throws exception.CommandException
     */
    public static void uploadJSON(List<Part> parts) throws CommandException {
        LogicController.uploadJSON(parts);
    }
    
    public static Category getCategoryFromName(String categoryName) throws CommandException{
        return LogicController.getCategoryFromName(categoryName);
    }

    public static void bulkEdit(Product p, List<String> bulkeditIDs) throws CommandException{
        LogicController.bulkEdit(p, bulkeditIDs);
    }

    /**
     * A method for getting products that have the same item number or products
     * which have an item number that contains the int in the number The
     * LogicFacade receives the product's information from the presentation
     * layer and passes that information on to the LogicController
     *
     * @param itemNumber Part of a category name or entire name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByItemNumber(int itemNumber)
            throws CommandException {
        return LogicController.getProductsByItemNumber(itemNumber);
    }

    /**
     * A method for getting products that have the same brand or products which
     * have a brand that contains the String in the name The LogicFacade
     * receives the product's information from the presentation layer and passes
     * that information on to the LogicController
     *
     * @param brand Part of a brand name or entire name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByBrand(String brand)
            throws CommandException {
        return LogicController.getProductsByBrand(brand);
    }

    /**
     * A method for getting products that have the same supplier or products
     * which have a supplier that contains the String in the name The
     * LogicFacade receives the product's information from the presentation
     * layer and passes that information on to the LogicController
     *
     * @param supplier Part of a supplier name or entire name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsBySupplier(String supplier)
            throws CommandException {
        return LogicController.getProductsBySupplier(supplier);
    }

    public static void bulkDelete(String[] bulkDelete) throws CommandException{
        LogicController.bulkDelete(bulkDelete);
    }
    
    public static void deleteCategory(int id) throws CommandException{
        LogicController.deleteCategory(id);
    }
}
