package presentation;

import exception.CommandException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.LogicFacade;
import logic.Product;
import org.apache.commons.lang.StringUtils;

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
        try {
//            int id = Integer.parseInt(request.getParameter("product_id"));
//            Product product = LogicFacade.getProduct(id);
//            catalog.add(product);
            String name = request.getParameter("product_name");
            if (name != null && StringUtils.isNotBlank(name)) {
                catalog = LogicFacade.getProductsByName(name);
            }
            String category = request.getParameter("product_category");
            if (category != null && StringUtils.isNotBlank(category)) {
                catalog = LogicFacade.getProductsByCategory(category);
            }
            String tag = request.getParameter("product_tag");
            if (tag != null && StringUtils.isNotBlank(tag)) {
                catalog = LogicFacade.getProductsByTag(tag);
            }
            String brand = request.getParameter("brand");
            if (brand != null && StringUtils.isNotBlank(brand)) {
                catalog = LogicFacade.getProductsByBrand(brand);
            }
            String supplier = request.getParameter("supplier");
            if (supplier != null && StringUtils.isNotBlank(supplier)) {
                catalog = LogicFacade.getProductsBySupplier(supplier);
            }

//            try {
//                int itemNumber = Integer.parseInt(request.getParameter("item_number"));
//                catalog = LogicFacade.getProductsByItemNumber(itemNumber);
//            } catch (NumberFormatException e) {
//                String brand = request.getParameter("brand");
//                if (brand != null && StringUtils.isNotBlank(brand)) {
//                    catalog = LogicFacade.getProductsByBrand(brand);
//                }
//                String supplier = request.getParameter("supplier");
//                if (supplier != null && StringUtils.isNotBlank(supplier)) {
//                    catalog = LogicFacade.getProductsBySupplier(supplier);
//                }
//            }
        } catch (CommandException ex) {
            //Doesn't have to throw an exception, it will just return an empty array
        }
        request.getSession()
                .setAttribute("catalog", catalog);

        return "productcatalog";
    }

}
