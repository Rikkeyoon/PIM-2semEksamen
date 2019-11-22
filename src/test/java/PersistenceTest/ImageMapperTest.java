/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceTest;

import exception.CommandException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author allan
 */
public class ImageMapperTest {
    
    private static PersistenceFacadeDB pf;

    @BeforeClass
    public static void clasSetup() {
        pf = new PersistenceFacadeDB(true);
    }

//    @Test
//    public void testDeletePicture() throws CommandException{
//        pf.removeImagesFromCloudinary("https://res.cloudinary.com/dmk5yii3m/image/upload/v1574424829/270px-Powerpuff_girls_characters_sn8709.jpg");
//    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
