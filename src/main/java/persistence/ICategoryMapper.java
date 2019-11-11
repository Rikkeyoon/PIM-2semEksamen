package persistence;

import exception.CommandException;
import logic.Product;

/**
 *
 * @author carol
 */
public interface ICategoryMapper {

    public void create(Product p) throws CommandException;
    
}
