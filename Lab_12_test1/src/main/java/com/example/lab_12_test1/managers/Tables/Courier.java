package com.example.lab_12_test1.managers.Tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Courier implements DBTable {
    private final StringProperty courierId;
    private final StringProperty name;
    private final StringProperty vehicle;

    public Courier(String courierId, String name, String vehicle) {
        this.courierId = new SimpleStringProperty(courierId);
        this.name = new SimpleStringProperty(name);
        this.vehicle = new SimpleStringProperty(vehicle);
    }

    public StringProperty courierIdProperty() {
        return courierId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty vehicleProperty() {
        return vehicle;
    }

    @Override
    public void insertData(Connection connection) {
        String query = "INSERT INTO couriers (name, vehicle) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name.get());
            statement.setString(2, vehicle.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Courier> retrieveData(Connection connection) {
        List<Courier> couriers = new ArrayList<>();
        String query = "SELECT * FROM couriers";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String courierId = resultSet.getString("courier_id");
                String name = resultSet.getString("name");
                String vehicle = resultSet.getString("vehicle");

                Courier courier = new Courier(courierId, name, vehicle);
                couriers.add(courier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return couriers;
    }

    @Override
    public void updateData(Connection connection) {
        String query = "UPDATE couriers SET name = ?, vehicle = ? WHERE courier_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name.get());
            statement.setString(2, vehicle.get());
            statement.setString(3, courierId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(Connection connection) {
        String query = "DELETE FROM couriers WHERE courier_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courierId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}