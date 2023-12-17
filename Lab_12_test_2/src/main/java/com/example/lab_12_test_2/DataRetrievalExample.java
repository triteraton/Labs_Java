package com.example.lab_12_test_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.example.lab_12_test_2.DatabaseConnector.getConnection;


public class DataRetrievalExample {

    private static Connection connection = getConnection();

    private static void retrieveDataFromCustomersTable() throws SQLException {
        String query = "SELECT * FROM Customers";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customerName = resultSet.getString("customer_name");
                String contactInfo = resultSet.getString("contact_info");

                System.out.println("Customer ID: " + customerId);
                System.out.println("Customer Name: " + customerName);
                System.out.println("Contact Info: " + contactInfo);
                System.out.println();
            }
        }
    }

    private static void retrieveDataFromCouriersTable() throws SQLException {
        String query = "SELECT * FROM Couriers";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int courierId = resultSet.getInt("courier_id");
                String courierName = resultSet.getString("courier_name");
                String contactInfo = resultSet.getString("contact_info");
                String employmentStatus = resultSet.getString("employment_status");
                String geolocation = resultSet.getString("geolocation");

                System.out.println("Courier ID: " + courierId);
                System.out.println("Courier Name: " + courierName);
                System.out.println("Contact Info: " + contactInfo);
                System.out.println("Employment Status: " + employmentStatus);
                System.out.println("Geolocation: " + geolocation);
                System.out.println();
            }
        }
    }

    private static void retrieveDataFromProductsTable() throws SQLException {
        String query = "SELECT * FROM Products";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int availability = resultSet.getInt("availability");

                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productName);
                System.out.println("Description: " + description);
                System.out.println("Price: " + price);
                System.out.println("Availability: " + availability);
                System.out.println();
            }
        }
    }

    private static void retrieveDataFromOrdersTable() throws SQLException {
        String query = "SELECT * FROM Orders";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String orderDate = resultSet.getString("order_date");
                int customerId = resultSet.getInt("customer_id");
                int productId = resultSet.getInt("product_id");
                int courierId = resultSet.getInt("courier_id");
                String orderStatus = resultSet.getString("order_status");

                System.out.println("Order ID: " + orderId);
                System.out.println("Order Date: " + orderDate);
                System.out.println("Customer ID: " + customerId);
                System.out.println("Product ID: " + productId);
                System.out.println("Courier ID: " + courierId);
                System.out.println("Order Status: " + orderStatus);
                System.out.println();
            }
        }
    }
}