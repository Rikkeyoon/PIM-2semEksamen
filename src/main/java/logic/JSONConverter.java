package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.CommandException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author carol
 */
public class JSONConverter {
    
    private static ObjectMapper mapper = new ObjectMapper();
    private static final String UPLOAD_DIR = "json";
    private static final String WORKING_DIR = System.getProperty("user.dir");

    /**
     * Method to convert Java Objects into JSON Strings using the jackson
     * ObjectMapper
     *
     * @param o Object
     * @throws exception.CommandException
     */
    public static void convertObjectToJSON(Object o) throws CommandException {
        try {
//            //get Class to acess the class' methods
//            Class<? extends Object> aClass = o.getClass();
//            //get the Object's name to use as the filename
//            String fileName = aClass.getName();
            String fileName = "product.json";
            
            File uploadFolder = new File(WORKING_DIR + File.separator + UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            
            File file = new File(WORKING_DIR + File.separator
                    + UPLOAD_DIR + File.separator + fileName);
            // Java objects to JSON string - compact-print
            mapper.writeValue(file, o);
//            file.delete();
        } catch (IOException ex) {
            throw new CommandException("" + ex);
        }
    }

    /**
     * Method to convert Java Objects into JSON Strings using the jackson
     * ObjectMapper
     *
     * @param products List of Products
     * @throws exception.CommandException
     */
    public static void convertProductsToJSON(List<Product> products) 
            throws CommandException {
        try {
            String fileName = "catalog.json";
            
            File uploadFolder = new File(WORKING_DIR + File.separator + UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            
            File file = new File(WORKING_DIR + File.separator
                    + UPLOAD_DIR + File.separator + fileName);
            
            StringBuilder sb = new StringBuilder();
            for (Product product : products) {
                // Java objects to JSON string - compact-print
                sb.append(mapper.writeValueAsString(product));   
            }
            
            mapper.writeValue(file, sb.toString());
        } catch (IOException ex) {
            throw new CommandException("" + ex);
        }
    }
    
    /**
     * Method to convert a JSON String into a Category Object
     *
     * @param jsonString
     * @return Object
     */
    public static Category convertJSONToCategoryObject(String jsonString) {
        Category category = null;
        try {
            Object o = mapper.readValue(jsonString, Object.class);
            category = mapper.convertValue(o, Category.class);
        } catch (IOException ex) {
        }
        return category;
    }

}
