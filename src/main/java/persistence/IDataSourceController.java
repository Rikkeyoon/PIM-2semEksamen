/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */
public interface IDataSourceController {
    
    public List<Product> getProducts() throws CommandException;
    
    public Product getProduct(String name) throws CommandException;                

    public void createProduct(Product p) throws CommandException;

    public void updateProduct(Product p) throws CommandException;

    }
