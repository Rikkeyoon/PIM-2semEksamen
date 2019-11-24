package persistence;

import exception.CommandException;
import java.util.List;

/**
 *
 * @author allan
 */
public interface ITagMapper {

    public List<String> getTagsForProductWithID(int id) throws CommandException;

}
