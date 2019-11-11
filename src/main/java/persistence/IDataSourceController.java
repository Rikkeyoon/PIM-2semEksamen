/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import logic.Product;

/**
 *
 * @author allan
 */
public interface IDataSourceController {
    
    public void getProducts();
    
    public void getProduct();                

    public void createProduct(Product p) throws CommandException;

    }
