package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryReportDAO {

    public void viewCategoryReport() throws SQLException {

        String sql =
            "SELECT c.category_name, " +
            "COUNT(p.product_id) AS total_products, " +
            "COALESCE(SUM(p.quantity), 0) AS total_quantity, " +
            "COALESCE(SUM(p.price * p.quantity), 0) AS stock_value " +
            "FROM categories c " +
            "LEFT JOIN products p " +
            "ON c.category_id = p.category_id " +
            "GROUP BY c.category_id, c.category_name " +
            "ORDER BY c.category_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- CATEGORY-WISE INVENTORY REPORT ---");

            while (rs.next()) {
                System.out.println("--------------------------------------");
                System.out.println(
                    "Category: " + rs.getString("category_name"));
                System.out.println(
                    "Total Products: " + rs.getInt("total_products"));
                System.out.println(
                    "Total Quantity: " + rs.getInt("total_quantity"));
                System.out.println(
                    "Stock Value: " + rs.getBigDecimal("stock_value"));
            }

            System.out.println("--------------------------------------");
        }
    }
}