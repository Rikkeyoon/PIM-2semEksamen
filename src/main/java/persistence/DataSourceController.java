/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.sql.Connection;

/**
 *
 * @author allan
 */
public class DataSourceController implements IDataSourceController{
 
    private static DBcon dbcon = DBcon.getInstance();
    private static Boolean testMode;
    
    public DataSourceController (boolean testMode)
    {
        this.testMode = testMode;
    }
        
    public static Connection getConnection() throws CommandException{
        return dbcon.getConnection(testMode);
    }
    
    @Override
    public void getProducts(){
        
    }
    
    @Override
    public void getProduct(){
        
    }
    
    @Override
    public void createProduct(){
        
    }
}
