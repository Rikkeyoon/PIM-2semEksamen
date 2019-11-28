package logic;

import exception.CommandException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author carol
 */
public class JSONConverterTest {

    @Test
    public void testConvertObjectToJSON_withCategoryObject() 
            throws CommandException {
        JSONConverter instance = new JSONConverter();
        String expected = "{\"categoryname\":\"Test category\",\"attributes\":[\"1st attribute\","
                + "\"2nd attribute\",\"3rd attribute\"]}";
        List<String> attributes = new ArrayList<>();
        attributes.add("1st attribute");
        attributes.add("2nd attribute");
        attributes.add("3rd attribute");
        Category c = new Category("Test category", attributes);
        
        String result = instance.convertObjectToJSON(c);
        assertEquals(expected, result);
    }
    
//    @Test
//    public void testConvertObjectToJSON_withProductObject() 
//            throws CommandException {
//        JSONConverter instance = new JSONConverter();
//        String expected = "{\"id\":1,\"itemnumber\":1,\"name\":\"Test product\","
//                + "\"brand\":\"Test brand\",\"description\":\"Test description\","
//                + "\"category\":{\"categoryname\":\"Test category\",\"attributes"
//                + "\":[\"1st attribute\",\"2nd attribute\",\"3rd attribute\"]},"
//                + "\"supplier\":\"Test supplier\",\"status\":1,\"categoryAttributes"
//                + "\":null,\"images\":[{\"Test primary image\":true}],\"tags\":"
//                + "[\"1st attribute\",\"2nd attribute\",\"3rd attribute\"],\""
//                + "seotext\":\"Test SEOText\",\"tagsAsString\":\"1st attribute, "
//                + "2nd attribute, 3rd attribute\",\"primaryImage\":\"Test primary"
//                + " image\"}";
//        List<String> attributes = new ArrayList<>();
//        attributes.add("1st attribute");
//        attributes.add("2nd attribute");
//        attributes.add("3rd attribute");
//        Category c = new Category("Test category", attributes);
//        List<Pair<String, Boolean>> images = new ArrayList<>();
//        images.add(new MutablePair<>("Test primary image", true));
//        Product p = new Product(1, 1, "Test product", "Test brand", 
//                "Test description", c, "Test supplier", "Test SEOText", 1, images);
//        p.setTags(attributes);
//        
//        String result = instance.convertObjectToJSON(p);
//        assertEquals(expected, result);
//    }
    
//    @Test 
//    public void testConvertJSONToObject_withCategoryObject() {
//        JSONConverter instance = new JSONConverter();
//        List<String> attributes = new ArrayList<>();
//        attributes.add("1st attribute");
//        attributes.add("2nd attribute");
//        attributes.add("3rd attribute");
//        Category expected = new Category("Test category", attributes);
//        
//        String json = "{\"categoryname\":\"Test category\",\"attributes\":[\"1st attribute\","
//                + "\"2nd attribute\",\"3rd attribute\"]}";
//        
//        Category result = instance.convertJSONToCategoryObject(json);
//        assertEquals(expected, result);
//    }
}
