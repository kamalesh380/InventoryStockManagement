package dao;

import database.DBConnection;
import model.*;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products " +
            "(product_name, category_id, price, quantity, min_stock_level) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setBigDecimal(3, p.getPrice());
            ps.setInt(4, p.getQuantity());
            ps.setInt(5, p.getMinStockLevel());

            ps.executeUpdate();
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT p.*, c.category_name FROM products p " +
                     "JOIN categories c ON p.category_id = c.category_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int categoryId = rs.getInt("category_id");

                Product p = createProduct(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    categoryId,
                    rs.getBigDecimal("price"),
                    rs.getInt("quantity"),
                    rs.getInt("min_stock_level")
                );

                products.add(p);
            }
        }

        return products;
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createProduct(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("category_id"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getInt("min_stock_level")
                    );
                }
            }
        }

        return null;
    }

    public boolean updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET product_name = ?, category_id = ?, " +
                     "price = ?, quantity = ?, min_stock_level = ? " +
                     "WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setBigDecimal(3, p.getPrice());
            ps.setInt(4, p.getQuantity());
            ps.setInt(5, p.getMinStockLevel());
            ps.setInt(6, p.getProductId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Product> getLowStockProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE quantity <= min_stock_level";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(createProduct(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("category_id"),
                    rs.getBigDecimal("price"),
                    rs.getInt("quantity"),
                    rs.getInt("min_stock_level")
                ));
            }
        }

        return products;
    }

    public BigDecimal getTotalStockValue() throws SQLException {
        String sql = "SELECT COALESCE(SUM(price * quantity), 0) AS total FROM products";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getBigDecimal("total");
        }
    }

    private Product createProduct(int id, String name, int categoryId,
                              BigDecimal price, int quantity, int minStock) {

    return switch (categoryId) {
        case 1 -> new ElectronicsProduct(
                id, name, categoryId, price, quantity, minStock);
        case 2 -> new StationeryProduct(
                id, name, categoryId, price, quantity, minStock);
        case 3 -> new FurnitureProduct(
                id, name, categoryId, price, quantity, minStock);
        default -> new ElectronicsProduct(
                id, name, categoryId, price, quantity, minStock);
    };
}
}