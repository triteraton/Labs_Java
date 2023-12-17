package com.example.lab_12_test1;

import com.example.lab_12_test1.managers.Tables.DBTable;
import com.example.lab_12_test1.managers.Tables.DBTableFactory;
import static com.example.lab_12_test1.database.DatabaseConnector.getConnection;
import static com.example.lab_12_test1.database.DatabaseCreator.createDatabase;
import static com.example.lab_12_test1.database.DatabaseCreator.createTables;

public class Example {

    public static void main(String[] args) {
        createDatabase();
        createTables();

        try {
            // Пример создания объекта класса Courier
            String courierId = "C001";
            String courierName = "John Doe";
            String vehicle = "Car";
            DBTable courier = DBTableFactory.create("Courier", courierId, courierName, vehicle);
            System.out.println("Добавлен объект Courier в базу данных");
            courier.insertData(getConnection()); // Отправка в базу данных
            System.out.println("Добавлен объект Courier в базу данных");

            // Пример создания объекта класса Customer
            String customerId = "CU001";
            String customerName = "Jane Smith";
            String address = "123 Main Street";
            DBTable customer = DBTableFactory.create("Customer", customerId, customerName, address);
            customer.insertData(getConnection()); // Отправка в базу данных
            System.out.println("Добавлен объект Customer в базу данных");

            // Пример создания объекта класса Order
            String orderId = "O001";
            String customerRef = "CU001";
            String status = "Pending";
            DBTable order = DBTableFactory.create("Order", orderId, customerRef, status);
            order.insertData(getConnection()); // Отправка в базу данных
            System.out.println("Добавлен объект Order в базу данных");

            // Пример создания объекта класса Product
            String productId = "P001";
            String productName = "Example Product";
            double price = 9.99;
            DBTable product = DBTableFactory.create("Product", productId, productName, price);
            product.insertData(getConnection()); // Отправка в базу данных
            System.out.println("Добавлен объект Product в базу данных");

            // Дальнейшая обработка созданных объектов...
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении операции вставки данных");
            e.printStackTrace();
        }
    }
}