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
public class DeleteProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        Product p = (Product) request.getSession().getAttribute("product");
        LogicFacade.deleteProduct(p);
//        return "productcatalog";
        return "index";
    }
    
}
