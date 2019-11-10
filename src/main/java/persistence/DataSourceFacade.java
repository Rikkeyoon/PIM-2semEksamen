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
public class DataSourceFacade implements IDataSourceFacade{

    private static DBcon dbcon = DBcon.getInstance();
    private static Boolean testMode;
    
    public DataSourceFacade(boolean testMode)
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
