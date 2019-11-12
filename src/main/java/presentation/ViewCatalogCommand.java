package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        //application scope attribute
//        request.getServletContext().setAttribute("catalog", catalog);
        request.getSession().setAttribute("catalog", catalog);
        return "productcatalog";
    }

    
}
