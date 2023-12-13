package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherWidgetGenerator {
    public static void main(String[] args) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://yahoo-weather5.p.rapidapi.com/weather?location=sunnyvale&format=json&u=c"))
                .header("X-RapidAPI-Key", "4383fe10e3mshb35a3d73caa755cp1b4fb1jsn734fbd627e7d")
                .header("X-RapidAPI-Host", "yahoo-weather5.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);

            // Парсинг JSON с помощью GSON
            Gson gson = new Gson();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            // Извлечение данных о погоде
            JsonObject location = json.getAsJsonObject("location");
            String city = location.get("city").getAsString();
            String country = location.get("country").getAsString();

            JsonObject currentObservation = json.getAsJsonObject("current_observation");
            String weatherText = currentObservation.getAsJsonObject("condition").get("text").getAsString();
            double temperature = currentObservation.getAsJsonObject("condition").get("temperature").getAsDouble();
            double pressure = currentObservation.getAsJsonObject("atmosphere").get("pressure").getAsDouble();
            int humidity = currentObservation.getAsJsonObject("atmosphere").get("humidity").getAsInt();

            JsonObject forecastToday = json.getAsJsonArray("forecasts").get(0).getAsJsonObject();
            int minTemperature = forecastToday.get("low").getAsInt();
            int maxTemperature = forecastToday.get("high").getAsInt();

            JsonObject wind = currentObservation.getAsJsonObject("wind");
            double windSpeed = wind.get("speed").getAsDouble();
            String windDirection = wind.get("direction").getAsString();

            int cloudiness = currentObservation.getAsJsonObject("atmosphere").get("visibility").getAsInt();

            // Создание HTML-страницы с виджетом погоды
            String htmlContent = "<html>\n" +
                    "<head>\n" +
                    "<title>Weather Widget</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Weather in " + city + ", " + country + "</h1>\n" +
                    "<p><strong>Current Weather:</strong> " + weatherText + "</p>\n" +
                    "<p><strong>Temperature:</strong> " + temperature + "°C</p>\n" +
                    "<p><strong>Pressure:</strong> " + pressure + " mmHg</p>\n" +
                    "<p><strong>Humidity:</strong> " + humidity + "%</p>\n" +
                    "<p><strong>Min Temperature:</strong> " + minTemperature + "°C</p>\n" +
                    "<p><strong>Max Temperature:</strong> " + maxTemperature + "°C</p>\n" +
                    "<p><strong>Wind:</strong> " + windSpeed + " m/s " + windDirection + "</p>\n" +
                    "<p><strong>Cloudiness:</strong> " + cloudiness + "%</p>\n" +
                    "</body>\n" +
                    "</html>";

            // Сохранение HTML-страницы в файл
            String filePath = "weather_widget.html";
            saveHtmlToFile(filePath, htmlContent);
            System.out.println("Weather widget HTML saved to file: " + filePath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void saveHtmlToFile(String filePath, String htmlContent) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
