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
import org.apache.maven.artifact.ArtifactUtils;

/**
 *
 * @author carol, Nina, Allan
 */
public class EditCategoryCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        String edit = request.getParameter("edit");
        if (edit.equals("editNew")) {
            String categoryname = request.getParameter("category");
            String[] attributes = request.getParameterValues("attribute");
            Category c = LogicFacade.editCategory(categoryname, attributes);
            request.getSession().setAttribute("category", c);
        } else if (edit.equals("editOld")) {
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
           // throw new CommandException("" + newAttr + " " + oldAttr);
            for (int i = 0; i < oldAttr.size(); i++) {
                if (!oldAttr.get(i).equals(newAttr.get(i))) {
                    LogicFacade.updateCategoryAttributename(oldAttr.get(i), newAttr.get(i));
                }
            }
        }
        return "productcatalog";
    }

}
