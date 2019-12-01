package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;
import logic.Product;
import logic.Category;

/**
 * The purpose of PersistenceFacadeDB is to instantiate a connection to the
 * database and also instantiate the mappers that are needed to store the data
 * PersistenceFacadeDB has gotten from the logic layer
 *
 * @author allan, carol, Nina
 */
public class PersistenceFacadeDB implements IPersistenceFacade {

    private static IDatabaseConnection DBC;
    private static ProductMapper pm = new ProductMapper();
    private static CategoryMapper cm = new CategoryMapper();
    private static TagMapper tm = new TagMapper();
    private static AttributeMapper am = new AttributeMapper();
    private static ImageMapper im = new ImageMapper();

    /**
     * Constructor for PersistenceFacadeDB where the connection can be set
     * according to whether or not the used database should be the test database
     * or the regular database
     *
     * @param testmode
     */
    public PersistenceFacadeDB(Boolean testmode) {
        try {
            DBC = new DatabaseConnection(testmode);
        } catch (CommandException ex) {
        }
    }

    /**
     * Method for getting a connection to the database
     *
     * @return @throws CommandException
     */
    public static Connection getConnection() throws CommandException {
        return DBC.getConnection();
    }

    /**
     * Method to get multiple products' storage ids from a tag
     *
     * @param tagSearch
     * @return List of Integers
     * @throws CommandException
     */
    public static List<Integer> getProductsIDFromTagNameSearch(String tagSearch)
            throws CommandException {
        return tm.getProductsIDFromTagNameSearch(tagSearch);
    }

    /**
     * Method to get pictures that are associated with a specific product
     *
     * @param id Product id
     * @return List Pair of String and boolean
     * @throws CommandException
     */
    public static List<Pair<String, Boolean>> getPicturesWithId(int id)
            throws CommandException {
        return im.getPicturesWithId(id);
    }

    /**
     * Method to get the primary picture for a specific product
     *
     * @param id Product id
     * @return Pair of String and boolean
     * @throws CommandException
     */
    public static Pair<String, Boolean> getPrimaryImageWithId(int id)
            throws CommandException {
        return im.getPrimaryPictureWithId(id);
    }

    @Override
    public List<Product> getCatalog() throws CommandException {
        return pm.getAllProducts();
    }

    @Override
    public Product getProduct(int id) throws CommandException {
        return pm.getProduct(id);
    }

    @Override
    public List<Product> getProductsByName(String name) throws CommandException {
        return pm.getProductsByName(name);
    }

    @Override
    public void createProduct(Product p) throws CommandException {
        try {
            Category c = p.getCategory();
            cm.createCategory(c);
        } catch (CommandException e) {
            //If an exception is thrown it means that the category already exits
            //We don't need to forward the message to the user
        }

        pm.create(p);
        if (p.getImages() != null) {
            im.addPictureURL(pm.getProductDBId(p), p.getImages());
        }
        pm.createAttributes(p);
    }

    @Override
    public void updateProduct(Product p) throws CommandException {
        try {
            Category c = p.getCategory();
            cm.createCategory(c);
        } catch (CommandException e) {
            //If an exception is thrown it means that the category already exits
            //We don't need to forward the message to the user
        }
        //List<Integer> tagIds = tm.updateTags(p);
        pm.update(p);
        try {
            pm.updateAttributes(p);
        } catch (CommandException e) {
        }
        if (p.getTags() != null) {
            tm.deleteTagsForProduct(p.getId());
            createProductTags(p.getId(), p.getTags());
            tm.deleteUnusedTags();
        }
    }

    @Override
    public void addImages(Product p) throws CommandException {
        if (!p.getImages().isEmpty()) {
            im.updatePrimaryPicture(p.getId(), p.getPrimaryImage());
            im.addPictureURL(p.getId(), p.getImages());
        }
    }

    @Override
    public void deleteImages(String[] picsToDelete) throws CommandException {
        for (String imageurl : picsToDelete) {
            im.removePictureFromCloudinary(imageurl);
        }
        im.deleteImages(picsToDelete);

    }

    @Override
    public void deleteProduct(Product p) throws CommandException {
        im.deleteAllImages(p);
        for (Pair<String, Boolean> image : p.getImages()) {
            im.removePictureFromCloudinary(image.getKey());
        }
        pm.delete(p);
    }

    @Override
    public List<Product> getProductsByCategory(String category)
            throws CommandException {
        return pm.getProductsByCategory(category);

    }

    @Override
    public Category getCategory(String categoryname) throws CommandException {
        return cm.getCategory(categoryname);
    }

    @Override
    public Product getProductWithCategoryAttributes(int id)
            throws CommandException {
        return pm.getProductWithCategoryAttributes(id);
    }

    @Override
    public void createCategory(Category c) throws CommandException {
        try {
            cm.createCategory(c);
        } catch (CommandException e) {
            throw new CommandException("The category already exists.");
        }
        List<Integer> attributeIds = new ArrayList<>();
        attributeIds = am.createAttributes(c.getAttributes());
        cm.createCategoryAttributes(c, attributeIds);
    }

    @Override
    public void editCategory(Category c) throws CommandException {
        List<String> newAttributes = new ArrayList<>();
        List<Integer> attributeIds = new ArrayList<>();
        for (String attribute : c.getAttributes()) {
            try {
                int attr = am.getAttributeId(attribute);
                attributeIds.add(attr);
            } catch (CommandException e) {
                newAttributes.add(attribute);
            }
        }
        List<Integer> ids = am.createAttributes(newAttributes);
        for (Integer attr : ids) {
            attributeIds.add(attr);
        }
        cm.createCategoryAttributes(c, attributeIds);
    }

    @Override
    public List<Category> getCategories() throws CommandException {
        return cm.getAllCategories();
    }

    @Override
    public List<Pair<String, Boolean>> uploadImagesToCloudinary(List<Part> parts, String primaryImage) throws CommandException {
        return im.uploadImages(parts, primaryImage);
    }

    @Override
    public List<String> getTagsForProductWithID(int id) throws CommandException {
        return tm.getTagsForProductWithID(id);
    }

    @Override
    public void createProductTags(int id, List<String> tags) throws CommandException {
        tm.createTags(tags);
        tm.createTagsAndProductRelation(id, tags);
    }

    @Override
    public List<Product> getProductsWithTagSearch(String tagSearch) throws CommandException {
        return pm.getProductsWithTagSearch(tagSearch);
    }

    @Override
    public int getProductStorageId(Product p) throws CommandException {
        return pm.getProductDBId(p);
    }

    public void updateCategoryAttributename(String oldAttr, String newAttr) throws CommandException {
        am.updateCategoryAttributename(oldAttr, newAttr);
    }

    @Override
    public void deleteAttributeFromCategory(List<String> removeAttr) throws CommandException {
        for (String s : removeAttr) {
            int i = am.getAttributeId(s);

            cm.deleteCategoryAttribute(i);
            pm.deleteProductAttribute(i);
            am.deleteAttribute(i);
        }
    }

}
