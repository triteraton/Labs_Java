package com.example.lab_12_test_2.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer implements DBTable {
    private int customerId;
    private String customerName;
    private String contactInfo;

    public Customer(int customerId, String customerName, String contactInfo) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactInfo = contactInfo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public void insertData(Connection connection) {
        try {
            String query = "INSERT INTO Customers (customer_id, customer_name, contact_info) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setString(2, customerName);
            statement.setString(3, contactInfo);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
