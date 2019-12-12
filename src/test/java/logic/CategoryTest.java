/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author carol
 */
public class CategoryTest {
    
    @Test
    public void testGetId() {
        List<String> attrs = new ArrayList<>();
        Category instance = new Category(1, "Category", attrs);
        assertEquals(1, instance.getId());
    }

    @Test
    public void testSetId() {
        List<String> attrs = new ArrayList<>();
        Category instance = new Category(1, "Category", attrs);
        instance.setId(2);
        assertEquals(2, instance.getId());
    }

    @Test
    public void testGetCategoryname() {
        List<String> attrs = new ArrayList<>();
        Category instance = new Category(1, "Category", attrs);
        assertEquals("Category", instance.getCategoryname());
    }

    @Test
    public void testSetCategoryname() {
        List<String> attrs = new ArrayList<>();
        Category instance = new Category(1, "Category", attrs);
        instance.setCategoryname("New");
        assertEquals("New", instance.getCategoryname());
    }

    @Test
    public void testGetAttributes() {
        List<String> attrs = new ArrayList<>();
        attrs.add("Attribute");
        Category instance = new Category(1, "Category", null);
        instance.setAttributes(attrs);
        assertEquals("Attribute", instance.getAttributes().get(0));
        assertEquals(1, instance.getAttributes().size());
    }
    
    @Test
    public void testSetAttributes() {
        List<String> attrs = new ArrayList<>();
        attrs.add("Attribute");
        Category instance = new Category(1, "Category", attrs);
        List<String> newAttrs = new ArrayList<>();
        newAttrs.add("New Attribute");
        instance.setAttributes(newAttrs);
        assertEquals("New Attribute", instance.getAttributes().get(0));
        assertEquals(1, instance.getAttributes().size());
    }
    
    @Test
    public void testToString() {
        List<String> attrs = new ArrayList<>();
        attrs.add("Attribute");
        Category instance = new Category(1, "Category Name", attrs);
        String expected = "Category{categoryname=Category Name, attributes=[Attribute]}";
        String result = instance.toString();
        assertEquals(expected, result);
    }
    
}
