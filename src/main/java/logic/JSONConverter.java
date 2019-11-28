package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.CommandException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author carol
 */
public class JSONConverter {

    private ObjectMapper mapper = new ObjectMapper();
    private static final String UPLOAD_DIR = "json";
    private static final String WORKING_DIR = System.getProperty("user.dir");

    /**
     * Method to convert Java Objects into JSON Strings using the jackson
     * ObjectMapper
     *
     * @param o Object
     * @return String
     * @throws exception.CommandException
     */
    public String convertObjectToJSON(Object o) throws CommandException {
        String json = "";
        try {
            //get Class to acess the class' methods
            Class<? extends Object> aClass = o.getClass();
            //get the Object's name to use as the filename
            String fileName = aClass.getName();

            File uploadFolder = new File(WORKING_DIR + File.separator + UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            File file = new File(WORKING_DIR + File.separator
                    + UPLOAD_DIR + File.separator + fileName);
            mapper.writeValue(file, o);
            //delete the file again, so it doesn't create a bunch of crap
            file.delete();

            // Java objects to JSON string - compact-print
            json = mapper.writeValueAsString(o);
        } catch (IOException ex) {
            throw new CommandException("" + ex);
        }
        return json;
    }

    /**
     * Method to convert a JSON String into a Category Object
     *
     * @param jsonString
     * @return Object
     */
    public Category convertJSONToCategoryObject(String jsonString) {
        Category category = null;
        try {
            Object o = mapper.readValue(jsonString, Object.class);
            category = mapper.convertValue(o, Category.class);
        } catch (IOException ex) {
        }
        return category;
    }
}
