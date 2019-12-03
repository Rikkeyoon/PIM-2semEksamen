package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.JSONConverter;
import logic.LogicFacade;
import logic.Product;

/**
 *
 *
 * @author carol
 */
public class DownloadCatalogCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        HttpSession session = request.getSession();
        List<Product> catalog = (List<Product>) session.getAttribute("catalog");
        LogicFacade.convertProductsToJSON(catalog);
        session.setAttribute("file_name", "catalog.json");
        return "/download";
    }

}
