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
    private String brand;
    private String description;
    private Category category;
    private String supplier;
    private String seatext;
    private int status;
    private Map<String, String> categoryAttributes;
    private List<Pair<String, Boolean>> images;
    public int getItemnumber;

    public Product(int id, int itemnumber, String name, String brand, String description, 
            Category category, String supplier, String seatext, int status, List<Pair<String, Boolean>> images) {

        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.seatext = seatext;
        this.status = status;
        this.images = images;
    }

    //Master Contructor
    public Product(int id, int itemnumber, String name, String brand, String description, Category categoryname, 
            String supplier, String seatext, int status, Map<String, String> categoryAttributes, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = categoryname;
        this.supplier = supplier;
        this.seatext = seatext;
        this.status = status;
        this.categoryAttributes = categoryAttributes;
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
    
    public String getBrand() {
        return brand;
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
    
    public String getSupplier(){
        return supplier;
    }
    
    public String getSeaText(){
        return seatext;
    }
    
    public int getStatus(){
        return status;
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

    public boolean compareTo(Product product) {
        return this.id == product.getId();
    }

    @Override
    public String toString() {
        return "Product with id: " + id + itemnumber + ", name: " + name + ", brand" + brand + ", description: "
                + description + ", category:" + category.getCategoryname() + ", supplier" + supplier + ", sea text" + seatext + status;
    }

}
