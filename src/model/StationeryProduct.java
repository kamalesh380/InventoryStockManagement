package model;

import java.math.BigDecimal;

public class StationeryProduct extends Product {

    public StationeryProduct(int productId, String productName,
                             int categoryId, BigDecimal price,
                             int quantity, int minStockLevel) {
        super(productId, productName, categoryId, price, quantity, minStockLevel);
    }

    @Override
    public String getProductType() {
        return "Stationery";
    }
}