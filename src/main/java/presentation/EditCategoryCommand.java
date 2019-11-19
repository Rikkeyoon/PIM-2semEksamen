package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Category;
import logic.LogicFacade;

/**
 *
 * @author carol
 */
public class EditCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        String categoryname = request.getParameter("category");
        String[] attributes = request.getParameterValues("attribute");
        Category c = LogicFacade.editCategory(categoryname, attributes);
        request.getSession().setAttribute("category", c);
        return "productcatalog";
    }
    
}
