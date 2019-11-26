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
 *
 * @author allan, carol
 */
public class PersistenceFacadeDB implements IPersistenceFacade {

    private static IDatabaseConnection DBC;
    private static IProductMapper pm = new ProductMapper();
    private static ICategoryMapper cm = new CategoryMapper();
    private static IAttributeMapper am = new AttributeMapper();
    private static IImageMapper im = new ImageMapper();

    public PersistenceFacadeDB(Boolean testmode) {
        try {
            DBC = new DatabaseConnection(testmode);
        } catch (CommandException ex) {
        }
    }

    public static Connection getConnection() throws CommandException {
        return DBC.getConnection();
    }

    public static Pair<String, Boolean> getPrimaryImageWithId(int id) throws CommandException {
        return im.getPrimaryPictureWithId(id);
    }

    public static List<Pair<String, Boolean>> getPicturesWithId(int id) throws CommandException {
        return im.getPicturesWithId(id);
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
            im.addPictureURL(pm.getHighestProductID(), p.getImages());
        }
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
        pm.update(p);
        try {
            pm.updateAttributes(p);
        } catch (CommandException e) {
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

}
