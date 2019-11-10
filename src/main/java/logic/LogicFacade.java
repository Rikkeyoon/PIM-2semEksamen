package logic;

/**
 *
 * @author carol
 */
public class LogicFacade {

    public static Product createProduct(int id, String name, String description) {
        return Controller.createProduct(id, name, description);
    }
    
}
