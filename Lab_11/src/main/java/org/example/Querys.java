package org.example;

import org.example.DatabaseConnector;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Querys {
    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("query_results.html");
            fileWriter.write("<html><body>");

            String cityName = getInput("Enter city name: ");
            String language = getInput("Enter language: ");
            int population = Integer.parseInt(getInput("Enter population: "));

            retrieveCityResidentsInfo(cityName, language, fileWriter);
            retrieveCityLanguageInfo(language, fileWriter);
            retrieveCityPopulationLanguageInfo(population, fileWriter);
            retrieveOldestCityResidentsInfo(fileWriter);

            fileWriter.write("</body></html>");
            fileWriter.close();
            System.out.println("Results saved to query_results.html");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the results.");
            e.printStackTrace();
        }
    }

    public static String getInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String generateHTMLTable(ResultSet resultSet) throws SQLException {
        StringBuilder htmlTable = new StringBuilder();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlTable.append("<table>");
        htmlTable.append("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            htmlTable.append("<th>").append(metaData.getColumnName(i)).append("</th>");
        }
        htmlTable.append("</tr>");

        while (resultSet.next()) {
            htmlTable.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                htmlTable.append("<td>").append(resultSet.getString(i)).append("</td>");
            }
            htmlTable.append("</tr>");
        }

        htmlTable.append("</table>");
        return htmlTable.toString();
    }

    public static void retrieveCityResidentsInfo(String cityName, String language, FileWriter fileWriter) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM Residents "
                    + "INNER JOIN Cities ON Residents.city_id = Cities.city_id "
                    + "WHERE Cities.city_name = ? AND Residents.communication_language = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cityName);
            statement.setString(2, language);

            ResultSet resultSet = statement.executeQuery();

            String htmlTable = generateHTMLTable(resultSet);
            fileWriter.write("<h2>City Residents Info</h2>");
            fileWriter.write(htmlTable);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveCityLanguageInfo(String language, FileWriter fileWriter) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT DISTINCT Cities.city_name FROM Cities "
                    + "INNER JOIN Residents ON Cities.city_id = Residents.city_id "
                    + "WHERE Residents.communication_language = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, language);

            ResultSet resultSet = statement.executeQuery();

            String htmlTable = generateHTMLTable(resultSet);
            fileWriter.write("<h2>City Language Info</h2>");
            fileWriter.write(htmlTable);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveCityPopulationLanguageInfo(int population, FileWriter fileWriter) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT Cities.city_name, Residents.communication_language FROM Cities "
                    + "INNER JOIN Residents ON Cities.city_id = Residents.city_id "
                    + "WHERE Cities.population = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, population);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String htmlTable = generateHTMLTable(resultSet);
                fileWriter.write("<h2>City Population Language Info</h2>");
                fileWriter.write(htmlTable);
            } else {
                fileWriter.write("<p>No city found with the specified population.</p>");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveOldestCityResidentsInfo(FileWriter fileWriter) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT Residents.resident_name, Residents.communication_language FROM Residents "
                    + "INNER JOIN Cities ON Residents.city_id = Cities.city_id "
                    + "WHERE Cities.foundation_year = (SELECT MIN(foundation_year) FROM Cities)";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            String htmlTable = generateHTMLTable(resultSet);
            fileWriter.write("<h2>Oldest City Residents Info</h2>");
            fileWriter.write(htmlTable);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}