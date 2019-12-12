package persistence;

import exception.CommandException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author carol
 */
public class AttributeMapperTest {

    private AttributeMapper instance = new AttributeMapper();
    private static PersistenceFacadeDB pf;

    @BeforeClass
    public static void classSetup() throws CommandException {
        pf = new PersistenceFacadeDB(true);
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
            stmt.execute("insert into pimTest.category_attributes select * pimTest_template.category_attributes;");
            stmt.execute("insert into pimTest.product_tags select * pimTest_template.product_tags;");
            stmt.execute("insert into pimTest.attributes select * pimTest_template.attributes;");
            stmt.execute("insert into pimTest.attribute_values select * pimTest_template.attribute_values;");
            stmt.execute("insert into pimTest.tags select * pimTest_template.tags;");
            stmt.execute("insert into pimTest.images select * pimTest_template.images;");
            stmt.execute("USE pimtest;");
        } catch (SQLException ex) {
        }
    }

    
    public void testGetAttributeId() throws Exception {

        int expected = 1;
        int result = instance.getAttributeId("Bike size");
        assertEquals(expected, result);

    }

    @Test(expected = CommandException.class)
    public void testGetAttributeId_UnknownName() throws Exception {
        instance.getAttributeId("ThisNameDoesn'tExist");
    }

    @Test
    public void testCreateAttributes() throws Exception {
        List<String> attributeNames = new ArrayList<>();
        attributeNames.add("1st test attribute");
        attributeNames.add("2nd test attribute");
        attributeNames.add("3rd test attribute");

        instance.createAttributes(attributeNames);

        assertNotNull(instance.getAttributeId("1st test attribute"));
        assertNotNull(instance.getAttributeId("2nd test attribute"));
        assertNotNull(instance.getAttributeId("3rd test attribute"));
    }

    @Test
    public void testCreateAttributes_DuplicateName() throws Exception {
        List<String> attributeNames = new ArrayList<>();
        attributeNames.add("Bike size");

        /* createattributes doesn't throw an exception for duplicate entries,
         * but we can test that the id that the method getAttributeId returns 
         * is the correct id
         */
        instance.createAttributes(attributeNames);

        int id = instance.getAttributeId("Bike size");
        assertEquals(1, id);
    }

    @Test(expected = CommandException.class)
    public void testCreateAttributes_ListWithNull() throws Exception {
        List<String> attributeNames = new ArrayList<>();
        attributeNames.add(null);
        instance.createAttributes(attributeNames);
    }

    @Test
    public void testUpdateAttributeName() throws Exception {
        List<String> attributeNames = new ArrayList<>();
        String expected = "5th test attribute";
        attributeNames.add("4th test attribute");
        instance.createAttributes(attributeNames);

        instance.updateAttributeName("4th test attribute", expected);

        assertNotNull(instance.getAttributeId(expected));
    }


    public void testUpdateAttributeName_DuplicateName() throws Exception {
        instance.updateAttributeName("ThisNameDoesntExist", "New name");
    }

    @Test
    public void testDeleteAttribute_int() throws Exception {

    }

    @Test(expected = CommandException.class)
    public void testDeleteAttribute_int_UnknownId() throws Exception {
        instance.deleteAttribute(0);
    }

    @Test
    public void testDeleteAttribute_String() throws Exception {
        pf.deleteAttribute("");
    }

}
