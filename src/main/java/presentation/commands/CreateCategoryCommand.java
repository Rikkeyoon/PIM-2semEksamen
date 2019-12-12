package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Category;
import logic.LogicFacade;


/**
 * The purpose of CreateCategoryCommand is to get the new category's information
 * and pass that information on to the LogicFacade, and then put the new category
 * as a session attribute
 * 
 * @author Nina
 */
public class CreateCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        request.setAttribute("returnPage", "createcategory");    
        String categoryName = request.getParameter("category_name");
        String[] attributes = request.getParameterValues("attribute");
        Category c = LogicFacade.createCategory(categoryName, attributes);
        request.getSession().setAttribute("catalog", LogicFacade.getCatalog());
        request.getSession().setAttribute("category", c);
        return "productcatalog";
    }

   
}
