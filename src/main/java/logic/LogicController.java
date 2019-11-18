package logic;

import exception.CommandException;
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
            String category) throws CommandException {
        Product p = new Product(id, name, description, category);
        pf.createProduct(p);
        return p;
    }

    public static Product updateProduct(Product p, String name, String description,
            String category) throws CommandException {
        p.setName(name);
        p.setDescription(description);
        p.setCategoryname(category);
        pf.updateProduct(p);
        return p;
    }

    public static void deleteProduct(Product p) throws CommandException{
        pf.deleteProduct(p);
    }

    public static List<Product> getCatalog() throws CommandException {
        return pf.getCatalog();
    }

    public static List<Product> getAllProductsWithCategoryAttributes() throws CommandException {
        //FIXME! Create a better solution somehow!!
        //get allProductsWithCategoryAttributes List
        List<Product> products = pf.getAllProductsWithCategoryAttributes();
        //iterate through the list
        for (int i = 0; i < products.size(); i++) {
            //make sure we don't get an IndexOutOfBoundsException
            if (i < products.size() - 1) {
                //check if there are any duplicates, by comparing the ids 
                //(which are ordered in ascending order by the mapper, so theres no need to check more than the next in line)
                if (products.get(i).compareTo(products.get(i + 1))) {
                    //if the ids match, get the CategoryAttributes maps 
                    Map<String, String> attributes = products.get(i).getCategoryAttributes();
                    Map<String, String> attributes1 = products.get(i + 1).getCategoryAttributes();
                    //insert the entry set from the second (yet identical) product into the first
                    for (Map.Entry<String, String> entry : attributes1.entrySet()) {
                        attributes.putIfAbsent(entry.getKey(), entry.getValue());
                    }
                    //add the correct CategoryAttributes map to the first product
                    products.get(i).setCategoryAttributes(attributes);
                    //delete the second product
                    products.remove(products.get(i + 1));
                }
            }
        }
        return products;
    }

    public static Product getProduct(int id) throws CommandException {
        return pf.getProduct(id);
    }

    public static Category getCategory(String categoryname) throws CommandException {
        return pf.getCategory(categoryname);
    }

}
