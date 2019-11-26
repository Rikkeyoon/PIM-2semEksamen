package logic;

import java.util.List;

/**
 * @author Nina
 */
public class Category {
    
    private String categoryname;
    private List<String> attributes;
    
    public Category(String categoryname, List<String> attributes) {
        this.categoryname = categoryname;
        this.attributes = attributes;
    }
    
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    } 
    
    public String getCategoryname() {
        return categoryname;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Category categoryname : " + categoryname + " with attribute(s) : " + attributes;
    }
}
