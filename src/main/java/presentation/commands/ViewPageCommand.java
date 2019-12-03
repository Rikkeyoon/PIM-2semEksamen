package presentation;

import exception.CommandException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Category;
import logic.LogicFacade;
import logic.Product;

/**
 * The purpose of ViewPageCommand is to get a generic command that can navigate
 * to another view page and get the wanted data for the view page to work
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
            List<Category> categories = LogicFacade.getCategories();
            session.setAttribute("categories", categories);
        } else if (view.contains("bulkedit")) {
            List<Product> catalog = LogicFacade.getProductsByCategory(request.getParameter("category"));
            session.setAttribute("catalog", catalog);
            session.setAttribute("category1", request.getParameter("category"));
        } else if (view.contains("bulkproductsedit")) {
            session.setAttribute("bulkeditids", Arrays.toString(request.getParameterValues("bulkEditSelected")));
        } else if (view.contains("category")) {
            session.setAttribute("category1", request.getParameter("category"));
        } else if (view.contains("viewproduct")) {
            session.setAttribute("category1", request.getParameter("category"));
            int id = Integer.parseInt(request.getParameter("product_id"));
            Product product = LogicFacade.getProduct(id);
            session.setAttribute("product", product);
        } else if (view.contains("createproduct")) {
            session.setAttribute("category1", request.getParameter("category"));
        }
        return view;
    }

}
