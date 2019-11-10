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
public interface DBconInterface {
    
    public Connection getConnection(Boolean testmode);
}
