# Inventory and Stock Management System

A console-based inventory management application developed using Java and MySQL.

## Features

* Add, view, search, update, and delete products
* Manage stock-in and stock-out operations
* View low-stock products
* Calculate total inventory value
* View stock transaction history
* Organize products by category

## Technologies Used

* Java
* MySQL
* JDBC
* MySQL Workbench
* Visual Studio Code

## Object-Oriented Programming

The project demonstrates encapsulation, inheritance, polymorphism, abstraction, packages, and exception handling.

## Database Structure

The application uses three tables:

* `categories`
* `products`
* `stock_transactions`

## Setup Instructions

1. Install the JDK and MySQL.
2. Create the database by running `sql/schema.sql`.
3. Insert sample records by running `sql/sample_data.sql`.
4. Configure the `DB_USER` and `DB_PASSWORD` environment variables.
5. Ensure the MySQL Connector/J JAR is available in the `lib` folder.
6. Compile the Java source files and run `app.Main`.

## Project Structure

* `src/app`: Application entry point
* `src/model`: Product model classes
* `src/dao`: Database operations
* `src/service`: Business logic
* `src/database`: JDBC connection
* `src/exception`: Custom exceptions
* `sql`: Database scripts
* `docs`: Project documentation

## Author

Student project developed for academic purposes.
