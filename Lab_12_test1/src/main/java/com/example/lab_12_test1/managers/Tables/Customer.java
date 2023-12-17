package com.example.lab_12_test1.managers.Tables;

import com.example.lab_12_test1.managers.Tables.DBTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer implements DBTable {
    private final StringProperty customerId;
    private final StringProperty name;
    private final StringProperty address;

    public Customer(String customerId, String name, String address) {
        this.customerId = new SimpleStringProperty(customerId);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
    }

    public StringProperty customerIdProperty() {
        return customerId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty addressProperty() {
        return address;
    }

    @Override
    public void insertData(Connection connection) {
        String query = "INSERT INTO customers (customer_id, name, address) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerId.get());
            statement.setString(2, name.get());
            statement.setString(3, address.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> retrieveData(Connection connection) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String customerId = resultSet.getString("customer_id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                Customer customer = new Customer(customerId, name, address);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public void updateData(Connection connection) {
        String query = "UPDATE customers SET name = ?, address = ? WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name.get());
            statement.setString(2, address.get());
            statement.setString(3, customerId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(Connection connection) {
        String query = "DELETE FROM customers WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}