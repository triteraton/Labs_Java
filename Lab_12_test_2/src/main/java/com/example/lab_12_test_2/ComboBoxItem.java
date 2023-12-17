package com.example.lab_12_test_2;

public class ComboBoxItem {
    private int id;
    private String name;

    public ComboBoxItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
