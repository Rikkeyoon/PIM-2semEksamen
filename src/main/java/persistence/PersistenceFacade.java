/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.sql.Connection;
import logic.Product;

/**
 *
 * @author allan
 */
public class PersistenceFacade implements IPersistenceFacade{

    private static DBcon dbcon = DBcon.getInstance();
    private static Boolean testMode;

    public static void createProduct(Product p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public PersistenceFacade(boolean testMode)
    {
        this.testMode = testMode;
    }
    
    public static Connection getConnection() throws CommandException{
        return dbcon.getConnection(testMode);
    }
    
    
    @Override
    public void getProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
