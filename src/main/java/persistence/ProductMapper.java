package persistence;

import logic.Product;
import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.dbutils.DbUtils;

/**
 * The purpose of the ProductMapper is to save the products in the database and
 * to edit the stored data when necessary
 *
 * @author Nina, Allan, carol
 */
public class ProductMapper {

    private static CategoryMapper cm = new CategoryMapper();

    /**
     * Method to insert a new product's data into the database
     *
     * @param product
     * @throws CommandException
     */
    public void create(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String insertSql = "INSERT INTO products "
                    + "(item_number, name, brand, description, category_name, "
                    + "supplier, seo_text, status) VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, product.getItemnumber());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getBrand());
            pstmt.setString(4, product.getDescription());
            pstmt.setString(5, product.getCategory().getCategoryname());
            pstmt.setString(6, product.getSupplier());
            pstmt.setString(7, product.getSEOText());
            pstmt.setInt(8, product.getStatus());

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new CommandException("Could not create product. Try again!" + e);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);

        }
    }

    /**
     * Method to get multiple products from the database that share the same
     * name or have the String as part of their names
     *
     * @param names
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByName(String names) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Product> products = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE name LIKE ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + names + '%');

            result = pstmt.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String brand = result.getString("brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images));

            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return products;
    }

    /**
     * Method to get a specific product by its unique database id
     *
     * @param id
     * @return Product
     * @throws CommandException
     */
    public Product getProduct(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        Product product = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE id = ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);

            result = pstmt.executeQuery();

            while (result.next()) {
                int itemnumber = result.getInt("item_number");
                String name = result.getString("name");
                String brand = result.getString("brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                product = new Product(id, itemnumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images);
            }

            if (product == null) {
                throw new SQLException();
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that id");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return product;
    }

    /**
     * Method to get a product's unique database id by the product's other data
     *
     * @param p Product
     * @return int
     * @throws CommandException
     */
    public int getProductDBId(Product p) throws CommandException {
        int returnInt = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try {
            String sql = "SELECT id FROM products WHERE item_number =? AND name =? "
                    + "AND brand=? AND description=? AND category_name=? "
                    + "AND supplier=? AND seo_text=?";
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, p.getItemnumber());
            pstmt.setString(2, p.getName());
            pstmt.setString(3, p.getBrand());
            pstmt.setString(4, p.getDescription());
            pstmt.setString(5, p.getCategory().getCategoryname());
            pstmt.setString(6, p.getSupplier());
            pstmt.setString(7, p.getSEOText());
            result = pstmt.executeQuery();

            if (result == null) {
                returnInt = 0;
            } else {
                result.next();
                returnInt = result.getInt("id");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the products database id");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return returnInt;
    }

    /**
     * Method to get all products that share a category or share the String as
     * part of their categories' names
     *
     * @param categorynames
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByCategory(String categorynames)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Product> products = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE category_name LIKE ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + categorynames + '%');

            result = pstmt.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                int itemnumber = result.getInt("item_number");
                String name = result.getString("name");
                String brand = result.getString("brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images));
            }

            if (products.size() < 1) {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the products with the chosen category");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return products;
    }

    /**
     * Method to get all products stored in the database
     *
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getAllProducts() throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Product> products = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products";
            pstmt = connection.prepareStatement(selectSql);

            result = pstmt.executeQuery(selectSql);

            while (result.next()) {

                int id = result.getInt("id");
                int itemnumber = result.getInt("item_number");
                String name = result.getString("name");
                String brand = result.getString("Brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return products;
    }

    /**
     * Method to get all products that share a tag or share the String as part
     * of their tags' names
     *
     * @param tagSearch
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsWithTagSearch(String tagSearch) throws CommandException {
        ArrayList<Product> products = new ArrayList<>();
        for (Integer i : PersistenceFacadeDB.getProductsIDFromTagNameSearch(tagSearch)) {
            products.add(getProduct(i));
        }
        
        return products;
    }

    /**
     * Method to get a product and its category attributes by the product's
     * unique database id
     *
     * @param id
     * @return Product
     * @throws CommandException
     */
    public Product getProductWithCategoryAttributes(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;

        Product product = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products_with_categories_and_attributes "
                    + "WHERE id = ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);

            result = pstmt.executeQuery();

            while (result.next()) {
                Map<String, String> categoryAttributes = new HashMap<>();
                String attribute = result.getString("attribute_name");
                String attrValue = result.getString("attribute_value");

                categoryAttributes.putIfAbsent(attribute, attrValue);

                if (product == null) {
                    int itemnumber = result.getInt("item_number");
                    String name = result.getString("name");
                    String brand = result.getString("brand");
                    String description = result.getString("description");
                    String categoryname = result.getString("category_name");
                    String supplier = result.getString("supplier");
                    String seotext = result.getString("seo_text");
                    int status = result.getInt("status");

                    List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);
                    product = new Product(id, itemnumber, name, brand, description,
                            cm.getCategory(categoryname), supplier, seotext,
                            status, categoryAttributes, images);
                } else {
                    Map<String, String> newCategoryAttr = product.getCategoryAttributes();
                    newCategoryAttr.putIfAbsent(attribute, attrValue);
                    product.setCategoryAttributes(newCategoryAttr);
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return product;
    }

    /**
     * Method to update the stored data for a specific product
     *
     * @param product
     * @throws CommandException
     */
    public void update(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String updateSql = "UPDATE products SET item_number = ?, name = ?, "
                    + "brand = ?, description = ?, category_name = ?,"
                    + "supplier = ?, seo_text = ?, status = ? WHERE id = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setInt(1, product.getItemnumber());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getBrand());
            pstmt.setString(4, product.getDescription());
            pstmt.setString(5, product.getCategory().getCategoryname());
            pstmt.setString(6, product.getSupplier());
            pstmt.setString(7, product.getSEOText());
            pstmt.setInt(8, product.getStatus());
            pstmt.setInt(9, product.getId());

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to update the stored data for a specific product's attributes
     *
     * @param product
     * @throws CommandException
     */
    public void updateAttributes(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            for (String key : product.getCategoryAttributes().keySet()) {
                String updateSql = "UPDATE attribute_values SET attribute_value = ? "
                        + "WHERE product_id = ? AND attribute_id = "
                        + "(SELECT id FROM attributes WHERE attribute_name LIKE ?)";
                pstmt = connection.prepareStatement(updateSql);
                pstmt.setString(1, product.getCategoryAttributes().get(key));
                pstmt.setInt(2, product.getId());
                pstmt.setString(3, key);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated == 0) {
                    createAttributes(product);
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to insert new attribute values for a specific product's based on
     * the product's category
     *
     * @param product
     * @throws CommandException
     */
    public void createAttributes(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            for (String key : product.getCategoryAttributes().keySet()) {
                String updateSql = "INSERT INTO attribute_values VALUES "
                        + "((SELECT id FROM attributes WHERE attribute_name LIKE ?), "
                        + "?, ?)";
                pstmt = connection.prepareStatement(updateSql);
                pstmt.setString(1, key);
                pstmt.setInt(2, product.getId());
                pstmt.setString(3, product.getCategoryAttributes().get(key));

                pstmt.executeUpdate();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    /**
     * Method to delete all data stored in the database about a specific product
     *
     * @param product
     * @throws CommandException
     */
    public void delete(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String deleteSql = "DELETE FROM products WHERE id = ?";
            pstmt = connection.prepareStatement(deleteSql);
            pstmt.setInt(1, product.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the product to be deleted");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    public void deleteProductAttribute(int i) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String insertSql = "DELETE FROM attribute_values WHERE attribute_id = ?";
        try {
            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(insertSql);

            pstmt.setInt(1, i);

            pstmt.executeUpdate();

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not delete attribute from product");
        } finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(pstmt);
        }
    }

    /**
     * Method to get all products that share a item number or share a part of
     * the item number
     *
     * @param itemNumber
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByItemNumber(int itemNumber) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Product> products = new ArrayList();

        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE item_number = ?;";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, itemNumber);

            result = pstmt.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String brand = result.getString("brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemNumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images));
            }

            if (products.size() < 1) {
                throw new SQLException();
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that item number");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return products;
    }

    /**
     * Method to get all products that share a brand or share the String as part
     * of their brands names
     *
     * @param brandname
     * @return List of Products
     * @throws CommandException
     */
    public List<Product> getProductsByBrand(String brandname) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Product> products = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE brand LIKE ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + brandname + '%');

            result = pstmt.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String brand = result.getString("brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images));

            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products with the chosen brand");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return products;
    }
    
    /**
     * Method to get all products that share a supplier or share the String as part
     * of their suppliers names
     *
     * @param suppliername
     * @return List of Products
     * @throws CommandException
     */
    List<Product> getProductsBySupplier(String suppliername) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Product> products = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE supplier LIKE ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + suppliername + '%');

            result = pstmt.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String brand = result.getString("brand");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String seotext = result.getString("seo_text");
                int status = result.getInt("status");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, brand, description,
                        cm.getCategory(categoryname), supplier, seotext, status, images));

            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products with the chosen supplier");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return products;
    }

}
