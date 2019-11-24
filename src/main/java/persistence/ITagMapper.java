package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */
public interface ITagMapper {

    public List<String> getTagsForProductWithID(int id) throws CommandException;

    public List<Integer> updateTags(Product p) throws CommandException;

}
