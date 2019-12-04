package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import exception.CommandException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Part;

/**
 * The purpose of JSONConverter is to convert Java Objects into JavaScript
 * Object Notation (JSON) Strings, or to convert JSON files into Java Objects
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

            //BufferedWriter bliver brugt for at slippe for String format med \"
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Product product : products) {
                    // Java objects to JSON string - compact-print
                    writer.append(mapper.writeValueAsString(product));
                    writer.append("\n");
                }
                mapper.writeValue(file, writer.toString());
            }
        } catch (IOException ex) {
            throw new CommandException("" + ex);
        }
    }

    /**
     * Method to convert a file, containing a JSON String, into a Category
     * Object or Product Object
     *
     * @param part
     * @return Object
     * @throws exception.CommandException
     */
    public static List<Object> convertPartToObjects(Part part) throws CommandException {
        List<Object> objects = new ArrayList<>();
        if (part.getContentType() != null && part.getSize() > 0) {
            String fileName = part.getSubmittedFileName();
            String contentType = part.getContentType();

            if (contentType != null && contentType.equalsIgnoreCase("application/json")) {
                try {
                    part.write(WORKING_DIR + File.separator + UPLOAD_DIR
                            + File.separator + fileName);
                    File file = new File(WORKING_DIR + File.separator
                            + UPLOAD_DIR + File.separator + fileName);

                    InputStream is = new FileInputStream(file);
                    BufferedReader bw = new BufferedReader(new InputStreamReader(is));
                    String line = bw.readLine();
//                    StringBuilder sb = new StringBuilder();

                    while (line != null) {
//                        sb.append(line);
//                        if (line.charAt(line.length() - 1) == '}') {
//                            String fileAsString = sb.toString();

                            try {
//                                c = mapper.readValue(fileAsString, Category.class);
                                Category c = mapper.readValue(line, Category.class);
                                objects.add(c);
                            } catch (UnrecognizedPropertyException e) {
                            }
                            try {
//                                p = mapper.readValue(fileAsString, Product.class);
                                Product p = mapper.readValue(line, Product.class);
                                objects.add(p);
                            } catch (UnrecognizedPropertyException e) {
                            }
//                        }
                        line = bw.readLine();
                    }

                } catch (IOException ex) {
                    throw new CommandException("Could not upload JSON file. Please try again! " + ex);
                }
            }
        }
//        if (p != null) {
//            return p;
//        } else if (objects != null) {
//            return objects;
//        }
        return objects;
    }
}