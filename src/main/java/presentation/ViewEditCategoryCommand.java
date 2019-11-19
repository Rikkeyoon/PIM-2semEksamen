package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Category;
import logic.LogicFacade;
import presentation.Command;

/**
 *
 * @author carol
 */
public class ViewEditCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        List<Category> categories = LogicFacade.getCategories();
        request.getSession().setAttribute("categories", categories);
        return "editcategory";
    }

}
