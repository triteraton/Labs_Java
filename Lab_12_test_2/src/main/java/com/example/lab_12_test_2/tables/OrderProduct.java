package com.example.lab_12_test_2.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderProduct implements DBTable{
    private int orderId;
    private int productId;

    public OrderProduct(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public void insertData(Connection connection) {
        try {
            String query = "INSERT INTO OrderProduct (order_id, product_id) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
