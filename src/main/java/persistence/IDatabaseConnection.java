package persistence;

import exception.CommandException;
import java.sql.Connection;

/**
 * The purpose of IDatabaseConnection is to create a connection to a database
 *
 * @author allan
 */
public interface IDatabaseConnection {
    
    /**
     * Method to get the connection that has been established to the database
     *
     * @return Connection
     * @throws CommandException
     */
    public Connection getConnection()throws CommandException;
}
