package com.example.lab_12_test_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.lab_12_test_2.DatabaseConnector.getConnection;


public class InsertData {

    private static Connection connection = getConnection();

    private static void insertDataIntoOrdersTable(String orderDate, int customerId, int productId, int courierId, String orderStatus) throws SQLException {
        String query = "INSERT INTO Orders (order_date, customer_id, product_id, courier_id, order_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderDate);
            statement.setInt(2, customerId);
            statement.setInt(3, productId);
            statement.setInt(4, courierId);
            statement.setString(5, orderStatus);
            statement.executeUpdate();
        }
    }

    private static void insertDataIntoProductsTable(String productName, String description, double price, int availability) throws SQLException {
        String query = "INSERT INTO Products (product_name, description, price, availability) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);
            statement.setString(2, description);
            statement.setDouble(3, price);
            statement.setInt(4, availability);
            statement.executeUpdate();
        }
    }

    private static void insertDataIntoCustomersTable(String customerName, String contactInfo) throws SQLException {
        String query = "INSERT INTO Customers (customer_name, contact_info) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerName);
            statement.setString(2, contactInfo);
            statement.executeUpdate();
        }
    }

    private static void insertDataIntoCouriersTable(String courierName, String contactInfo, String employmentStatus, String geolocation) throws SQLException {
        String query = "INSERT INTO Couriers (courier_name, contact_info, employment_status, geolocation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courierName);
            statement.setString(2, contactInfo);
            statement.setString(3, employmentStatus);
            statement.setString(4, geolocation);
            statement.executeUpdate();
        }
    }
}