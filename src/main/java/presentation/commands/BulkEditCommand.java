/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import exception.CommandException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.LogicFacade;
import logic.Product;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author allan
 */
public class BulkEditCommand extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.setAttribute("returnPage", "bulkedit");
        Map<String, String[]> parameterMap = request.getParameterMap();
        String brand = null, supplier = null, seotext = null;
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        List<String> tags = null, attributes = null, attributes_name = null;
        List<String> bulkeditIDs = null;
        
        for (String key : parameterMap.keySet()) {
            if (key.equalsIgnoreCase("brand")) {
                brand = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("supplier")) {
                supplier = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("seo_text")) {
                seotext = parameterMap.get(key)[0];
            } else if (key.equalsIgnoreCase("attributename")) {
                attributes_name = new ArrayList<>(Arrays.asList(parameterMap.get(key)));
            } else if (key.equalsIgnoreCase("attributes")) {
                attributes = new ArrayList<>(Arrays.asList(parameterMap.get(key)));
            } else if (key.equalsIgnoreCase("bulkEditSelected")) {
                bulkeditIDs = new ArrayList<>(Arrays.asList(parameterMap.get(key)));
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

        if (attributes_name != null && attributes
                != null && attributes_name.size()
                == attributes.size()) {
            for (int i = 0; i < attributes_name.size(); i++) {
                categoryAttributes.put(attributes_name.get(i), attributes.get(i));
            }
        }

        Product p = new Product(brand, supplier, seotext, tags, categoryAttributes);

        LogicFacade.bulkEdit(p, bulkeditIDs);
        request.getSession().setAttribute("catalog", LogicFacade.getCatalog());
        request.getSession().setAttribute("categories", LogicFacade.getCategories());
       return "productcatalog";

    }
}
