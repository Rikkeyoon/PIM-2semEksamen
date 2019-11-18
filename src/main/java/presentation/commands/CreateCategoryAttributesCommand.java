package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Nina
 */
public class CreateCategoryAttributesCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        String category = request.getParameter("category_name");
        
        return "productcatalog";
    }

   
}
