package persistence;

import exception.CommandException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import javax.servlet.http.Part;

/**
 *
 * @author allan
 */
public interface IImageMapper {

    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage)throws CommandException;

    public void addPictureURL(int id, List<Pair<String, Boolean>> images)throws CommandException;
    
    public List<Pair<String, Boolean>> getPicturesWithId(int id) throws CommandException;
    
    public List<Pair<String, Boolean>> getPrimaryPictureWithId(int id) throws CommandException;

    public void removePictureFromCloudinary(String URL) throws CommandException;
}
