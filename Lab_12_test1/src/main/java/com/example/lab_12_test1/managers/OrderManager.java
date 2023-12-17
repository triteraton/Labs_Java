package com.example.lab_12_test1.managers;

import com.example.lab_12_test1.database.DatabaseConnector;
import com.example.lab_12_test1.managers.Tables.Order;
import com.example.lab_12_test1.managers.Tables.DBTable;
import com.example.lab_12_test1.managers.Tables.DBTableFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;

import static com.example.lab_12_test1.database.DatabaseConnector.getConnection;

public class OrderManager implements Manager<Order> {
    private ObservableList<Order> orders;
    private Connection connection = getConnection(); // Подключение к БД

    public OrderManager() {
        this.orders = FXCollections.observableArrayList();
    }

    @Override
    public void add(Order order) {
        orders.add(order);
        order.insertData(connection); // Вставка данных заказа в БД
    }

    @Override
    public void remove(Order order) {
        orders.remove(order);
        order.deleteData(connection); // Удаление данных заказа из БД
    }

    @Override
    public void update(Order order) {
        // Обновление данных заказа в локальной коллекции
        int index = orders.indexOf(order);
        if (index != -1) {
            orders.set(index, order);
        }
        order.updateData(connection); // Обновление данных заказа в БД
    }

    @Override
    public ObservableList<Order> getAll() {
        ObservableList<Order> orders = FXCollections.observableArrayList(); // Создание локальной коллекции orders
        Order order = (Order) DBTableFactory.create("Order", null, null, null);
        orders = order.retrieveData(connection);

        return orders;
    }
}