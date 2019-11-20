package presentation;

import exception.CommandException;
import java.util.Map;
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
        Map<String, String[]> parameterMap = request.getParameterMap();
        p = LogicFacade.updateProduct(p, parameterMap);
        session.setAttribute("product", p);
        return "index";
    }

}
