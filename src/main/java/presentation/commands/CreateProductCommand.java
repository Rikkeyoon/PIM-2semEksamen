package presentation;

import exception.CommandException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import logic.LogicFacade;
import logic.Product;

/**
 *
 * @author carol
 */
public class CreateProductCommand extends Command {
    
    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        int id = Integer.parseInt(request.getParameter("product_id"));
        int itemnumber = Integer.parseInt(request.getParameter("item_number"));
        String name = request.getParameter("product_name");
        String description = request.getParameter("product_desc");
        String category = request.getParameter("product_category");
        String supplier = request.getParameter("supplier");
        List<Pair<String, Boolean>> imageURLs = LogicFacade.uploadImages((List<Part>)request.getAttribute("partList"), request.getParameter("fileSelected"));
        Product p = LogicFacade.createProduct(id, itemnumber, name, description, category, supplier, imageURLs);
        //FIXME: Skal sættes ind i kataloget somehow?
        request.getSession().setAttribute("product", p);
//        return "productcatalog";
        return "index";
    }
    

}
