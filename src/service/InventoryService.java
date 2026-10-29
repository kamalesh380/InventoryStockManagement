package service;

import model.Product;
import exception.ProductNotFoundException;
import exception.InvalidProductException;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

public interface InventoryService {

    void addProduct(Product product)
        throws SQLException, InvalidProductException;

    List<Product> viewProducts() throws SQLException;

    Product searchProduct(int id)
        throws SQLException, ProductNotFoundException;

    void updateProduct(Product product)
        throws SQLException, ProductNotFoundException, InvalidProductException;

    void deleteProduct(int id)
        throws SQLException, ProductNotFoundException;

    List<Product> viewLowStockProducts() throws SQLException;

    BigDecimal calculateStockValue() throws SQLException;
}