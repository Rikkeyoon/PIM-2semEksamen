package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
//    private static IPersistenceFacade pf = new PersistenceFacadeDB(true);

    /**
     * A method to create a product The LogicController receives the new
     * product's information from the LogicFacade, creates a Product Object and
     * then passes the Product to the PersistenceFacade to get the product's
     * information stored
     *
     * @param p
     * @param category
     * @param fileSelected
     * @param parts
     * @return Product
     * @throws CommandException
     */
    public static Product createProduct(Product p, String category,
            String fileSelected, List<Part> parts) throws CommandException {
        List<Image> imageURLs = null;

        p.setCategory(pf.getCategory(category));
        //uploads images
        if (fileSelected != null) {
            imageURLs = pf.uploadImagesToCloudinary(parts, fileSelected);
        }
        p.setImages(imageURLs);
        p.calculateStatus();
        //creates product
        int id = pf.createProduct(p);
        p.setId(id);
        //saves attributes
        pf.createProductAttributes(p);
        //saves images
        pf.addImages(p);
        //saves tags
        pf.createProductTags(p.getId(), p.getTags());
        return p;
    }

    /**
     * A method to upload images to an extern service The LogicController
     * receives information from the LogicFacade and passes it on to the
     * PersistenceFacade
     *
     * @param parts
     * @param primaryImage
     * @return List of Pair with String and boolean
     * @throws CommandException
     */
    public static List<Image> uploadImages(List<Part> parts, String primaryImage)
            throws CommandException {
        return pf.uploadImagesToCloudinary(parts, primaryImage);
    }

    /**
     * A method to update a product The LogicController recieves information
     * from the LogicFacade, updates the Product Object and passes the updated
     * product to the PersistenceFacade
     *
     * @param p
     * @param category
     * @param picsToDelete
     * @param parts
     * @param fileSelected
     * @return Product
     * @throws CommandException
     */
    public static Product updateProduct(Product p, String category, String[] picsToDelete,
            String fileSelected, List<Part> parts) throws CommandException {
        p.setCategory(pf.getCategory(category));
        //saves attributes
        pf.updateProductAttributes(p);
        //uploads images
        if (fileSelected != null) {
            List<Image> imageURLs = null;
            imageURLs = pf.uploadImagesToCloudinary(parts, fileSelected);
            p.setImages(imageURLs);
        }
        if (picsToDelete != null && picsToDelete.length > 0) {
            p.removeImages(picsToDelete);
            pf.deleteImages(picsToDelete);
        }
        if (p.getImages() != null && !p.getImages().isEmpty()) {
            //saves images
            pf.updatePrimaryPicture(p.getId(), fileSelected);
            pf.addImages(p);
        }

        p.setImages(pf.getPicturesForProduct(p.getId()));
        if (!p.getTags().isEmpty()) {
            //saves tags
            pf.deleteTagsForProduct(p.getId());
            pf.createProductTags(p.getId(), p.getTags());
            pf.deleteUnusedTags();
        }
        p.calculateStatus();
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
        pf.deleteAllImages(p.getId());
        pf.deleteTagsForProduct(p.getId());
        pf.deleteProductAttributes(p.getId());
        pf.deleteProduct(p.getId());
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
     * A method for getting products by name The LogicController receives
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
     * A method for products by category The LogicController receives products'
     * category or part of a category from the LogicFacade and passes it on to
     * the PersistenceFacade and returns the found products
     *
     * @param category
     * @return
     * @throws CommandException
     */
    public static List<Product> getProductsByCategory(String category)
            throws CommandException {
        List<Product> products = pf.getProductsByCategory(category);
        for (Product p : products) {
            p.setTags(pf.getTagsForProductWithID(p.getId()));
        }
        return products;
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
        List<Product> productsTag = pf.getProductsWithTagSearch(tag);
        Collections.sort(productsTag, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getId() - p2.getId();
            }
        });
        return productsTag;
    }

    /**
     * A method for getting a specific category by its name The LogicController
     * receives information from the LogicFacade and passes it on to the
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
     * A method for creating a new category The LogicController receives
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
        if (attributes != null) {
            for (String attribute : attributes) {
                if (StringUtils.isNotBlank(attribute)) {
                    attributeList.add(attribute);
                }
            }
        }
        Category c = new Category(categoryname, attributeList);
        pf.createCategory(c);
        return c;
    }

    /**
     * A method for editing a category's data The LogicController receives
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
//        for (String attribute : oldAttributes) {
//            newAttributes.add(attribute);
//        }

        for (String attribute : attributes) {
            newAttributes.add(attribute);
        }
        c.setAttributes(newAttributes);
        pf.editCategory(c);
        List<Product> products = pf.getProductsByCategory(c.getCategoryname());
        for (Product product : products) {
            pf.createEmptyAttribute(product.getId(), newAttributes);
            Map<String, String> categoryAttributes = product.getCategoryAttributes();
            for (String attribute : attributes) {
                categoryAttributes.put(attribute, "");
            }
            product.setCategoryAttributes(categoryAttributes);
            product.calculateStatus();
            pf.updateProductStatus(product.getId(), product.getStatus());
        }
        
        return c;
    }

    /**
     * A method for getting all categories The LogicController receives
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
            categoryAttributes = new LinkedHashMap<>();
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

    /**
     * Method for uploading JSON file and converting it to Java Objects
     *
     * @param parts List of Part
     * @throws exception.CommandException
     */
    public static void uploadJSON(List<Part> parts) throws CommandException {
        List<Object> objects = new ArrayList<>();
        for (Part part : parts) {
            if (part.getContentType() != null && part.getSize() > 0) {
                List<Object> ob = JSONConverter.convertPartToObjects(part);
                for (Object object : ob) {
                    objects.add(object);
                }
            }
        }
        for (Object object : objects) {
            if (object instanceof Product) {
                Product product = (Product) object;
                Map<String, String> categoryAttributes = new LinkedHashMap<>();
                if (product.getCategoryAttributes() == null
                        || product.getCategoryAttributes().isEmpty()) {
                    for (String s : product.getCategory().getAttributes()) {
                        categoryAttributes.put(s, "");
                    }
                    product.setCategoryAttributes(categoryAttributes);
                }
                int dbId = pf.createProduct(product);
                product.setId(dbId);
                pf.createProductAttributes(product);
                pf.addImages(product);
                try {
                    pf.createProductTags(dbId, product.getTags());
                } catch (CommandException e) {
                }
            } else if (object instanceof Category) {
                Category category = (Category) object;
                pf.createCategory(category);
            }
        }
    }

    /**
     * A method for products by item number The LogicController recieves
     * products' item number or part of a item number from the LogicFacade and
     * passes it on to the PersistenceFacade and returns the found products
     *
     * @param itemNumber
     * @return
     * @throws CommandException
     */
    public static List<Product> getProductsByItemNumber(int itemNumber)
            throws CommandException {
        return pf.getProductsByItemNumber(itemNumber);
    }

    /**
     * A method for products by brand The LogicController recieves products'
     * brand or part of a brand from the LogicFacade and passes it on to the
     * PersistenceFacade and returns the found products
     *
     * @param brand
     * @return
     * @throws CommandException
     */
    public static List<Product> getProductsByBrand(String brand) throws CommandException {
        return pf.getProductsByBrand(brand);
    }

    /**
     * A method for getting products by supplier The LogicController recieves
     * products' supplier or part of a suppliername from the LogicFacade and
     * passes it on to the PersistenceFacade and returns the found products
     *
     * @param supplier
     * @return List of Products
     * @throws CommandException
     */
    public static List<Product> getProductsBySupplier(String supplier) throws CommandException {
        return pf.getProductsBySupplier(supplier);
    }

    public static Category getCategoryFromName(String categoryName) throws CommandException {
        return pf.getCategory(categoryName);
    }

    public static void bulkEdit(Product p, List<String> bulkeditIDs) throws CommandException {
        if (bulkeditIDs != null) {
            for (String s : bulkeditIDs) {
                p.setId(Integer.parseInt(s));
                pf.updateProductAttributes(p);
                if (!p.getTags().isEmpty()) {
                    //saves tags
                    pf.deleteTagsForProduct(p.getId());
                    pf.createProductTags(p.getId(), p.getTags());
                }
                pf.deleteUnusedTags();

            }
            pf.updateProduct_BulkEdit(p, bulkeditIDs);
        }else{
            throw new CommandException("no products choosen");
        }
    }

    public static void bulkDelete(String[] bulkDelete) throws CommandException {

        for (String stringID : bulkDelete) {
            int id = Integer.parseInt(stringID);
            pf.deleteAllImages(id);
            pf.deleteTagsForProduct(id);
            pf.deleteProductAttributes(id);
            pf.deleteProduct(id);
        }
    }

    public static void deleteCategory(int id) throws CommandException {
        List<String> removeAttr = pf.getCategoryAttributes(id);
        pf.deleteCategory(id);
        for (String name : removeAttr) {
            pf.deleteAttribute(name);
        }
    }

}
