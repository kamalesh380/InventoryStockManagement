package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/inventory_db";

    private static final String USER =
            System.getenv().getOrDefault("DB_USER", "root");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if (PASSWORD == null || PASSWORD.isEmpty()) {
            throw new SQLException(
                    "Database password not configured. Set DB_PASSWORD.");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}