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
public class CreateProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        int id = Integer.parseInt(request.getParameter("product_id"));
        String name = request.getParameter("product_name");
        String description = request.getParameter("product_desc");
        Product p = LogicFacade.createProduct(id, name, description);
        HttpSession session = request.getSession();
        session.setAttribute("product", p);
        return "";
    }
    
}
