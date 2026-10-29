package model;

import java.math.BigDecimal;

public class ElectronicsProduct extends Product {

    public ElectronicsProduct(int productId, String productName,
                              int categoryId, BigDecimal price,
                              int quantity, int minStockLevel) {
        super(productId, productName, categoryId, price, quantity, minStockLevel);
    }

    @Override
    public String getProductType() {
        return "Electronics";
    }
}