package com.example.lab_12_test_2;

import com.example.lab_12_test_2.tables.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static com.example.lab_12_test_2.DatabaseConnector.getConnection;

public class TestDataPopulate {
    public static void main(String[] args) {
        // Установить соединение с базой данных
        Connection connection = getConnection();

        // Создать уникальный ключ на основе системного времени
        LocalDateTime currentTime = LocalDateTime.now();
        String uniqueKey = currentTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Создать курьера
        Courier courier = TableFactory.createCourier((int) Long.parseLong(uniqueKey), "John Doe", "123-456-7890", "Employed", "New York", connection);

        // Создать клиента
        Customer customer = TableFactory.createCustomer((int) Long.parseLong(uniqueKey), "Jane Smith", "jane@example.com", connection);

        // Создать продукт
        Product product = TableFactory.createProduct((int) Long.parseLong(uniqueKey), "Example Product", "Product description", new BigDecimal("9.99"), 10, connection);

        // Создать заказ
        LocalDateTime orderDate = LocalDateTime.now();
        Order order = TableFactory.createOrder((int) Long.parseLong(uniqueKey), orderDate, customer.getCustomerId(), courier.getCourierId(), "Pending", connection);

        // Связать продукт с заказом
        OrderProduct orderProduct = TableFactory.createOrderProduct(order.getOrderId(), product.getProductId(), connection);

        System.out.println("Тестовые данные успешно добавлены в базу данных.");
    }
}