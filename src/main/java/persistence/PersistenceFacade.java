package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */

public class PersistenceFacade implements IPersistenceFacade{

    private IDatabaseConnection DBC;
    private IProductMapper pm = new ProductMapper();
    private ICategoryMapper cm = new CategoryMapper();
    
    public PersistenceFacade(Boolean testmode){
        if(testmode){
            
        }else{
            
        }
    }
    
    public Connection getConnection() throws CommandException{
        return DBC.getConnection();
    }

    @Override
    public List<Product> getCatalog() throws CommandException {
        return pm.getAllProducts();
    }

    @Override
    public Product getProduct(int id) throws CommandException {
        return pm.getProduct(id);
    }

    @Override
    public void createProduct(Product p) throws CommandException{
        pm.create(p);
    }

    @Override
    public void updateProduct(Product p) throws CommandException {
        pm.update(p);
    }

    @Override
    public void deleteProduct(Product p) throws CommandException {
        pm.delete(p);
    }

}