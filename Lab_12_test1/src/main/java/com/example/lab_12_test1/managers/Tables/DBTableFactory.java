package com.example.lab_12_test1.managers.Tables;

import java.sql.Connection;

public class DBTableFactory {
    public static DBTable create(String className, Object... args) {
        switch (className) {
            case "Courier":
                if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof String) {
                    String courierId = (String) args[0];
                    String name = (String) args[1];
                    String vehicle = (String) args[2];
                    return new Courier(courierId, name, vehicle);
                }
                break;
            case "Customer":
                if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof String) {
                    String customerId = (String) args[0];
                    String name = (String) args[1];
                    String address = (String) args[2];
                    return new Customer(customerId, name, address);
                }
                break;
            case "Order":
                if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof String) {
                    String orderId = (String) args[0];
                    String customer = (String) args[1];
                    String status = (String) args[2];
                    return new Order(orderId, customer, status);
                }
                break;
            case "Product":
                if (args.length == 3 && args[0] instanceof String && args[1] instanceof String && args[2] instanceof Double) {
                    String productId = (String) args[0];
                    String name = (String) args[1];
                    double price = (Double) args[2];
                    return new Product(productId, name, price);
                }
                break;
        }
        return null;
    }
}