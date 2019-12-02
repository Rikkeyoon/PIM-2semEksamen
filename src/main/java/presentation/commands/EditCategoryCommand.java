package presentation;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import exception.CommandException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.Category;
import logic.LogicFacade;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.ArtifactUtils;

/**
 * The purpose of EditCategoryCommand is to receive the new information from the
 * FrontController, passing on the information to the LogicFacade, getting a
 * updated Category Object back and then putting the Category as a session
 * attribute
 *
 * @author carol, Nina, Allan
 */
public class EditCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> removeAttr = new ArrayList();
        List<String> newAttr = new ArrayList();
        List<String> oldAttr = new ArrayList();
        for (String attr : parameterMap.keySet()) {
            if (attr.contains("attr_old")) {
                oldAttr = new ArrayList<String>(Arrays.asList(parameterMap.get(attr)));
            } else if (attr.contains("attr_new")) {
                newAttr = new ArrayList<String>(Arrays.asList(parameterMap.get(attr)));
            } else if (attr.contains("attr_remove")) {
                removeAttr = new ArrayList<String>(Arrays.asList(parameterMap.get(attr)));
            }
        }
        for (String s : removeAttr) {
            int i = oldAttr.indexOf(removeAttr.get(0));
            try {
                newAttr.remove(i);
                oldAttr.remove(i);
            } catch (Exception e) {
                throw new CommandException("blæh" + e);
            }
            LogicFacade.deleteAttributeFromCategory(removeAttr);
        }
        for (int i = 0; i < oldAttr.size(); i++) {
            if (!oldAttr.get(i).equals(newAttr.get(i))) {
                LogicFacade.updateCategoryAttributename(oldAttr.get(i), newAttr.get(i));
            }
        }
        String categoryname = request.getParameter("category");
        List<String> gAttr = new ArrayList();
        for (String s: oldAttr){
        if (oldAttr != null && StringUtils.isNotBlank(s)) {
            gAttr.add(s);
     
            }
        }
        String[] cAttr = new String[gAttr.size()];
        Category c = LogicFacade.editCategory(categoryname, gAttr.toArray(cAttr));
            request.getSession().setAttribute("category", c);
        return "productcatalog";
    }

}
