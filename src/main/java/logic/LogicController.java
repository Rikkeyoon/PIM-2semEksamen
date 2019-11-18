package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author carol
 */
public class LogicController {

    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);

    public static Product createProduct(int id, String name, String description,
            String categoryname) throws CommandException {
        Product p = new Product(id, name, description, getCategory(categoryname));
        pf.createProduct(p);
        return p;
    }

    public static Product updateProduct(Product p, String name, String description,
            String categoryname) throws CommandException {
        p.setName(name);
        p.setDescription(description);
        p.setCategory(getCategory(categoryname));
        pf.updateProduct(p);
        return p;
    }

    public static void deleteProduct(Product p) throws CommandException {
        pf.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        return convertTemporaryProductListToProductList(pf.getCatalog());
    }

    public static Product getProduct(int id) throws CommandException {
        Product p = null;
        try {
            p = convertTemporaryProductToProduct(pf.getProductWithCategoryAttributes(id));
        } catch (CommandException ex) {
            //Doesn't need to give this command exception to the user. 
            //It just means that the product with that id doesn't have any category attributes yet
        }
        if (p == null) {
            p = convertTemporaryProductToProduct(pf.getProduct(id));
        }
        return p;
    }

    public static Category getCategory(String categoryname) throws CommandException {
        return pf.getCategory(categoryname);
    }

    private static Product convertTemporaryProductToProduct(TemporaryProduct temp)
            throws CommandException {
        Category category = pf.getCategory(temp.getCategoryname());
        Map<String, String> categoryAttributes;

        if (temp.getCategoryAtrributes().isEmpty()) {
            categoryAttributes = new HashMap<>();
            List<String> attributes = category.getAttributes();

            for (String attribute : attributes) {
                categoryAttributes.putIfAbsent(attribute, "");
            }
        } else {
            categoryAttributes = temp.getCategoryAtrributes();
        }

        Product product = new Product(temp.getId(), temp.getName(),
                temp.getDescription(), category, categoryAttributes);
        return product;
    }

    private static List<Product> convertTemporaryProductListToProductList(List<TemporaryProduct> temps)
            throws CommandException {
        List<Product> products = new ArrayList<>();
        for (TemporaryProduct temp : temps) {
            Category category = pf.getCategory(temp.getCategoryname());
            Map<String, String> categoryAttributes;

            if (temp.getCategoryAtrributes().isEmpty()) {
                categoryAttributes = new HashMap<>();
                List<String> attributes = category.getAttributes();

                for (String attribute : attributes) {
                    categoryAttributes.putIfAbsent(attribute, "");
                }
            } else {
                categoryAttributes = temp.getCategoryAtrributes();
            }

            products.add(new Product(temp.getId(), temp.getName(),
                    temp.getDescription(), category, categoryAttributes));
        }
        return products;
    }

}
