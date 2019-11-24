package persistence;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.Part;
import logic.Category;
import logic.Product;
import logic.TemporaryProduct;
import org.apache.commons.lang3.tuple.Pair;


/**
 *
 * @author allan
 */
public interface IPersistenceFacade {

    public List<TemporaryProduct> getCatalog() throws CommandException;

    public TemporaryProduct getProduct(int id) throws CommandException;

    public List<TemporaryProduct> getProductsByName(String name) throws CommandException;

    public void createProduct(Product p) throws CommandException;

    public void updateProduct(Product p) throws CommandException;

    public void deleteProduct(Product p) throws CommandException;

    public List<TemporaryProduct> getProductsByCategory(String category)
            throws CommandException;

    public Category getCategory(String categoryname) throws CommandException;

    public TemporaryProduct getProductWithCategoryAttributes(int id)
            throws CommandException;

    public void createCategory(Category c) throws CommandException;

    public void editCategory(Category c) throws CommandException;
    
    public List<Category> getCategories() throws CommandException;
    
    public List<String> getTagsForProductWithID(int id) throws CommandException;

    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException;

}
