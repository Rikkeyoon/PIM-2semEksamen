package logic;

import persistence.PersistenceFacade;

/**
 *
 * @author carol
 */
public class Controller {

    public static Product createProduct(int id, String name, String description, 
            String category) {
        Product p = new Product(id, name, description, category);
        PersistenceFacade.createProduct(p);
        return p;
    }
    
}
