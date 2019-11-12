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
public class ViewProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        int id = Integer.parseInt(request.getParameter("product_id"));
        Product p1 = LogicFacade.getProduct(id);
        HttpSession session = request.getSession();
        session.setAttribute("product", p1);
        return "producteditor";
    }
    
}
