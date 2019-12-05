package logic;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The purpose of the Product class is to represent the product which follows
 * the business rules
 *
 * @author Nina, carol
 */
public class Product {

    private int id;
    private int itemnumber;
    private String name;
    private String brand;
    private String description;
    private Category category;
    private String supplier;
    private String SEOText;
    private int status;
    private Map<String, String> categoryAttributes;
    private List<Pair<String, Boolean>> images;
    private List<String> tags;

    /**
     * Constructor for Product without category attributes
     *
     * @param id The product's database id
     * @param itemnumber The product's given item number
     * @param name The product's name
     * @param brand The product's brand
     * @param description The product's description
     * @param category The name of the product's category
     * @param supplier The product's supplier
     * @param SEOText The product's Search Engine Optimization text
     * @param status The product's status
     * @param images Urls which contain the images associated with the product
     */
    public Product(int id, int itemnumber, String name, String brand, String description,
            Category category, String supplier, String SEOText,
            int status, List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.SEOText = SEOText;
        this.status = status;
        this.images = images;
    }

    /**
     * Constructor for Product with all data
     *
     * @param id The product's database id
     * @param itemnumber The product's given item number
     * @param name The product's name
     * @param brand The product's brand
     * @param description The product's description
     * @param categoryname The name of the product's category
     * @param supplier The product's supplier
     * @param SEOText The product's Search Engine Optimization text
     * @param status The product's status
     * @param categoryAttributes The product's category's attributes mapped
     * together with the attributes values, which are specific to this product
     * @param images Urls which contain the images associated with the product
     */
    public Product(int id, int itemnumber, String name, String brand,
            String description, Category categoryname, String supplier,
            String SEOText, int status, Map<String, String> categoryAttributes,
            List<Pair<String, Boolean>> images) {
        this.id = id;
        this.itemnumber = itemnumber;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = categoryname;
        this.supplier = supplier;
        this.SEOText = SEOText;
        this.status = status;
        this.categoryAttributes = categoryAttributes;
        this.images = images;
    }

    /**
     * A method for getting the product's id in the database
     *
     * @return id int
     */
    public int getId() {
        return id;
    }

    /**
     * A method for getting the product's item number
     *
     * @return itemnumber int
     */
    public int getItemnumber() {
        return itemnumber;
    }

    /**
     * A method for changing the product's item number
     *
     * @param itemnumber The new item number for the product
     */
    public void setItemnumber(int itemnumber) {
        this.itemnumber = itemnumber;
    }

    /**
     * A method for getting the product's name
     *
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * A method for changing the product's name
     *
     * @param name The new name for the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A method for getting the product's brand
     *
     * @return brand String
     */
    public String getBrand() {
        return brand;
    }

    /**
     * A method for changing the product's brand
     *
     * @param brand The new brand for the product
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * A method for getting the product's description
     *
     * @return description String
     */
    public String getDescription() {
        return description;
    }

    /**
     * A method for changing the product's description
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A method for getting the product's category
     *
     * @return category Category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * A method for changing the product's category
     *
     * @param category The new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * A method for getting the product's supplier
     *
     * @return supplier String
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * A method for changing the product's supplier
     *
     * @param supplier The new supplier
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * A method for getting the product's Search Engine Optimization text
     *
     * @return SEOText String
     */
    public String getSEOText() {
        return SEOText;
    }

    /**
     * A method for changing the product's Search Engine Optimization text
     *
     * @param SEOText The new SEO text
     */
    public void setSEOText(String SEOText) {
        this.SEOText = SEOText;
    }

    /**
     * A method for getting the product's status
     *
     * @return status int
     */
    public int getStatus() {
        return status;
    }

    /**
     * A method for changing the product's status
     *
     * @param status The new status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * A method for getting the product's category attributes
     *
     * @return categoryAttributes Map String, String
     */
    public Map<String, String> getCategoryAttributes() {
        return categoryAttributes;
    }

    /**
     * A method for changing the product's category attributes
     *
     * @param categoryAttributes The new category attributes
     */
    public void setCategoryAttributes(Map<String, String> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }

    /**
     * A method for getting the Uniform Resource Locators (URLs) for the images
     * associated with the product
     *
     * @return images List of Pair String, Boolean
     */
    public List<Pair<String, Boolean>> getImages() {
        return images;
    }

    /**
     * A method for changing the Uniform Resource Locators (URLs) for the images
     * associated with the product
     *
     * @param images The new URLs for the images
     */
    public void setImages(List<Pair<String, Boolean>> images) {
        this.images = images;
    }

    /**
     * A method for removing some images from the product
     *
     * @param picsToDelete String array consisting of URLs for the images that
     * are to be removed
     */
    public void removeImages(String[] picsToDelete) {
        List<Pair<String, Boolean>> newImages = new ArrayList<>();
        for (Pair<String, Boolean> image : this.images) {
            for (String string : picsToDelete) {
                if (!image.getKey().equalsIgnoreCase(string)) {
                    newImages.add(image);
                }
            }
        }
        setImages(newImages);
    }

    /**
     * A method for getting the Uniform Resource Locator (URL) for the primary
     * image associated with the product, which is the picture for the catalog
     *
     * @return primaryImage String consisting of the image's URL
     */
    public String getPrimaryImage() {
        String result = "";
        for (Pair<String, Boolean> image : images) {
            if (image.getValue()) {
                result = image.getKey();
            }
        }
        return result;
    }

    /**
     * A method for changing the primary image associated with the product,
     * which is the picture for the catalog
     *
     * @param imageURL The new primary image's URL
     */
    public void setPrimaryImage(String imageURL) {
        for (Pair<String, Boolean> image : images) {
            if (image.getKey().equalsIgnoreCase(imageURL)) {
                image.setValue(true);
            } else {
                image.setValue(false);
            }
        }
    }

    /**
     * A method for getting the product's tags
     *
     * @return tags List of Strings
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * A method for getting the product's tags as a String Object
     *
     * @return tagAsString String
     */
    public String getTagsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String s : tags) {
            sb.append(s).append(", ");
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 2);
    }

    /**
     * A method for changing the product's tags
     *
     * @param tags The new tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * A method for comparing two products to eachother by comparing the
     * product's database id
     *
     * @param product
     * @return boolean
     */

    /**
     * A method for converting the Product class to a String, which contains the
     * various data that the Product class has
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Product with id: " + id + itemnumber + ", name: " + name + ", brand" + brand + ", description: "
                + description + ", category:" + category.getCategoryname() + ", supplier" + supplier + ", SEO text" + SEOText + status;
    }

}
