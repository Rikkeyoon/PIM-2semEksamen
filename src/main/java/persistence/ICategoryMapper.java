package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 *
 * @author carol
 */
public interface ICategoryMapper {

    public void createCategory(Product p) throws CommandException;
    
    public List<String> getAllCategories() throws CommandException;
    
}
