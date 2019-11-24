/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.util.List;

/**
 *
 * @author allan
 */
public interface ITagMapper {
    public List<String> getTagsForProductWithID(int id) throws CommandException;
    
    public void createTagsAndProductRelation(int productID, List<String> tags) throws CommandException;
    
    public void createTags(List<String> tags) throws CommandException;
            
}
