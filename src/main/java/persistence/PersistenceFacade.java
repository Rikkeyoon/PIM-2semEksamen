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
public class PersistenceFacade {

    private static PersistenceFacade instance = new PersistenceFacade();
    private static DataSourceController DScontroller = new DataSourceController(false);

    private PersistenceFacade() {

    }

    public static PersistenceFacade getInstance() {
        return instance;
    }

    public void getProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void createProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
