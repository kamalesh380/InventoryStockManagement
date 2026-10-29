
package dao;

import database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class StockTransactionDAO {

    public void updateStock(int productId, String type, int quantity)
            throws SQLException {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }

        String selectSQL =
                "SELECT quantity FROM products WHERE product_id = ? FOR UPDATE";

        String updateSQL =
                "UPDATE products SET quantity = ? WHERE product_id = ?";

        String transactionSQL =
                "INSERT INTO stock_transactions " +
                "(product_id, transaction_type, quantity) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int currentQuantity;

                try (PreparedStatement ps = con.prepareStatement(selectSQL)) {
                    ps.setInt(1, productId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new IllegalArgumentException(
                                    "Product ID not found.");
                        }
                        currentQuantity = rs.getInt("quantity");
                    }
                }

                int newQuantity;

                if (type.equals("IN")) {
                    newQuantity = currentQuantity + quantity;
                } else if (type.equals("OUT")) {
                    if (quantity > currentQuantity) {
                        throw new IllegalArgumentException(
                                "Insufficient stock available.");
                    }
                    newQuantity = currentQuantity - quantity;
                } else {
                    throw new IllegalArgumentException(
                            "Invalid transaction type.");
                }

                try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
                    ps.setInt(1, newQuantity);
                    ps.setInt(2, productId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps =
                             con.prepareStatement(transactionSQL)) {
                    ps.setInt(1, productId);
                    ps.setString(2, type);
                    ps.setInt(3, quantity);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Stock updated successfully.");

            } catch (SQLException | RuntimeException e) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // The connection is closed after this operation.
                }
            }
        }
    }

    public void viewTransactionHistory() throws SQLException {

        String sql =
                "SELECT t.transaction_id, p.product_name, " +
                "t.transaction_type, t.quantity, t.transaction_date " +
                "FROM stock_transactions t " +
                "JOIN products p ON t.product_id = p.product_id " +
                "ORDER BY t.transaction_date DESC, t.transaction_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- STOCK TRANSACTION HISTORY ---");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.printf(
                        "Transaction ID: %d | Product: %s | Type: %s | " +
                        "Quantity: %d | Date: %s%n",
                        rs.getInt("transaction_id"),
                        rs.getString("product_name"),
                        rs.getString("transaction_type"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("transaction_date"));
            }

            if (!found) {
                System.out.println("No transactions recorded yet.");
            }
        }
    }
}