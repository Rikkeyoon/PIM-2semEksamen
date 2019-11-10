package logic;

/**
 *
 * @author Nina Lisakowski
 */
public class Product {
    
    private int id;
    private String name;
    private String description;
    private String categoryname;

    public Product(int id, String name, String description, String categoryname) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryname = categoryname;
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

    @Override
    public String toString() {
        return "Product with id: " + id + ", name: " + name + ", description: " + description + ", categoryname:" + categoryname;
    }
    
}
