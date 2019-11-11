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
        Product p = LogicFacade.updateProduct();
        HttpSession session = request.getSession();
        session.setAttribute("product", p);
        return "";
    }

}
