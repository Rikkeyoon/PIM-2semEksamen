/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceTest;

import exception.CommandException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import persistence.IPersistenceFacade;
import persistence.PersistenceFacadeDB;

/**
 *
 * @author allan
 */
public class TagMapperTest {

    
    public void tagproductsmastertest() throws CommandException{
        IPersistenceFacade pf = new PersistenceFacadeDB(false);
        assertEquals(2, pf.getProductsWithTagSearch("g").size());
    }
    
    public void tagmastertest() throws CommandException {
        
        IPersistenceFacade pf = new PersistenceFacadeDB(false);
        int id = 1;
        ArrayList<String> tags = new ArrayList<>();
        tags.add("rød");
        tags.add("Cykel");
        tags.add("børn");
        tags.add("køretøj");
        tags.add("Jernhest");
        pf.createProductTags(id, tags);
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
