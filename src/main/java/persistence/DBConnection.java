package persistence;

import exception.CommandException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author allan, carol
 */
public class DBConnection {

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    private static DBConnection dbc;
    private BasicDataSource basicDS;
    private static Boolean testMode;

    //private constructor
    private DBConnection() throws CommandException {
        try {

            InputStream prob = null;
            if (!testMode) {
                prob = DBConnection.class.getResourceAsStream("/db.properties");
            } else {
                prob = DBConnection.class.getResourceAsStream("/testdb.properties");
            }
            Properties pros = new Properties();
            pros.load(prob);

            // assign db parameters
            String URL = pros.getProperty("url");
            String USER = pros.getProperty("user");
            String PASSWORD = pros.getProperty("password");
            // create a connection to the database
            basicDS = new BasicDataSource();
            basicDS.setDriverClassName(DRIVER_CLASS);
            basicDS.setUrl(URL);
            basicDS.setUsername(USER);
            basicDS.setPassword(PASSWORD);

            // Parameters for connection pooling
            basicDS.setMaxIdle(7);
            basicDS.setMinIdle(2);
            basicDS.setInitialSize(2);
            basicDS.setMaxTotal(10);

        } catch (IOException ex) {
            throw new CommandException("Failed to load properties file. " + ex.getMessage());
        }
//  Class.forName(DRIVER); //necessary because of servlet
//  conn = DriverManager.getConnection(url, user, password);
    }

    /**
     * static method for getting instance.
     */
    public static DBConnection getInstance(boolean testMode) throws CommandException {
        if (dbc == null || testMode != DBConnection.testMode) {
            DBConnection.testMode = testMode;
            dbc = new DBConnection();
        }
        return dbc;
    }
    
    public BasicDataSource getBasicDS() {
        return basicDS;
    }

    public void setBasicDS(BasicDataSource basicDS) {
        this.basicDS = basicDS;
    }
}
