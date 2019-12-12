package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logic.Category;
import logic.Image;
import logic.Product;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author carol
 */
public class ProductMapperTest {

    private static PersistenceFacadeDB instance;
    private static Category c;
    private static List<String> attrs = new ArrayList<>();
    private static Map<String, String> categoryAttrs = new HashMap<>();
    private static List<Image> images = new ArrayList<>();

    @BeforeClass
    public static void classSetup() throws CommandException {
        instance = new PersistenceFacadeDB(true);
        attrs.add("1st attribute");
        categoryAttrs.put("1st attribute", "1st attribute value");
        images.add(new Image("www.dr.dk", true));
    }

    @Before
    public void testSetup() throws CommandException {
        try {
            Statement stmt = PersistenceFacadeDB.getConnection().createStatement();
            stmt.execute("drop schema if exists pimTest;");
            stmt.execute("create schema pimTest;");
            stmt.execute("create table pimTest.category_attributes like pimTest_template.category_attributes;");
            stmt.execute("create table pimTest.categories like pimTest_template.categories;");
            stmt.execute("create table pimTest.products like pimTest_template.products;");
            stmt.execute("create table pimTest.product_tags like pimTest_template.product_tags;");
            stmt.execute("create table pimTest.attributes like pimTest_template.attributes;");
            stmt.execute("create table pimTest.attribute_values like pimTest_template.attribute_values;");
                stmt.execute("create table pimTest.tags like pimTest_template.tags;");
            stmt.execute("create table pimTest.images like pimTest_template.images;");
            stmt.execute("CREATE OR REPLACE VIEW pimTest.categories_and_attributes AS SELECT c.id AS category_id, attribute_name FROM pimTest.categories c JOIN pimTest.category_attributes ca ON c.id = ca.category_id JOIN pimTest.attributes a ON ca.attribute_id = a.id;");
            stmt.execute("CREATE OR REPLACE VIEW pimTest.products_with_categories_and_attributes AS SELECT p.id,p.item_number, p.name, p.brand, p.description, p.category_id, p.supplier, p.seo_text, p.status, a.attribute_name, av.attribute_value FROM pimTest.products p JOIN pimTest.category_attributes c ON p.category_id = c.category_id JOIN pimTest.attributes a ON c.attribute_id = a.id LEFT JOIN pimTest.attribute_values av ON p.id = av.product_id AND av.attribute_id = a.id ORDER BY p.id ASC;");
            stmt.execute("insert into pimTest.categories select * from pimTest_template.categories;");
            stmt.execute("insert into pimTest.products select * from pimTest_template.products;");
            stmt.execute("insert into pimTest.category_attributes select * from pimTest_template.category_attributes;");
            stmt.execute("insert into pimTest.product_tags select * from pimTest_template.product_tags;");
            stmt.execute("insert into pimTest.attributes select * from pimTest_template.attributes;");
            stmt.execute("insert into pimTest.attribute_values select * from pimTest_template.attribute_values;");
            stmt.execute("insert into pimTest.tags select * from pimTest_template.tags;");
            stmt.execute("insert into pimTest.images select * from pimTest_template.images;");
            stmt.execute("USE pimTest;");
        } catch (SQLException ex) {
        }

    }
    
    @Test
    public void testGetConnection() throws CommandException {
        Connection con = instance.getConnection();
        assertNotNull(con);
    }

    @Test
    public void testCreate() throws CommandException {
        c = new Category("Test Category", attrs);
        instance.createCategory(c);
        c.setId(6);
        Product expected = new Product(0, 3001, "Test Name", "Test Brand",
                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
                categoryAttrs, images);

        int productId = instance.createProduct(expected);
        Product result = instance.getProduct(productId);

        assertEquals(expected.getItemnumber(), result.getItemnumber());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getBrand(), result.getBrand());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getCategory().getCategoryname(),
                result.getCategory().getCategoryname());
        assertEquals(expected.getSupplier(), result.getSupplier());
        assertEquals(expected.getSEOText(), result.getSEOText());
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test(expected = CommandException.class)
    public void testCreate_DuplicateItemNumber() throws CommandException {
        Product p = new Product(0, 132, "Test Name", "Test Brand",
                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
                categoryAttrs, images);

        instance.createProduct(p);
    }

    @Test(expected = CommandException.class)
    public void testCreate_NullProduct() throws CommandException {
        instance.createProduct(null);
    }

    @Test
    public void testGetProductsByName_EntirelySameName() throws CommandException {
        Category c = new Category("Test Category", attrs);
        c.setId(6);
        List<Product> expProducts = new ArrayList<>();
        Product p1 = new Product(0, 3002, "Same Name", "Test Brand 2",
                "Test Description 2", c, "Test Supllier 2", "Test SEO-text 2", 100,
                categoryAttrs, images);
        Product p2 = new Product(0, 3003, "Same Name", "Test Brand 3",
                "Test Description 3", c, "Test Supllier 3", "Test SEO-text 3", 100,
                categoryAttrs, images);
        Product p3 = new Product(0, 3004, "Same Name", "Test Brand 4",
                "Test Description 4", c, "Test Supllier 4", "Test SEO-text 4", 100,
                categoryAttrs, images);
        expProducts.add(p1);
        expProducts.add(p2);
        expProducts.add(p3);
        for (Product exp : expProducts) {
            instance.createProduct(exp);
        }

        List<Product> resultProducts = instance.getProductsByName("Same Name");

        Product expected;
        for (int i = 0; i < resultProducts.size(); i++) {
            Product result = resultProducts.get(i);
            expected = expProducts.get(i);

            assertEquals(expected.getItemnumber(), result.getItemnumber());
            assertEquals(expected.getName(), result.getName());
            assertEquals(expected.getBrand(), result.getBrand());
            assertEquals(expected.getDescription(), result.getDescription());
            assertEquals(expected.getSupplier(), result.getSupplier());
            assertEquals(expected.getSEOText(), result.getSEOText());
            assertEquals(expected.getStatus(), result.getStatus());
        }
    }

    @Test
    public void testGetProductsByName_PartlySameName() throws CommandException {
        Category c = new Category("Test Category", attrs);
        c.setId(6);
        List<Product> expProducts = new ArrayList<>();
        Product p1 = new Product(0, 3005, "Xyztest", "Test Brand 2",
                "Test Description 2", c, "Test Supllier 2", "Test SEO-text 2", 100,
                categoryAttrs, images);
        Product p2 = new Product(0, 3006, "Similar xyzname", "Test Brand 3",
                "Test Description 3", c, "Test Supllier 3", "Test SEO-text 3", 100,
                categoryAttrs, images);
        Product p3 = new Product(0, 3007, "Abc xyz", "Test Brand 4",
                "Test Description 4", c, "Test Supllier 4", "Test SEO-text 4", 100,
                categoryAttrs, images);
        expProducts.add(p1);
        expProducts.add(p2);
        expProducts.add(p3);
        for (Product exp : expProducts) {
            instance.createProduct(exp);
        }

        List<Product> resultProducts = instance.getProductsByName("xyz");

        Product expected;
        for (int i = 0; i < resultProducts.size(); i++) {
            Product result = resultProducts.get(i);
            expected = expProducts.get(i);

            assertEquals(expected.getItemnumber(), result.getItemnumber());
            assertEquals(expected.getName(), result.getName());
            assertEquals(expected.getBrand(), result.getBrand());
            assertEquals(expected.getDescription(), result.getDescription());
            assertEquals(expected.getSupplier(), result.getSupplier());
            assertEquals(expected.getSEOText(), result.getSEOText());
            assertEquals(expected.getStatus(), result.getStatus());
        }
    }

    @Test(expected = CommandException.class)
    public void testGetProductsByName_UnknownName() throws CommandException {
        instance.getProductsByName("ThisNameDoesn'tExist");
    }

    @Test(expected = CommandException.class)
    public void testGetProductsByName_NullName() throws CommandException {
        instance.getProductsByName(null);
    }

    @Test
    public void testGetProduct() throws CommandException {
        Category category = new Category("Bicycle", attrs);
        Product expected = new Product(1, 132, "Red Bicycle", "Winther",
                "A Bicycle that is red", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);

        Product result = instance.getProduct(1);

        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getItemnumber(), result.getItemnumber());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getBrand(), result.getBrand());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getCategory().getCategoryname(),
                result.getCategory().getCategoryname());
        assertEquals(expected.getSupplier(), result.getSupplier());
        assertEquals(expected.getSEOText(), result.getSEOText());
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test(expected = CommandException.class)
    public void testGetProduct_UnknownId() throws CommandException {
        instance.getProduct(0);
    }

    @Test
    public void testGetProductDBId() throws CommandException {
        Category category = new Category("Bicycle", attrs);
        category.setId(1);
        Product p = new Product(1, 132, "Red Bicycle", "Winther",
                "A Bicycle that is red", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        int expected = 1;

        int result = instance.getProductStorageId(p);

        assertEquals(expected, result);
    }

    @Test(expected = CommandException.class)
    public void testGetProductDBId_UnknownProduct() throws CommandException {
        Product p = new Product(0, 3008, "Test Product Not In DB", "Test Brand",
                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
                categoryAttrs, images);
        instance.getProductStorageId(p);
    }

    @Test(expected = CommandException.class)
    public void testGetProductDBId_NullProduct() throws CommandException {
        instance.getProductStorageId(null);
    }

    @Test
    public void testGetProductsByCategoryID() throws CommandException {
        Category category = new Category("Bicycle", attrs);
        List<Product> expProducts = new ArrayList<>();
        Product p1 = new Product(1, 132, "Red Bicycle", "Winther",
                "A Bicycle that is red", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        Product p2 = new Product(2, 207, "Green Bicycle", "Winther",
                "A Bicycle that is green", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        Product p3 = new Product(3, 345, "Blue Bicycle", "Winther",
                "A Bicycle that is blue", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        Product p4 = new Product(4, 426, "Pink Bicycle", "Winther",
                "A Bicycle that is pink", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        Product p5 = new Product(5, 574, "Black Bicycle", "Winther",
                "A Bicycle that is black", category, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        expProducts.add(p1);
        expProducts.add(p2);
        expProducts.add(p3);
        expProducts.add(p4);
        expProducts.add(p5);

        List<Product> resultProducts = instance.getProductsByCategory(category.getCategoryname());

        Product expected;
        for (int i = 0; i < resultProducts.size(); i++) {
            Product result = resultProducts.get(i);
            expected = expProducts.get(i);

            assertEquals(expected.getItemnumber(), result.getItemnumber());
            assertEquals(expected.getName(), result.getName());
            assertEquals(expected.getBrand(), result.getBrand());
            assertEquals(expected.getDescription(), result.getDescription());
            assertEquals(expected.getCategory().getCategoryname(),
                    result.getCategory().getCategoryname());
            assertEquals(expected.getSupplier(), result.getSupplier());
            assertEquals(expected.getSEOText(), result.getSEOText());
            assertEquals(expected.getStatus(), result.getStatus());
        }
    }

    @Test //(expected = CommandException.class)
    public void testGetProductsByCategoryID_UnknownCategory() throws CommandException {
        Category category = new Category("ThisCategoryDoesntExist", attrs);
        instance.getProductsByCategory(category.getCategoryname());
    }

    @Test
    public void testGetAllProducts() throws CommandException {
        List<Product> result = instance.getCatalog();

        assertNotNull(result);
        //assertTrue(result.size() == 25);
    }

    @Test (expected = CommandException.class)
    public void testGetAllProducts_EmptyDataBase() throws CommandException {
        try {
            Statement stmt = PersistenceFacadeDB.getConnection().createStatement();
            stmt.execute("drop schema if exists pimTest;");
            stmt.execute("create schema pimTest;");
            stmt.execute("use pimTest;");
        } catch (SQLException ex) {
        }
        instance.getCatalog();
    }

    @Test
    public void testGetProductsWithTagSearch() throws CommandException {
        Category category1 = new Category("Bicycle", attrs);
        Category category2 = new Category("Alcohol", attrs);
        List<Product> expProducts = new ArrayList<>();
        Product p1 = new Product(1, 132, "Red Bicycle", "Winther",
                "A Bicycle that is red", category1, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);
        Product p2 = new Product(13, 1309, "Sierra Silver Tequila", "Sierra Silver",
                "Tequila is a Mexican brandy, produced by the juice from blue agave.",
                category2, "Navada Lakeview brewery", "Tequila, Party", 100,
                categoryAttrs, images);
        Product p3 = new Product(14, 1419, "Smirnoff Vodka 37,5%", "Smirnoff",
                "The classic vodka for every party, can be mixed with almost everything",
                category2, "Smirnoff AS", "Vodka, Party", 100, categoryAttrs, images);
        expProducts.add(p1);
        expProducts.add(p2);
        expProducts.add(p3);

        List<Product> resultProducts = instance.getProductsWithTagSearch("Red");

        Product expected;
        for (int i = 0; i < resultProducts.size(); i++) {
            Product result = resultProducts.get(i);
            expected = expProducts.get(i);

            assertEquals(expected.getItemnumber(), result.getItemnumber());
            assertEquals(expected.getName(), result.getName());
            assertEquals(expected.getBrand(), result.getBrand());
            assertEquals(expected.getDescription(), result.getDescription());
            assertEquals(expected.getCategory().getCategoryname(),
                    result.getCategory().getCategoryname());
            assertEquals(expected.getSupplier(), result.getSupplier());
            assertEquals(expected.getSEOText(), result.getSEOText());
            assertEquals(expected.getStatus(), result.getStatus());
        }
    }

    @Test //(expected = CommandException.class)
    public void testGetProductsWithTagSearch_UnknownTag() throws CommandException {
        instance.getProductsWithTagSearch("ThisTagDoesntExist");
    }

    @Test
    public void testUpdate() throws CommandException {
        Product p = new Product(0, 3008, "Test Name", "Test Brand",
                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
                categoryAttrs, images);
        int productId = instance.createProduct(p);
        Product expected = p;
        expected.setId(productId);
        expected.setDescription("This is the new description");
        expected.setName("This is the new name");

        instance.updateProduct(expected);
        Product result = instance.getProduct(productId);

        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getItemnumber(), result.getItemnumber());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getBrand(), result.getBrand());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getSupplier(), result.getSupplier());
        assertEquals(expected.getSEOText(), result.getSEOText());
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test(expected = CommandException.class)
    public void testUpdate_UnknownProduct() throws CommandException {
        Product p = new Product();
        instance.updateProduct(p);
    }

    @Test(expected = CommandException.class)
    public void testUpdate_NullProduct() throws CommandException {
        instance.updateProduct(null);
    }

//    @Test
//    public void testUpdateAttributes() throws CommandException {
//        Product p = new Product(0, 3009, "Test Name", "Test Brand",
//                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
//                categoryAttrs, images);
//        int productId = instance.createProduct(p);
//        Product expected = p;
//        expected.setId(productId);
//        categoryAttrs.put("1st attribute", "new attribute value");
//        expected.setCategoryAttributes(categoryAttrs);
//
//        instance.updateProductAttributes(expected);
//        Product result = instance.getProduct(productId);
//
//        assertEquals(expected.getId(), result.getId());
//        assertEquals(expected.getItemnumber(), result.getItemnumber());
//        assertEquals(expected.getName(), result.getName());
//        assertEquals(expected.getBrand(), result.getBrand());
//        assertEquals(expected.getDescription(), result.getDescription());
//        assertEquals(expected.getCategory().getCategoryname(),
//                result.getCategory().getCategoryname());
//        assertEquals(expected.getSupplier(), result.getSupplier());
//        assertEquals(expected.getSEOText(), result.getSEOText());
//        assertEquals(expected.getStatus(), result.getStatus());
//        for (String key : categoryAttrs.keySet()) {
//            assertTrue(result.getCategoryAttributes().containsKey(key));
//            assertTrue(result.getCategoryAttributes().containsValue(categoryAttrs.get(key)));
//        }
//    }

    @Test(expected = CommandException.class)
    public void testUpdateAttributes_UnknownProduct() throws CommandException {
        Product p = new Product();
        instance.updateProductAttributes(p);
    }

    @Test(expected = CommandException.class)
    public void testUpdateAttributes_NullProduct() throws CommandException {
        instance.updateProductAttributes(null);
    }

//    @Test
//    public void testCreateAttributes() throws CommandException {
//        Product p = new Product(0, 3010, "Test Name", "Test Brand",
//                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
//                categoryAttrs, images);
//        int productId = instance.createProduct(p);
//        Product expected = p;
//        expected.setId(productId);
//        attrs.add("2nd attribute");
//        c.setAttributes(attrs);
//        categoryAttrs.put("2nd attribute", "2nd attribute value");
//        expected.setCategoryAttributes(categoryAttrs);
//
//        instance.createProductAttributes(expected);
//        Product result = instance.getProduct(productId);
//
//        assertEquals(expected.getId(), result.getId());
//        assertEquals(expected.getItemnumber(), result.getItemnumber());
//        assertEquals(expected.getName(), result.getName());
//        assertEquals(expected.getBrand(), result.getBrand());
//        assertEquals(expected.getDescription(), result.getDescription());
//        assertEquals(expected.getSupplier(), result.getSupplier());
//        assertEquals(expected.getSEOText(), result.getSEOText());
//        assertEquals(expected.getStatus(), result.getStatus());
//        for (String key : categoryAttrs.keySet()) {
//            assertTrue(result.getCategoryAttributes().containsKey(key));
//            assertTrue(result.getCategoryAttributes().containsValue(categoryAttrs.get(key)));
//        }
//    }

    @Test(expected = CommandException.class)
    public void testCreateAttributes_UnknownProduct() throws CommandException {
        Product p = new Product();
        instance.createProductAttributes(p);
    }

    @Test(expected = CommandException.class)
    public void testCreateAttributes_NullProduct() throws CommandException {
        instance.createProductAttributes(null);
    }

    @Test(expected = CommandException.class)
    public void testDelete() throws CommandException {
        c = new Category("Test Category", attrs);
        Product expected = new Product(0, 3011, "Test Name", "Test Brand",
                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
                categoryAttrs, images);
        int productId = instance.createProduct(expected);

        instance.deleteProduct(productId);

        instance.getProduct(productId);
    }

    @Test //(expected = CommandException.class)
    public void testDelete_UnknownProduct() throws CommandException {
        instance.deleteProduct(0);
    }

//    @Test
//    public void testDeleteProductAttribute() throws CommandException {
//        Product expected = new Product(0, 3012, "Test Name", "Test Brand",
//                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
//                categoryAttrs, images);
//        int productId = instance.createProduct(expected);
//        expected.setId(productId);
//        attrs.add("2nd attribute");
//        c.setAttributes(attrs);
//        categoryAttrs.put("2nd attribute", "2nd attribute value");
//        expected.setCategoryAttributes(categoryAttrs);
//        ProductMapper instance1 = new ProductMapper();
//
//        instance1.deleteProductAttribute(c.getId());
//
//        Product result = instance.getProduct(productId);
//        assertEquals(expected.getItemnumber(), result.getItemnumber());
//        assertEquals(expected.getName(), result.getName());
//        assertEquals(expected.getBrand(), result.getBrand());
//        assertEquals(expected.getDescription(), result.getDescription());
//        assertEquals(expected.getCategory().getCategoryname(),
//                result.getCategory().getCategoryname());
//        assertEquals(expected.getSupplier(), result.getSupplier());
//        assertEquals(expected.getSEOText(), result.getSEOText());
//        assertEquals(expected.getStatus(), result.getStatus());
//        for (String key : categoryAttrs.keySet()) {
//            assertTrue(result.getCategoryAttributes().containsKey(key));
//            assertTrue(result.getCategoryAttributes().containsValue(categoryAttrs.get(key)));
//        }
//    }

    @Test //(expected = CommandException.class)
    public void testDeleteProductAttribute_UnknownAtrributeId() throws CommandException {
        ProductMapper instance1 = new ProductMapper();
        instance1.deleteProductAttribute(0);
    }

//    @Test
//    public void testDeleteProductAttributes() throws CommandException {
//        Product expected = new Product(0, 3013, "Test Name", "Test Brand",
//                "Test Description", c, "Test Supllier", "Test SEO-text", 100,
//                categoryAttrs, images);
//        int productId = instance.createProduct(expected);
//        attrs.add("2nd attribute");
//        c.setAttributes(attrs);
//        categoryAttrs.put("1st attribute", "");
//        categoryAttrs.put("2nd attribute", "");
//        expected.setCategoryAttributes(categoryAttrs);
//
//        instance.deleteProductAttributes(productId);
//
//        Product result = instance.getProduct(productId);
//        assertEquals(expected.getItemnumber(), result.getItemnumber());
//        assertEquals(expected.getName(), result.getName());
//        assertEquals(expected.getBrand(), result.getBrand());
//        assertEquals(expected.getDescription(), result.getDescription());
//        assertEquals(expected.getSupplier(), result.getSupplier());
//        assertEquals(expected.getSEOText(), result.getSEOText());
//        assertEquals(expected.getStatus(), result.getStatus());
//        for (String key : expected.getCategoryAttributes().keySet()) {
//            assertTrue(result.getCategoryAttributes().containsKey(key));
//            assertTrue(result.getCategoryAttributes()
//                    .containsValue(expected.getCategoryAttributes().get(key)));
//        }
//    }

    @Test //(expected = CommandException.class)
    public void testDeleteProductAttributes_UnknownProduct() throws CommandException {
        instance.deleteProductAttributes(0);
    }

    @Test
    public void testGetProductsByItemNumber() throws CommandException {
        Category category1 = new Category("Bicycle", attrs);
        Product expected = new Product(1, 132, "Red Bicycle", "Winther",
                "A Bicycle that is red", category1, "Jupiter",
                "Bicycle, Bike, Transport, Sport", 100, categoryAttrs, images);

        List<Product> resultList = instance.getProductsByItemNumber(132);

        Product result = resultList.get(0);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getItemnumber(), result.getItemnumber());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getBrand(), result.getBrand());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getCategory().getCategoryname(),
                result.getCategory().getCategoryname());
        assertEquals(expected.getSupplier(), result.getSupplier());
        assertEquals(expected.getSEOText(), result.getSEOText());
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test //(expected = CommandException.class)
    public void testGetProductsByItemNumber_UnknownItemNumber() throws CommandException {
        instance.getProductsByItemNumber(0);
    }

    @Test
    public void testGetProductsByBrand() throws CommandException {
    }

    @Test
    public void testGetProductsBySupplier() throws CommandException {
    }

    @Test
    public void testUpdate_BulkEdit() throws CommandException {
    }
}
