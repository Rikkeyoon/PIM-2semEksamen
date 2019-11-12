package persistence;

import exception.CommandException;
import java.util.ArrayList;
import java.util.List;
import logic.Product;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import stubobjects.StubProductMapper;

/**
 *
 * @author carol
 */
public class ProductMapperTest {

//    private static IProductMapper instance;
    private static List<Product> products = new ArrayList<>();
    private static StubProductMapper instance = new StubProductMapper(products);

    @BeforeClass
    public static void setup() {
        Product product = new Product(1, "Test1", "This is a test object",
                "Test objects");
        products.add(product);
    }

    @Test
    public void testCreate() throws CommandException {
        Product product = new Product(2, "Test2", "This is a test object",
                "Test objects");
        instance.create(product);
        assertEquals(2, instance.products.size());
        assertTrue(product.compareTo(instance.products.get(1)));
    }

    @Test(expected = CommandException.class)
    public void testCreate_DuplicateId() throws CommandException {
        Product product = new Product(1, "Test", "This is a test object",
                "Test objects");
        instance.create(product);
    }

    @Test
    public void testGetProduct() throws CommandException {
        String name = "Test1";
        Product result = instance.getProduct(name);
        assertTrue(result.compareTo(instance.products.get(0)));
    }

    @Test(expected = CommandException.class)
    public void testGetProduct_UnknownName() throws CommandException {
        String name = "Unknown";
        instance.getProduct(name);
    }

    @Test
    public void testGetAllProducts() throws CommandException {
        List<Product> result = instance.getAllProducts();
        assertEquals(result, instance.products);
    }

    @Test(expected = CommandException.class)
    public void testGetAllProducts_NoProducts() throws CommandException {
        List<Product> emptyList = new ArrayList<>();
        StubProductMapper spm = new StubProductMapper(emptyList);
        List<Product> result = spm.getAllProducts();
    }
    
    @Test
    public void testUpdate() throws CommandException {
        Product product = new Product(1, "Test3", "This is a new test object",
                "Test objects");
        instance.update(product);
        assertTrue(product.compareTo(instance.products.get(0)));
        assertTrue(instance.products.size() == 2);
    }
    
    @Test (expected = CommandException.class)
    public void testUpdate_UnknownId() throws CommandException {
        Product product = new Product(7, "Test4", "This is a new test object",
                "Test objects");
        instance.update(product); 
    }
    
//    @Test
//    public void testDelete() throws CommandException {
//        Product product = new Product(3, "Test5", "This is a test object",
//                "Test objects");
//        instance.create(product);
//        instance.delete(product);
//    }
    
    @Test (expected = CommandException.class)
    public void testDelete_UnknownId() throws CommandException {
        Product product = new Product(7, "Test4", "This is a new test object",
                "Test objects");
        instance.delete(product);
    }
    
}
