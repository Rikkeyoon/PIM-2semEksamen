package presentation;

import exception.CommandException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import logic.LogicFacade;
import logic.Product;
import org.apache.commons.lang.StringUtils;

/**
 * The purpose of UpdateProductCommand is to receive the information from the
 * FrontController and pass it on to the LogicFacade and get a updated product
 * back which is put as a session attribute, and then a view is returned
 *
 * @author carol
 */
public class UpdateProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        HttpSession session = request.getSession();
        request.setAttribute("returnPage", "updateproduct");
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Part> parts = null;
        int itemnumber = -1, id = -1;
        String[] picsToDelete = null;
        String name = null, brand = null, description = null, supplier = null, seotext = null, fileSelected = null, category = null;
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        List<String> tags = null, attributes = null, attributes_name = null;

        for (String key : parameterMap.keySet()) {
            if (key.equalsIgnoreCase("product_id")) {
                id = Integer.parseInt(parameterMap.get(key)[0]);
            }else if (key.equalsIgnoreCase("item_number")) {
                itemnumber = Integer.parseInt(parameterMap.get(key)[0]); 
            }else if (key.equalsIgnoreCase("product_name")) {
                name = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("brand")) {
                brand = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("product_desc")) {
                description = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("supplier")) {
                supplier = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("seo_text")) {
                seotext = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("fileSelected")) {
                fileSelected = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("product_category")) {
                category = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("attributename")) {
                attributes_name = new ArrayList<>(Arrays.asList(parameterMap.get(key)));
            } else if (key.equalsIgnoreCase("attributes")) {
                attributes = new ArrayList<>(Arrays.asList(parameterMap.get(key)));
            } else if (key.equalsIgnoreCase("product_tags")) {
                String str = parameterMap.get(key)[0];
                tags = new ArrayList<>();
                for (String s : Arrays.asList(str.split(",[ ]*"))) {
                    if (StringUtils.isNotBlank(s)) {
                        tags.add(s);
                    }
                }
            } else if (key.equalsIgnoreCase("delete_chosen_pics")) {
                picsToDelete = parameterMap.get(key);
            }
        }

        if (attributes_name != null && attributes
                != null && attributes_name.size()
                == attributes.size()) {
            for (int i = 0; i < attributes_name.size(); i++) {
                categoryAttributes.put(attributes_name.get(i), attributes.get(i));
            }
        }

        try {
            parts = (List<Part>) request.getParts();

        } catch (ServletException | IOException ex) {
            throw new CommandException(ex.getMessage());
        }

        Product p = new Product(id, itemnumber, name, brand, description, supplier,
                seotext, tags, null, categoryAttributes, null);
        request.getSession().setAttribute("product", p);

        p = LogicFacade.updateProduct(p, category, picsToDelete, fileSelected, parts);

        
        session.setAttribute("product", p);
        session.setAttribute("category1", p.getCategory());

        return "viewproduct";

    }

}
