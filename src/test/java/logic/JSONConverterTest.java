    package logic ;

    import exception.CommandException ;
    import java.util.ArrayList ;
    import java.util.List ;
    import org.junit.Test ;
    import static org.junit.Assert.
    *;
    import persistence.PersistenceFacadeDB ;

    /**
     *
     * @author carol
     */
    public class JSONConverterTest {

//        @Test
//        public void testConvertObjectToJSON_withCategoryObject()
//                throws CommandException {
//            String expected = "{\"categoryname\":\"Test category\",\"attributes\":[\"1st attribute\","
//                    + "\"2nd attribute\",\"3rd attribute\"]}";
//            List<String> attributes = new ArrayList<>();
//            attributes.add("1st attribute");
//            attributes.add("2nd attribute");
//            attributes.add("3rd attribute");
//            Category c = new Category("Test category", attributes);
//
//            JSONConverter.convertObjectToJSON(c);
//            //assertEquals(expected, result);
//        }
//
//        @Test
//        public void testConvertObjectToJSON_withProductObject() throws CommandException {
//            PersistenceFacadeDB pf = new PersistenceFacadeDB(true);
//            Product product = pf.getProductWithCategoryAttributes(6);
//            List<String> tags = pf.getTagsForProductWithID(product.getId());
//            product.setTags(tags);
//            JSONConverter.convertObjectToJSON(product);
//        }

    
}
