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
            switch (parameter) {
                case "product_id":
                    int id = Integer.parseInt(request.getParameter("search"));
                    Product product = LogicFacade.getProduct(id);
                    catalog.add(product);
                    break;
                case "product_itemnumber":
                    int itemNumber = Integer.parseInt(request.getParameter("search"));
                    catalog = LogicFacade.getProductsByItemNumber(itemNumber);
                    break;
                case "product_name":
                    String name = request.getParameter("search");
                    catalog = LogicFacade.getProductsByName(name);
                    break;
                case "product_brand":
                    String brand = request.getParameter("search");
                    catalog = LogicFacade.getProductsByBrand(brand);
                    break;
                case "product_category":
                    String category = request.getParameter("search");
                    catalog = LogicFacade.getProductsByCategory(category);
                    break;
                case "product_tag":
                    String tag = request.getParameter("search");
                    catalog = LogicFacade.getProductsByTag(tag);
                    break;
                case "product_supplier":
                    String supplier = request.getParameter("search");
                    catalog = LogicFacade.getProductsBySupplier(supplier);
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
