//package logic;
//
//import exception.CommandException;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import persistence.PersistenceFacadeDB;
//
///**
// *
// * @author carol
// */
//public class JSONConverterTest {
//
//    @Test
//    public void testConvertObjectToJSON_withCategoryObject() 
//            throws CommandException {
//        String expected = "{\"categoryname\":\"Test category\",\"attributes\":[\"1st attribute\","
//                + "\"2nd attribute\",\"3rd attribute\"]}";
//        List<String> attributes = new ArrayList<>();
//        attributes.add("1st attribute");
//        attributes.add("2nd attribute");
//        attributes.add("3rd attribute");
//        Category c = new Category("Test category", attributes);
//        
//        JSONConverter.convertObjectToJSON(c);
//        assertEquals(expected, result);
//    }
//    
//    @Test
//    public void testConvertObjectToJSON_withProductObject() throws CommandException {
//        PersistenceFacadeDB pf = new PersistenceFacadeDB(true);
//        Product product = pf.getProductWithCategoryAttributes(6);
//        List<String> tags = pf.getTagsForProductWithID(product.getId());
//        product.setTags(tags);
//        JSONConverter.convertObjectToJSON(product);
//    }
//    
//    @Test 
//    public void testConvertJSONToProductObject() throws CommandException {
//        String json = "{\"id\":10,\"itemnumber\":10,\"name\":\"Sony Ericsson Xperia\","
//                + "\"brand\":\"Sony Ericson\",\"description\":\"Revolutionerende "
//                + "telefon fra Sony Erricson\\r\\n\",\"category\":{\"categoryname"
//                + "\":\"Mobiler\",\"attributes\":[\"Kamera\",\"Processor\"]},"
//                + "\"supplier\":\"Ericsson Manufacturing\",\"status\":1,"
//                + "\"categoryAttributes\":{\"Processor\":\"En sexy processor\\r\\n"
//                + "\",\"Kamera\":\"Et shitty kamera\"},\"images\":["
//                + "{\"https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sonyEricssonXperia.jpg\""
//                + ":true}],\"tags\":[\"Elektronik\"],\"primaryImage\":\""
//                + "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/sonyEricssonXperia.jpg\""
//                + ",\"seotext\":\"Smartphone, Android\",\"tagsAsString\":\"Elektronik\"}";
//        PersistenceFacadeDB pf = new PersistenceFacadeDB(true);
//        Product p = pf.getProductWithCategoryAttributes(10);
//        List<String> tags = pf.getTagsForProductWithID(p.getId());
//        p.setTags(tags);
//        
//        Product p1 = JSONConverter.convertJSONToProductObject(json);
//        assertTrue(p.getName().equals(p1.getName()));
//    }
//}
