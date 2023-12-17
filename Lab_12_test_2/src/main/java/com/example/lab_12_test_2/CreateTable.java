package com.example.lab_12_test_2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.lab_12_test_2.DatabaseConnector.getConnection;

public class CreateTable {
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            dropTables(connection);
            createCustomersTable(connection);
            createProductsTable(connection);
            createCouriersTable(connection);
            createOrdersTable(connection);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCustomersTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE Customers (" +
                "customer_id INT PRIMARY KEY AUTO_INCREMENT," +
                "customer_name VARCHAR(100) NOT NULL," +
                "contact_info VARCHAR(100) NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    private static void createProductsTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE Products (" +
                "product_id INT PRIMARY KEY AUTO_INCREMENT," +
                "product_name VARCHAR(100) NOT NULL," +
                "product_price DECIMAL(10, 2) NOT NULL"+
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    private static void createCouriersTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE Couriers (" +
                "courier_id INT PRIMARY KEY AUTO_INCREMENT," +
                "courier_name VARCHAR(100) NOT NULL," +
                "contact_info VARCHAR(100) NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    private static void createOrdersTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE Orders (" +
                "order_id INT PRIMARY KEY AUTO_INCREMENT," +
                "order_date DATETIME NOT NULL," +
                "customer_id INT NOT NULL," + // Изменено на customer_id
                "courier_id INT NOT NULL," +
                "FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)," +
                "FOREIGN KEY (courier_id) REFERENCES Couriers(courier_id)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    private static void dropTables(Connection connection) throws SQLException {
        String[] tableNames = {"OrderProduct", "Orders", "Customers", "Products", "Couriers"};

        try (Statement statement = connection.createStatement()) {
            for (String tableName : tableNames) {
                String query = "DROP TABLE IF EXISTS " + tableName;
                statement.executeUpdate(query);
            }
        }
    }
}