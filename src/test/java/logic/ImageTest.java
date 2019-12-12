/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author carol
 */
public class ImageTest {
    
    @Test
    public void testGetUrl() {
        String expected = "www.url.com";
        Image instance = new Image(expected, true);
        String result = instance.getUrl();
        assertEquals(expected, result);
    }

    @Test
    public void testSetUrl() {
        String expected = "www.url.com";
        Image instance = new Image();
        instance.setUrl(expected);
        String result = instance.getUrl();
        assertEquals(expected, result);
    }

    @Test
    public void testIsPrimary() {
        Image instance = new Image("www.url.com", true);
        Image instance1 = new Image("www.url.com", false);
        assertTrue(instance.isPrimary());
        assertFalse(instance1.isPrimary());
    }

    @Test
    public void testSetPrimary() {
        Image instance = new Image();
        instance.setPrimary(true);
        assertTrue(instance.isPrimary());
    }
    
}
