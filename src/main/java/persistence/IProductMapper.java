package persistence;

import exception.CommandException;
import java.util.List;
import logic.Category;
import logic.Product;

/**
 * @author Nina
 */
public interface IProductMapper {

    public void create(Product product) throws CommandException;

    public Product getProduct(int id) throws CommandException;

    public List<Product> getProductsByName(String name) throws CommandException;

    public List<Product> getProductsByCategory(String category) throws CommandException;

    public List<Product> getAllProducts() throws CommandException;

    public Product getProductWithCategoryAttributes(int id) throws CommandException;

    public void update(Product product) throws CommandException;

    public void updateAttributes(Product product) throws CommandException;

    public void createAttributes(Product product) throws CommandException;

    public void delete(Product product) throws CommandException;

    public void deleteProductAttribute(int i) throws CommandException;

}
