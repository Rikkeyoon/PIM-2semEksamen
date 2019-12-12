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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;
import persistence.TagMapper;

/**
 *
 * @author søren
 */
public class TagMapperTest {

        private static PersistenceFacadeDB pf;
        private static TagMapper tm = new TagMapper();


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
            stmt.execute("create table pimTest.tags like pimTest_template.tags;");
            stmt.execute("create table pimTest.products like pimTest_template.products;");
            stmt.execute("create table pimTest.product_tags like pimTest_template.product_tags;");
            stmt.execute("insert into pimTest.categories select * from pimTest_template.categories;");
            stmt.execute("insert into pimTest.products select * from pimTest_template.products;");
            stmt.execute("insert into pimTest.tags select * from pimTest_template.tags;");
            stmt.execute("insert into pimTest.product_tags select * from pimTest_template.product_tags;");
            stmt.execute("USE pimtest;");
        } catch (SQLException ex) {
        }

    }

    @Test //Tests that we can get a connection with the database.
    public void getConnectionTest() throws CommandException {

        //Arrange
        Connection con = null;

        //Act
        con = PersistenceFacadeDB.getConnection();

        //Assert
        assertNotNull(con);

    }
               
    @Test
    public void getTagsForProductWithIDtest() throws CommandException {
       //Arrange
       List<String> tagresult = null;
       List<String> tagexpected = new ArrayList <> ();
       tagexpected.add("Red");
       tagexpected.add("Wheels");
       tagexpected.add("Pedal");
       tagexpected.add("Lights");
       
       //Act
       tagresult = pf.getTagsForProductWithID(1);
       
       //Assert
       assertArrayEquals(tagresult.toArray(),tagexpected.toArray());    
    }
    
//    @Test   (expected = CommandException.class)
//    public void getTagsForProductWithIDtestn() throws CommandException {
//       //Arrange
//       List<String> tagresult = null;
//       //Act
//       tagresult = pf.getTagsForProductWithID(45);
//       //Assert
//       assertEquals(tagresult.size(),0);
//    }
    
    @Test
    public void createTagtest() throws CommandException {
        //Arrange
        assertEquals(pf.getTagsForProductWithID(1).size(),4);
        List<String> createtag = new ArrayList <> ();    
        createtag.add("test");
        //Act
        pf.createProductTags(1, createtag);
        //Assert
        assertEquals(pf.getTagsForProductWithID(1).size(),5);
    }
    
//        @Test (expected = CommandException.class)
//    public void createTagtestn() throws CommandException {
//        //Arrange
//        assertEquals(pf.getTagsForProductWithID(1).size(),4);
//        List<String> createtag = new ArrayList <> ();    
//        createtag.add("test");
//        createtag.add("test");
//        //Act
//        pf.createProductTags(1, createtag);
////        pf.createProductTags(1, createtag);
//        //Assert
////        assertEquals(pf.getTagsForProductWithID(1).size(),5);
//    }
    
    @Test
    public void getProductsIDFromTagNameSearchtest() throws CommandException {
        //Arrange
       List<Integer> idresult = null;
       List<Integer> idexpected = new ArrayList <> ();
       idexpected.add(1);
       idexpected.add(2);
       idexpected.add(3);
       idexpected.add(4);
       idexpected.add(5);
        //Act
        idresult = pf.getProductsIDFromTagNameSearch("Pedal");
        //Assert
        assertArrayEquals(idresult.toArray(),idexpected.toArray());  
    }
    
    @Test (expected = CommandException.class) //nullpointer
    public void getProductsIDFromTagNameSearchtestn() throws CommandException {
        //Arrange
       List<Integer> idresult = null;
       List<Integer> idexpected = new ArrayList <> ();
       idexpected.add(1);
       idexpected.add(2);
       idexpected.add(3);
       idexpected.add(4);
       idexpected.add(5);
        //Act
        idresult = pf.getProductsIDFromTagNameSearch(null);
        //Assert
        assertArrayEquals(idresult.toArray(),idexpected.toArray());  
    }
    
    @Test
    public void deleteTagsForProducttest() throws CommandException {
        //Arrange
        List<String> tagsresultbefore = null;
        List<String> tagsexpectedbefore = new ArrayList <> ();
        tagsexpectedbefore.add("Red");
        tagsexpectedbefore.add("Wheels");
        tagsexpectedbefore.add("Pedal");
        tagsexpectedbefore.add("Lights");
        List<String> tagsresultafter = null;
        //Act
        tagsresultbefore = pf.getTagsForProductWithID(1);
        pf.deleteTagsForProduct(1);
        tagsresultafter = pf.getTagsForProductWithID(1);
        //Assert
        assertArrayEquals(tagsresultbefore.toArray(),tagsexpectedbefore.toArray());
        assertEquals(tagsresultafter.size(),0); 
    }
    
    @Test (expected = CommandException.class)
    public void deleteTagsForProducttestn() throws CommandException {
        //Arrange
        List<String> tagsresultbefore = null;
        List<String> tagsexpectedbefore = new ArrayList <> ();
        tagsexpectedbefore.add("Red");
        tagsexpectedbefore.add("Wheels");
        tagsexpectedbefore.add("Pedal");
        tagsexpectedbefore.add("Lights");
        List<String> tagsresultafter = null;
        //Act
        tagsresultbefore = pf.getTagsForProductWithID(1);
        pf.deleteTagsForProduct(0);
        tagsresultafter = pf.getTagsForProductWithID(1);
        //Assert
        assertArrayEquals(tagsresultbefore.toArray(),tagsexpectedbefore.toArray());
        assertEquals(tagsresultafter.size(),0); 
    }
    
    @Test
    public void getTagIdFromNametest() throws CommandException {
        //Arrange
        int resultid = 0;
        int expectedid = 1;
        
        //Act
        resultid = tm.getTagIdFromName("Red");
        
        //Assert
        assertEquals(resultid,expectedid);     
    }
    
    @Test
    public void getAllTagIdstest() throws CommandException {
        //Arrange
        List <Integer> allresultids = null;
        List <Integer> allexpectedids = new ArrayList <> ();
        for (int i = 1; i <= 29; i++) {
            allexpectedids.add(i);
        }
        //Act
        allresultids = tm.getAllTagIds();
        //Assert
        assertArrayEquals(allresultids.toArray(),allexpectedids.toArray());
    }

    @Test
    public void deleteUnusedTagstest() throws CommandException {
        //Arrange
        List <Integer> allresultids = tm.getAllTagIds();
        List <String> unusedids = new ArrayList <> ();
        unusedids.add("Test1");
        unusedids.add("Test2");
        tm.createTags(unusedids);
        List <Integer> allexpectedids = new ArrayList <> ();
//        for (int i = 1; i <= 29; i++) {
//            allexpectedids.add(i);
//        }
        
        //Act
        pf.deleteUnusedTags();
        allresultids = tm.getAllTagIds();
        
        //Assert
        assertArrayEquals(allresultids.toArray(),allexpectedids.toArray());
    }
}
