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
public class ViewProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        int id = Integer.parseInt(request.getParameter("product_id"));
        Product p = LogicFacade.getProduct(id);
        request.getSession().setAttribute("product", p);
        return "viewproduct";
    }
    
}
