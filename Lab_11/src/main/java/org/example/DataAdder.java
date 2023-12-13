package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataAdder {
    public static void addCity(String cityName, int foundationYear, double area) {
        String cityQuery = "INSERT INTO Cities (city_name, foundation_year, area) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement cityStatement = connection.prepareStatement(cityQuery)) {

            cityStatement.setString(1, cityName);
            cityStatement.setInt(2, foundationYear);
            cityStatement.setDouble(3, area);

            int cityRowsAffected = cityStatement.executeUpdate();
            System.out.println(cityRowsAffected + " city row(s) inserted successfully.");

            // Обновление популяции городов
            updatePopulation(cityName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addResident(String cityName, String residentName, String communicationLanguage) {
        String residentQuery = "INSERT INTO residents (city_id, resident_name, communication_language) " +
                "VALUES ((SELECT city_id FROM cities WHERE city_name = ?), ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement residentStatement = connection.prepareStatement(residentQuery)) {

            residentStatement.setString(1, cityName);
            residentStatement.setString(2, residentName);
            residentStatement.setString(3, communicationLanguage);

            int residentRowsAffected = residentStatement.executeUpdate();
            System.out.println(residentRowsAffected + " resident row(s) inserted successfully.");

            // Обновление популяции городов
            updatePopulation(cityName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePopulation(String cityName) {
        String updatePopulationQuery = "UPDATE Cities " +
                "SET population = (SELECT COUNT(*) FROM residents WHERE residents.city_id = Cities.city_id) " +
                "WHERE city_name = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(updatePopulationQuery)) {

            statement.setString(1, cityName);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " city population(s) updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}