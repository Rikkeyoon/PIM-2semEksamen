package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Category;
import logic.LogicFacade;
import logic.Product;

/**
 *
 * @author carol
 */
public class ViewPageCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        String view = request.getParameter("view");
        if (view.contains("catalog")) {
                List<Product> catalog = LogicFacade.getCatalog();
                request.getSession().setAttribute("catalog", catalog);
        } else if (view.contains("category")) {
                List<Category> categories = LogicFacade.getCategories();
                request.getSession().setAttribute("categories", categories);
        } else if (view.contains("viewproduct")) {
                int id = Integer.parseInt(request.getParameter("product_id"));
                Product product = LogicFacade.getProduct(id);
                request.getSession().setAttribute("product", product);
        }
        return view;
    }

}
