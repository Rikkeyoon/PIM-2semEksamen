package presentation;

import exception.CommandException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.JSONConverter;
import logic.LogicFacade;
import logic.Product;

/**
 *
 * @author carol
 */
public class DownloadProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        HttpSession session = request.getSession();
        Product p = (Product) session.getAttribute("product");
        LogicFacade.convertObjectToJSON(p);
        session.setAttribute("file_name", "product.json");
        return "/download";
    }
    
}
