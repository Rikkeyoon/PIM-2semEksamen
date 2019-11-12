/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceTest;

import exception.CommandException;
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

    @Test
    public void getConnectionTest() throws CommandException {
        assertNotNull(dsc.getConnection());
    }

    @Test //getProductTest1, tests that we can get all the bikes(cykler) from the testDatabase.
    public void getProductTest1() throws CommandException {
        assertEquals(1, dsc.getProduct("Rød Cykel").getId());
        assertEquals(2, dsc.getProduct("Grøn Cykel").getId());
        assertEquals(3, dsc.getProduct("Blå Cykel").getId());
        assertEquals(4, dsc.getProduct("Pink Cykel").getId());
        assertEquals(5, dsc.getProduct("SORT Cykel").getId());
    }

    @Test //getProductTest2, tests that we can get all the phones(mobiler) from the testDatabase.
    public void getProductTest2() throws CommandException {
        assertEquals(6, dsc.getProduct("Samsung Galaxy S10").getId());
        assertEquals(7, dsc.getProduct("Apple iphone 11").getId());
        assertEquals(8, dsc.getProduct("Hauwei P30").getId());
        assertEquals(9, dsc.getProduct("Xiaomi redmi note 5").getId());
        assertEquals(10, dsc.getProduct("Sony Ericsson Xperia").getId());
    }

    @Test //getProductTest3, tests that we can get all the alcohol(alkohol) from the testDatabase.
    public void getProductTest3() throws CommandException {
        assertEquals(11, dsc.getProduct("Tuborg Classic 6 pack").getId());
        assertEquals(12, dsc.getProduct("Carlsberg 6 pack").getId());
        assertEquals(13, dsc.getProduct("Sierra Silver Tequila").getId());
        assertEquals(14, dsc.getProduct("Smirnoff Vodka 37,5%").getId());
        assertEquals(15, dsc.getProduct("Bornholmer Honningsyp").getId());
    }

    @Test //getProductTest4, tests that we can get all the computers(computer) from the testDatabase.
    public void getProductTest4() throws CommandException {
        assertEquals(16, dsc.getProduct("Hauwei R5").getId());
        assertEquals(17, dsc.getProduct("Apple Pro").getId());
        assertEquals(18, dsc.getProduct("Asus Zenbook").getId());
        assertEquals(19, dsc.getProduct("Acer Cromebook").getId());
        assertEquals(20, dsc.getProduct("Lenovo thinkpad L590").getId());
    }

    @Test //getProductTest5, tests that we can get all the beds(seng) from the testDatabase.
    public void getProductTest5() throws CommandException {
        assertEquals(21, dsc.getProduct("Auping Royal").getId());
        assertEquals(22, dsc.getProduct("Viking Birka").getId());
        assertEquals(23, dsc.getProduct("Jensen Prestige").getId());
        assertEquals(24, dsc.getProduct("Carpe Diem Harmano").getId());
        assertEquals(25, dsc.getProduct("Tempur Fusion").getId());
    }

    @Test //getProductTest1, tests that we can get all the bikes(cykler) from the testDatabase.
    public void getProductTest6() throws CommandException {
        Product p = dsc.getProduct("Rød Cykel");
        assertEquals(1, p.getId());
        assertEquals("Rød Cykel", p.getName());
        assertEquals("En Cykel der er rød", p.getDescription());
        assertEquals("Cykler", p.getCategoryname());
    }

    //Test that CommandException is thrown when searching for nonexisting product
    @Test(expected = CommandException.class)
    public void getProductTestFail() throws CommandException {
        Product p = dsc.getProduct("Flying Bike");
    }

    public void getProductsTest1() throws CommandException{
        List<Product> productList = dsc.getProducts();
        assertEquals(25, productList.size());
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            assertEquals(i + 1, product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getDescription());
            assertNotNull(product.getCategoryname());
        }
    }
    public void getProductsTest2() throws CommandException{
        List<Product> productList = dsc.getProducts();
        assertEquals(25, productList.size());
        Product p = productList.get(0);
        assertEquals(1, p.getId());
        assertEquals("Rød Cykel", p.getName());
        assertEquals("En Cykel der er rød", p.getDescription());
        assertEquals("Cykler", p.getCategoryname());
        
        p = productList.get(5);
        assertEquals(1, p.getId());
        assertEquals("Samsung Galaxy S10", p.getName());
        assertEquals("Samsungs nyeste telefon med kraftig processor", p.getDescription());
        assertEquals("Mobiler", p.getCategoryname());
        
        p = productList.get(10);
        assertEquals(1, p.getId());
        assertEquals("Tuborg Classic 6 pack", p.getName());
        assertEquals("Klassisk god smag, til alle lejligheder", p.getDescription());
        assertEquals("Alkohol", p.getCategoryname());
        
        p = productList.get(15);
        assertEquals(1, p.getId());
        assertEquals("Hauwei R5", p.getName());
        assertEquals("Kraftig og stilfuld computer fra Hauwei", p.getDescription());
        assertEquals("Computer", p.getCategoryname());
        
        p = productList.get(20);
        assertEquals(21, p.getId());
        assertEquals("Auping Royal", p.getName());
        assertEquals("Fantastisk seng fra Auping med 5 motorer i hver bund samt stilfuld sengeramme.", p.getDescription());
        assertEquals("Seng", p.getCategoryname());
    }
    
    public void getProductsByCategory() throws CommandException{
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
    public void getProductsByCategoryFail() throws CommandException{
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

    @Test
    public void createProductTest() throws CommandException {
        Product p1 = new Product(123, "testProdukt", "Dette er et TestProdukt", "Cykler");
        dsc.createProduct(p1);
        Product p2 = dsc.getProduct("testProdukt");
        assertEquals(123, p2.getId());
        assertEquals("testProdukt", p2.getName());
        assertEquals("Dette er et TestProdukt", p2.getDescription());
        assertEquals("Cykler", p2.getCategoryname());

    }
    
     //createproduct-newcategory TEST
    
    @Test(expected = CommandException.class)
    public void createProductTestFail() throws CommandException {
        Product p1 = new Product(123, "testProdukt", "Dette er et TestProdukt", "Cykler");
        dsc.createProduct(p1);

    }
    
    @Test
    public void updateTest() throws CommandException {
        Product p = dsc.getProduct("testProdukt");
        assertEquals(123, p.getId());
        assertEquals("testProdukt", p.getName());
        assertEquals("Dette er et TestProdukt", p.getDescription());
        assertEquals("Cykler", p.getCategoryname());
        dsc.updateProduct(new Product(p.getId(), "Test2produkt", "TestTestTest", "Mobiler"));
        p = dsc.getProduct("Test2produkt");
        assertEquals(123, p.getId());
        assertEquals("Test2produkt", p.getName());
        assertEquals("TestTestTest", p.getDescription());
        assertEquals("Mobiler", p.getCategoryname());
    }
    
    @Test(expected = CommandException.class)
    public void updateTestFail() throws CommandException {
        dsc.updateProduct(new Product(-1, "Test2produkt", "TestTestTest", "Mobiler"));
    }


    @Test(expected = CommandException.class)
    public void deleteTest() throws CommandException {
        Product p = dsc.getProduct("testProdukt");
        assertEquals(123, p.getId());
        dsc.deleteProduct(new Product(p.getId(), "Test2produkt", "TestTestTest", "Mobiler"));
        p = dsc.getProduct("testProdukt");
    }
    
    @Test(expected = CommandException.class)
    public void deleteTestFail() throws CommandException {
        dsc.deleteProduct(new Product(-1, "Test2produkt", "TestTestTest", "Mobiler"));
    }
    
    
}

