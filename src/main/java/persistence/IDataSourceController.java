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
    
    public void getProducts() throws CommandException;
    
    public void getProduct() throws CommandException;                

    public void createProduct(Product p) throws CommandException;

    public void updateProduct(Product p) throws CommandException;

    }
