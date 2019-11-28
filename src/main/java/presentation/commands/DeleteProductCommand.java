package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.LogicFacade;
import logic.Product;

/**
 * The purpose of DeleteProductCommand is to get the session's product, which is
 * going to be deleted, and then passing that information on to the LogicFacade
 *
 * @author carol
 */
public class DeleteProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        HttpSession session = request.getSession();
        Product p = (Product) session.getAttribute("product");
        LogicFacade.deleteProduct(p);
        return "index";
    }

}
