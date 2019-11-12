package PersistenceTest;

import exception.CommandException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import logic.Product;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import persistence.DataSourceController;

/**
 *
 * @author allan
 */
public class ProductMapperTest {

    private static DataSourceController dsc;

    @BeforeClass
    public static void setup() {
        dsc = new DataSourceController(true);
    }

    @Test //Tests that we can get a connection with the database.
    public void getConnectionTest() throws CommandException {

        //Arrange
        Connection con = null;

        //Act
        con = DataSourceController.getConnection();

        //Assert
        assertNotNull(con);

    }

    @Test //getProductTest1, tests that we can get min, min+1, middle-1, middle, middle+1, max-1 & max ID objects from the the test database.
    public void getProductTest1() throws CommandException {
       //Arrange
        Product[] products = new Product[7];

        //Act
        products[0] = dsc.getProduct("Rød Cykel");
        products[1] = dsc.getProduct("Grøn Cykel");
        products[2] = dsc.getProduct("Carlsberg 6 pack");
        products[3] = dsc.getProduct("Sierra Silver Tequila");
        products[4] = dsc.getProduct("Smirnoff Vodka 37,5%");
        products[5] = dsc.getProduct("Carpe Diem Harmano");
        products[6] = dsc.getProduct("Tempur Fusion");

        //Assert
        assertEquals(1, products[0].getId());
        assertEquals(2, products[1].getId());
        assertEquals(12, products[2].getId());
        assertEquals(13, products[3].getId());
        assertEquals(14, products[4].getId());
        assertEquals(24, products[5].getId());
        assertEquals(25, products[6].getId());

    }

    @Test //getProductTest2, test that we can get a specific product with all fields.
    public void getProductTest2() throws CommandException {

        //Arrange
        Product p = null;

        //Act
        p = dsc.getProduct("Rød Cykel");

        //Assert

        assertEquals(1, p.getId());
        assertEquals("Rød Cykel", p.getName());
        assertEquals("En Cykel der er rød", p.getDescription());
        assertEquals("Cykler", p.getCategoryname());
    }

    //getProductTestFail, Tests that a CommandException is thrown when searching for nonexisting product
    @Test(expected = CommandException.class)
    public void getProductTestFail() throws CommandException {

        //Arrange
        //Act
        dsc.getProduct("Flying Bike");
        //Assert
    }

    @Test //GetProductsTest1, test that we can get all products in one list and the fields are not null
    public void getProductsTest1() throws CommandException {
        //Arrange
        List<Product> productList = null;

        //Act
        productList = dsc.getProducts();

        //Assert
        //assertEquals(25, productList.size());
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            assertNotNull(product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getDescription());
            assertNotNull(product.getCategoryname());
        }
    }

    @Test //getProductsTest2, Test that we can get all products and assert the field values at min, min+1, middle-1, middle, middle+1, max-1 & max ID objects from the the test database.
    public void getProductsTest2() throws CommandException {
        //Arrange
        List<Product> productList = null;
        Product[] products = new Product[7];

        //Act
        productList = dsc.getProducts();
        products[0] = productList.get(0);
        products[1] = productList.get(1);
        products[2] = productList.get(11);
        products[3] = productList.get(12);
        products[4] = productList.get(13);
        products[5] = productList.get(23);
        products[6] = productList.get(24);

        //Assert
        //assertEquals(25, productList.size());

        assertEquals(1, products[0].getId());
        assertEquals("Rød Cykel", products[0].getName());
        assertEquals("En Cykel der er rød", products[0].getDescription());
        assertEquals("Cykler", products[0].getCategoryname());

        assertEquals(2, products[1].getId());
        assertEquals("Grøn Cykel", products[1].getName());
        assertEquals("En Cykel der er grøn", products[1].getDescription());
        assertEquals("Cykler", products[1].getCategoryname());

        assertEquals(12, products[2].getId());
        assertEquals("Carlsberg 6 pack", products[2].getName());
        assertEquals("Probably the best beer in the world", products[2].getDescription());
        assertEquals("Alkohol", products[2].getCategoryname());

        assertEquals(13, products[3].getId());
        assertEquals("Sierra Silver Tequila", products[3].getName());
        assertEquals("Tequila er en mexicansk brændevin, der fremstilles af saften fra blå agave.", products[3].getDescription());
        assertEquals("Alkohol", products[3].getCategoryname());

        assertEquals(14, products[4].getId());
        assertEquals("Smirnoff Vodka 37,5%", products[4].getName());
        assertEquals("Den klassiske vodka til alle fester, kan blandes med næsten alt", products[4].getDescription());
        assertEquals("Alkohol", products[4].getCategoryname());

        assertEquals(24, products[5].getId());
        assertEquals("Carpe Diem Harmano", products[5].getName());
        assertEquals("Carpe Diem Harmano leverer det bedste fra Darpe Diem.", products[5].getDescription());
        assertEquals("Seng", products[5].getCategoryname());

        assertEquals(25, products[6].getId());
        assertEquals("Tempur Fusion", products[6].getName());
        assertEquals("TEMPUR® Fusion Box gør valget dejlig enkelt. Du får både de trykaflastende fordele og springmadrassens bevægelighed.", products[6].getDescription());
        assertEquals("Seng", products[6].getCategoryname());
    }

    public void getProductsByCategory() throws CommandException {
        List<Product> productList = dsc.getProductsByCategory("Cykler");
        assertEquals(5, productList.size());
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            assertEquals(i + 1, product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getDescription());
            assertNotNull(product.getCategoryname());
        }
    }

    //(expected = CommandException.class)
    public void getProductsByCategoryFail() throws CommandException {
        List<Product> productList = dsc.getProductsByCategory("Cykler");
        assertEquals(25, productList.size());
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            assertEquals(i + 1, product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getDescription());
            assertNotNull(product.getCategoryname());
        }
    }

    @Test // createProductTes1t, Creates a product with a preexisting category
    public void createProductTest1() throws CommandException {
        //Arrange
        Product pSubmit = new Product(321, "testProdukt321", "Dette er et TestProdukt", "Cykler");
        Product pResult = null;

        //Act
        dsc.createProduct(pSubmit);
        pResult = dsc.getProduct("testProdukt321");

        //Arrange
        assertEquals(321, pResult.getId());
        assertEquals("testProdukt321", pResult.getName());
        assertEquals("Dette er et TestProdukt", pResult.getDescription());
        assertEquals("Cykler", pResult.getCategoryname());
    }

    @Test // createProductTest2, Creates a product with a new category
    public void createProductTest2() throws CommandException {
        //Arrange
        Product pSubmit = new Product(1234, "testProdukt2", "Dette er et TestProdukt", "Test");
        Product pResult = null;

        //Act
        dsc.createProduct(pSubmit);
        pResult = dsc.getProduct("testProdukt2");

        //Arrange
        assertEquals(1234, pResult.getId());
        assertEquals("testProdukt2", pResult.getName());
        assertEquals("Dette er et TestProdukt", pResult.getDescription());
        assertEquals("Test", pResult.getCategoryname());
    }

    //createProductTestFail, Tests that a CommandException is thrown when creating a product with preexisting ID
    @Test(expected = CommandException.class)
    public void createProductTestFail() throws CommandException {

        //Arrange
        Product p1 = new Product(231, "testProdukt", "Dette er et TestProdukt", "Test");
        Product p2 = new Product(231, "produktTest", "Test Testen Tester Testing Tests", "Test");
    
        //Act
        dsc.createProduct(p1);
        
        dsc.createProduct(p2);

        //Assert
    }

    @Test //updateTest, Tests that we can update a product
    public void updateTest() throws CommandException {
        //Arrange
        Product pSubmit = new Product(12345, "testProdukt4", "Gæt hvem der tester?, tester igen, gæt hvem der der tester.", "Test");
        Product pBeforeUpdate = null;
        Product pAfterUpdate = null;
        
        //Act
        dsc.createProduct(pSubmit);
        pBeforeUpdate = dsc.getProduct("testProdukt4");
        dsc.updateProduct(new Product(12345, "testProdukt5", "TestTestTest", "Test"));
        pAfterUpdate = dsc.getProduct("testProdukt5");
        
        //Assert
        assertEquals(12345, pBeforeUpdate.getId());
        assertEquals("testProdukt4", pBeforeUpdate.getName());
        assertEquals("Gæt hvem der tester?, tester igen, gæt hvem der der tester.", pBeforeUpdate.getDescription());
        assertEquals("Test", pBeforeUpdate.getCategoryname());
        
        assertEquals(12345, pAfterUpdate.getId());
        assertEquals("testProdukt5", pAfterUpdate.getName());
        assertEquals("TestTestTest", pAfterUpdate.getDescription());
        assertEquals("Test", pAfterUpdate.getCategoryname());
    }
    
    //@Test Omitted until method is features is implemented //updateTest2, Tests that we can update a product with a new category
    public void updateTest2() throws CommandException {
        //Arrange
        Product pSubmit = new Product(12345, "testProdukt4", "Gæt hvem der tester?, tester igen, gæt hvem der der tester.", "Test");
        Product pBeforeUpdate = null;
        Product pAfterUpdate = null;
        
        //Act
        dsc.createProduct(pSubmit);
        pBeforeUpdate = dsc.getProduct("testProdukt4");
        dsc.updateProduct(new Product(12345, "testProdukt5", "TestTestTest", "Test2"));
        pAfterUpdate = dsc.getProduct("testProdukt5");
        
        //Assert
        assertEquals(12345, pBeforeUpdate.getId());
        assertEquals("testProdukt4", pBeforeUpdate.getName());
        assertEquals("Gæt hvem der tester?, tester igen, gæt hvem der der tester.", pBeforeUpdate.getDescription());
        assertEquals("Test", pBeforeUpdate.getCategoryname());
        
        assertEquals(12345, pAfterUpdate.getId());
        assertEquals("testProdukt5", pAfterUpdate.getName());
        assertEquals("TestTestTest", pAfterUpdate.getDescription());
        assertEquals("Test2", pAfterUpdate.getCategoryname());
    }

    //updateTestFail, Test that we get an CommandException if there was no ID match
    @Test(expected = CommandException.class)
    public void updateTestFail() throws CommandException {
        //Arrange
        
        //Act
        dsc.updateProduct(new Product(-1, "Test2produkt", "TestTestTest", "Mobiler"));
        
        //Assert
    }

    //deleteTest, tests that we can delete a product with ID
    @Test(expected = CommandException.class)
    public void deleteTest() throws CommandException {
        //Arrange
        Product pSubmit = new Product(123456, "testProdukt6", "TEST TEST TEST, He said me haffi.", "Test");
        Product pBeforeDelete = null;
        Product afterDelete = null;
        
        //Act
        pBeforeDelete = dsc.getProduct("testProdukt6");
        
        //Assert
        assertEquals(12345, pBeforeDelete.getId());
        assertEquals("testProdukt6", pBeforeDelete.getName());
        assertEquals("TEST TEST TEST, He said me haffi.", pBeforeDelete.getDescription());
        assertEquals("Test", pBeforeDelete.getCategoryname());
        dsc.deleteProduct(pBeforeDelete);
        afterDelete = dsc.getProduct("testProdukt6");        
        
    }

    @Test(expected = CommandException.class)
    public void deleteTestFail() throws CommandException {
        dsc.deleteProduct(new Product(-1, "Test2produkt", "TestTestTest", "Mobiler"));
    }

}
