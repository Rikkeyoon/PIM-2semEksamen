package presentation;

import exception.CommandException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.LogicFacade;
import logic.Product;
import presentation.Command;

/**
 *
 * @author carol
 */
public class SearchProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<Product> catalog = new ArrayList<>();
        try {
            try {
                int id = Integer.parseInt(request.getParameter("product_id"));
                Product product = LogicFacade.getProduct(id);
                catalog.add(product);
            } catch (NumberFormatException e) {
                String name = request.getParameter("product_name");
                if (name != null && !name.isBlank()) {
                    catalog = LogicFacade.getProductsByName(name);
                } else {
                    String category = request.getParameter("product_category");
                    catalog = LogicFacade.getProductsByCategory(category);
                }
            }
        } catch (CommandException ex) {
            //Doesn't have to throw an exception, it will just return an empty array
        }
        request.getSession().setAttribute("catalog", catalog);
        return "productcatalog";
    }

}
