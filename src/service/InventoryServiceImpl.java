package service;

import dao.ProductDAO;
import model.Product;
import exception.ProductNotFoundException;
import exception.InvalidProductException;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

public class InventoryServiceImpl implements InventoryService {

    private final ProductDAO dao = new ProductDAO();

    @Override
    public void addProduct(Product product)
            throws SQLException, InvalidProductException {

        validateProduct(product);
        dao.addProduct(product);
    }

    @Override
    public List<Product> viewProducts() throws SQLException {
        return dao.getAllProducts();
    }

    @Override
    public Product searchProduct(int id)
            throws SQLException, ProductNotFoundException {

        Product product = dao.getProductById(id);

        if (product == null) {
            throw new ProductNotFoundException("Product ID " + id + " not found.");
        }

        return product;
    }

    @Override
    public void updateProduct(Product product)
            throws SQLException, ProductNotFoundException, InvalidProductException {

        validateProduct(product);

        if (dao.getProductById(product.getProductId()) == null) {
            throw new ProductNotFoundException(
                "Product ID " + product.getProductId() + " not found."
            );
        }

        dao.updateProduct(product);
    }

    @Override
    public void deleteProduct(int id)
            throws SQLException, ProductNotFoundException {

        if (!dao.deleteProduct(id)) {
            throw new ProductNotFoundException("Product ID " + id + " not found.");
        }
    }

    @Override
    public List<Product> viewLowStockProducts() throws SQLException {
        return dao.getLowStockProducts();
    }

    @Override
    public BigDecimal calculateStockValue() throws SQLException {
        return dao.getTotalStockValue();
    }

    private void validateProduct(Product product)
            throws InvalidProductException {

        if (product == null) {
            throw new InvalidProductException("Product cannot be null.");
        }

        if (product.getProductName() == null ||
            product.getProductName().trim().isEmpty()) {
            throw new InvalidProductException("Product name is required.");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0 ||
            product.getQuantity() < 0 ||
            product.getMinStockLevel() < 0 ||
            product.getCategoryId() <= 0) {

            throw new InvalidProductException("Invalid product details.");
        }
    }
}