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

/**
 *
 * @author allan
 */

public interface IPersistenceFacade {
    
    public List<Product> getCatalog() throws CommandException;
    
    public Product getProduct(int id) throws CommandException;
    
    public Product getProduct(String name) throws CommandException;
    
    public void createProduct(Product p) throws CommandException;
    
    public void updateProduct(Product p) throws CommandException;
    
    public void deleteProduct(Product p) throws CommandException;
    
    public List<Product> getProductsByCategory(String category) throws CommandException;
    
    public Category getCategory(String categoryname) throws CommandException;

    public List<Product> getAllProductsWithCategoryAttributes() throws CommandException;


}
