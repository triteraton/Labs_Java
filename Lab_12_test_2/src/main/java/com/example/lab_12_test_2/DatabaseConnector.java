package com.example.lab_12_test_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL_CREATED = "jdbc:mysql://localhost:3306/courier";
    private static final String URL_NOT_CREATED = "jdbc:mysql://localhost:3306/";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private static final String DATABASE_NAME = "courier";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(getURL(), USERNAME, PASSWORD);
            createDatabaseIfNotExists(connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getURL() {
        return isDatabaseCreated() ? URL_CREATED : URL_NOT_CREATED;
    }

    private static boolean isDatabaseCreated() {
        try (Connection connection = DriverManager.getConnection(URL_NOT_CREATED, USERNAME, PASSWORD)) {
            String checkDatabaseQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
            try (var statement = connection.prepareStatement(checkDatabaseQuery)) {
                statement.setString(1, DATABASE_NAME);
                var resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void createDatabaseIfNotExists(Connection connection) throws SQLException {
        if (!isDatabaseCreated()) {
            String createDatabaseQuery = "CREATE DATABASE " + DATABASE_NAME;
            try (var statement = connection.createStatement()) {
                statement.executeUpdate(createDatabaseQuery);
            }
        }
    }
}