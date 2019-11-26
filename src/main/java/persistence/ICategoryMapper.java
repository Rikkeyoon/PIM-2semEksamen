package persistence;

import exception.CommandException;
import java.util.List;
import logic.Category;

/**
 *
 * @author carol
 */
public interface ICategoryMapper {

    public void createCategory(Category c) throws CommandException;
    
    public void createCategoryAttributes(Category category, List<Integer> attributeIds)
            throws CommandException;
    
    public List<Category> getAllCategories() throws CommandException;

    public Category getCategory(String categoryname) throws CommandException;

    public void deleteCategoryAttribute(int i) throws CommandException;
    
}
