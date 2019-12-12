/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author allan
 */
public class ProductTest {

    public ProductTest() {

    }
    @Test
    public void productremoveImagesTest() {
        //Arrange
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image("testa321", false));
        images.add(new Image("testb123", true));
        images.add(new Image("testc222", false));
        images.add(new Image("testd666", false));
        images.add(new Image("teste420", false));
        String[] delete = new String[]{"teste420", "testd666"};

        Product p = new Product(0, 0, "", "", "", new Category(), "", "", 0, images);

        //Act
        List<Image> resultbefore = p.getImages();
        p.removeImages(delete);
        List<Image> resultAfter = p.getImages();

        //Assert
        assertEquals(5, resultbefore.size());
        assertEquals(8, resultAfter.size());
    }
    
    @Test
    /**
     * removeImages(String[])
     */
    public void productgetPrimaryImageTest() {
        //Arrange
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image("test321", false));
        images.add(new Image("test123", true));
        images.add(new Image("test222", false));

        Product p = new Product(0, 0, "", "", "", new Category(), "", "", 0, null, images);

        //Act
        String result = p.getPrimaryImage();

        //Assert
        assertEquals("test123", result);
    }

    @Test
    public void productsetPrimaryImageTest() {
        //Arrange
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image("test321", false));
        images.add(new Image("test123", true));
        images.add(new Image("test222", false));

        Product p = new Product(0, 0, "", "", "", new Category(), "", "", 0, images);
        Product p1 = new Product("", "", "", null, null);
        p.toString();

        //Act
        p.setPrimaryImage("test222");
        String result = p.getPrimaryImage();

        //Assert
        assertEquals("test222", result);
    }

    @Test
    public void productGetTagsAsString() {
        //Arrange
        ArrayList<String> tags = new ArrayList<>();
        tags.add("test1");
        tags.add("test123");
        tags.add("test321");
        Product p1 = new Product(0, 1, "", "", "", "", "", tags, new Category(), new LinkedHashMap<String, String>(), null);
        Product p2 = new Product(0, 1, "", "", "", "", "", new ArrayList<String>(), new Category(), new LinkedHashMap<String, String>(), null);
        Product p3 = new Product(0, 1, "", "", "", "", "", null, new Category(), new LinkedHashMap<String, String>(), null);

        //Act
        String result1 = p1.getTagsAsString();
        String result2 = p2.getTagsAsString();
        String result3 = p3.getTagsAsString();

        //Assert
        assertEquals("test1, test123, test321", result1);
        assertEquals("", result2);
        assertEquals("", result3);
    }

    @Test
    public void productCalculateStatus_noCategoryAttributes() {

        //Arrange
        List<String> tags = new ArrayList<>();
        tags.add("test");
        List<Image> images = new ArrayList<>();
        images.add(new Image("test123", true));
        Product p1 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), new LinkedHashMap<String, String>(), null);
        Product p2 = new Product(1, "bike", "Jupiter", "123", "", "", null, new Category(), new LinkedHashMap<String, String>(), null);
        Product p3 = new Product(1, "bike", "Jupiter", "", "123", "", null, new Category(), new LinkedHashMap<String, String>(), null);
        Product p4 = new Product(1, "bike", "Jupiter", "", "", "123", null, new Category(), new LinkedHashMap<String, String>(), null);
        Product p5 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), new LinkedHashMap<String, String>(), null);
        Product p6 = new Product(1, "bike", "Jupiter", "", "", "", tags, new Category(), new LinkedHashMap<String, String>(), null);
        Product p7 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), new LinkedHashMap<String, String>(), null);
        Product p8 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), new LinkedHashMap<String, String>(), images);
        Product p9 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), new LinkedHashMap<String, String>(), images);
        Product p10 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), new LinkedHashMap<String, String>(), images);

        //Act
        p1.calculateStatus();
        p2.calculateStatus();
        p3.calculateStatus();
        p4.calculateStatus();
        p5.calculateStatus();
        p6.calculateStatus();
        p7.calculateStatus();
        p8.calculateStatus();
        p9.calculateStatus();
        p10.calculateStatus();

        //Assert
        assertEquals(36, p1.getStatus());
        assertEquals(48, p2.getStatus());
        assertEquals(48, p3.getStatus());
        assertEquals(48, p4.getStatus());
        assertEquals(72, p5.getStatus());
        assertEquals(48, p6.getStatus());
        assertEquals(84, p7.getStatus());
        assertEquals(48, p8.getStatus());
        assertEquals(84, p9.getStatus());
        assertEquals(100, p10.getStatus());

    }

    @Test
    public void productCalculateStatus_threeCategoryAttributes_Filled() {

        //Arrange
        List<String> tags = new ArrayList<>();
        tags.add("test");
        List<Image> images = new ArrayList<>();
        images.add(new Image("test123", true));
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        categoryAttributes.put("test1", "test123");
        categoryAttributes.put("test2", "test321");
        categoryAttributes.put("test3", "test231");

        Product p1 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, null);
        Product p2 = new Product(1, "bike", "Jupiter", "123", "", "", null, new Category(), categoryAttributes, null);
        Product p3 = new Product(1, "bike", "Jupiter", "", "123", "", null, new Category(), categoryAttributes, null);
        Product p4 = new Product(1, "bike", "Jupiter", "", "", "123", null, new Category(), categoryAttributes, null);
        Product p5 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, null);
        Product p6 = new Product(1, "bike", "Jupiter", "", "", "", tags, new Category(), categoryAttributes, null);
        Product p7 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, null);
        Product p8 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, images);
        Product p9 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, images);
        Product p10 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, images);

        //Act
        p1.calculateStatus();
        p2.calculateStatus();
        p3.calculateStatus();
        p4.calculateStatus();
        p5.calculateStatus();
        p6.calculateStatus();
        p7.calculateStatus();
        p8.calculateStatus();
        p9.calculateStatus();
        p10.calculateStatus();

        //Assert
        assertEquals(54, p1.getStatus());
        assertEquals(63, p2.getStatus());
        assertEquals(63, p3.getStatus());
        assertEquals(63, p4.getStatus());
        assertEquals(81, p5.getStatus());
        assertEquals(63, p6.getStatus());
        assertEquals(90, p7.getStatus());
        assertEquals(63, p8.getStatus());
        assertEquals(90, p9.getStatus());
        assertEquals(100, p10.getStatus());

    }

    @Test
    public void productCalculateStatus_ThreeCategoryAttributes_Empty() {

        //Arrange
        List<String> tags = new ArrayList<>();
        tags.add("test");
        List<Image> images = new ArrayList<>();
        images.add(new Image("test123", true));
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        categoryAttributes.put("test1", "");
        categoryAttributes.put("test2", "");
        categoryAttributes.put("test3", "");

        Product p1 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, null);
        Product p2 = new Product(1, "bike", "Jupiter", "123", "", "", null, new Category(), categoryAttributes, null);
        Product p3 = new Product(1, "bike", "Jupiter", "", "123", "", null, new Category(), categoryAttributes, null);
        Product p4 = new Product(1, "bike", "Jupiter", "", "", "123", null, new Category(), categoryAttributes, null);
        Product p5 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, null);
        Product p6 = new Product(1, "bike", "Jupiter", "", "", "", tags, new Category(), categoryAttributes, null);
        Product p7 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, null);
        Product p8 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, images);
        Product p9 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, images);
        Product p10 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, images);

        //Act
        p1.calculateStatus();
        p2.calculateStatus();
        p3.calculateStatus();
        p4.calculateStatus();
        p5.calculateStatus();
        p6.calculateStatus();
        p7.calculateStatus();
        p8.calculateStatus();
        p9.calculateStatus();
        p10.calculateStatus();

        //Assert
        assertEquals(27, p1.getStatus());
        assertEquals(36, p2.getStatus());
        assertEquals(36, p3.getStatus());
        assertEquals(36, p4.getStatus());
        assertEquals(54, p5.getStatus());
        assertEquals(36, p6.getStatus());
        assertEquals(63, p7.getStatus());
        assertEquals(36, p8.getStatus());
        assertEquals(63, p9.getStatus());
        assertEquals(72, p10.getStatus());

    }

    @Test
    public void productCalculateStatus_SixCategoryAttributes_Filled() {

        //Arrange
        List<String> tags = new ArrayList<>();
        tags.add("test");
        List<Image> images = new ArrayList<>();
        images.add(new Image("test123", true));
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        categoryAttributes.put("test1", "test123");
        categoryAttributes.put("test2", "test321");
        categoryAttributes.put("test3", "test3211");
        categoryAttributes.put("test4", "test231");
        categoryAttributes.put("test5", "test132");
        categoryAttributes.put("test6", "test112");

        Product p1 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, null);
        Product p2 = new Product(1, "bike", "Jupiter", "123", "", "", null, new Category(), categoryAttributes, null);
        Product p3 = new Product(1, "bike", "Jupiter", "", "123", "", null, new Category(), categoryAttributes, null);
        Product p4 = new Product(1, "bike", "Jupiter", "", "", "123", null, new Category(), categoryAttributes, null);
        Product p5 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, null);
        Product p6 = new Product(1, "bike", "Jupiter", "", "", "", tags, new Category(), categoryAttributes, null);
        Product p7 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, null);
        Product p8 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, images);
        Product p9 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, images);
        Product p10 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, images);

        //Act
        p1.calculateStatus();
        p2.calculateStatus();
        p3.calculateStatus();
        p4.calculateStatus();
        p5.calculateStatus();
        p6.calculateStatus();
        p7.calculateStatus();
        p8.calculateStatus();
        p9.calculateStatus();
        p10.calculateStatus();

        //Assert
        assertEquals(63, p1.getStatus());
        assertEquals(70, p2.getStatus());
        assertEquals(70, p3.getStatus());
        assertEquals(70, p4.getStatus());
        assertEquals(84, p5.getStatus());
        assertEquals(70, p6.getStatus());
        assertEquals(91, p7.getStatus());
        assertEquals(70, p8.getStatus());
        assertEquals(91, p9.getStatus());
        assertEquals(100, p10.getStatus());

    }

    @Test
    public void productCalculateStatus_SixCategoryAttributes_Empty() {

        //Arrange
        List<String> tags = new ArrayList<>();
        tags.add("test");
        List<Image> images = new ArrayList<>();
        images.add(new Image("test123", true));
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        categoryAttributes.put("test1", "");
        categoryAttributes.put("test2", "");
        categoryAttributes.put("test3", "");
        categoryAttributes.put("test4", "");
        categoryAttributes.put("test5", "");
        categoryAttributes.put("test6", "");

        Product p1 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, null);
        Product p2 = new Product(1, "bike", "Jupiter", "123", "", "", null, new Category(), categoryAttributes, null);
        Product p3 = new Product(1, "bike", "Jupiter", "", "123", "", null, new Category(), categoryAttributes, null);
        Product p4 = new Product(1, "bike", "Jupiter", "", "", "123", null, new Category(), categoryAttributes, null);
        Product p5 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, null);
        Product p6 = new Product(1, "bike", "Jupiter", "", "", "", tags, new Category(), categoryAttributes, null);
        Product p7 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, null);
        Product p8 = new Product(1, "bike", "Jupiter", "", "", "", null, new Category(), categoryAttributes, images);
        Product p9 = new Product(1, "bike", "Jupiter", "123", "123", "123", null, new Category(), categoryAttributes, images);
        Product p10 = new Product(1, "bike", "Jupiter", "123", "123", "123", tags, new Category(), categoryAttributes, images);

        //Act
        p1.calculateStatus();
        p2.calculateStatus();
        p3.calculateStatus();
        p4.calculateStatus();
        p5.calculateStatus();
        p6.calculateStatus();
        p7.calculateStatus();
        p8.calculateStatus();
        p9.calculateStatus();
        p10.calculateStatus();

        //Assert
        assertEquals(21, p1.getStatus());
        assertEquals(28, p2.getStatus());
        assertEquals(28, p3.getStatus());
        assertEquals(28, p4.getStatus());
        assertEquals(42, p5.getStatus());
        assertEquals(28, p6.getStatus());
        assertEquals(49, p7.getStatus());
        assertEquals(28, p8.getStatus());
        assertEquals(49, p9.getStatus());
        assertEquals(56, p10.getStatus());

    }

    @Test
    public void productCalculateStatus_nullValuesAndEmptyLists() {

        //Arrange
        List<String> tags = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        categoryAttributes.put("test1", null);

        Product p1 = new Product(0, "", "", "", "", "", null, new Category(), new LinkedHashMap<String, String>(), null);
        Product p2 = new Product(0, "", "", "", "", "", tags, new Category(), new LinkedHashMap<String, String>(), null);
        Product p3 = new Product(0, "", "", "", "", "", null, new Category(), new LinkedHashMap<String, String>(), images);
        Product p4 = new Product(0, "", "", "", "", "", null, new Category(), categoryAttributes, null);

        //Act
        p1.calculateStatus();
        p2.calculateStatus();
        p3.calculateStatus();
        p4.calculateStatus();

        //Assert
        assertEquals(0, p1.getStatus());
        assertEquals(0, p2.getStatus());
        assertEquals(0, p3.getStatus());
        assertEquals(0, p4.getStatus());

    }
    
    @Test
    public void testSetters() {
        Product p = new Product(0, "", "", "", "", "", null, null, null, null);
        Category category = new Category(1, "Category", new ArrayList<String>());
        Map<String, String> categoryAttributes = new LinkedHashMap<>();
        List<String> tags = new ArrayList<>();
        
        p.setBrand("Brand");
        p.setCategory(category);
        p.setCategoryAttributes(categoryAttributes);
        p.setDescription("Description");
        p.setId(1);
        p.setItemnumber(2);
        p.setName("Name");
        p.setSEOText("SEOText");
        p.setSupplier("supplier");
        p.setTags(tags);
        
        assertEquals("Brand", p.getBrand());
        assertEquals(category, p.getCategory());
        assertEquals(categoryAttributes, p.getCategoryAttributes());
        assertEquals("Description", p.getDescription());
        assertEquals(1, p.getId());
        assertEquals(2, p.getItemnumber());
        assertEquals("Name", p.getName());
        assertEquals("SEOText", p.getSEOText());
        assertEquals("supplier", p.getSupplier());
        assertEquals(tags, p.getTags());
    }

}
