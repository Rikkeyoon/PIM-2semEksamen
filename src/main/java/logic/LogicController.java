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

//    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);
    private static IPersistenceFacade pf = new PersistenceFacadeDB(true);

    public static Product createProduct(int id, String name, String description,
            String categoryname) throws CommandException {
        Product p = new Product(id, name, description, getCategory(categoryname));
        pf.createProduct(p);
        return p;
    }

    public static Product updateProduct(Product p, String name, String description,
            String categoryname, String[] attributeValues) throws CommandException {
        p.setName(name);
        p.setDescription(description);
        p.setCategory(getCategory(categoryname));
        p.setAttributeValues(attributeValues);
        pf.updateProduct(p);
        return p;
    }

    public static void deleteProduct(Product p) throws CommandException {
        pf.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        List<TemporaryProduct> catalog = pf.getCatalog();
        return convertTemporaryProductListToProductList(catalog);
    }

    public static Product getProduct(int id) throws CommandException {
        TemporaryProduct temp = null;
        try {
            temp = pf.getProductWithCategoryAttributes(id);
        } catch (CommandException ex) {
            //Doesn't need to give this command exception to the user. 
            //It just means that the product with that id doesn't have any category attributes yet
        }
        if (temp == null) {
            temp = pf.getProduct(id);
        }
        return convertTemporaryProductToProduct(temp);
    }

    public static List<Product> getProductsByName(String name) throws CommandException {
        List<TemporaryProduct> temps = pf.getProductsByName(name);
        return convertTemporaryProductListToProductList(temps);
    }

    public static List<Product> getProductsByCategory(String category)
            throws CommandException {
        List<TemporaryProduct> products = pf.getProductsByCategory(category);
        return convertTemporaryProductListToProductList(products);
    }

    public static Category getCategory(String categoryname) throws CommandException {
        return pf.getCategory(categoryname);
    }

    public static Category createCategory(String categoryname, String[] attributes)
            throws CommandException {
        List<String> attributeList = new ArrayList<>();
        for (String attribute : attributes) {
            if (!attribute.isBlank()) {
                attributeList.add(attribute);
            }
        }
        Category c = new Category(categoryname, attributeList);
        pf.createCategory(c);
        return c;
    }

    public static Category editCategory(String categoryname, String[] attributes)
            throws CommandException {
        Category c = pf.getCategory(categoryname);
        List<String> oldAttributes = c.getAttributes();
        List<String> newAttributes = new ArrayList<>();
        for (String attribute : oldAttributes) {
            newAttributes.add(attribute);
        }

        for (String attribute : attributes) {
            newAttributes.add(attribute);
        }
        c.setAttributes(newAttributes);
        pf.editCategory(c);
        return c;
    }

    public static List<Category> getCategories() throws CommandException {
        return pf.getCategories();
    }

    private static Product convertTemporaryProductToProduct(TemporaryProduct temp)
            throws CommandException {
        Category category = pf.getCategory(temp.getCategoryname());
        Map<String, String> categoryAttributes;

        if (temp.getCategoryAttributes() != null) {
            categoryAttributes = temp.getCategoryAttributes();
        } else {
            categoryAttributes = new HashMap<>();
            List<String> attributes = category.getAttributes();

            for (String attribute : attributes) {
                categoryAttributes.putIfAbsent(attribute, "");
            }
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

            if (temp.getCategoryAttributes() != null) {
                categoryAttributes = temp.getCategoryAttributes();
            } else {
                categoryAttributes = new HashMap<>();
                List<String> attributes = category.getAttributes();

                for (String attribute : attributes) {
                    categoryAttributes.putIfAbsent(attribute, "");
                }
            }

            products.add(new Product(temp.getId(), temp.getName(),
                    temp.getDescription(), category, categoryAttributes));
        }
        return products;
    }

}
