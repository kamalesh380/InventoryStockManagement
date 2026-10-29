package model;

import java.math.BigDecimal;

public abstract class Product {

    private int productId;
    private String productName;
    private int categoryId;
    private BigDecimal price;
    private int quantity;
    private int minStockLevel;

    public Product(int productId, String productName, int categoryId,
                   BigDecimal price, int quantity, int minStockLevel) {
        this.productId = productId;
        setProductName(productName);
        setCategoryId(categoryId);
        setPrice(price);
        setQuantity(quantity);
        setMinStockLevel(minStockLevel);
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        this.productName = productName.trim();
    }

    public void setCategoryId(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID.");
        }
        this.categoryId = categoryId;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public void setMinStockLevel(int minStockLevel) {
        if (minStockLevel < 0) {
            throw new IllegalArgumentException("Minimum stock cannot be negative.");
        }
        this.minStockLevel = minStockLevel;
    }

    public abstract String getProductType();

    public boolean isLowStock() {
        return quantity <= minStockLevel;
    }

    public BigDecimal getStockValue() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Name: %s | Category: %d | Type: %s | Price: %s | Quantity: %d",
            productId, productName, categoryId, getProductType(), price, quantity
        );
    }
}