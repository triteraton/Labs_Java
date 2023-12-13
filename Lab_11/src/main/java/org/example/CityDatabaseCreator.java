package org.example;

import org.example.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CityDatabaseCreator {
    public static void main(String[] args) {
        createDatabase();
        createTables();
        calculatePopulation();
    }

    private static void createDatabase() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS city_db";
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            // Создание таблицы Cities
            String createCitiesTableQuery = "CREATE TABLE IF NOT EXISTS Cities (" +
                    "city_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "city_name VARCHAR(50) NOT NULL," +
                    "foundation_year INT," +
                    "area DECIMAL(10,2)," +
                    "population INT DEFAULT 0" +
                    ")";
            statement.executeUpdate(createCitiesTableQuery);

            // Создание таблицы Residents
            String createResidentsTableQuery = "CREATE TABLE IF NOT EXISTS Residents (" +
                    "resident_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "city_id INT," +
                    "resident_name VARCHAR(50) NOT NULL," +
                    "communication_language VARCHAR(50)," +
                    "FOREIGN KEY (city_id) REFERENCES Cities(city_id)" +
                    ")";
            statement.executeUpdate(createResidentsTableQuery);

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void calculatePopulation() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            // Обновление популяции городов
            String updatePopulationQuery = "UPDATE Cities " +
                    "SET population = (SELECT COUNT(*) FROM Residents WHERE Residents.city_id = Cities.city_id)";
            statement.executeUpdate(updatePopulationQuery);

            System.out.println("Population calculated and updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}