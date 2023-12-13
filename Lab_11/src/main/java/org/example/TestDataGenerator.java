package org.example;

import static org.example.DataAdder.addCity;
import static org.example.DataAdder.addResident;

public class TestDataGenerator {
    public static void main(String[] args) {
        // Заполнение таблицы Cities
        addCity("City A", 2000, 100.5);
        addCity("City B", 1995, 75.2);
        addCity("City C", 2010, 120.8);

        // Заполнение таблицы Residents
        addResident("City A", "John Doe", "English");
        addResident("City A", "Jane Smith", "English");
        addResident("City B", "Michael Johnson", "English");
        addResident("City C", "Maria Garcia", "Spanish");
    }
}