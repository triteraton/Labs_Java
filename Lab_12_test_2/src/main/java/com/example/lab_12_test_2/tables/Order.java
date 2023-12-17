package com.example.lab_12_test_2.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Order implements DBTable{
    private int orderId;
    private LocalDateTime orderDate;
    private int customerId;
    private int courierId;
    private String orderStatus;

    public Order(int orderId, LocalDateTime orderDate, int customerId, int courierId, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.courierId = courierId;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public void insertData(Connection connection) {
        try {
            String query = "INSERT INTO Orders (order_id, order_date, customer_id, courier_id, order_status) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.setTimestamp(2, Timestamp.valueOf(orderDate));
            statement.setInt(3, customerId);
            statement.setInt(4, courierId);
            statement.setString(5, orderStatus);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
