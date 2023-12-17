package com.example.lab_12_test1.managers;

import com.example.lab_12_test1.database.DatabaseConnector;
import com.example.lab_12_test1.managers.Tables.Customer;
import com.example.lab_12_test1.managers.Tables.DBTable;
import com.example.lab_12_test1.managers.Tables.DBTableFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class CustomerManager implements Manager<Customer> {
    private ObservableList<Customer> customers;
    private Connection connection; // Подключение к БД

    public CustomerManager(Connection connection) {
        this.customers = FXCollections.observableArrayList();
        this.connection = connection;
    }

    @Override
    public void add(Customer customer) {
        customers.add(customer);
        customer.insertData(connection); // Вставка данных покупателя в БД
    }

    @Override
    public void remove(Customer customer) {
        customers.remove(customer);
        // Добавьте код для удаления данных покупателя из БД
    }

    @Override
    public void update(Customer customer) {
        // Обновление данных покупателя в локальной коллекции
        int index = customers.indexOf(customer);
        if (index != -1) {
            customers.set(index, customer);
        }
        customer.updateData(connection); // Обновление данных покупателя в БД
    }

    @Override
    public ObservableList<Customer> getAll() {
        // Извлечение данных всех покупателей из БД и добавление их в локальную коллекцию
        Customer customer = (Customer) DBTableFactory.create("Customer", null, null, null);
        Connection connection = DatabaseConnector.getConnection();
        customer.retrieveData(connection);
        return customers;
    }
}