package logic;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author carol
 */
public class TemporaryProduct {

    private int id;
    private int itemnumber;
    private String name;
    private String description;
    private String categoryname;
    private Map<String, String> categoryAtrributes;
    private List<Pair<String, Boolean>> images;
    

    public TemporaryProduct(int id, int itemnumber, String name, String description,
            String categoryname, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
        this.images = images;
    }

    public TemporaryProduct(int id, int itemnumber, String name, String description, String categoryname,
            Map<String, String> categoryAttributes, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
        this.categoryAtrributes = categoryAttributes;
        this.images = images;
    }

    public int getId() {
        return id;
    }
    
    public int getItemnumber() {
        return itemnumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Map<String, String> getCategoryAttributes() {
        return categoryAtrributes;
    }

    public void setCategoryAtrributes(Map<String, String> categoryAtrributes) {
        this.categoryAtrributes = categoryAtrributes;
    }

    public List<Pair<String, Boolean>> getImages() {
        return images;
    }

    public void setImages(List<Pair<String, Boolean>> images) {
        this.images = images;
    }
    
}
