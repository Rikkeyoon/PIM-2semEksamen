package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        if (view.contains("catalog")) {
            List<Product> catalog = LogicFacade.getCatalog();
            session.setAttribute("catalog", catalog);
        } else if (view.contains("category")) {
            List<Category> categories = LogicFacade.getCategories();
            session.setAttribute("categories", categories);
        } else if (view.contains("viewproduct")) {
            int id = Integer.parseInt(request.getParameter("product_id"));
            Product product = LogicFacade.getProduct(id);
            session.setAttribute("product", product);
        }
        return view;
    }

}
