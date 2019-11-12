package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.LogicFacade;
import logic.Product;

/**
 *
 * @author carol
 */
public class ViewCatalogCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        List<Product> catalog = LogicFacade.getCatalog();
        HttpSession session = request.getSession();
        session.setAttribute("catalog", catalog);
        return "productcatalog";
    }

    
}
