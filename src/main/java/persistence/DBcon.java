/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author allan
 */
public class DBcon implements DBconInterface{
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Connection getConnection(Boolean testmode) {
        Connection conn = null;
        try {
            InputStream prob = null;
            if(!testmode){
                prob = DBcon.class.getResourceAsStream("/db.properties");
            }else{
                prob = DBcon.class.getResourceAsStream("/testdb.properties");
            }
            Properties pros = new Properties();
            pros.load(prob);

            // assign db parameters
            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");

            // create a connection to the database
            if (conn == null) {

                Class.forName(DRIVER); //necessary because of servlet
                conn = DriverManager.getConnection(url, user, password);
            }
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            System.out.println("DB CONNECTOR:" + ex.getMessage());

        }
        return conn;

    }
}
