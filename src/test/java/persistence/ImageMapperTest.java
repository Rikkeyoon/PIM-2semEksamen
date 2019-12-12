/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logic.Image;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author allan
 */
public class ImageMapperTest {

    private static PersistenceFacadeDB pf;
    private static ImageMapper im = new ImageMapper();

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

    @Test(expected = NullPointerException.class)
    /**
     * Cannot test beacause the method requires parts which is not available
     * outside multipart form input
     */
    public void testCloudinaryUploadAndDownload() throws CommandException {
        pf.uploadImagesToCloudinary(null, "");
        assertTrue(true);
    }

    @Test
    public void deleteAllImagesForProductTest() throws CommandException {
        List<Image> before = null;
        List<Image> after = null;

        before = pf.getPicturesForProduct(1);
        pf.deleteAllImages(1);
        after = pf.getPicturesForProduct(1);

        assertEquals(1, before.size());
        assertEquals(0, after.size());
    }

    @Test
    public void deleteImagesWithURL() throws CommandException {
        List<Image> before = null;
        List<Image> addList = new ArrayList<>();
        addList.add(new Image("test123", true));
        addList.add(new Image("test321", false));
        addList.add(new Image("test222", false));
        List<Image> after = null;
        
        im.addPictureURL(1, addList);
        before = pf.getPicturesForProduct(1);
        String[] deletelist = new String[]{"test123", "test321", "test222"};
        im.deleteImages(deletelist);
        after = pf.getPicturesForProduct(1);

        assertEquals(4, before.size());
        assertEquals(1, after.size());
    }
    
    @Test
    public void getPrimaryPictureTest() throws CommandException{
        Image image = null;
        
        image = im.getPrimaryPictureWithId(1);
        
        assertEquals("https://res.cloudinary.com/dmk5yii3m/image/upload/v1574331134/roedCykel.jpg" ,image.getUrl());
    }

}
