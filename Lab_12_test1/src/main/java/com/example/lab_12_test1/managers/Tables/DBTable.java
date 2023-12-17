package com.example.lab_12_test1.managers.Tables;

import java.sql.Connection;
import java.util.List;

public interface DBTable<T> {
    void insertData(Connection connection);
    List<T> retrieveData(Connection connection);
    void updateData(Connection connection);
    void deleteData(Connection connection);
}