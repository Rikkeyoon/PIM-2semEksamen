package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */
public interface IDataSourceController {
    
    public List<Product> getProducts() throws CommandException;
    
    public Product getProduct(int id) throws CommandException;                

    public Product getProduct(String name) throws CommandException;
    
    public void createProduct(Product p) throws CommandException;

    public void updateProduct(Product p) throws CommandException;

    public List<Product> getProductsByCategory(String category) throws CommandException;
    
    public List<Product> getAllProductsWithCategoryAttributes() throws CommandException;

    public void deleteProduct(Product p) throws CommandException;

    }
