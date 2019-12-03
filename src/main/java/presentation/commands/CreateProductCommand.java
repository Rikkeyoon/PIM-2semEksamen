package presentation;

import exception.CommandException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import logic.Category;
import logic.LogicFacade;
import logic.Product;
import org.apache.commons.lang.StringUtils;

/**
 * The purpose of CreateProductCommand is to get the new product's information
 * and pass that information on to the LogicFacade, and then put the new product
 * as a session attribute
 *
 * @author carol
 */
public class CreateProductCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        request.setAttribute("returnPage", "createproduct");
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Part> parts = null;
        int itemnumber = -1;
        String name = null, brand = null, description = null, supplier = null, seotext = null, fileSelected = null, category = null;
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        List<String> tags = null, attributes = null, attributes_name = null;

        for (String key : parameterMap.keySet()) {
            if (key.equalsIgnoreCase("item_number")) {
                itemnumber = Integer.parseInt(parameterMap.get(key)[0]);
            } else if (key.equalsIgnoreCase("product_name")) {
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
            } else if (key.equalsIgnoreCase("category")) {
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
            }
        }

        if (attributes_name != null && attributes != null && attributes_name.size() == attributes.size()) {
            for (int i = 0; i < attributes_name.size(); i++) {
                categoryAttributes.put(attributes_name.get(i), attributes.get(i));
            }
        }
        try {
            parts = (List<Part>) request.getParts();

        } catch (ServletException | IOException ex) {
            throw new CommandException(ex.getMessage());
        }

        Product p = new Product(itemnumber, name, brand, description, supplier, seotext, tags, null, categoryAttributes, null);
        request.getSession().setAttribute("product", p);

        p = LogicFacade.createProduct(p, category, fileSelected, parts);

        p.calculateStatus();
        session.setAttribute("product", p);
        session.setAttribute("category1", p.getCategory());
        
        return "viewproduct";
    }

}
