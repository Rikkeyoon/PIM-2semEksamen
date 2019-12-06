package logic;

import java.util.List;

/**
 * The purpose of the Category class is to represent the category which follows
 * the business rules
 *
 * @author Nina, carol
 */
public class Category {

    private int id;
    private String categoryname;
    private List<String> attributes;
    
    /**
     * Default constructor
     * Used when converting from JSON string
     */
    public Category() {
    }

    /**
     * Constructor for Category
     *
     * @param categoryname The category's name
     * @param attributes A list of attributes associated with the category
     */
    public Category(String categoryname, List<String> attributes) {
        this.categoryname = categoryname;
        this.attributes = attributes;
    }

    public Category(int id, String categoryname, List<String> attributes) {
        this.id = id;
        this.categoryname = categoryname;
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * A method for changing the category's name
     *
     * @param categoryname String. The new name for the category
     */
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    /**
     * A method for getting the current name for the category
     *
     * @return categoryname String
     */
    public String getCategoryname() {
        return categoryname;
    }

    /**
     * A method for changing the category's attributes
     *
     * @param attributes List of Strings. The new attributes for the category
     */
    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    /**
     * A method for getting the current attributes for the category
     *
     * @return attributes List of Strings
     */
    public List<String> getAttributes() {
        return attributes;
    }

    
    
    @Override
    public String toString() {
        return "Category{" + "categoryname=" + categoryname + ", attributes=" + attributes + '}';
    }
}
