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
                    + "(id, name, description, category_name) VALUES"
                    + "(?, ?, ?, ?)";

            pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getCategory().getCategoryname());

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
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);
                String categoryname = result.getString(4);

                tempProducts.add(new TemporaryProduct(id, name, description,
                        categoryname, null));
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
                String name = result.getString(2);
                String description = result.getString(3);
                String categoryname = result.getString(4);

                tempProduct = new TemporaryProduct(id, name, description,
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
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);
                String category = result.getString(4);

                tempProducts.add(new TemporaryProduct(id, name, description,
                        category, null));
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
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);
                String categoryname = result.getString(4);
                List<Pair<String, Boolean>> images = PersistenceFacadeDB.getPrimaryImageWithId(id);
                tempProducts.add(new TemporaryProduct(id, name, description,
                        categoryname, images));

            }

        } catch (SQLException | NullPointerException ex) {
            throw new CommandException("Could not find any products");
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
                String name = result.getString(2);
                String description = result.getString(3);
                String categoryname = result.getString(4);
                String attribute = result.getString(5);
                String attrValue = result.getString(6);

                categoryAttributes.putIfAbsent(attribute, attrValue);

                if (product != null) {
                    Map<String, String> newCategoryAttr = product.getCategoryAttributes();
                    newCategoryAttr.putIfAbsent(attribute, attrValue);
                    product.setCategoryAtrributes(newCategoryAttr);
                } else {
                    product = new TemporaryProduct(id, name, description, categoryname,
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
            pstmt.setInt(4, product.getId());
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
