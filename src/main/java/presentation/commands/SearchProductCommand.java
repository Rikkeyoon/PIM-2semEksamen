package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author carol
 */
public class SearchProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
