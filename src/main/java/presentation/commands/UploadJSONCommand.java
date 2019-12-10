package presentation;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import logic.LogicFacade;

/**
 *
 * @author carol
 */
public class UploadJSONCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) 
            throws CommandException {
        List<Part> parts = (List<Part>) request.getAttribute("partList");
        LogicFacade.uploadJSON(parts);
        request.getSession().setAttribute("catalog", LogicFacade.getCatalog());
        return "productcatalog";
    }
    
}
