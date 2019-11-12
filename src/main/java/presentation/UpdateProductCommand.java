package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.LogicFacade;
import logic.Product;

/**
 *
 * @author carol
 */
public class UpdateProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        HttpSession session = request.getSession();
        Product p = (Product) session.getAttribute("product");
        String name = request.getParameter("product_name");
        String description = request.getParameter("product_desc");
        String category = request.getParameter("product_category");
        //TODO: Change to String... (varargs) or ArrayList<String>
        p = LogicFacade.updateProduct(p, name, description, category);
        session.setAttribute("product", p);
        return "productcatalog";
    }

}
