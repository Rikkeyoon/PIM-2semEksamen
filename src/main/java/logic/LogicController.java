package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static Product createProduct(int id, String name, String description,
            String categoryname, List<Pair<String, Boolean>> images) throws CommandException {
        Category category = getCategory(categoryname);
        Product p = new Product(id, name, description, category, images);
        p.setCategoryAttributes(createCategoryAttributeMap(p));

        pf.createProduct(p);
        return p;
    }

    public static List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage)
            throws CommandException {
        return pf.uploadImagesToCloudinary(parts, primaryImage);
    }

    public static Product updateProduct(Product p, Map<String, String[]> parameterMap,
            List<Pair<String, Boolean>> imageURLs) throws CommandException {
        Map<String, String> categoryAttributes = new HashMap<>();
        try {
            categoryAttributes = p.getCategoryAttributes();
        } catch (NullPointerException e) {
        }
        
        for (String key : parameterMap.keySet()) {
            if (key.equalsIgnoreCase("product_name")) {
                p.setName(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_desc")) {
                p.setDescription(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_tags")) {
                String str = parameterMap.get(key)[0];
                List<String> tags = Arrays.asList(str.split(",[ ]*"));
                p.setTags(tags);
            } else if (key.equalsIgnoreCase("product_category")) {
                p.setCategory(pf.getCategory(parameterMap.get(key)[0]));
            } else if (key.equalsIgnoreCase("delete_chosen_pics")) {
                String[] picsToDelete = parameterMap.get(key);
                p.removeImages(picsToDelete);
                pf.deleteImages(picsToDelete);
            } else {
                try {
                    categoryAttributes.replace(key, parameterMap.get(key)[0]);
                } catch (NullPointerException e) {
                }
            }
        }
        List<Pair<String, Boolean>> images = p.getImages();
        for (Pair<String, Boolean> imageURL : imageURLs) {
            images.add(imageURL);
        }
        p.setImages(images);
        pf.updateProduct(p);
        return p;
    }

    public static void deleteProduct(Product p) throws CommandException {
        pf.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        return pf.getCatalog();
    }

    public static Product getProduct(int id) throws CommandException {
        Product product = null;
        try {
            product = pf.getProductWithCategoryAttributes(id);
        } catch (CommandException ex) {
            //Doesn't need to give this command exception to the user. 
            //It just means that the product with that id doesn't have any category attributes yet
        }
        if (product == null) {
            product = pf.getProduct(id);
        }
        product.setTags(pf.getTagsForProductWithID(product.getId()));
        return product;
    }

    public static List<Product> getProductsByName(String name) throws CommandException {
        return pf.getProductsByName(name);
    }

    public static List<Product> getProductsByCategory(String category)
            throws CommandException {
        return pf.getProductsByCategory(category);
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

    private static Map<String, String> createCategoryAttributeMap(Product product)
            throws CommandException {
        Category category = product.getCategory();
        Map<String, String> categoryAttributes;

        if (product.getCategoryAttributes() != null) {
            categoryAttributes = product.getCategoryAttributes();
        } else {
            categoryAttributes = new HashMap<>();
            List<String> attributes = category.getAttributes();

            for (String attribute : attributes) {
                categoryAttributes.putIfAbsent(attribute, "");
            }
        }
        return categoryAttributes;
    }

}
