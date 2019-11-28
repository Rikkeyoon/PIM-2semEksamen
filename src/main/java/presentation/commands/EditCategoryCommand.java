package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Category;
import logic.LogicFacade;

/**
 * The purpose of EditCategoryCommand is to receive the new information from the
 * FrontController, passing on the information to the LogicFacade, getting a
 * updated Category Object back and then putting the Category as a session
 * attribute
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
