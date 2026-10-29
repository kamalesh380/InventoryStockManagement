package app;

import dao.StockTransactionDAO;
import dao.InventoryReportDAO;
import model.*;
import service.InventoryServiceImpl;
import exception.ProductNotFoundException;
import exception.InvalidProductException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final InventoryServiceImpl service =
            new InventoryServiceImpl();
    private static final StockTransactionDAO transactionDAO =
            new StockTransactionDAO();
    private static final InventoryReportDAO reportDAO =
        new InventoryReportDAO();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println(" INVENTORY AND STOCK MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. View Low Stock Products");
            System.out.println("7. View Total Stock Value");
            System.out.println("8. Stock In");
            System.out.println("9. Stock Out");
            System.out.println("10. View Transaction History");
            System.out.println("11. Category-wise Inventory Report");
            System.out.println("12. Exit");
            System.out.println("======================================");

            choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> viewProducts();
                    case 3 -> searchProduct();
                    case 4 -> updateProduct();
                    case 5 -> deleteProduct();
                    case 6 -> viewLowStockProducts();
                    case 7 -> viewTotalStockValue();
                    case 8 -> stockIn();
                    case 9 -> stockOut();
                    case 10 -> viewTransactionHistory();
                    case 11 -> reportDAO.viewCategoryReport();
                    case 12 -> System.out.println(
                            "Thank you for using the system!");
                    default -> System.out.println(
                            "Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println(
                        "Database error: " + e.getMessage());
            } catch (ProductNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InvalidProductException e) {
                System.out.println(
                        "Invalid product: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Invalid input: " + e.getMessage());
            }

        } while (choice != 12);

        sc.close();
    }

    private static void addProduct()
            throws SQLException, InvalidProductException {

        System.out.println("\n--- ADD PRODUCT ---");

        String name = readString("Enter product name: ");
        int categoryId = readInt(
                "Enter category ID (1-Electronics, 2-Stationery, 3-Furniture): ");
        BigDecimal price = readPrice("Enter price: ");
        int quantity = readInt("Enter initial quantity: ");
        int minStock = readInt("Enter minimum stock level: ");

        Product product = createProduct(
                0, name, categoryId, price, quantity, minStock);

        service.addProduct(product);

        System.out.println("Product added successfully!");
    }

    private static void viewProducts() throws SQLException {

        System.out.println("\n--- ALL PRODUCTS ---");

        List<Product> products = service.viewProducts();

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void searchProduct()
            throws SQLException, ProductNotFoundException {

        System.out.println("\n--- SEARCH PRODUCT ---");

        int id = readInt("Enter product ID: ");

        Product product = service.searchProduct(id);

        System.out.println(product);
        System.out.println(
                "Stock value: " + product.getStockValue());
    }

    private static void updateProduct()
            throws SQLException, ProductNotFoundException,
            InvalidProductException {

        System.out.println("\n--- UPDATE PRODUCT ---");

        int id = readInt("Enter product ID to update: ");

        Product existing = service.searchProduct(id);

        System.out.println("Current details:");
        System.out.println(existing);

        String name = readString("Enter new product name: ");
        int categoryId = readInt(
                "Enter new category ID (1-Electronics, 2-Stationery, 3-Furniture): ");
        BigDecimal price = readPrice("Enter new price: ");
        int quantity = readInt("Enter new quantity: ");
        int minStock = readInt("Enter new minimum stock level: ");

        Product updated = createProduct(
                id, name, categoryId, price, quantity, minStock);

        service.updateProduct(updated);

        System.out.println("Product updated successfully!");
    }

    private static void deleteProduct()
            throws SQLException, ProductNotFoundException {

        System.out.println("\n--- DELETE PRODUCT ---");

        int id = readInt("Enter product ID to delete: ");

        service.deleteProduct(id);

        System.out.println("Product deleted successfully!");
    }

    private static void viewLowStockProducts()
            throws SQLException {

        System.out.println("\n--- LOW STOCK PRODUCTS ---");

        List<Product> products =
                service.viewLowStockProducts();

        if (products.isEmpty()) {
            System.out.println(
                    "No products are below their stock threshold.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
            System.out.println(
                    "Minimum stock level: "
                            + product.getMinStockLevel());
        }
    }

    private static void viewTotalStockValue()
            throws SQLException {

        System.out.println("\n--- TOTAL STOCK VALUE ---");

        BigDecimal total = service.calculateStockValue();

        System.out.println("Total inventory value: " + total);
    }

    private static void stockIn() throws SQLException {

        System.out.println("\n--- STOCK IN ---");

        int productId = readInt("Enter product ID: ");
        int quantity = readInt(
                "Enter quantity to add: ");

        transactionDAO.updateStock(
                productId, "IN", quantity);
    }

    private static void stockOut() throws SQLException {

        System.out.println("\n--- STOCK OUT ---");

        int productId = readInt("Enter product ID: ");
        int quantity = readInt(
                "Enter quantity to remove: ");

        transactionDAO.updateStock(
                productId, "OUT", quantity);
    }

    private static void viewTransactionHistory()
            throws SQLException {

        transactionDAO.viewTransactionHistory();
    }

    private static Product createProduct(
            int id,
            String name,
            int categoryId,
            BigDecimal price,
            int quantity,
            int minStock) {

        return switch (categoryId) {
            case 1 -> new ElectronicsProduct(
                    id, name, categoryId,
                    price, quantity, minStock);

            case 2 -> new StationeryProduct(
                    id, name, categoryId,
                    price, quantity, minStock);

            case 3 -> new FurnitureProduct(
                    id, name, categoryId,
                    price, quantity, minStock);

            default -> throw new IllegalArgumentException(
                    "Category ID must be 1, 2, or 3.");
        };
    }

    private static int readInt(String message) {

        System.out.print(message);

        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Please enter a valid whole number.");
        }
    }

    private static String readString(String message) {

        System.out.print(message);

        String input = sc.nextLine().trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException(
                    "Input cannot be empty.");
        }

        return input;
    }

    private static BigDecimal readPrice(String message) {

        System.out.print(message);

        try {
            BigDecimal price =
                    new BigDecimal(sc.nextLine().trim());

            if (price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Price cannot be negative.");
            }

            return price;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Please enter a valid price.");
        }
    }
}