### Database Design

The Inventory and Stock Management System uses MySQL as its relational database management system. The database consists of three tables: `categories`, `products`, and `stock_transactions`.

The `categories` table stores category information. The `products` table stores product details, including the category, price, current quantity, and minimum stock level. The `stock_transactions` table maintains a history of stock additions and removals.

The `category_id` column in the `products` table references the primary key of the `categories` table. Similarly, the `product_id` column in the `stock_transactions` table references the primary key of the `products` table.

These foreign-key relationships maintain referential integrity and connect products to their categories and stock transactions.

The system supports product creation, retrieval, updating, and deletion, along with stock-in and stock-out operations, low-stock identification, inventory valuation, and transaction-history reporting.

# INVENTORY AND STOCK MANAGEMENT SYSTEM USING JAVA AND MYSQL

## 1. Abstract

The Inventory and Stock Management System is a console-based application developed using Java and MySQL. It is designed to manage product information, monitor stock quantities, and maintain records of stock additions and removals.

The application provides features such as adding, viewing, searching, updating, and deleting products. It also supports stock-in and stock-out operations, low-stock identification, inventory valuation, and transaction-history reporting.

Java is used to implement the application logic and object-oriented programming concepts, while MySQL stores the product, category, and transaction data. JDBC establishes communication between the Java application and the database.

The system aims to improve the accuracy of inventory records, simplify stock monitoring, and provide a structured approach to inventory management.

## 2. Problem Statement

Managing inventory manually can lead to inaccurate stock records, difficulty tracking stock movements, and delays in identifying products that require replenishment.

The proposed system addresses these issues by providing a centralized database and a Java-based application for maintaining product details, monitoring stock levels, and recording inventory transactions.

## 3. Objectives

* Develop a console-based inventory management application using Java.
* Store product and category information in a MySQL database.
* Implement CRUD operations for product management.
* Record stock-in and stock-out transactions.
* Identify products that have reached their minimum stock levels.
* Calculate the total value of the available inventory.
* Apply object-oriented programming concepts.
* Establish database connectivity using JDBC.
* Maintain data consistency using primary keys, foreign keys, and database transactions.

## 4. Software and Hardware Requirements

### Software Requirements

* Programming Language: Java
* Database Management System: MySQL
* Database Tool: MySQL Workbench
* IDE/Editor: Visual Studio Code
* Database Connectivity: JDBC
* Operating System: Windows

### Hardware Requirements

* Computer or laptop
* Minimum 4 GB RAM recommended
* Sufficient disk space for the JDK, MySQL, and project files

## 5. System Architecture

The application follows a layered structure.

### Presentation Layer

The `Main` class provides the terminal menu and accepts user input.

### Service Layer

The `InventoryService` interface and `InventoryServiceImpl` class implement the inventory operations and business rules.

### Data Access Layer

The DAO classes execute SQL queries and communicate with the MySQL database using JDBC.

### Model Layer

The model classes represent products and their categories. They encapsulate product attributes and related behavior.

### Database Layer

MySQL stores categories, products, and stock transaction records.

## 6. Database Design

The system uses three tables:

1. `categories`: Stores category identifiers, names, and descriptions.
2. `products`: Stores product names, category references, prices, quantities, and minimum stock levels.
3. `stock_transactions`: Records stock-in and stock-out operations with their quantities and timestamps.

### Relationships

* One category can contain multiple products.
* One product can have multiple stock transactions.
* Primary keys uniquely identify records.
* Foreign keys maintain relationships between the tables.

## 7. Object-Oriented Programming Concepts

### Encapsulation

The `Product` class uses private attributes and public getter and setter methods to control access to product information.

### Inheritance

`ElectronicsProduct`, `StationeryProduct`, and `FurnitureProduct` inherit common attributes and behavior from the abstract `Product` class.

### Polymorphism

The subclasses provide their own implementations of the abstract `getProductType()` method. A product can be handled through a common `Product` reference while providing category-specific behavior.

### Abstraction

The abstract `Product` class defines common product behavior without specifying every implementation detail. The `InventoryService` interface defines the operations that its implementation must provide.

### Packages

The application is organized into packages such as `app`, `model`, `dao`, `service`, `database`, and `exception`. This improves modularity and maintainability.

### Exception Handling

Custom exceptions such as `ProductNotFoundException` and `InvalidProductException` handle application-level errors. SQL exceptions are handled when database operations fail.

## 8. Functional Modules

### Product Management

Supports adding, viewing, searching, updating, and deleting products.

### Stock Management

Supports stock-in and stock-out operations. Stock changes are recorded in the transaction table.

### Low-Stock Monitoring

Identifies products whose quantities are less than or equal to their configured minimum stock levels.

### Inventory Valuation

Calculates the total inventory value using product prices and available quantities.

### Transaction History

Displays recorded stock movements, including transaction type, quantity, and date.

### Category-Wise Reporting

Summarizes the number of products, total quantity, and inventory value for each category, if the reporting feature has been implemented.

## 9. Testing

The application should be tested using the following cases:

| Test Case                        | Expected Result                                               |
| -------------------------------- | ------------------------------------------------------------- |
| Add a product                    | Product is stored successfully                                |
| View products                    | Stored products are displayed                                 |
| Search using a valid ID          | Matching product is displayed                                 |
| Search using an invalid ID       | Appropriate error is displayed                                |
| Update a product                 | Product details are modified                                  |
| Delete a product                 | Product is removed                                            |
| Add stock                        | Quantity increases and a transaction is recorded              |
| Remove stock                     | Quantity decreases and a transaction is recorded              |
| Remove more stock than available | Operation is rejected                                         |
| View low-stock products          | Products at or below their minimum stock levels are displayed |
| Calculate inventory value        | Total stock value is displayed                                |
| View transaction history         | Recorded stock transactions are displayed                     |

## 10. Conclusion

The Inventory and Stock Management System demonstrates the development of a database-driven Java application using object-oriented programming and JDBC. It provides essential inventory operations, supports stock movement tracking, and stores data in a relational MySQL database.

The project demonstrates modular program design, database connectivity, exception handling, and the use of primary-key and foreign-key relationships.

## 11. Future Enhancements

* Add a graphical user interface.
* Implement user authentication and role-based access.
* Generate printable inventory reports.
* Add supplier and purchase-order management.
* Introduce automated low-stock notifications.
* Add date-range filtering for transaction reports.
