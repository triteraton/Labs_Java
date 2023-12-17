package com.example.lab_12_test1.managers.Tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order implements DBTable {
    private final StringProperty orderId;
    private final StringProperty customer;
    private final StringProperty status;

    public Order(String orderId, String customer, String status) {
        this.orderId = new SimpleStringProperty(orderId);
        this.customer = new SimpleStringProperty(customer);
        this.status = new SimpleStringProperty(status);
    }

    public StringProperty orderIdProperty() {
        return orderId;
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public StringProperty statusProperty() {
        return status;
    }

    @Override
    public void insertData(Connection connection) {
        String query = "INSERT INTO orders (order_id, customer, status) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId.get());
            statement.setString(2, customer.get());
            statement.setString(3, status.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Order> retrieveData(Connection connection) {
        String query = "SELECT * FROM orders";
        ObservableList<Order> orders = FXCollections.observableArrayList();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderId = resultSet.getString("order_id");
                String customer = resultSet.getString("customer");
                String status = resultSet.getString("status");

                Order order = new Order(orderId, customer, status);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public void updateData(Connection connection) {
        String query = "UPDATE orders SET customer = ?, status = ? WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.get());
            statement.setString(2, status.get());
            statement.setString(3, orderId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(Connection connection) {
        String query = "DELETE FROM orders WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}