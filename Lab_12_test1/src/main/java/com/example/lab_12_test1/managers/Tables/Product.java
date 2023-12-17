package com.example.lab_12_test1.managers.Tables;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product implements DBTable {
    private final StringProperty productId;
    private final StringProperty name;
    private final DoubleProperty price;

    public Product(String productId, String name, double price) {
        this.productId = new SimpleStringProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public StringProperty productIdProperty() {
        return productId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    @Override
    public void insertData(Connection connection) {
        String query = "INSERT INTO products (product_id, name, price) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productId.get());
            statement.setString(2, name.get());
            statement.setDouble(3, price.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> retrieveData(Connection connection) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String productId = resultSet.getString("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                Product product = new Product(productId, name, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void updateData(Connection connection) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name.get());
            statement.setDouble(2, price.get());
            statement.setString(3, productId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(Connection connection) {
        String query = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productId.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}