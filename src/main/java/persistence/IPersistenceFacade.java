package persistence;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.Part;
import logic.Category;
import logic.Product;
import org.apache.commons.lang3.tuple.Pair;


/**
 *
 * @author allan
 */
public interface IPersistenceFacade {

    public List<Product> getCatalog() throws CommandException;
    
    public Product getProduct(int id) throws CommandException;

    public List<Product> getProductsByName(String name) throws CommandException;

    public void createProduct(Product p) throws CommandException;

    public void updateProduct(Product p) throws CommandException;
    
    public void deleteImages(String[] picsToDelete) throws CommandException;
    
    public void addImages(Product p) throws CommandException;

    public void deleteProduct(Product p) throws CommandException;

    public List<Product> getProductsByCategory(String category)
            throws CommandException;

    public Category getCategory(String categoryname) throws CommandException;

    public Product getProductWithCategoryAttributes(int id)
            throws CommandException;

    public void createCategory(Category c) throws CommandException;

    public void editCategory(Category c) throws CommandException;
    
    public List<Category> getCategories() throws CommandException;
    
    public List<String> getTagsForProductWithID(int id) throws CommandException;
    
    public void createProductTags(int id, List<String> tags) throws CommandException;

    public List<Pair<String, Boolean>> uploadImagesToCloudinary(List<Part> parts, String primaryImage) 
            throws CommandException;
    
    public List<Product> getProductsWithTagSearch(String tagSearch) throws CommandException;

    public int getProductDBId(Product p) throws CommandException;


}
