package persistence;

import exception.CommandException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * The purpose of the DatabaseConnection is to create a connection and
 * connection pools to a database
 *
 * @author allan, carol
 */
public class DatabaseConnection implements IDatabaseConnection {

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private BasicDataSource basicDS;

    /**
     * Constructor for DatabaseConnection with the boolean for whether or not
     * the connection should be made to the test database It also sets up the
     * connection pool accordingly
     *
     * @param testMode
     * @throws CommandException
     */
    public DatabaseConnection(Boolean testMode) throws CommandException {
        try {
            InputStream prob = null;
            basicDS = new BasicDataSource();
            if (!testMode) {
                prob = DatabaseConnection.class.getResourceAsStream("/db.properties");
                connectionPoolSetup(8, 5, 5, 100);
            } else {
                prob = DatabaseConnection.class.getResourceAsStream("/testdb.properties");
                connectionPoolSetup(1, 1, 1, 100);
            }
            Properties pros = new Properties();
            pros.load(prob);
            basicDS.setDriverClassName(DRIVER_CLASS);
            basicDS.setUrl(pros.getProperty("url"));
            basicDS.setUsername(pros.getProperty("user"));
            basicDS.setPassword(pros.getProperty("password"));

        } catch (IOException ex) {
            throw new CommandException("Failed to load properties file. " + ex.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws CommandException {
        if (basicDS == null) {
            throw new CommandException("ConnectionPool Not instantiated yet!");
        }
        try {
            return basicDS.getConnection();
        } catch (SQLException ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    /**
     * Private method to set up the connection pool
     *
     * @param maxIdle
     * @param minIdle
     * @param initialSize
     * @param maxTotal
     */
    private void connectionPoolSetup(int maxIdle, int minIdle, int initialSize, int maxTotal) {
        basicDS.setMaxIdle(maxIdle);
        basicDS.setMinIdle(minIdle);
        basicDS.setInitialSize(initialSize);
        basicDS.setMaxTotal(maxTotal);
    }

}
