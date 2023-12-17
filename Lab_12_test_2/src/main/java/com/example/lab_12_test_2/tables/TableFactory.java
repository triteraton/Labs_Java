package com.example.lab_12_test_2.tables;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;

public class TableFactory {

    public static Courier createCourier(int courierId, String courierName, String contactInfo, String employmentStatus, String geolocation, Connection connection) {
        Courier courier = new Courier(courierId, courierName, contactInfo, employmentStatus, geolocation);
        courier.insertData(connection);
        return courier;
    }

    public static Customer createCustomer(int customerId, String customerName, String contactInfo, Connection connection) {
        Customer customer = new Customer(customerId, customerName, contactInfo);
        customer.insertData(connection);
        return customer;
    }

    public static Order createOrder(int orderId, LocalDateTime orderDate, int customerId, int courierId, String orderStatus, Connection connection) {
        Order order = new Order(orderId, orderDate, customerId, courierId, orderStatus);
        order.insertData(connection);
        return order;
    }

    public static OrderProduct createOrderProduct(int orderId, int productId, Connection connection) {
        OrderProduct orderProduct = new OrderProduct(orderId, productId);
        orderProduct.insertData(connection);
        return orderProduct;
    }

    public static Product createProduct(int productId, String productName, String description, BigDecimal price, int availability, Connection connection) {
        Product product = new Product(productId, productName, description, price, availability);
        product.insertData(connection);
        return product;
    }
}