package presentation;

import exception.CommandException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import logic.Category;
import logic.Image;
import logic.LogicFacade;
import logic.Product;

/**
 * The purpose of CreateProductCommand is to get the new product's information
 * and pass that information on to the LogicFacade, and then put the new product
 * as a session attribute
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
        String brand = request.getParameter("brand");
        String description = request.getParameter("product_desc");
        String tags = request.getParameter("product_tags");
        String category = request.getParameter("product_category");
        String supplier = request.getParameter("supplier");
        String seotext = request.getParameter("seo_text");
        int status = Integer.parseInt(request.getParameter("status"));
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Image> imageURLs = LogicFacade.uploadImages(
                (List<Part>) request.getAttribute("partList"),
                request.getParameter("fileSelected"));

        Product p = LogicFacade.createProduct(id, itemnumber, name, brand,
                description, tags, supplier, seotext, status, parameterMap, imageURLs);
        request.getSession().setAttribute("product", p);

        return "index";
    }

}
