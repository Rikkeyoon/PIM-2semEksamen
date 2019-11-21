package logic;

import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Nina
 */
public class Product {

    private int id;
    private String name;
    private String description;
    private String categoryname;
    private List<Pair<String, Boolean>> images;

    public Product(int id, String name, String description, String categoryname) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
    }

    public Product(int id, String name, String description, String categoryname, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
        this.images = images;
    }

    public List<Pair<String, Boolean>> getImages() {
        return images;
    }
    
    public int getId() {
        return id;
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

    public void setImages(List<Pair<String, Boolean>> images) {
        this.images = images;
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

    public boolean compareTo(Product product) {
        return this.id == product.getId();
    }

    @Override
    public String toString() {
        return "Product with id: " + id + ", name: " + name + ", description: " + description + ", categoryname:" + categoryname;
    }

}
