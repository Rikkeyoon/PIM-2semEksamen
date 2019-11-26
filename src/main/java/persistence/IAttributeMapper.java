package persistence;

import exception.CommandException;
import java.util.List;

/**
 *
 * @author carol
 */
public interface IAttributeMapper {
    
    public int getAttributeId(String attributename) throws CommandException;
    
    public List<Integer> createAttributes(List<String> attributeNames)
            throws CommandException;

    public void updateCategoryAttributename(String oldAttr, String newAttr) throws CommandException;

    public void deleteAttribute(int i) throws CommandException;
    
}
