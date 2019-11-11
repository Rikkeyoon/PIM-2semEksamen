/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exception.CommandException;
import java.sql.Connection;
import java.util.List;
import logic.Product;

/**
 *
 * @author allan
 */
public class DataSourceController implements IDataSourceController {

    private static DBcon dbcon = DBcon.getInstance();
    private static Boolean testMode;
    private IProductMapper pm = new ProductMapper();

    public DataSourceController(boolean testMode) {
        this.testMode = testMode;
    }

    public static Connection getConnection() throws CommandException {
        return dbcon.getConnection(testMode);
    }

    @Override
    public void createProduct(Product p) throws CommandException{
        pm.create(p);
    }

    @Override
    public Product getProduct(String name) throws CommandException {
        return pm.getProduct(name);

    }

    @Override
    public List<Product> getProducts() throws CommandException {
        return pm.getAllProducts();

    }

    @Override
    public void updateProduct() throws CommandException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
