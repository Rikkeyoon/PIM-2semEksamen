package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 * @author Nina 
 */
public interface IProductMapper {
    
    public void create(Product product) throws CommandException;
    
    public Product getProduct(String name) throws CommandException;
    
    public List<Product> getProductsByCategory(String categoryname) throws CommandException;
    
    public List<Product> getAllProducts() throws CommandException;
    
    public void update(Product product) throws CommandException;
    
    public void delete(Product product) throws CommandException;

}
