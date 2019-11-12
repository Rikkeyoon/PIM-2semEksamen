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
 * @author allan, carol
 */
public class DBConnection {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;
    private static Boolean testmode;

    private static void setConnection(boolean isTestmode) throws CommandException {
        try {
            InputStream prob = null;
            testmode = isTestmode;
            if (!isTestmode) {
                prob = DBConnection.class.getResourceAsStream("/db.properties");
            } else {
                prob = DBConnection.class.getResourceAsStream("/testdb.properties");
            }
            Properties pros = new Properties();
            pros.load(prob);

            // assign db parameters
            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            // create a connection to the database
            Class.forName(DRIVER); //necessary because of servlet
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            throw new CommandException("Connection to database failed" + ex.getMessage());
        }
    }

    public static Connection getConnection(boolean isTestmode) throws CommandException {
        if (conn == null || testmode != isTestmode) {
            setConnection(isTestmode);
        }
        return conn;
    }
}
