package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carol
 */
public class ViewProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        return "producteditor";
    }
    
}
