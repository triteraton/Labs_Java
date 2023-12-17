package com.example.lab_12_test1.database;

import java.sql.*;

public class DatabaseCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private static final String DATABASE_NAME = "courier";

    public static void main(String[] args) {
        createDatabase();
        createTables();
        /*if (isDatabaseCreated()) {
            System.out.println("Database already exists.");
        } else {
            createDatabase();
            createTables();
        }*/
    }

    public static boolean isDatabaseCreated() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?"
             )) {
            statement.setString(1, DATABASE_NAME);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables() {
        // Код для создания таблиц
    }
}