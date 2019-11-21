/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import javafx.util.Pair;
import javax.servlet.http.Part;

/**
 *
 * @author allan
 */
public interface IImageMapper {

    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage);
    
}
