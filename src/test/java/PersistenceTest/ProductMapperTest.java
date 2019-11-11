///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package PersistenceTest;
//
//import exception.CommandException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.BeforeClass;
//import persistence.DataSourceController;
//import persistence.PersistenceFacade;
//
///**
// *
// * @author allan
// */
//public class ProductMapperTest {
//
//    private DataSourceController DSC;
//
//    @BeforeClass
//    public void setup() {
//        DSC = new DataSourceController(true);
//    }
//
//    @Test
//    public void getConnectionTest() throws CommandException {
//        assertNotNull(DSC.getConnection());
//    }
//
//    @Test //getProductTest1, tests that we can get all the bikes(cykler) from the testDatabase.
//    public void getProductTest1() throws CommandException {
//        assertEquals(1, DSC.getProduct("Rød Cykel").getId());
//        assertEquals(2, DSC.getProduct("Grøn Cykel").getId());
//        assertEquals(3, DSC.getProduct("Blå Cykel").getId());
//        assertEquals(4, DSC.getProduct("Pink Cykel").getId());
//        assertEquals(5, DSC.getProduct("SORT Cykel").getId());
//    }
//
//    @Test
//    public void getProductTest2() throws CommandException {
//        assertEquals(6, DSC.getProduct("Samsung Galaxy S10").getId());
//        assertEquals(7, DSC.getProduct("Apple iphone 11").getId());
//        assertEquals(8, DSC.getProduct("Hauwei P30").getId());
//        assertEquals(9, DSC.getProduct("Xiaomi redmi note 5").getId());
//        assertEquals(10, DSC.getProduct("Sony Ericsson Xperia").getId());
//    }
//
//    @Test
//    public void getProductTest3() throws CommandException {
//        assertEquals(11, DSC.getProduct("Tuborg Classic 6 pack").getId());
//        assertEquals(12, DSC.getProduct("Carlsberg 6 pack").getId());
//        assertEquals(13, DSC.getProduct("Sierra Silver Tequila").getId());
//        assertEquals(14, DSC.getProduct("Smirnoff Vodka 37,5%").getId());
//        assertEquals(15, DSC.getProduct("Bornholmer Honningsyp").getId());
//    }
//
//    @Test
//    public void getProductTest4() throws CommandException {
//        assertEquals(16, DSC.getProduct("Hauwei R5").getId());
//        assertEquals(17, DSC.getProduct("Apple Pro").getId());
//        assertEquals(18, DSC.getProduct("Asus Zenbook").getId());
//        assertEquals(19, DSC.getProduct("Acer Cromebook").getId());
//        assertEquals(20, DSC.getProduct("Lenovo thinkpad L590").getId());
//    }
//
//    @Test
//    public void getProductTest5() throws CommandException {
//        assertEquals(21, DSC.getProduct("Auping Royal").getId());
//        assertEquals(22, DSC.getProduct("Viking Birka").getId());
//        assertEquals(23, DSC.getProduct("Jensen Prestige").getId());
//        assertEquals(24, DSC.getProduct("Carpe Diem Harmano").getId());
//        assertEquals(25, DSC.getProduct("Tempur Fusion").getId());
//    }
//
//    //Test that CommandException is thrown when searching for nonexisting product
//    @Test(expected = CommandException.class)
//    public void getProductTestFail() throws CommandException {
//        DSC.getProduct("Flying Bike");
//    }
//
//    @Test
//    public void getProductsTest() {
//        ArrayList<Invoice> invoiceList = im.getAllInvoices();
//        assertEquals(im.getHighestID(), invoiceList.size());
//        for (int i = 0; i < invoiceList.size(); i++) {
//            Invoice invoice = invoiceList.get(i);
//            assertEquals(i + 1, invoice.getID());
//            assertNotNull(invoice.getCustomerID());
//            assertNotNull(invoice.getPrice());
//            assertNotNull(invoice.getTime());
//            assertNotNull(invoice.getOrderlines());
//        }
//
//    }
//
//    @Test
//    public void createProductTest() {
//        
//    }
//}
