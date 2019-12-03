package presentation;

import exception.CommandException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import logic.Image;
import logic.LogicFacade;
import logic.Product;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The purpose of UpdateProductCommand is to receive the information from the FrontController
 * and pass it on to the LogicFacade and get a updated product back which is put
 * as a session attribute, and then a view is returned
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
        List<Image> imageURLs = LogicFacade.uploadImages(
                (List<Part>)request.getAttribute("partList"),
                request.getParameter("fileSelected"));
        p = LogicFacade.updateProduct(p, parameterMap, imageURLs);
        session.setAttribute("product", p);
        return "index";
    }

}
