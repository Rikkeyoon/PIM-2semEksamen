package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Category;
import logic.Product;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author carol, Allan
 */
public class CategoryMapperTest {

    private static PersistenceFacadeDB pf;

    @BeforeClass
    public static void clasSetup() {
        pf = new PersistenceFacadeDB(true);
    }

    @Before
    public void testSetup() throws CommandException {
        try {
            Statement stmt = PersistenceFacadeDB.getConnection().createStatement();
            stmt.execute("drop schema if exists pimTest;");
            stmt.execute("create schema pimTest;");
            stmt.execute("create table pimTest.categories like pimTest_template.categories;");
            stmt.execute("create table pimTest.products like pimTest_template.products;");
            stmt.execute("create table pimTest.attributes like pimTest_template.attributes;");
            stmt.execute("create table pimTest.category_attributes like pimTest_template.category_attributes;");
            stmt.execute("create table pimTest.attribute_values like  pimTest_template.attribute_values");
            stmt.execute("create table pimTest.images like pimTest_template.images;");
            stmt.execute("create table pimTest.tags like pimTest_template.tags;");
            stmt.execute("create table pimTest.product_tags like pimTest_template.product_tags;");
            stmt.execute("CREATE OR REPLACE VIEW pimTest.categories_and_attributes AS SELECT c.id AS category_id, attribute_name FROM pimTest.categories c JOIN pimTest.category_attributes ca ON c.id = ca.category_id JOIN pimTest.attributes a ON ca.attribute_id = a.id;");
            stmt.execute("CREATE OR REPLACE VIEW pimTest.products_with_categories_and_attributes AS SELECT p.id,p.item_number, p.name, p.brand, p.description, p.category_id, p.supplier, p.seo_text, p.status, a.attribute_name, av.attribute_value FROM pimTest.products p JOIN pimTest.category_attributes c ON p.category_id = c.category_id JOIN pimTest.attributes a ON c.attribute_id = a.id LEFT JOIN pimTest.attribute_values av ON p.id = av.product_id AND av.attribute_id = a.id ORDER BY p.id ASC;");
            stmt.execute("insert into pimTest.categories select * from pimTest_template.categories;");
            stmt.execute("insert into pimTest.products select * from pimTest_template.products;");
            stmt.execute("insert into pimTest.attributes select * from pimTest_template.attributes;");
            stmt.execute("insert into pimTest.category_attributes select * from pimTest_template.category_attributes;");
            stmt.execute("insert into pimTest.attribute_values select * from pimTest_template.attribute_values;");
            stmt.execute("insert into pimTest.tags select * from pimTest_template.tags;");
            stmt.execute("insert into pimTest.product_tags select * from pimTest_template.product_tags;");
            stmt.execute("insert into pimTest.images select * from pimTest_template.images;");

            stmt.execute("USE pimtest;");
        } catch (SQLException ex) {
            System.out.println("her " + ex.getMessage());
        }

    }

    @Test
    public void testGetConnection() throws CommandException {
        Connection con = pf.getConnection();

        assertNotNull(con);
    }

    @Test
    public void testCreateCategory() throws CommandException {
        List<String> attr = new ArrayList<>();
        attr.add("Test");
        Category expected = new Category("CreateTest", attr);
        pf.createCategory(expected);
        Category result = pf.getCategory("CreateTest");
        assertEquals(expected.getCategoryname(), result.getCategoryname());
        assertEquals(expected.getAttributes().get(0), result.getAttributes().get(0));
        assertEquals(6, result.getId());

    }

    @Test(expected = CommandException.class)
    public void testCreateCategory_dublicateName() throws CommandException {
        List<String> attr = new ArrayList<>();
        attr.add("Test");
        Category c1 = new Category("CreateTest2", attr);
        Category c2 = new Category("CreateTest2", attr);
        pf.createCategory(c1);
        pf.createCategory(c2);
    }

    @Test
    public void testGetCategory_noAttributes() throws CommandException {
        List<String> attr = new ArrayList<>();
        Category expected = new Category("Bicycle", attr);
        Category result = pf.getCategory("Bicycle");

        assertEquals(expected.getCategoryname(), result.getCategoryname());
    }

    @Test
    public void testGetCategory_withAttributes() throws CommandException {
        List<String> attr1 = new ArrayList<>();
        attr1.add("Camera");
        attr1.add("Screen-size");
        Category expected1 = new Category("Mobile Phone", attr1);
        Category result1 = pf.getCategory("Mobile Phone");

        List<String> attr2 = new ArrayList<>();
        attr2.add("Alcohol Percentage");
        Category expected2 = new Category("Alcohol", attr2);
        Category result2 = pf.getCategory("Alcohol");

        assertEquals(expected1.getCategoryname(), result1.getCategoryname());
        assertEquals(expected1.getAttributes().get(0), result1.getAttributes().get(0));
        assertEquals(expected1.getAttributes().get(1), result1.getAttributes().get(1));

        assertEquals(expected2.getCategoryname(), result2.getCategoryname());
        assertEquals(expected2.getAttributes().get(0), result2.getAttributes().get(0));
    }

    @Test(expected = CommandException.class)
    public void testGetCategory_unknownCategory() throws CommandException {
        Category result = pf.getCategory("CategoryThatDoesn'tExist");
    }

    @Test(expected = CommandException.class)
    public void testGetCategory_emptyAttribute() throws CommandException {
        List<String> attr1 = new ArrayList<>();
        attr1.add(null);
        pf.createCategory(new Category("Empty", attr1));
    }

    @Test
    public void testCreateCategory_withDupllicateAttribute() throws CommandException {
        List<String> attr = new ArrayList<>();
        attr.add("Test");
        attr.add("Test");
        Category expected = new Category("CreateTest", attr);
        pf.createCategory(expected);
        Category result = pf.getCategory("CreateTest");
        assertEquals(expected.getCategoryname(), result.getCategoryname());
        assertEquals(expected.getAttributes().get(0), result.getAttributes().get(0));
        assertEquals(6, result.getId());
    }

    @Test
    public void testGetProductsWithCategorySearch_oneCategory() throws CommandException {
        List<Product> products = null;

        products = pf.getProductsByCategory("Alcohol");

        assertEquals(5, products.size());
    }

    @Test
    public void testGetProductsWithCategorySearch_multipleCategories() throws CommandException {
        List<Product> products = null;

        products = pf.getProductsByCategory("le");

        assertEquals(10, products.size());
    }

    public void testGetProductsWithCategorySearch_emptySearch() throws CommandException {
        List<Product> products = null;

        products = pf.getProductsByCategory("thisshouldnotwork");

        assertEquals(0, products.size());
    }

    @Test
    public void testGetAllCategories() throws CommandException {
        List<Category> categories = null;

        categories = pf.getCategories();

        assertEquals(5, categories.size());
        for (Category category : categories) {
            assertNotNull(category.getCategoryname());
            assertNotNull(category.getId());
            assertNotNull(category.getAttributes());
        }
    }

    @Test
    public void testGetAllCategories_afterAdd(){
        List<Category> categories = null;
        List<String> attr = new ArrayList<>();
        attr.add("Test123");
        Category newCategory = new Category("CreateTest", attr);
        
        try {
            pf.createCategory(newCategory);
            categories = pf.getCategories();
        } catch (CommandException ex) {
            System.out.println(ex.getMessage());
        }


        assertEquals(6, categories.size());
        for (Category category : categories) {
            assertNotNull(category.getCategoryname());
            assertNotNull(category.getId());
            assertNotNull(category.getAttributes());
        }
    }

}
