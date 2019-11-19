package logic;

import java.util.Map;

/**
 *
 * @author carol
 */
public class TemporaryProduct {
    
    private int id;
    private String name;
    private String description;
    private String categoryname;
    private Map<String, String> categoryAtrributes;

    public TemporaryProduct(int id, String name, String description, 
            String categoryname) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
    }

    public TemporaryProduct(int id, String name, String description, 
            String categoryname, Map<String, String> categoryAttributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
        this.categoryAtrributes = categoryAttributes;
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
}
