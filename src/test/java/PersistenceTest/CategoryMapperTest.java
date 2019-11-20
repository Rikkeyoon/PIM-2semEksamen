//package PersistenceTest;
//
//import exception.CommandException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import logic.Category;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import persistence.PersistenceFacadeDB;
//
///**
// *
// * @author carol
// */
//public class CategoryMapperTest {
//    
//    private static PersistenceFacadeDB instance;
//
//    @BeforeClass
//    public static void clasSetup() {
//        instance = new PersistenceFacadeDB(true);
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
//    @Test 
//    public void testGetConnection() throws CommandException {
//        Connection con = instance.getConnection();
//        
//        assertNotNull(con);
//    }
//    
//    @Test
//    public void testCreateCategory() throws CommandException {
//        List<String> attr = new ArrayList<>();
//        attr.add("Test");
//        Category expected = new Category("CreateTest", attr);
//        instance.createCategory(expected);
//        Category result = instance.getCategory("CreateTest");
//        
//        assertEquals(expected.getCategoryname(), result.getCategoryname());
//        assertEquals(expected.getAttributes().get(0), result.getAttributes().get(0));
//    }
//    
//    @Test (expected = CommandException.class)
//    public void testCreateCategory_dublicateName() throws CommandException {
//        List<String> attr = new ArrayList<>();
//        attr.add("Test");
//        Category c1 = new Category("CreateTest2", attr);
//        Category c2 = new Category("CreateTest2", attr);
//        instance.createCategory(c1);
//        instance.createCategory(c2);
//    }
//    
//    @Test
//    public void testGetCategory_noAttributes() throws CommandException {
//        List<String> attr = new ArrayList<>();
//        Category expected = new Category("Cykler", attr);
//        Category result = instance.getCategory("Cykler");
//        
//        assertEquals(expected.getCategoryname(), result.getCategoryname());
//    }
//    
//    @Test
//    public void testGetCategory_withAttributes() throws CommandException {
//        List<String> attr1 = new ArrayList<>();
//        attr1.add("Kamera");
//        attr1.add("Processor");
//        Category expected1 = new Category("Mobiler", attr1);
//        Category result1 = instance.getCategory("Mobiler");
//        
//        List<String> attr2 = new ArrayList<>();
//        attr2.add("Alkohol Procent");
//        Category expected2 = new Category("Alkohol", attr2);
//        Category result2 = instance.getCategory("Alkohol");
//        
//        assertEquals(expected1.getCategoryname(), result1.getCategoryname());
//        assertEquals(expected1.getAttributes().get(0), result1.getAttributes().get(0));
//        assertEquals(expected1.getAttributes().get(1), result1.getAttributes().get(1));
//        
//        assertEquals(expected2.getCategoryname(), result2.getCategoryname());
//        assertEquals(expected2.getAttributes().get(0), result2.getAttributes().get(0));
//    }
//    
//    @Test (expected = CommandException.class)
//    public void testGetCategory_unknownCategory() throws CommandException {
//        Category result = instance.getCategory("CategoryThatDoesn'tExist");
//    }
//}
