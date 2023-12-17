package com.example.lab_12_test_2.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Courier implements DBTable{
    private int courierId;
    private String courierName;
    private String contactInfo;
    private String employmentStatus;
    private String geolocation;

    public Courier(int courierId, String courierName, String contactInfo, String employmentStatus, String geolocation) {
        this.courierId = courierId;
        this.courierName = courierName;
        this.contactInfo = contactInfo;
        this.employmentStatus = employmentStatus;
        this.geolocation = geolocation;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    @Override
    public void insertData(Connection connection) {
        try{
            String query = "INSERT INTO Couriers (courier_id, courier_name, contact_info, employment_status, geolocation) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courierId);
            statement.setString(2, courierName);
            statement.setString(3, contactInfo);
            statement.setString(4, employmentStatus);
            statement.setString(5, geolocation);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
