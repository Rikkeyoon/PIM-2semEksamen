/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
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
public class DBcon{
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static DBcon instance;
    
    private DBcon (){
        
    }
    public static DBcon getInstance(){
        if(instance == null){
            instance = new DBcon();
        }
        return instance;
    }
    
    public Connection getConnection(Boolean testmode) throws CommandException{
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
            throw new CommandException("Connection to database failed" + ex.getMessage());

        }
        return conn;

    }
}
