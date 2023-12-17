package com.example.lab_12_test1;

import com.example.lab_12_test1.managers.*;
import com.example.lab_12_test1.managers.Tables.Courier;
import com.example.lab_12_test1.managers.Tables.Customer;
import com.example.lab_12_test1.managers.Tables.Order;
import com.example.lab_12_test1.managers.Tables.Product;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.lab_12_test1.database.DatabaseConnector.getConnection;

public class ManagementApp extends Application {
    private Manager<Order> orderManager;
    private Manager<Product> productManager;
    private Manager<Customer> customerManager;
    private Manager<Courier> courierManager;
    private GridPane contentPane;
    private Connection connection = getConnection();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Учет");

        // Создание меню выбора экрана
        MenuBar menuBar = new MenuBar();

        Menu ordersMenu = new Menu("Заказы");
        MenuItem ordersMenuItem = new MenuItem("Заказы");
        ordersMenuItem.setOnAction(event -> switchToOrderManagement());
        ordersMenu.getItems().add(ordersMenuItem);

        Menu productsMenu = new Menu("Продукты");
        MenuItem productsMenuItem = new MenuItem("Продукты");
        productsMenuItem.setOnAction(event -> switchToProductManagement());
        productsMenu.getItems().add(productsMenuItem);

        Menu couriersMenu = new Menu("Курьеры");
        MenuItem couriersMenuItem = new MenuItem("Курьеры");
        couriersMenuItem.setOnAction(event -> switchToCouriersManagement());
        couriersMenu.getItems().add(couriersMenuItem);

        Menu customersMenu = new Menu("Покупатели");
        MenuItem customersMenuItem = new MenuItem("Покупатели");
        customersMenuItem.setOnAction(event -> switchToCustomersManagement());
        customersMenu.getItems().add(customersMenuItem);

        menuBar.getMenus().addAll(ordersMenu, productsMenu, couriersMenu, customersMenu);

        // Создание корневого контейнера
        VBox root = new VBox();
        root.getChildren().add(menuBar);

        // Создание контейнера для отображения разделов
        contentPane = new GridPane();
        contentPane.setHgap(10);
        contentPane.setVgap(10);
        contentPane.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().add(contentPane);

        // Установка сцены
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Создание менеджеров для каждой сущности
        orderManager = new OrderManager();
        productManager = new ProductManager(connection);
        customerManager = new CustomerManager(connection);
        courierManager = new CourierManager(connection);

        // По умолчанию открываем экран "Заказы"
        switchToOrderManagement();
    }

    private void switchToOrderManagement() {
        TableView<Order> orderTable = new TableView<>();
        TableColumn<Order, String> orderIdColumn = new TableColumn<>("ID заказа");
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        TableColumn<Order, String> customerColumn = new TableColumn<>("Клиент");
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        TableColumn<Order, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        orderTable.getColumns().addAll(orderIdColumn, customerColumn, statusColumn);

        // Создание полей для добавления нового заказа
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("ID заказа");
        TextField customerField = new TextField();
        customerField.setPromptText("Клиент");
        TextField statusField = new TextField();
        statusField.setPromptText("Статус");

        // Создание кнопок
        Button addButton = new Button("Добавить");
        Button removeButton = new Button("Удалить");
        Button updateButton = new Button("Обновить");

        OrderManager orderManager = new OrderManager(); // Create an instance of OrderManager

        if (hasData("Order")) {
            orderTable.setItems(orderManager.getAll());
        }

        addButton.setOnAction(event -> {
            String orderId = orderIdField.getText();
            String customer = customerField.getText();
            String status = statusField.getText();
            Order order = new Order(orderId, customer, status);
            orderManager.add(order);

            // Очистка полей после добавления заказа
            orderIdField.clear();
            customerField.clear();
            statusField.clear();

            // Обновление данных в таблице
            orderTable.setItems(orderManager.getAll());
        });

        removeButton.setOnAction(event -> {
            Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                orderManager.remove(selectedOrder);

                // Обновление данных в таблице
                orderTable.setItems(orderManager.getAll());
            }
        });

        updateButton.setOnAction(event -> {
            Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                orderManager.update(selectedOrder);

                // Обновление данных в таблице
                orderTable.setItems(orderManager.getAll());
            }
        });

        // Создание контейнера для полей добавления нового заказа
        VBox addOrderContainer = new VBox(10);
        addOrderContainer.getChildren().addAll(orderIdField, customerField, statusField);

        // Создание контейнера для кнопок
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(addButton, removeButton, updateButton);

        // Создание контейнера для таблицы и полей добавления заказа
        VBox contentContainer = new VBox(10);
        contentContainer.getChildren().addAll(orderTable, addOrderContainer, buttonContainer);

        // Очистка текущего содержимого и добавление нового контента
        contentPane.getChildren().clear();
        contentPane.add(contentContainer, 0, 0);

        // Добавление слушателя для заполнения полей данными по выбранной строке таблицы
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderIdField.setText(newValue.orderIdProperty().getValue());
                customerField.setText(newValue.customerProperty().getValue());
                statusField.setText(newValue.statusProperty().getValue());
            }
        });
    }

    private void switchToProductManagement() {
        TableView<Product> productTable = new TableView<>();
        TableColumn<Product, String> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        productTable.getColumns().addAll(productIdColumn, nameColumn, priceColumn);

        // Create fields for adding a new product
        TextField productIdField = new TextField();
        productIdField.setPromptText("Product ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Product Price");

        // Create buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button updateButton = new Button("Update");

        ProductManager productManager = new ProductManager(connection); // Create an instance of ProductManager

        if (hasData("Product")) {
            productTable.setItems(productManager.getAll());
        }

        addButton.setOnAction(event -> {
            String productId = productIdField.getText();
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            Product product = new Product(productId, name, price);
            productManager.add(product);

            // Clear fields after adding a product
            productIdField.clear();
            nameField.clear();
            priceField.clear();

            // Update data in the table
            productTable.setItems(productManager.getAll());
        });

        removeButton.setOnAction(event -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productManager.remove(selectedProduct);

                // Update data in the table
                productTable.setItems(productManager.getAll());
            }
        });

        updateButton.setOnAction(event -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productManager.update(selectedProduct);

                // Update data in the table
                productTable.setItems(productManager.getAll());
            }
        });

        // Create container for fields to add a new product
        VBox addProductContainer = new VBox(10);
        addProductContainer.getChildren().addAll(productIdField, nameField, priceField);

        // Create container for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(addButton, removeButton, updateButton);

        // Create container for the table and fields to add a new product
        VBox contentContainer = new VBox(10);
        contentContainer.getChildren().addAll(productTable, addProductContainer, buttonContainer);

        // Clear the current content and add the new content
        contentPane.getChildren().clear();
        contentPane.add(contentContainer, 0, 0);

        // Add a listener to populate the fields with data from the selected table row
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productIdField.setText(newValue.productIdProperty().getValue());
                nameField.setText(newValue.nameProperty().getValue());
                priceField.setText(String.valueOf(newValue.priceProperty().getValue()));
            }
        });
    }

    private void switchToCouriersManagement() {
        TableView<Courier> courierTable = new TableView<>();
        TableColumn<Courier, String> courierIdColumn = new TableColumn<>("Courier ID");
        courierIdColumn.setCellValueFactory(cellData -> cellData.getValue().courierIdProperty());
        TableColumn<Courier, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Courier, String> vehicleColumn = new TableColumn<>("Vehicle");
        vehicleColumn.setCellValueFactory(cellData -> cellData.getValue().vehicleProperty());
        courierTable.getColumns().addAll(courierIdColumn, nameColumn, vehicleColumn);

        // Create fields for adding a new courier
        TextField courierIdField = new TextField();
        courierIdField.setPromptText("Courier ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Courier Name");
        TextField vehicleField = new TextField();
        vehicleField.setPromptText("Courier Vehicle");

        // Create buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button updateButton = new Button("Update");

        CourierManager courierManager = new CourierManager(connection); // Create an instance of CourierManager

        if (hasData("Couriers")) {
            courierTable.setItems(courierManager.getAll());
        }

        addButton.setOnAction(event -> {
            String courierId = courierIdField.getText();
            String name = nameField.getText();
            String vehicle = vehicleField.getText();
            Courier courier = new Courier(courierId, name, vehicle);
            courierManager.add(courier);

            // Clear fields after adding a courier
            courierIdField.clear();
            nameField.clear();
            vehicleField.clear();

            // Update data in the table
            courierTable.setItems(courierManager.getAll());
        });

        removeButton.setOnAction(event -> {
            Courier selectedCourier = courierTable.getSelectionModel().getSelectedItem();
            if (selectedCourier != null) {
                courierManager.remove(selectedCourier);

                // Update data in the table
                courierTable.setItems(courierManager.getAll());
            }
        });

        updateButton.setOnAction(event -> {
            Courier selectedCourier = courierTable.getSelectionModel().getSelectedItem();
            if (selectedCourier != null) {
                courierManager.update(selectedCourier);

                // Update data in the table
                courierTable.setItems(courierManager.getAll());
            }
        });

        // Create container for fields to add a new courier
        VBox addCourierContainer = new VBox(10);
        addCourierContainer.getChildren().addAll(courierIdField, nameField, vehicleField);

        // Create container for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(addButton, removeButton, updateButton);

        // Create container for the table and fields to add a new courier
        VBox contentContainer = new VBox(10);
        contentContainer.getChildren().addAll(courierTable, addCourierContainer, buttonContainer);

        // Clear the current content and add the new content
        contentPane.getChildren().clear();
        contentPane.add(contentContainer, 0, 0);

        // Add a listener to populate the fields with data from the selected table row
        courierTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                courierIdField.setText(newValue.courierIdProperty().getValue());
                nameField.setText(newValue.nameProperty().getValue());
                vehicleField.setText(newValue.vehicleProperty().getValue());
            }
        });
    }

    private void switchToCustomersManagement() {
        TableView<Customer> customerTable = new TableView<>();
        TableColumn<Customer, String> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        customerTable.getColumns().addAll(customerIdColumn, nameColumn, addressColumn);

        // Create fields for adding a new customer
        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");
        TextField addressField = new TextField();
        addressField.setPromptText("Customer Address");

        // Create buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button updateButton = new Button("Update");

        CustomerManager customerManager = new CustomerManager(connection);

        if (hasData("Customers")) {
            customerTable.setItems(customerManager.getAll());
        }

        addButton.setOnAction(event -> {
            String customerId = customerIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            Customer customer = new Customer(customerId, name, address);
            customerManager.add(customer);

            // Clear fields after adding a customer
            customerIdField.clear();
            nameField.clear();
            addressField.clear();

            // Update data in the table
            customerTable.setItems(customerManager.getAll());
        });

        removeButton.setOnAction(event -> {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                customerManager.remove(selectedCustomer);

                // Update data in the table
                customerTable.setItems(customerManager.getAll());
            }
        });

        updateButton.setOnAction(event -> {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                customerManager.update(selectedCustomer);

                // Update data in the table
                customerTable.setItems(customerManager.getAll());
            }
        });

        // Create container for fields to add a new customer
        VBox addCustomerContainer = new VBox(10);
        addCustomerContainer.getChildren().addAll(customerIdField, nameField, addressField);

        // Create container for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(addButton, removeButton, updateButton);

        // Create container for the table and fields to add a new customer
        VBox contentContainer = new VBox(10);
        contentContainer.getChildren().addAll(customerTable, addCustomerContainer, buttonContainer);

        // Clear the current content and add the new content
        contentPane.getChildren().clear();
        contentPane.add(contentContainer, 0, 0);

        // Add a listener to populate the fields with data from the selected table row
    }

    public boolean hasData(String tableName) {
        // Ваш код для проверки наличия данных в таблице
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(*) FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}