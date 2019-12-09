package presentation;

import exception.CommandException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.LogicFacade;
import logic.Product;

/**
 * The purpose of SearchProductCommand is to be able to handle receiving
 * different search terms, ie the id or category, and then return the searched
 * for products or an empty list, if the products doesn't exist
 *
 * @author carol
 */
public class SearchProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        List<Product> catalog = new ArrayList<>();
        String parameter = request.getParameter("searchType");
        try {
            String search = request.getParameter("search");
            switch (parameter) {
                case "product_id":
                    Product product = LogicFacade.getProduct(Integer.parseInt(search));
                    catalog.add(product);
                    break;
                case "product_itemnumber":
                    catalog = LogicFacade.getProductsByItemNumber(Integer.parseInt(search));
                    break;
                case "product_name":
                    catalog = LogicFacade.getProductsByName(search);
                    break;
                case "product_brand":
                    catalog = LogicFacade.getProductsByBrand(search);
                    break;
                case "product_category":
                    catalog = LogicFacade.getProductsByCategory(search);
                    break;
                case "product_tag":
                    catalog = LogicFacade.getProductsByTag(search);
                    break;
                case "product_supplier":
                    catalog = LogicFacade.getProductsBySupplier(search);
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException | CommandException | NullPointerException e) {
            //Doesn't have to throw an exception, it will just return an empty array
        }
        request.getSession().setAttribute("catalog", catalog);
        return "productcatalog";
    }

}
