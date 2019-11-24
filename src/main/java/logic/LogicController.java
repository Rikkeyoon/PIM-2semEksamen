package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;
import org.apache.commons.lang.StringUtils;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author carol
 */
public class LogicController {

    private static IPersistenceFacade pf = new PersistenceFacadeDB(false);

    public static Product createProduct(int id, int itemnumber, String name, String description,
            String category, List<Pair<String, Boolean>> images) throws CommandException {
        Product p = new Product(id, itemnumber, name, description, getCategory(category), images);

        pf.createProduct(p);
        return p;
    }

    public static List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) throws CommandException {
        return pf.uploadImages(parts, primaryImage);
    }

    public static Product updateProduct(Product p, Map<String, String[]> parameterMap)
            throws CommandException {
        Map<String, String> categoryAttributes = p.getCategoryAttributes();
        for (String key : parameterMap.keySet()) {
            if (key.equalsIgnoreCase("product_name")) {
                p.setName(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_desc")) {
                p.setDescription(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_category")) {
                p.setCategory(pf.getCategory(parameterMap.get(key)[0]));
            } else {
                categoryAttributes.replace(key, parameterMap.get(key)[0]);
            }
        }
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
            if (StringUtils.isNotBlank(attribute)) {
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

        Product product = new Product(temp.getId(), temp.getItemnumber(), temp.getName(),
                temp.getDescription(), category, categoryAttributes, temp.getImages());
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

            products.add(new Product(temp.getId(), temp.getItemnumber(), temp.getName(),
                    temp.getDescription(), category, categoryAttributes, temp.getImages()));
        }
        return products;
    }

}
