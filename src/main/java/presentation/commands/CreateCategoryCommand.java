package presentation;

import logic.Category;
import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.LogicFacade;


/**
 * @author Nina
 */
public class CreateCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        String categoryname = request.getParameter("category_name");
        Category c = LogicFacade.createCategory(categoryname);
        request.getSession().setAttribute("category", c);
        return "createcategory";
    }
    
}
