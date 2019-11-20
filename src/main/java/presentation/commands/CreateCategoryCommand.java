package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Category;
import logic.LogicFacade;


/**
 * @author Nina
 */
public class CreateCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        String categoryName = request.getParameter("category_name");
        String[] attributes = request.getParameterValues("attribute");
        Category c = LogicFacade.createCategory(categoryName, attributes);
        request.getSession().setAttribute("category", c);
        return "index";
    }

   
}
