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
import org.apache.commons.lang.StringUtils;

/**
 * The purpose of JSONConverter is to convert Java Objects into JavaScript
 * Object Notation (JSON) Strings, or to convert JSON files into Java Objects
 *
 * @author carol
 */
public class JSONConverter {

    private static ObjectMapper mapper = new ObjectMapper();
    private static final String UPLOAD_DIR = "json";
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static String working_dir = "";

    public static void workingDirSetup() {
        if (OS.contains("win") || OS.contains("mac")) {
            working_dir = System.getProperty("user.dir");
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            working_dir = System.getProperty("catalina.base");
        } else {
            working_dir = "";
        }
    }

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
            File file = getFile(fileName);

            // Java objects to JSON string - compact-print
            mapper.writeValue(file, o);
//            file.delete();
        } catch (IOException ex) {
            throw new CommandException("Could not convert the product to JSON" + ex);
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
            File file = getFile(fileName);

            /*BufferedWriter bliver brugt for at slippe for String format med \"
             *Dog er der en known bug ved brug af BufferedWriter, men det er den 
             *bedste løsning, vi har fundet frem til
             */
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Product product : products) {
                    // Java objects to JSON string - compact-print
                    writer.append(mapper.writeValueAsString(product));
                    writer.newLine();
                }
                mapper.writeValue(file, writer.toString());
            }
        } catch (IOException ex) {
            throw new CommandException("Could not convert the products to JSON" + ex);
        }
    }

    /**
     * Method to convert Java Objects into JSON Strings using the jackson
     * ObjectMapper
     *
     * @param categories List of Products
     * @throws exception.CommandException
     */
    public static void convertCategoriesToJSON(List<Category> categories)
            throws CommandException {
        try {
            String fileName = "categories.json";
            File file = getFile(fileName);

            /*BufferedWriter bliver brugt for at slippe for String format med \"
             *Dog er der en known bug ved brug af BufferedWriter, men det er den 
             *bedste løsning, vi har fundet frem til
             */
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Category category : categories) {
                    // Java objects to JSON string - compact-print
                    writer.append(mapper.writeValueAsString(category));
                    writer.newLine();
                }
                mapper.writeValue(file, writer.toString());
            }
        } catch (IOException ex) {
            throw new CommandException("Could not convert the categories to JSON" + ex);
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
        String fileName = part.getSubmittedFileName();
        String contentType = part.getContentType();
        workingDirSetup();

        if (contentType != null && contentType.equalsIgnoreCase("application/json")) {
            try {
                String filePath = working_dir + File.separator + UPLOAD_DIR
                        + File.separator + fileName;
                part.write(filePath);
                File file = new File(filePath);

                InputStream is = new FileInputStream(file);
                BufferedReader bw = new BufferedReader(new InputStreamReader(is));
                String line = bw.readLine();

                while (line != null) {
                    try {
                        Category c = mapper.readValue(line, Category.class);
                        objects.add(c);
                    } catch (UnrecognizedPropertyException e) {
                    }
                    try {
                        Product p = mapper.readValue(line, Product.class);
                        objects.add(p);
                    } catch (UnrecognizedPropertyException e) {
                    }
                    line = bw.readLine();
                }
            } catch (IOException ex) {
                throw new CommandException("Could not upload JSON file. Please try again! " + ex);
            }
        }
        return objects;
    }

    /**
     * Private method to create a file, made to reduce some duplicate code
     *
     * @param fileName
     * @return File
     */
    private static File getFile(String fileName) {
        workingDirSetup();
        File uploadFolder = new File(working_dir + File.separator + UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        File file = new File(working_dir + File.separator
                + UPLOAD_DIR + File.separator + fileName);
        return file;
    }
}
