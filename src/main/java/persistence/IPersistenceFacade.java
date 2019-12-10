package persistence;

import exception.CommandException;
import java.util.List;
import javax.servlet.http.Part;
import logic.Category;
import logic.Image;
import logic.Product;

/**
 * The purpose of IPersistenceFacade is to keep low coupling between the logic
 * layer and the persistence layer by being the first object beyond the logic
 * layer to receive and coordinate a system operation The purpose for this
 * facade to be an interface is so it can easily be replaced by a new storage
 * type
 *
 * @author allan, carol
 */
public interface IPersistenceFacade {

    /**
     * Method to get the catalog
     *
     * @return @throws CommandException
     */
    public List<Product> getCatalog() throws CommandException;

    /**
     * Method to get a specific product by its storage id
     *
     * @param id
     * @return Product
     * @throws CommandException
     */
    public Product getProduct(int id) throws CommandException;

    /**
     * Method to get products with the same name or that have names that all
     * contain the String
     *
     * @param name
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByName(String name) throws CommandException;

    /**
     * Method to create a product and store its data
     *
     * @param p Product
     * @throws CommandException
     */
    public int createProduct(Product p) throws CommandException;

    /**
     * Method to update a specific product's data and store the new data
     *
     * @param p Product
     * @throws CommandException
     */
    public void updateProduct(Product p) throws CommandException;

    /**
     * Method to delete images from the storage
     *
     * @param picsToDelete String of image URLs
     * @throws CommandException
     */
    public void deleteImages(String[] picsToDelete) throws CommandException;

    /**
     * Method to add images to a product and store the new images
     *
     * @param p Product
     * @throws CommandException
     */
    public void addImages(Product p) throws CommandException;

    /**
     * Method to delete a product from the storage
     *
     * @param p Product
     * @throws CommandException
     */
    public void deleteProduct(int id) throws CommandException;

    /**
     * Method to get products by a category or part of the category's name
     *
     * @param category
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByCategory(String category)
            throws CommandException;

    /**
     * Method to get a category
     *
     * @param categoryname
     * @return Category
     * @throws CommandException
     */
    public Category getCategory(String categoryname) throws CommandException;

    /**
     * Method to get a product that has category attributes by the product's
     * storage id
     *
     * @param id
     * @return Product
     * @throws CommandException
     */
    public Product getProductWithCategoryAttributes(int id)
            throws CommandException;

    /**
     * Method to create a category and store its data
     *
     * @param c Category
     * @throws CommandException
     */
    public void createCategory(Category c) throws CommandException;

    /**
     * Method to edit a category's data and store the new data
     *
     * @param c Category
     * @throws CommandException
     */
    public void editCategory(Category c) throws CommandException;

    /**
     * Method to get all stored categories
     *
     * @return List of Categories
     * @throws CommandException
     */
    public List<Category> getCategories() throws CommandException;

    /**
     * Method to get all stored tags a specific product has by the product's
     * storage id
     *
     * @param id
     * @return List of Strings
     * @throws CommandException
     */
    public List<String> getTagsForProductWithID(int id) throws CommandException;

    /**
     * A method to create tags for a specific product and store the new tags
     *
     * @param id Product's id
     * @param tags
     * @throws CommandException
     */
    public void createProductTags(int id, List<String> tags) throws CommandException;

    /**
     * Method to upload images to cloudinary to store the images there
     *
     * @param parts
     * @param primaryImage
     * @return List of Pair of String and boolean
     * @throws CommandException
     */
    public List<Image> uploadImagesToCloudinary(List<Part> parts, String primaryImage)
            throws CommandException;

    /**
     * Method to get all stored products that have the same tag, or that all
     * have a tag which contains the String
     *
     * @param tagSearch
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsWithTagSearch(String tagSearch) throws CommandException;

    /**
     * Method to get the unique storage id that the product have
     *
     * @param p Product
     * @return int
     * @throws CommandException
     */
    public int getProductStorageId(Product p) throws CommandException;

    public void updateCategoryAttributename(String oldAttr, String newAttr)
            throws CommandException;

    public void deleteAttributeFromCategory(List<String> removeAttr)
            throws CommandException;

    public void createProductAttributes(Product product) throws CommandException;

    public void updateProductAttributes(Product product) throws CommandException;

    public void deleteTagsForProduct(int id) throws CommandException;

    public void deleteUnusedTags() throws CommandException;

    public void updatePrimaryPicture(int productId, String imageURL) throws CommandException;

    public List<Image> getPicturesForProduct(int id) throws CommandException;

    public void deleteAllImages(int id)throws CommandException;

    public void deleteProductAttributes(int id)throws CommandException;
    /**
     * Method to get products by an item number or part of the item number
     *
     * @param itemNumber
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByItemNumber(int itemNumber) throws CommandException;

    /**
     * Method to get products by a brand or part of the brands's name
     *
     * @param brand
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByBrand(String brand) throws CommandException;

    /**
     * Method to get products by a supplier or part of the suppliers name
     *
     * @param supplier
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsBySupplier(String supplier) throws CommandException;


    public void updateProduct_BulkEdit(Product p, List<String> bulkeditIDs) throws CommandException ;

    /**
     *
     * @param id
     * @throws CommandException
     */
    public void deleteCategory(int id) throws CommandException;
    
    public List<String> getCategoryAttributes(int id) throws CommandException;
    
    public void deleteAttribute(String name) throws CommandException;
    
    public void createEmptyAttribute(int id, List<String> attributes) throws CommandException;
    
    public void updateProductStatus(int id, int status) throws CommandException;

}

