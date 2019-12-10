//package PersistenceTest;
//
//import exception.CommandException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import logic.Category;
//import logic.Product;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import persistence.PersistenceFacadeDB;
//
///**
// *
// * @author allan
// */
//public class ProductMapperTest {
//
//    private static PersistenceFacadeDB pf;
//
//    @BeforeClass
//    public static void clasSetup() {
//        pf = new PersistenceFacadeDB(true);
//    }
//
//    @Before
//    public void testSetup() throws CommandException {
//        try {
//            Statement stmt = PersistenceFacadeDB.getConnection().createStatement();
//            stmt.execute("drop schema if exists pimTest;");
//            stmt.execute("create schema pimTest;");
//            stmt.execute("create table pimTest.categories like pimTest_template.categories;");
//            stmt.execute("create table pimTest.products like pimTest_template.products;");
//            stmt.execute("insert into pimTest.categories select * from pimTest_template.categories;");
//            stmt.execute("insert into pimTest.products select * from pimTest_template.products;");
//            stmt.execute("USE pimtest;");
//        } catch (SQLException ex) {
//        }
//
//    }
//
//
//    @Test //Tests that we can get a connection with the database.
//    public void getConnectionTest() throws CommandException {
//
//        //Arrange
//        Connection con = null;
//
//        //Act
//        con = PersistenceFacadeDB.getConnection();
//
//        //Assert
//        assertNotNull(con);
//
//    }
//
//    @Test //getProductTest, Tests that we can get a product from each category with the correct ID when using name search
//    public void getProductWithNameTest_GetOneProductFromEachCategory() throws CommandException {
//        //Arrange
//        Product[] products = new Product[5];
//
//        //Act
//        products[0] = pf.getProduct("Rød Cykel");
//        products[1] = pf.getProduct("Samsung Galaxy S10");
//        products[2] = pf.getProduct("Tuborg Classic 6 pack");
//        products[3] = pf.getProduct("Hauwei R5");
//        products[4] = pf.getProduct("Auping Royal");
//
//        //Assert
//        assertEquals(1, products[0].getId());
//        assertEquals(6, products[1].getId());
//        assertEquals(11, products[2].getId());
//        assertEquals(16, products[3].getId());
//        assertEquals(21, products[4].getId());
//
//
//    }
//
//    @Test //getProductTest, test that we can get a specific product with all fields using name search.
//    public void getProductWithNameTest_AssertFieldsInOneProduct() throws CommandException {
//        //Arrange
//        TemporaryProduct p = null;
//
//        //Act
//        p = pf.getProduct("Rød Cykel");
//
//        //Assert
//        assertEquals(1, p.getId());
//        assertEquals("Rød Cykel", p.getName());
//        assertEquals("En Cykel der er rød", p.getDescription());
//        assertEquals("Cykler", p.getCategoryname());
//    }
//
//
//    //getProductTestFail, Tests that a CommandException is thrown when searching for nonexisting product
//    @Test(expected = CommandException.class)
//    public void getProductTestFail() throws CommandException {
//        //Arrange
//        //Act
//        pf.getProduct("Flying Bike");
//        //Assert
//    }
//
//    @Test //getProductTest, Tests that we can get a product from each category with the correct name using Id to search
//    public void getProductWithIDTest_GetOneProductFromEachCategory() throws CommandException {
//        //Arrange
//        TemporaryProduct[] products = new TemporaryProduct[5];
//
//        //Act
//        products[0] = pf.getProduct(1);
//        products[1] = pf.getProduct(6);
//        products[2] = pf.getProduct(11);
//        products[3] = pf.getProduct(16);
//        products[4] = pf.getProduct(21);
//
//        //Assert
//        assertEquals("Rød Cykel", products[0].getName());
//        assertEquals("Samsung Galaxy S10", products[1].getName());
//        assertEquals("Tuborg Classic 6 pack", products[2].getName());
//        assertEquals("Hauwei R5", products[3].getName());
//        assertEquals("Auping Royal", products[4].getName());
//
//
//    }
//
//    @Test //getProductTest, test that we can get a specific product from id, with all fields.
//    public void getProductWithIDTest_AssertFieldsInOneProduct() throws CommandException {
//        //Arrange
//        TemporaryProduct p = null;
//
//        //Act
//        p = pf.getProduct(1);
//
//        //Assert
//        assertEquals(1, p.getId());
//        assertEquals("Rød Cykel", p.getName());
//        assertEquals("En Cykel der er rød", p.getDescription());
//        assertEquals("Cykler", p.getCategoryname());
//    }
//
//    //getProductTest, Tests that a CommandException is thrown when searching for nonexisting product with id
//    @Test(expected = CommandException.class)
//    public void getProductWithIDTest_Fail() throws CommandException {
//
//        //Arrange
//        //Act
//        pf.getProduct(-1);
//        //Assert
//    }
//    @Test //GetProductsTest, test that we can get all products in one list and the fields are not null
//    public void getCatalogTest_getAllProductsFieldsNotEmpty() throws CommandException {
//
//        //Arrange
//        List<TemporaryProduct> productList = null;
//
//        //Act
//        productList = pf.getCatalog();
//
//        //Assert
//        //assertEquals(25, productList.size());
//        for (int i = 0; i < productList.size(); i++) {
//            TemporaryProduct product = productList.get(i);
//            assertNotNull(product.getId());
//            assertNotNull(product.getName());
//            assertNotNull(product.getDescription());
//            assertNotNull(product.getCategoryname());
//        }
//    }
//
//    @Test //getProductsTest2, Test that we can get all products and assert the field values at min, min+1, middle-1, middle, middle+1, max-1 & max ID objects from the the test database.
//    public void getCatalogTest_assertFieldsInProducts() throws CommandException {
//        //Arrange
//        List<TemporaryProduct> productList = null;
//        TemporaryProduct[] products = new TemporaryProduct[7];
//
//        //Act
//        productList = pf.getCatalog();
//        products[0] = productList.get(0);
//        products[1] = productList.get(1);
//        products[2] = productList.get(11);
//        products[3] = productList.get(12);
//        products[4] = productList.get(13);
//        products[5] = productList.get(23);
//        products[6] = productList.get(24);
//
//        //Assert
//        assertEquals(26, productList.size());
//        assertEquals(1, products[0].getId());
//        assertEquals("Rød Cykel", products[0].getName());
//        assertEquals("En Cykel der er rød", products[0].getDescription());
//        assertEquals("Cykler", products[0].getCategoryname());
//
//        assertEquals(2, products[1].getId());
//        assertEquals("Grøn Cykel", products[1].getName());
//        assertEquals("En Cykel der er grøn", products[1].getDescription());
//        assertEquals("Cykler", products[1].getCategoryname());
//
//        assertEquals(12, products[2].getId());
//        assertEquals("Carlsberg 6 pack", products[2].getName());
//        assertEquals("Probably the best beer in the world", products[2].getDescription());
//        assertEquals("Alkohol", products[2].getCategoryname());
//
//        assertEquals(13, products[3].getId());
//        assertEquals("Sierra Silver Tequila", products[3].getName());
//        assertEquals("Tequila er en mexicansk brændevin, der fremstilles af saften fra blå agave.", products[3].getDescription());
//        assertEquals("Alkohol", products[3].getCategoryname());
//
//        assertEquals(14, products[4].getId());
//        assertEquals("Smirnoff Vodka 37,5%", products[4].getName());
//        assertEquals("Den klassiske vodka til alle fester, kan blandes med næsten alt", products[4].getDescription());
//        assertEquals("Alkohol", products[4].getCategoryname());
//
//        assertEquals(24, products[5].getId());
//        assertEquals("Carpe Diem Harmano", products[5].getName());
//        assertEquals("Carpe Diem Harmano leverer det bedste fra Darpe Diem.", products[5].getDescription());
//        assertEquals("Seng", products[5].getCategoryname());
//
//        assertEquals(25, products[6].getId());
//        assertEquals("Tempur Fusion", products[6].getName());
//        assertEquals("TEMPUR® Fusion Box gør valget dejlig enkelt. Du får både de trykaflastende fordele og springmadrassens bevægelighed.", products[6].getDescription());
//        assertEquals("Seng", products[6].getCategoryname());
//    }
//
//
//    @Test //GetProductsByCategory, Test that we get all products from at category and the fields are not null
//    public void getProductsByCategory_AssertFieldsNotNull() throws CommandException {
//        //arrange
//        List<TemporaryProduct> productList = null;
//        
//        //act
//        productList = pf.getProductsByCategory("Cykler");
//        
//        //Assert
//        assertEquals(6, productList.size());
//        for (int i = 0; i < productList.size(); i++) {
//            TemporaryProduct product = productList.get(i);
//            assertEquals(i + 1, product.getId());
//            assertNotNull(product.getName());
//            assertNotNull(product.getDescription());
//            assertNotNull(product.getCategoryname());
//        }
//    }
//
//    @Test(expected = CommandException.class) //getproductsByCategory, test that we get a command exception with nonexistent category
//    public void getProductsByCategory_Fail() throws CommandException {
//        //Arrange
//        List<TemporaryProduct> productList = null;
//                
//        //Act
//        productList = pf.getProductsByCategory("pneumonia");
//        
//        //Assert
//    }
//
//    @Test // createProductTes1t, Creates a product with a preexisting category
//    public void createProductTest_WithPrexistingCategory() throws CommandException {
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Cykler", strings);
//        Product pSubmit = new Product(123, "test123", "Dette er et TestProdukt", c);
//
//        TemporaryProduct pResult = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pResult = pf.getProduct("test123");
//
//        //Arrange
//        assertEquals(123, pResult.getId());
//        assertEquals("test123", pResult.getName());
//        assertEquals("Dette er et TestProdukt", pResult.getDescription());
//        assertEquals("Cykler", pResult.getCategoryname());
//    }
//
//    @Test // createProductTest2, Creates a product with a new category(also creates the new category)
//    public void createProductTest_WithNewcategory() throws CommandException {
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Test", strings);
//        Product pSubmit = new Product(124, "test123", "Dette er et TestProdukt", c);
//
//        TemporaryProduct pResult = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pResult = pf.getProduct("test123");
//
//        //Arrange
//        assertEquals(124, pResult.getId());
//        assertEquals("test123", pResult.getName());
//        assertEquals("Dette er et TestProdukt", pResult.getDescription());
//        assertEquals("Test", pResult.getCategoryname());
//    }
//
//    //createProductTestFail, Tests that a CommandException is thrown when creating a product with preexisting ID
//    @Test(expected = CommandException.class)
//    public void createProductTestFail() throws CommandException {
//
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Test", strings);
//        Product p1 = new Product(125, "testProdukt", "Dette er et TestProdukt", c);
//        Product p2 = new Product(125, "produktTest", "Test Testen Tester Testing Tests", c);
//
//        //Act
//        pf.createProduct(p1);
//        pf.createProduct(p2);
//
//        //Assert
//    }
//
//
//    @Test //updateTest, Tests that we can update a product with new name and description
//    public void updateTest_NewNameAndDescription() throws CommandException {
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Test", strings);
//        Product pSubmit = new Product(126, "testProdukt4", "Gæt hvem der tester?, tester igen, gæt hvem der der tester.", c);
//        TemporaryProduct pBeforeUpdate = null;
//        TemporaryProduct pAfterUpdate = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pBeforeUpdate = pf.getProduct("testProdukt4");
//        pf.updateProduct(new Product(126, "testProdukt5", "TestTestTest", c));
//        pAfterUpdate = pf.getProduct("testProdukt5");
//
//        //Assert
//        assertEquals(126, pBeforeUpdate.getId());
//        assertEquals("testProdukt4", pBeforeUpdate.getName());
//        assertEquals("Gæt hvem der tester?, tester igen, gæt hvem der der tester.", pBeforeUpdate.getDescription());
//        assertEquals("Test", pBeforeUpdate.getCategoryname());
//
//        assertEquals(126, pAfterUpdate.getId());
//        assertEquals("testProdukt5", pAfterUpdate.getName());
//        assertEquals("TestTestTest", pAfterUpdate.getDescription());
//        assertEquals("Test", pAfterUpdate.getCategoryname());
//    }
//
//
//    @Test //updateTest2, Tests that we can update a product with a new category
//    public void updateTest_NewNameDescriptionCategory() throws CommandException {
//
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Test", strings);
//        Category c1 = new Category("Test2", strings);
//        Product pSubmit = new Product(12345, "testProdukt4", "Gæt hvem der tester?, tester igen, gæt hvem der der tester.", c);
//        TemporaryProduct pBeforeUpdate = null;
//        TemporaryProduct pAfterUpdate = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pBeforeUpdate = pf.getProduct("testProdukt4");
//        pf.updateProduct(new Product(12345, "testProdukt5", "TestTestTest", c1));
//        pAfterUpdate = pf.getProduct("testProdukt5");
//
//        //Assert
//        assertEquals(12345, pBeforeUpdate.getId());
//        assertEquals("testProdukt4", pBeforeUpdate.getName());
//        assertEquals("Gæt hvem der tester?, tester igen, gæt hvem der der tester.", pBeforeUpdate.getDescription());
//        assertEquals("Test", pBeforeUpdate.getCategoryname());
//
//        assertEquals(12345, pAfterUpdate.getId());
//        assertEquals("testProdukt5", pAfterUpdate.getName());
//        assertEquals("TestTestTest", pAfterUpdate.getDescription());
//        assertEquals("Test2", pAfterUpdate.getCategoryname());
//    }
//
//    //updateTestFail, Test that we get an CommandException if there was no ID match
//    @Test(expected = CommandException.class)
//    public void updateTestFail() throws CommandException {
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Mobiler", strings);
//
//        //Act
//        pf.updateProduct(new Product(-1, "Test2produkt", "TestTestTest", c));
//
//        //Assert
//    }
//
//    //deleteTest, tests that we can delete a product with ID
//    @Test(expected = CommandException.class)
//    public void deleteTest() throws CommandException {
//        //Arrange
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Test", strings);
//        Product pSubmit = new Product(123456, "testProdukt6", "TEST TEST TEST, He said me haffi.", c);
//        TemporaryProduct pBeforeDelete = null;
//        TemporaryProduct afterDelete = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pBeforeDelete = pf.getProduct("testProdukt6");
//
//        //Assert
//        assertEquals(123456, pBeforeDelete.getId());
//        assertEquals("testProdukt6", pBeforeDelete.getName());
//        assertEquals("TEST TEST TEST, He said me haffi.", pBeforeDelete.getDescription());
//        assertEquals("Test", pBeforeDelete.getCategoryname());
//        pf.deleteProduct(pSubmit);
//        afterDelete = pf.getProduct("testProdukt6");
//    }
//
//    @Test(expected = CommandException.class)
//    public void deleteTestFail() throws CommandException {
//        List<String> strings = new ArrayList<>();
//        Category c = new Category("Test", strings);
//        pf.deleteProduct(new Product(-1, "Test2produkt", "TestTestTest", c));
//    }
//
//}
//
//=======
//package PersistenceTest;
//
//import exception.CommandException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import logic.Product;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import persistence.PersistenceFacadeDB;
//
///**
// *
// * @author allan
// */
//public class ProductMapperTest {
//
//    private static PersistenceFacadeDB pf;
//
//    @BeforeClass
//    public static void clasSetup() {
//        pf = new PersistenceFacadeDB(true);
//    }
//
//    @Before
//    public void testSetup() throws CommandException {
//        try {
//            Statement stmt = PersistenceFacadeDB.getConnection().createStatement();
//            stmt.execute("drop schema if exists pimTest;");
//            stmt.execute("create schema pimTest;");
//            stmt.execute("create table pimTest.categories like pimTest_template.categories;");
//            stmt.execute("create table pimTest.products like pimTest_template.products;");
//            stmt.execute("create table pimTest.images like pimTest_template.images;");
//            stmt.execute("insert into pimTest.categories select * from pimTest_template.categories;");
//            stmt.execute("insert into pimTest.products select * from pimTest_template.products;");
//            stmt.execute("insert into pimTest.images select * from pimTest_template.images;");
//            stmt.execute("USE pimtest;");
//        } catch (SQLException ex) {
//            System.out.println("Test setup Failed: " + ex.getMessage());
//        }
//
//    }
//
//    @Test //Tests that we can get a connection with the database.
//    public void getConnectionTest() throws CommandException {
//
//        //Arrange
//        Connection con = null;
//
//        //Act
//        con = PersistenceFacadeDB.getConnection();
//
//        //Assert
//        assertNotNull(con);
//
//    }
//
//    @Test //getProductTest, Tests that we can get a product from each category with the correct ID when using name search
//    public void getProductWithNameTest_GetOneProductFromEachCategory() throws CommandException {
//        //Arrange
//        Product[] products = new Product[5];
//
//        //Act
//        products[0] = pf.getProduct("Rød Cykel");
//        products[1] = pf.getProduct("Samsung Galaxy S10");
//        products[2] = pf.getProduct("Tuborg Classic 6 pack");
//        products[3] = pf.getProduct("Hauwei R5");
//        products[4] = pf.getProduct("Auping Royal");
//
//        //Assert
//        assertEquals(1, products[0].getId());
//        assertEquals(6, products[1].getId());
//        assertEquals(11, products[2].getId());
//        assertEquals(16, products[3].getId());
//        assertEquals(21, products[4].getId());
//
//    }
//
//    @Test //getProductTest, test that we can get a specific product with all fields using name search.
//    public void getProductWithNameTest_AssertFieldsInOneProduct() throws CommandException {
//        //Arrange
//        Product p = null;
//
//        //Act
//        p = pf.getProduct("Rød Cykel");
//
//        //Assert
//        assertEquals(1, p.getId());
//        assertEquals("Rød Cykel", p.getName());
//        assertEquals("En Cykel der er rød", p.getDescription());
//        assertEquals("Cykler", p.getCategoryname());
//    }
//
//    //getProductTestFail, Tests that a CommandException is thrown when searching for nonexisting product
//    @Test(expected = CommandException.class)
//    public void getProductTestFail() throws CommandException {
//        //Arrange
//        //Act
//        pf.getProduct("Flying Bike");
//        //Assert
//    }
//
//    @Test //getProductTest, Tests that we can get a product from each category with the correct name using Id to search
//    public void getProductWithIDTest_GetOneProductFromEachCategory() throws CommandException {
//        //Arrange
//        Product[] products = new Product[5];
//
//        //Act
//        products[0] = pf.getProduct(1);
//        products[1] = pf.getProduct(6);
//        products[2] = pf.getProduct(11);
//        products[3] = pf.getProduct(16);
//        products[4] = pf.getProduct(21);
//
//        //Assert
//        assertEquals("Rød Cykel", products[0].getName());
//        assertEquals("Samsung Galaxy S10", products[1].getName());
//        assertEquals("Tuborg Classic 6 pack", products[2].getName());
//        assertEquals("Hauwei R5", products[3].getName());
//        assertEquals("Auping Royal", products[4].getName());
//
//    }
//
//    @Test //getProductTest, test that we can get a specific product from id, with all fields.
//    public void getProductWithIDTest_AssertFieldsInOneProduct() throws CommandException {
//        //Arrange
//        Product p = null;
//
//        //Act
//        p = pf.getProduct(1);
//
//        //Assert
//        assertEquals(1, p.getId());
//        assertEquals("Rød Cykel", p.getName());
//        assertEquals("En Cykel der er rød", p.getDescription());
//        assertEquals("Cykler", p.getCategoryname());
//    }
//
//    //getProductTest, Tests that a CommandException is thrown when searching for nonexisting product with id
//    @Test(expected = CommandException.class)
//    public void getProductWithIDTest_Fail() throws CommandException {
//
//        //Arrange
//        //Act
//        pf.getProduct(-1);
//        //Assert
//    }
//
//    @Test //GetProductsTest, test that we can get all products in one list and the fields are not null
//    public void getCatalogTest_getAllProductsFieldsNotEmpty() throws CommandException {
//
//        //Arrange
//        List<Product> productList = null;
//
//        //Act
//        productList = pf.getCatalog();
//
//        //Assert
//        //assertEquals(25, productList.size());
//        for (int i = 0; i < productList.size(); i++) {
//            Product product = productList.get(i);
//            assertNotNull(product.getId());
//            assertNotNull(product.getName());
//            assertNotNull(product.getDescription());
//            assertNotNull(product.getCategoryname());
//        }
//    }
//
//    @Test //getProductsTest2, Test that we can get all products and assert the field values at min, min+1, middle-1, middle, middle+1, max-1 & max ID objects from the the test database.
//    public void getCatalogTest_assertFieldsInProducts() throws CommandException {
//        //Arrange
//        List<Product> productList = null;
//        Product[] products = new Product[7];
//
//        //Act
//        productList = pf.getCatalog();
//        products[0] = productList.get(0);
//        products[1] = productList.get(1);
//        products[2] = productList.get(11);
//        products[3] = productList.get(12);
//        products[4] = productList.get(13);
//        products[5] = productList.get(23);
//        products[6] = productList.get(24);
//
//        //Assert
//        assertEquals(25, productList.size());
//        assertEquals(1, products[0].getId());
//        assertEquals("Rød Cykel", products[0].getName());
//        assertEquals("En Cykel der er rød", products[0].getDescription());
//        assertEquals("Cykler", products[0].getCategoryname());
//
//        assertEquals(2, products[1].getId());
//        assertEquals("Grøn Cykel", products[1].getName());
//        assertEquals("En Cykel der er grøn", products[1].getDescription());
//        assertEquals("Cykler", products[1].getCategoryname());
//
//        assertEquals(12, products[2].getId());
//        assertEquals("Carlsberg 6 pack", products[2].getName());
//        assertEquals("Probably the best beer in the world", products[2].getDescription());
//        assertEquals("Alkohol", products[2].getCategoryname());
//
//        assertEquals(13, products[3].getId());
//        assertEquals("Sierra Silver Tequila", products[3].getName());
//        assertEquals("Tequila er en mexicansk brændevin, der fremstilles af saften fra blå agave.", products[3].getDescription());
//        assertEquals("Alkohol", products[3].getCategoryname());
//
//        assertEquals(14, products[4].getId());
//        assertEquals("Smirnoff Vodka 37,5%", products[4].getName());
//        assertEquals("Den klassiske vodka til alle fester, kan blandes med næsten alt", products[4].getDescription());
//        assertEquals("Alkohol", products[4].getCategoryname());
//
//        assertEquals(24, products[5].getId());
//        assertEquals("Carpe Diem Harmano", products[5].getName());
//        assertEquals("Carpe Diem Harmano leverer det bedste fra Darpe Diem.", products[5].getDescription());
//        assertEquals("Seng", products[5].getCategoryname());
//
//        assertEquals(25, products[6].getId());
//        assertEquals("Tempur Fusion", products[6].getName());
//        assertEquals("TEMPUR® Fusion Box gør valget dejlig enkelt. Du får både de trykaflastende fordele og springmadrassens bevægelighed.", products[6].getDescription());
//        assertEquals("Seng", products[6].getCategoryname());
//    }
//
//    @Test //GetProductsByCategory, Test that we get all products from at category and the fields are not null
//    public void getProductsByCategory_AssertFieldsNotNull() throws CommandException {
//        //arrange
//        List<Product> productList = null;
//
//        //act
//        productList = pf.getProductsByCategory("Cykler");
//
//        //Assert
//        assertEquals(5, productList.size());
//        for (int i = 0; i < productList.size(); i++) {
//            Product product = productList.get(i);
//            assertEquals(i + 1, product.getId());
//            assertNotNull(product.getName());
//            assertNotNull(product.getDescription());
//            assertNotNull(product.getCategoryname());
//        }
//    }
//
//    @Test(expected = CommandException.class) //getproductsByCategory, test that we get a command exception with nonexistent category
//    public void getProductsByCategory_Fail() throws CommandException {
//        //Arrange
//        List<Product> productList = null;
//
//        //Act
//        productList = pf.getProductsByCategory("pneumonia");
//
//        //Assert
//    }
//
//    @Test // createProductTes1t, Creates a product with a preexisting category
//    public void createProductTest_WithPrexistingCategory() throws CommandException {
//        //Arrange
//        Product pSubmit = new Product(123, "test123", "Dette er et TestProdukt", "Cykler");
//
//        Product pResult = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pResult = pf.getProduct("test123");
//
//        //Arrange
//        assertEquals(123, pResult.getId());
//        assertEquals("test123", pResult.getName());
//        assertEquals("Dette er et TestProdukt", pResult.getDescription());
//        assertEquals("Cykler", pResult.getCategoryname());
//    }
//
//    @Test // createProductTest2, Creates a product with a new category(also creates the new category)
//    public void createProductTest_WithNewcategory() throws CommandException {
//        //Arrange
//        Product pSubmit = new Product(123, "test123", "Dette er et TestProdukt", "Test");
//        Product pResult = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pResult = pf.getProduct("test123");
//
//        //Arrange
//        assertEquals(123, pResult.getId());
//        assertEquals("test123", pResult.getName());
//
//        assertEquals("Dette er et TestProdukt", pResult.getDescription());
//        assertEquals("Test", pResult.getCategoryname());
//    }
//
//    //createProductTestFail, Tests that a CommandException is thrown when creating a product with preexisting ID
//    @Test(expected = CommandException.class)
//    public void createProductTestFail() throws CommandException {
//
//        //Arrange
//        Product p1 = new Product(123, "testProdukt", "Dette er et TestProdukt", "Test");
//        Product p2 = new Product(123, "produktTest", "Test Testen Tester Testing Tests", "Test");
//
//        //Act
//        pf.createProduct(p1);
//        pf.createProduct(p2);
//
//        //Assert
//    }
//
//    @Test //updateTest, Tests that we can update a product with new name and description
//    public void updateTest_NewNameAndDescription() throws CommandException {
//        //Arrange
//        Product pSubmit = new Product(12345, "testProdukt4", "Gæt hvem der tester?, tester igen, gæt hvem der der tester.", "Test");
//        Product pBeforeUpdate = null;
//        Product pAfterUpdate = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pBeforeUpdate = pf.getProduct("testProdukt4");
//        pf.updateProduct(new Product(12345, "testProdukt5", "TestTestTest", "Test"));
//        pAfterUpdate = pf.getProduct("testProdukt5");
//
//        //Assert
//        assertEquals(12345, pBeforeUpdate.getId());
//        assertEquals("testProdukt4", pBeforeUpdate.getName());
//        assertEquals("Gæt hvem der tester?, tester igen, gæt hvem der der tester.", pBeforeUpdate.getDescription());
//        assertEquals("Test", pBeforeUpdate.getCategoryname());
//
//        assertEquals(12345, pAfterUpdate.getId());
//        assertEquals("testProdukt5", pAfterUpdate.getName());
//        assertEquals("TestTestTest", pAfterUpdate.getDescription());
//        assertEquals("Test", pAfterUpdate.getCategoryname());
//    }
//
//    @Test //updateTest2, Tests that we can update a product with a new category
//    public void updateTest_NewNameDescriptionCategory() throws CommandException {
//
//        //Arrange
//        Product pSubmit = new Product(12345, "testProdukt4", "Gæt hvem der tester?, tester igen, gæt hvem der der tester.", "Test");
//        Product pBeforeUpdate = null;
//        Product pAfterUpdate = null;
//
//        //Act
//        pf.createProduct(pSubmit);
//        pBeforeUpdate = pf.getProduct("testProdukt4");
//        pf.updateProduct(new Product(12345, "testProdukt5", "TestTestTest", "Test2"));
//        pAfterUpdate = pf.getProduct("testProdukt5");
//
//        //Assert
//        assertEquals(12345, pBeforeUpdate.getId());
//        assertEquals("testProdukt4", pBeforeUpdate.getName());
//        assertEquals("Gæt hvem der tester?, tester igen, gæt hvem der der tester.", pBeforeUpdate.getDescription());
//        assertEquals("Test", pBeforeUpdate.getCategoryname());
//
//        assertEquals(12345, pAfterUpdate.getId());
//        assertEquals("testProdukt5", pAfterUpdate.getName());
//        assertEquals("TestTestTest", pAfterUpdate.getDescription());
//        assertEquals("Test2", pAfterUpdate.getCategoryname());
//    }
//
//    //updateTestFail, Test that we get an CommandException if there was no ID match
//    @Test(expected = CommandException.class)
//    public void updateTestFail() throws CommandException {
//        //Arrange
//
//        //Act
//        pf.updateProduct(new Product(-1, "Test2produkt", "TestTestTest", "Mobiler"));
//
//        //Assert
//    }
//
//    //deleteTest, tests that we can delete a product with ID
//    @Test(expected = CommandException.class)
//    public void deleteTest() throws CommandException {
//        //Arrange
//        Product pSubmit = new Product(123456, "testProdukt6", "TEST TEST TEST, He said me haffi.", "Test");
//        Product pBeforeDelete = null;
//        Product afterDelete = null;
//
//        //Act
//        pBeforeDelete = pf.getProduct("testProdukt6");
//
//        //Assert
//        assertEquals(12345, pBeforeDelete.getId());
//        assertEquals("testProdukt6", pBeforeDelete.getName());
//        assertEquals("TEST TEST TEST, He said me haffi.", pBeforeDelete.getDescription());
//        assertEquals("Test", pBeforeDelete.getCategoryname());
//        pf.deleteProduct(pBeforeDelete);
//        afterDelete = pf.getProduct("testProdukt6");
//
//    }
//
//    @Test(expected = CommandException.class)
//    public void deleteTestFail() throws CommandException {
//        pf.deleteProduct(new Product(-1, "Test2produkt", "TestTestTest", "Mobiler"));
//    }
//
//}
