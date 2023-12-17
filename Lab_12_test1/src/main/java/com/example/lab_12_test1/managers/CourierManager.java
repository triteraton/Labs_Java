package com.example.lab_12_test1.managers;

import com.example.lab_12_test1.database.DatabaseConnector;
import com.example.lab_12_test1.managers.Tables.Courier;
import com.example.lab_12_test1.managers.Tables.DBTable;
import com.example.lab_12_test1.managers.Tables.DBTableFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class CourierManager implements Manager<Courier> {
    private ObservableList<Courier> couriers;
    private Connection connection; // Подключение к БД

    public CourierManager(Connection connection) {
        this.couriers = FXCollections.observableArrayList();
        this.connection = connection;
    }

    @Override
    public void add(Courier courier) {
        couriers.add(courier);
        courier.insertData(connection); // Вставка данных курьера в БД
    }

    @Override
    public void remove(Courier courier) {
        couriers.remove(courier);
        courier.deleteData(connection); // Удаление данных курьера из БД
    }

    @Override
    public void update(Courier courier) {
        // Обновление данных курьера в локальной коллекции
        int index = couriers.indexOf(courier);
        if (index != -1) {
            couriers.set(index, courier);
        }
        courier.updateData(connection); // Обновление данных курьера в БД
    }

    @Override
    public ObservableList<Courier> getAll() {
        // Извлечение данных всех курьеров из БД и добавление их в локальную коллекцию
        Courier courier = (Courier) DBTableFactory.create("Courier", null, null, null);
        Connection connection = DatabaseConnector.getConnection();
        courier.retrieveData(connection);
        return couriers;
    }
}