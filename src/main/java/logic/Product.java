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
    private int itemnumber;
    private String name;
    private String description;
    private Category category;
    private String supplier;
    private Map<String, String> categoryAttributes;
    private List<Pair<String, Boolean>> images;
    public int getItemnumber;

    public Product(int id, int itemnumber, String name, String description, Category category, String supplier) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
    }
    public Product(int id, int itemnumber, String name, String description, Category category, String supplier, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.images = images;
    }


//    public Product(int id, int itemnumber, String name, String description, Category category,
//            Map<String, String> categoryAttributes) {
//        this.id = id;
//        this.itemnumber = itemnumber;  
//        this.name = name;
//        this.description = description;
//        this.category = category;
//        this.categoryAttributes = categoryAttributes;
//    }

    //Master Contructor
    public Product(int id, int itemnumber, String name, String description, Category categoryname, String supplier, 
            Map<String, String> categoryAttributes, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.description = description;
        this.category = categoryname;
        this.supplier = supplier;
        this.categoryAttributes = categoryAttributes;
        this.images = images;
    }

    public List<Pair<String, Boolean>> getImages() {
        return images;
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

    public void setImages(List<Pair<String, Boolean>> images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }
    
    public String getSupplier(){
        return supplier;
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
        return "Product with id: " + id + itemnumber + ", name: " + name + ", description: "
                + description + ", category:" + category.getCategoryname() + ", supplier" + supplier;
    }

}
