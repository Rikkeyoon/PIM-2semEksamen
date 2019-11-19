package persistence;

import logic.TemporaryProduct;
import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 * @author Nina
 */
public interface IProductMapper {

    public void create(Product product) throws CommandException;

    public TemporaryProduct getProduct(String name) throws CommandException;

    public TemporaryProduct getProduct(int id) throws CommandException;

    public List<TemporaryProduct> getProductsByCategory(String categoryname) throws CommandException;

    public List<TemporaryProduct> getAllProducts() throws CommandException;

    public TemporaryProduct getProductWithCategoryAttributes(int id) throws CommandException;

    public void update(Product product) throws CommandException;
    
    public void updateAttributes(Product product) throws CommandException;

    public void delete(Product product) throws CommandException;

}
