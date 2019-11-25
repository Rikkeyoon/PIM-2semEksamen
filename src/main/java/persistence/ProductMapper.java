package persistence;

import logic.TemporaryProduct;
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
    public List<TemporaryProduct> getProductsByName(String names) 
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<TemporaryProduct> tempProducts = new ArrayList();
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
                String category_name = result.getString("category_name");
                String supplier = result.getString("supplier");

                tempProducts.add(new TemporaryProduct(id, itemnumber, name, description, supplier, 
                        category_name, null));
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that name");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }

        return tempProducts;
    }

    @Override
    public TemporaryProduct getProduct(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        TemporaryProduct tempProduct = null;
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

                tempProduct = new TemporaryProduct(id, itemnumber, name, description, supplier, 
                        categoryname, null);
            }

            if (tempProduct == null) {
                throw new SQLException();
            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any product with that id");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return tempProduct;
    }

@Override
    public int getHighestProductID() throws CommandException {
        String sql = "SELECT * FROM products ORDER BY ID DESC LIMIT 0, 1";
        int returnInt = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        TemporaryProduct tempProduct = null;
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
    public List<TemporaryProduct> getProductsByCategory(String categoryname)
            throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<TemporaryProduct> tempProducts = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products WHERE category_name LIKE ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, '%' + categoryname + '%');

            result = pstmt.executeQuery();

            while (result.next()) {
                int id = result.getInt("Id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String description = result.getString("description");
                String category_name = result.getString("category_name");
                String supplier = result.getString("supplier");

                tempProducts.add(new TemporaryProduct(id, itemnumber, name, description,
                        category_name, supplier, null));
            }

            if (tempProducts.size() < 1) {
                throw new SQLException();
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find the products with the chosen category");
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return tempProducts;
    }

    @Override
    public List<TemporaryProduct> getAllProducts() throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<TemporaryProduct> tempProducts = new ArrayList();
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products";
            pstmt = connection.prepareStatement(selectSql);

            result = pstmt.executeQuery(selectSql);

            while (result.next()) {
                int id = result.getInt("Id");
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                
                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPrimaryImageWithId(id);
                tempProducts.add(new TemporaryProduct(id, itemnumber, name, description,
                        categoryname, supplier, images));

            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products" + ex.getMessage());
        } finally {
            DbUtils.closeQuietly(connection, pstmt, result);
        }
        return tempProducts;
    }

    @Override
    public TemporaryProduct getProductWithCategoryAttributes(int id) throws CommandException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;

        TemporaryProduct product = null;
        try {
            connection = PersistenceFacadeDB.getConnection();
            String selectSql = "SELECT * FROM products_with_categories_and_attributes "
                    + "WHERE id = ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);

            result = pstmt.executeQuery();

            while (result.next()) {
                Map<String, String> categoryAttributes = new HashMap<>();
                int itemnumber = result.getInt("Item_number");
                String name = result.getString("name");
                String description = result.getString("description");
                String categoryname = result.getString("category_name");
                String supplier = result.getString("supplier");
                String attribute = result.getString("attribute_id");
                String attrValue = result.getString("attribute_value");

                categoryAttributes.putIfAbsent(attribute, attrValue);

                if (product != null) {
                    Map<String, String> newCategoryAttr = product.getCategoryAttributes();
                    newCategoryAttr.putIfAbsent(attribute, attrValue);
                    product.setCategoryAtrributes(newCategoryAttr);
                } else {
                    product = new TemporaryProduct(id, itemnumber, name, description, categoryname, supplier,
                            categoryAttributes, null);
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
            String updateSql = "UPDATE products SET name = ?, description = ?, "
                    + "category_name = ? WHERE id = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setString(3, product.getCategory().getCategoryname());
            pstmt.setString(4, product.getSupplier());
            pstmt.setInt(5, product.getId());
            pstmt.setInt(6, product.getItemnumber);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated");
            }
        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find a product with the given ID" + ex);
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
            throw new CommandException("Could not find a product with the given ID");
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
            throw new CommandException("Could not find a product with the given ID");
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
