package logic;

import java.util.Map;

/**
 *
 * @author Nina
 */
public class Product {

    private int id;
    private String name;
    private String description;
    private Category category;
    private Map<String, String> categoryAttributes;

    public Product(int id, String name, String description, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Product(int id, String name, String description, Category category,
            Map<String, String> categoryAttributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.categoryAttributes = categoryAttributes;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<String, String> getCategoryAttributes() {
        return categoryAttributes;
    }

    public void setCategoryAttributes(Map<String, String> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }

    public boolean compareTo(Product product) {
        return this.id == product.getId();
    }

    public void setAttributeValues(String[] attributeValues) {
        //FIXME!!!
        for (String key : categoryAttributes.keySet()) {
            for (String attributeValue : attributeValues) {
                categoryAttributes.replace(key, attributeValue);
            }
        }
    }

    @Override
    public String toString() {
        return "Product with id: " + id + ", name: " + name + ", description: "
                + description + ", category:" + category.getCategoryname();
    }

}
