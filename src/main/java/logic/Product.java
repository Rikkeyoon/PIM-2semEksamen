package logic;

import java.util.Map;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

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
    private List<Pair<String, Boolean>> images;
    private List<String> tags;

    public Product(int id, String name, String description, Category category,
            List<Pair<String, Boolean>> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.images = images;
    }

    public Product(int id, String name, String description, Category categoryname,
            Map<String, String> categoryAttributes, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = categoryname;
        this.categoryAttributes = categoryAttributes;
        this.images = images;
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

    public List<Pair<String, Boolean>> getImages() {
        return images;
    }

    public void setImages(List<Pair<String, Boolean>> images) {
        this.images = images;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getTagsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String s : tags) {
            sb.append(s).append(", ");
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 2);
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean compareTo(Product product) {
        return this.id == product.getId();
    }

    @Override
    public String toString() {
        return "Product with id: " + id + ", name: " + name + ", description: "
                + description + ", category:" + category.getCategoryname();
    }

}
