package com.example.lab_12_test1.managers;

import com.example.lab_12_test1.database.DatabaseConnector;
import com.example.lab_12_test1.managers.Tables.Product;
import com.example.lab_12_test1.managers.Tables.DBTable;
import com.example.lab_12_test1.managers.Tables.DBTableFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class ProductManager implements Manager<Product> {
    private ObservableList<Product> products;
    private Connection connection; // Подключение к БД

    public ProductManager(Connection connection) {
        this.products = FXCollections.observableArrayList();
        this.connection = connection;
    }

    @Override
    public void add(Product product) {
        products.add(product);
        product.insertData(connection); // Вставка данных продукта в БД
    }

    @Override
    public void remove(Product product) {
        products.remove(product);
        product.deleteData(connection); // Удаление данных продукта из БД
    }

    @Override
    public void update(Product product) {
        // Обновление данных продукта в локальной коллекции
        int index = products.indexOf(product);
        if (index != -1) {
            products.set(index, product);
        }
        product.updateData(connection); // Обновление данных продукта в БД
    }

    @Override
    public ObservableList<Product> getAll() {
        // Извлечение данных всех продуктов из БД и добавление их в локальную коллекцию
        Product product = (Product) DBTableFactory.create("Product", null, null, null);
        Connection connection = DatabaseConnector.getConnection();
        product.retrieveData(connection);
        return products;
    }
}