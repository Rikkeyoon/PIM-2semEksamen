package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;
import org.apache.commons.lang.StringUtils;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 * The purpose of LogicController is to create Java Objects and to delegate
 * tasks to the PersistenceFacade
 *
 * @author carol, Nina
 */
public class LogicController {

    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);
    //private static IPersistenceFacade pf = new PersistenceFacadeDB(true);

    /**
     * A method to create a product The LogicController receives the new
     * product's information from the LogicFacade, creates a Product Object and
     * then passes the Product to the PersistenceFacade to get the product's
     * information stored
     *
     * @param id
     * @param itemnumber
     * @param name
     * @param brand
     * @param description
     * @param tags
     * @param categoryname
     * @param supplier
     * @param seotext
     * @param status
     * @param images
     * @return Product
     * @throws CommandException
     */
    public static Product createProduct(Product p, String category, String fileSelected, List<Part> parts) throws CommandException {
        List<Pair<String, Boolean>> imageURLs = null;

        p.setCategory(pf.getCategory(category));
        //creates product
        int id = pf.createProduct(p);
        p.setId(id);
        //saves attributes
        pf.createProductAttributes(p);
        //uploads images
        if (fileSelected != null) {
            imageURLs = pf.uploadImagesToCloudinary(parts, fileSelected);
        }
        p.setImages(imageURLs);
        //saves images
        pf.addImages(p);
        //saves tags
        pf.createProductTags(p.getId(), p.getTags());

        return p;
    }

    /**
     * A method to upload images to an extern service The LogicController
     * recieves information from the LogicFacade and passes it on to the
     * PersistenceFacade
     *
     * @param parts
     * @param primaryImage
     * @return List of Pair with String and boolean
     * @throws CommandException
     */
    public static List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage)
            throws CommandException {
        return pf.uploadImagesToCloudinary(parts, primaryImage);
    }

    /**
     * A method to update a product The LogicController recieves information
     * from the LogicFacade, updates the Product Object and passes the updated
     * product to the PersistenceFacade
     *
     * @param p
     * @param parameterMap
     * @param imageURLs
     * @return Product
     * @throws CommandException
     */
    public static Product updateProduct(Product p, String category, String[] picsToDelete, String fileSelected, List<Part> parts) throws CommandException {
        List<Pair<String, Boolean>> imageURLs = null;

        p.setCategory(pf.getCategory(category));
        //creates product
        pf.updateProduct(p);
        //saves attributes
        pf.updateProductAttributes(p);
        //uploads images
        if (fileSelected != null) {
            imageURLs = pf.uploadImagesToCloudinary(parts, fileSelected);
        }
        p.setImages(imageURLs);
        //saves images
        pf.addImages(p);
        if (picsToDelete != null && picsToDelete.length > 0) {
            p.removeImages(picsToDelete);
            pf.deleteImages(picsToDelete);
        }
        pf.updatePrimaryPicture(p.getId(), fileSelected);
        //saves tags
        pf.deleteTagsForProduct(p.getId());
        pf.createProductTags(p.getId(), p.getTags());
        pf.deleteUnusedTags();

        return p;

    }

    /**
     * Method to delete a product The LogicController recieves information from
     * the LogicFacade and passes it on to the PersistenceFacade
     *
     * @param p Product
     * @throws CommandException
     */
    public static void deleteProduct(Product p) throws CommandException {
        pf.deleteProduct(p);
    }

    /**
     * Method to get the catalog The LogicController recieves information from
     * the LogicFacade and passes it on to the PersistenceFacade
     *
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getCatalog() throws CommandException {
        return pf.getCatalog();
    }

    /**
     * Method for getting a specific product by the database's id The
     * LogicController recieves product's database id from the LogicFacade and
     * passes it on to the PersistenceFacade and returns the found product
     *
     * @param id
     * @return Product
     * @throws CommandException
     */
    public static Product getProduct(int id) throws CommandException {
        Product product = null;
        try {
            product = pf.getProductWithCategoryAttributes(id);
        } catch (CommandException ex) {
            //Doesn't need to give this command exception to the user. 
            //It just means that the product with that id doesn't have any category attributes yet
        }
        if (product == null) {
            product = pf.getProduct(id);
        }

        product.setTags(pf.getTagsForProductWithID(product.getId()));
        return product;

    }

    /**
     * A method for getting products by name The LogicController recieves
     * products' name or part of a name from the LogicFacade and passes it on to
     * the PersistenceFacade and returns the found products
     *
     * @param name
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByName(String name) throws CommandException {
        return pf.getProductsByName(name);
    }

    /**
     * A method for products by category The LogicController recieves products'
     * category or part of a category from the LogicFacade and passes it on to
     * the PersistenceFacade and returns the found products
     *
     * @param category
     * @return
     * @throws CommandException
     */
    public static List<Product> getProductsByCategory(String category)
            throws CommandException {
        return pf.getProductsByCategory(category);
    }

    /**
     * A method for getting products that share a tag The LogicController
     * receives information from the LogicFacade and passes it on to the
     * PersistenceFacade and returns the found products
     *
     * @param tag
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsByTag(String tag) throws CommandException {
        return pf.getProductsWithTagSearch(tag);
    }

    /**
     * A method for getting a specific category by its name The LogicController
     * recieves information from the LogicFacade and passes it on to the
     * PersistenceFacade and returns the found category
     *
     * @param categoryname
     * @return Category
     * @throws CommandException
     */
    public static Category getCategory(String categoryname) throws CommandException {
        return pf.getCategory(categoryname);
    }

    /**
     * A method for creating a new category The LogicController recieves
     * information from the LogicFacade, creates a new Category Object and
     * passes it on to the PersistenceFacade to be stored
     *
     * @param categoryname
     * @param attributes
     * @return Category
     * @throws CommandException
     */
    public static Category createCategory(String categoryname, String[] attributes)
            throws CommandException {
        List<String> attributeList = new ArrayList<>();
        for (String attribute : attributes) {
            if (StringUtils.isNotBlank(attribute)) {
                attributeList.add(attribute);
            }
        }
        Category c = new Category(categoryname, attributeList);
        pf.createCategory(c);
        return c;
    }

    /**
     * A method for editing a category's data The LogicController recieves
     * information from the LogicFacade, updates the category's data based on
     * the given information and passes it on to the PersistenceFacade to be
     * changed in the storage
     *
     * @param categoryname
     * @param attributes
     * @return Category
     * @throws CommandException
     */
    public static Category editCategory(String categoryname, String[] attributes)
            throws CommandException {
        Category c = pf.getCategory(categoryname);
        List<String> oldAttributes = c.getAttributes();
        List<String> newAttributes = new ArrayList<>();
        for (String attribute : oldAttributes) {
            newAttributes.add(attribute);
        }

        for (String attribute : attributes) {
            newAttributes.add(attribute);
        }
        c.setAttributes(newAttributes);
        pf.editCategory(c);
        return c;
    }

    /**
     * A method for getting all categories The LogicController recieves
     * information from the LogicFacade and passes it on to the
     * PersistenceFacade which returns all stored categories
     *
     * @return List of Categories
     * @throws CommandException
     */
    public static List<Category> getCategories() throws CommandException {
        return pf.getCategories();
    }

    /**
     * A private method for creating a category attribute map with all the
     * product's category's attributes
     *
     * @param product
     * @return Map with String, sTring
     * @throws CommandException
     */
    private static Map<String, String> createCategoryAttributeMap(Product product)
            throws CommandException {
        Category category = product.getCategory();
        Map<String, String> categoryAttributes;

        if (product.getCategoryAttributes() != null) {
            categoryAttributes = product.getCategoryAttributes();
        } else {
            categoryAttributes = new HashMap<>();
            List<String> attributes = category.getAttributes();

            for (String attribute : attributes) {
                categoryAttributes.putIfAbsent(attribute, "");
            }
        }

        return categoryAttributes;

    }

    public static void updateCategoryAttributename(String oldAttr, String newAttr) throws CommandException {
        pf.updateCategoryAttributename(oldAttr, newAttr);
    }

    public static void deleteAttributeFromCategory(List<String> removeAttr) throws CommandException {
        pf.deleteAttributeFromCategory(removeAttr);
    }

}
