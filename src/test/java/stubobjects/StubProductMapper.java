package stubobjects;

import exception.CommandException;
import java.util.ArrayList;
import java.util.List;
import logic.Product;
import persistence.IProductMapper;

/**
 *
 * @author carol
 */
public class StubProductMapper implements IProductMapper {

    public List<Product> products;

    public StubProductMapper(List<Product> products) {
        this.products = products;
    }

    @Override
    public void create(Product product) throws CommandException {
        for (Product product1 : products) {
            if (product1.compareTo(product)) {
                throw new CommandException("Could not create product. Try again!");
            }
        }
        products.add(product);
    }

    @Override
    public Product getProduct(String name) throws CommandException {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new CommandException("Could not find any product with that name");
    }
    
    @Override
    public Product getProduct(int id) throws CommandException {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        throw new CommandException("Could not find any product with that name");
    }

    @Override
    public List<Product> getProductsByCategory(String categoryname)
            throws CommandException {
        List<Product> productsByCategory = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategoryname().equals(categoryname)) {
                productsByCategory.add(product);
            }
        }
        
        if (productsByCategory.isEmpty()) {
            throw new CommandException("Could not find any product with that category");
        }
        return productsByCategory;
    }

    @Override
    public List<Product> getAllProducts() throws CommandException {
        if (products.isEmpty()) {
            throw new CommandException("Could not find any products");
        }
        return products;
    }

    @Override
    public void update(Product product) throws CommandException {
        boolean hasUpdated = false;
        for (Product product1 : products) {
            if (product1.compareTo(product)) {
                product1.setName(product.getName());
                product1.setDescription(product.getDescription());
                product1.setCategoryname(product.getCategoryname());
                hasUpdated = true;
            }
        }
        if (!hasUpdated) {
            throw new CommandException("Could not find a product with the given ID");
        }
    }

    @Override
    public void delete(Product product) throws CommandException {
        boolean hasDeleted = false;
        for (Product product1 : products) {
            if (product1.compareTo(product)) {
                products.remove(product1);
                hasDeleted = true;
            }
        }
        if (!hasDeleted) {
            throw new CommandException("Could not find a product with the given ID");
        }
    }

}
