package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Category;
import logic.LogicFacade;

/**
 *
 * @author carol
 */
public class DownloadCategoriesCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        HttpSession session = request.getSession();
        List<Category> categories = (List<Category>)session.getAttribute("categories");
        LogicFacade.convertCategoriesToJSON(categories);
        session.setAttribute("file_name", "categories.json");
        return "/download";
    }
    
}
