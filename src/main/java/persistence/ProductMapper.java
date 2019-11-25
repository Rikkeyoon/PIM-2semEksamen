package persistence;

import logic.Product;
import exception.CommandException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.dbutils.DbUtils;

/**
 * @author Nina Lisakowski, Allan, carol
 */
public class ProductMapper implements IProductMapper {

    private static ICategoryMapper cm = new CategoryMapper();

    @Override
    public void create(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String insertSql = "INSERT INTO products "
                    + "(item_number, name, description, category_name, supplier) VALUES"
                    + "(?, ?, ?, ?, ?)";

            pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, product.getItemnumber());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getCategory().getCategoryname());
            pstmt.setString(5, product.getSupplier());

            pstmt.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new CommandException("Could not create product. Try again!");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);

        }
    }

    @Override
    public List<Product> getProductsByName(String names)
            throws CommandException {
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

                int id = result.getInt("Id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, description,
                        cm.getCategory(categoryname), supplier, images));

            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return products;
    }

    @Override
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
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                product = new Product(id, itemnumber, name, description,
                        cm.getCategory(categoryname), supplier, images);

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

    @Override
    public int getHighestProductID() throws CommandException {
        String sql = "SELECT * FROM products ORDER BY ID DESC LIMIT 0, 1";
        int returnInt = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try {

            connection = PersistenceFacadeDB.getConnection();
            pstmt = connection.prepareStatement(sql);
            result = pstmt.executeQuery();

            if (result == null) {

                returnInt = 0;
            } else {
                result.next();
                returnInt = result.getInt("id");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the highest product id");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return returnInt;
    }

    @Override
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

                int id = result.getInt("Id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, description,
                        cm.getCategory(categoryname), supplier, images));

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

    @Override
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
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");

                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);

                products.add(new Product(id, itemnumber, name, description,
                        cm.getCategory(categoryname), supplier, images));
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products" + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return products;
    }

    @Override
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

                String attribute = result.getString("attribute_id");
                String attrValue = result.getString("attribute_value");

                categoryAttributes.putIfAbsent(attribute, attrValue);

                if (product == null) {
                    int itemnumber = result.getInt("Item_number");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String categoryname = result.getString("category_name");
                    String supplier = result.getString("supplier");

                    List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPicturesWithId(id);
                    product = new Product(id, itemnumber, name, description, cm.getCategory(categoryname), 
                            supplier, categoryAttributes, images);
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

    @Override
    public void update(Product product) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String updateSql = "UPDATE products SET name = ?, item_number = ?, description = ?, "
                    + "category_name = ? ,supplier = ? WHERE id = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getItemnumber);
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getCategory().getCategoryname());
            pstmt.setString(5, product.getSupplier());
            pstmt.setInt(6, product.getId());
            
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID");
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
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
            throw new CommandException("Could not find a product with the given ID" + ex);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
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

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new SQLException("No rows updated");
                }
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID" + ex);
        } finally {
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
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
}
