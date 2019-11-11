/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;

/**
 *
 * @author allan
 */
public class DataSourceFacade implements DataSourceFacadeInterface{

    private static DBconInterface DBcon = new DBcon();
    private static Boolean testMode;
    
    public DataSourceFacade(boolean testMode)
    {
        this.testMode = testMode;
    }
    
    public static Connection getConnection(){
        return DBcon.getConnection(testMode);
    }
    
    
    @Override
    public void getProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
