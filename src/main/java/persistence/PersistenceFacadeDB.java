package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.http.Part;
import logic.Product;
import logic.Category;

/**
 *
 * @author allan
 */
public class PersistenceFacadeDB implements IPersistenceFacade {

    private static IDatabaseConnection DBC;
    private static IProductMapper pm = new ProductMapper();
    private static ICategoryMapper cm = new CategoryMapper();
    private static IImageMapper im = new ImageMapper();

    public PersistenceFacadeDB(Boolean testmode){
        try{
            DBC = new DatabaseConnection(testmode);
        }catch(CommandException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static Connection getConnection() throws CommandException {
        return DBC.getConnection();
    }

    public static List<Pair<String, Boolean>> getPrimaryImageWithId(int id) throws CommandException{
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
    public Product getProduct(String name) throws CommandException {
        return pm.getProduct(name);
    }

    @Override
    public void createProduct(Product p) throws CommandException {
        try {
            cm.createCategory(p);
        } catch (CommandException e) {
            //If an exception is thrown it means that the category already exits
            //We don't need to forward the message to the user
        }
        
        pm.create(p);
        if(p.getImages() != null){
           im.addPictureURL(p.getId(),p.getImages()); 
        }
    }

    @Override
    public void updateProduct(Product p) throws CommandException {
        try {
            cm.createCategory(p);
        } catch (CommandException e) {
            //If an exception is thrown it means that the category already exits
            //We don't need to forward the message to the user
        }
        pm.update(p);
    }

    @Override
    public void deleteProduct(Product p) throws CommandException {
        pm.delete(p);
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CommandException {
        return pm.getProductsByCategory(category);

    }
    
    @Override
    public Category getCategory(String categoryname) throws CommandException {
        return cm.getCategory(categoryname);
    }

    @Override
    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException {
        return im.uploadImages(parts, primaryImage);
    }
    
}
