package logic;

/**
 * @author Nina
 */
public class Category {
    
    private String categoryname;
    
    public Category(String categoryname) {
        this.categoryname = categoryname;
    }
    
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    } 
    
    public String getCategoryname() {
        return categoryname;
    }
    
}
