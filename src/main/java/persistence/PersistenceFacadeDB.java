package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;
import logic.Product;
import logic.Category;
import logic.TemporaryProduct;

/**
 *
 * @author allan, carol
 */
public class PersistenceFacadeDB implements IPersistenceFacade {

    private static IDatabaseConnection DBC;
    private static IProductMapper pm = new ProductMapper();
    private static ICategoryMapper cm = new CategoryMapper();
    private static AttributeMapper am = new AttributeMapper();
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
    public List<TemporaryProduct> getCatalog() throws CommandException {
        return pm.getAllProducts();
    }

    @Override
    public TemporaryProduct getProduct(int id) throws CommandException {
        return pm.getProduct(id);
    }

    @Override
    public List<TemporaryProduct> getProductsByName(String name) throws CommandException {
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
            im.addPictureURL(p.getId(), p.getImages());
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
        pm.updateAttributes(p);
        if (p.getImages() != null) {
            im.deleteAllImages(p);
            im.addPictureURL(p.getId(), p.getImages());
        }
    }

    @Override
    public void deleteProduct(Product p) throws CommandException {
        im.deleteAllImages(p);
        pm.delete(p);
    }

    @Override
    public List<TemporaryProduct> getProductsByCategory(String category)
            throws CommandException {
        return pm.getProductsByCategory(category);

    }

    @Override
    public Category getCategory(String categoryname) throws CommandException {
        return cm.getCategory(categoryname);
    }

    @Override
    public TemporaryProduct getProductWithCategoryAttributes(int id)
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
        for (String attribute : c.getAttributes()) {
            try {
                String attr = am.getAttribute(attribute);
            } catch (CommandException e) {
                newAttributes.add(attribute);
            }
        }
        List<Integer> attributeIds = new ArrayList<>();
        attributeIds = am.createAttributes(newAttributes);
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
    public void removeImageFromCloudinary(String URL) throws CommandException{
        im.removePictureFromCloudinary(URL);
    }
}
