/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.util.List;
import logic.Category;
import logic.Product;
import logic.TemporaryProduct;

/**
 *
 * @author allan
 */

public interface IPersistenceFacade {
    
    public List<TemporaryProduct> getCatalog() throws CommandException;
    
    public TemporaryProduct getProduct(int id) throws CommandException;
    
    public TemporaryProduct getProduct(String name) throws CommandException;
    
    public void createProduct(Product p) throws CommandException;
    
    public void updateProduct(Product p) throws CommandException;
    
    public void deleteProduct(Product p) throws CommandException;
    
    public List<TemporaryProduct> getProductsByCategory(String category) 
            throws CommandException;
    
    public Category getCategory(String categoryname) throws CommandException;

    public TemporaryProduct getProductWithCategoryAttributes(int id) 
            throws CommandException;


}
