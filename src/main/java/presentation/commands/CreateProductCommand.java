package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.LogicFacade;
import logic.Product;

/**
 *
 * @author carol
 */
public class CreateProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        int id = Integer.parseInt(request.getParameter("product_id"));
        String name = request.getParameter("product_name");
        String description = request.getParameter("product_desc");
        String category = request.getParameter("product_category");
        Product p = LogicFacade.createProduct(id, name, description, category);
        //FIXME: Skal sættes ind i kataloget somehow?
        request.getSession().setAttribute("product", p);
//        return "productcatalog";
        return "index";
    }

}
