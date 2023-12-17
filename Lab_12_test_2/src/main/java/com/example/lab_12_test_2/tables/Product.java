package com.example.lab_12_test_2.tables;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Product implements DBTable{
    private int productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int availability;

    public Product(int productId, String productName, String description, BigDecimal price, int availability) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public void insertData(Connection connection) {
        try {
            String query = "INSERT INTO Products (product_id, product_name, description, price, availability) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            statement.setString(2, productName);
            statement.setString(3, description);
            statement.setBigDecimal(4, price);
            statement.setInt(5, availability);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}