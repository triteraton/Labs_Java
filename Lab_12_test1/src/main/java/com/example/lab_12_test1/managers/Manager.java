package com.example.lab_12_test1.managers;

import javafx.collections.ObservableList;

// Менеджеры для управления данными
public interface Manager<T> {
    void add(T item);
    void remove(T item);
    void update(T item);
    ObservableList<T> getAll();
}