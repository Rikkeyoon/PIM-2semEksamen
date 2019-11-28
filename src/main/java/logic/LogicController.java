package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;
import org.apache.commons.lang.StringUtils;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 * The purpose of LogicController is to create Java Objects and to delegate
 * tasks to the PersistenceFacade
 *
 * @author carol
 */
public class LogicController {

    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);
//    private static IPersistenceFacade pf = new PersistenceFacadeDB(true);

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
    public static Product createProduct(int id, int itemnumber, String name,
            String brand, String description, String tags, String categoryname,
            String supplier, String seotext, int status,
            List<Pair<String, Boolean>> images) throws CommandException {
        Category category = getCategory(categoryname);
        Product p = new Product(id, itemnumber, name, brand, description,
                category, supplier, seotext, status, images);
        p.setCategoryAttributes(createCategoryAttributeMap(p));
        pf.createProduct(p);

        List<String> tagsList = Arrays.asList(tags.split(",[ ]*"));
        pf.createProductTags(pf.getProductStorageId(p), tagsList);
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
    public static Product updateProduct(Product p, Map<String, String[]> parameterMap,
            List<Pair<String, Boolean>> imageURLs) throws CommandException {
        List<Pair<String, Boolean>> images = p.getImages();
        for (Pair<String, Boolean> imageURL : imageURLs) {
            images.add(imageURL);
        }
        p.setImages(images);
        pf.addImages(p);

        Map<String, String> categoryAttributes = new HashMap<>();
        try {
            categoryAttributes = p.getCategoryAttributes();
        } catch (NullPointerException e) {
        }

        for (String key : parameterMap.keySet()) {
            if (key.equalsIgnoreCase("product_name")) {
                p.setName(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_desc")) {
                p.setDescription(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_tags")) {
                String str = parameterMap.get(key)[0];
                List<String> tags = new ArrayList<>();
                for (String s : Arrays.asList(str.split(",[ ]*"))) {
                    if (StringUtils.isNotBlank(s)) {
                        tags.add(s);
                    }
                }
                p.setTags(tags);
            } else if (key.equalsIgnoreCase("product_category")) {
                p.setCategory(pf.getCategory(parameterMap.get(key)[0]));
            } else if (key.equalsIgnoreCase("fileSelected")) {
                p.setPrimaryImage(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("delete_chosen_pics")) {
                String[] picsToDelete = parameterMap.get(key);
                p.removeImages(picsToDelete);
                pf.deleteImages(picsToDelete);
            } else {
                try {
                    categoryAttributes.replace(key, parameterMap.get(key)[0]);
                } catch (NullPointerException e) {
                }
            }
        }
        pf.updateProduct(p);
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
            product = pf.getProduct(id);
            createCategoryAttributeMap(product);
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
     * A method for getting products that share a tag The LogicController receives
     * information from the LogicFacade and passes it on to the PersistenceFacade 
     * and returns the found products
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
     * A private method for creating a category attribute map with all the product's 
     * category's attributes
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

}
