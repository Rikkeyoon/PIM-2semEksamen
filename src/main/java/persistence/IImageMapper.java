package persistence;

import exception.CommandException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;
import logic.Product;

/**
 *
 * @author allan
 */
public interface IImageMapper {

    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage)throws CommandException;

    public void addPictureURL(int productId, List<Pair<String, Boolean>> images)throws CommandException;
    
    public List<Pair<String, Boolean>> getPicturesWithId(int productId) throws CommandException;
    
    public Pair<String, Boolean> getPrimaryPictureWithId(int productId) throws CommandException;
    
    public void updatePrimaryPicture(int productId, String imageURL) throws CommandException;

    public void deleteImages(List<Pair<String, Boolean>> images) throws CommandException;

    public void deleteAllImages(Product p) throws CommandException;
}
