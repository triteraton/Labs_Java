package com.example.lab_12_test_2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.lab_12_test_2.DatabaseConnector.getConnection;

public class DataManagementApp extends Application {

    private Connection connection;

    private TextField customerNameField;
    private TextField contactInfoField;
    private TextArea customerDataDisplayArea;

    private TextField orderDateField;
    private ComboBox<ComboBoxItem> productListComboBox;
    private ComboBox<ComboBoxItem> courierListComboBox;
    private TextArea orderDataDisplayArea;
    TextField productField = new TextField();
    TextArea productDataDisplayArea = new TextArea();
    TextField courierField = new TextField();
    TextArea courierDataDisplayArea = new TextArea();
    TextField productPriceField = new TextField();
    TextField courierContactInfoField = new TextField();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        // Установить соединение с базой данных
        connection = getConnection();

        // Создать элементы управления для страницы клиентов
        customerNameField = new TextField();
        contactInfoField = new TextField();
        customerDataDisplayArea = new TextArea();
        customerDataDisplayArea.setEditable(false);



        Button insertCustomerButton = new Button("Добавить клиента");
        insertCustomerButton.setOnAction(e -> insertDataIntoCustomersTable());

        Button retrieveCustomerButton = new Button("Получить клиентов");
        retrieveCustomerButton.setOnAction(e -> retrieveDataFromCustomersTable());

        // Создать элементы управления для страницы заказов
        orderDateField = new TextField();
        productListComboBox = new ComboBox<ComboBoxItem>(); // Обновлено
        courierListComboBox = new ComboBox<ComboBoxItem>(); // Обновлено
        orderDataDisplayArea = new TextArea();
        orderDataDisplayArea.setEditable(false);

        // Создать элементы управления для страницы продуктов
        ListView<ComboBoxItem> productList = new ListView<>();
        ObservableList<ComboBoxItem> products = FXCollections.observableArrayList();
        productList.setItems(products);
        productListComboBox.setItems(products);

        // Создать элементы управления для страницы курьеров
        ListView<ComboBoxItem> courierList = new ListView<>();
        ObservableList<ComboBoxItem> couriers = FXCollections.observableArrayList();
        courierList.setItems(couriers);
        courierListComboBox.setItems(couriers);

        Button insertOrderButton = new Button("Добавить заказ");
        insertOrderButton.setOnAction(e -> insertDataIntoOrdersTable());

        Button retrieveOrderButton = new Button("Получить заказы");
        retrieveOrderButton.setOnAction(e -> retrieveDataFromOrdersTable());

        Button insertProductButton = new Button("Добавить продукт");
        insertProductButton.setOnAction(e -> insertDataIntoProductsTable());
        Button retrieveProductButton = new Button("Получить продукты");
        retrieveProductButton.setOnAction(e -> retrieveDataFromProductsTable());
        productDataDisplayArea.setEditable(false);

        Button insertCourierButton = new Button("Добавить курьера");
        insertCourierButton.setOnAction(e -> insertDataIntoCouriersTable());
        Button retrieveCourierButton = new Button("Получить курьеров");
        retrieveCourierButton.setOnAction(e -> retrieveDataFromCouriersTable());
        courierDataDisplayArea.setEditable(false);

        // Создать сетку и расположить элементы управления для страницы клиентов
        GridPane customerGridPane = new GridPane();
        customerGridPane.setPadding(new Insets(10));
        customerGridPane.setHgap(10);
        customerGridPane.setVgap(10);
        customerGridPane.add(new Label("Имя клиента:"), 0, 0);
        customerGridPane.add(customerNameField, 1, 0);
        customerGridPane.add(new Label("Контактная информация:"), 0, 1);
        customerGridPane.add(contactInfoField, 1, 1);
        customerGridPane.add(insertCustomerButton, 0, 2);
        customerGridPane.add(retrieveCustomerButton, 1, 2);
        customerGridPane.add(new Label("Данные клиентов:"), 0, 3);
        customerGridPane.add(customerDataDisplayArea, 0, 4, 2, 1);

        // Создать сетку и расположить элементы управления для страницы заказов
        GridPane orderGridPane = new GridPane();
        orderGridPane.setPadding(new Insets(10));
        orderGridPane.setHgap(10);
        orderGridPane.setVgap(10);
        orderGridPane.add(new Label("Дата заказа:"), 0, 0);
        orderGridPane.add(orderDateField, 1, 0);
        orderGridPane.add(new Label("Продукт:"), 0, 1);
        orderGridPane.add(productListComboBox, 1, 1);
        orderGridPane.add(new Label("Курьер:"), 0, 2);
        orderGridPane.add(courierListComboBox, 1, 2);
        orderGridPane.add(insertOrderButton, 0, 3);
        orderGridPane.add(retrieveOrderButton, 1, 3);
        orderGridPane.add(new Label("Данные заказов:"), 0, 4);
        orderGridPane.add(orderDataDisplayArea, 0, 5, 2, 1);

        // Создать сетку и расположить элементы управления для страницы продуктов
        GridPane productGridPane = new GridPane();
        productGridPane.setPadding(new Insets(10));
        productGridPane.setHgap(10);
        productGridPane.setVgap(10);
        productGridPane.add(new Label("Продукт:"), 0, 0);
        productGridPane.add(new Label("Цена продукта:"), 0, 1);
        productGridPane.add(productPriceField, 1, 1);
        productGridPane.add(productField, 1, 0);
        productGridPane.add(insertProductButton, 0, 1);
        productGridPane.add(retrieveProductButton, 1, 1);
        productGridPane.add(new Label("Данные продуктов:"), 0, 2);
        productGridPane.add(productDataDisplayArea, 0, 3, 2, 1);

        // Создать сетку и расположить элементы управления для страницы курьеров
        GridPane courierGridPane = new GridPane();
        courierGridPane.setPadding(new Insets(10));
        courierGridPane.setHgap(10);
        courierGridPane.setVgap(10);
        courierGridPane.add(new Label("Контактная информация:"), 0, 1);
        courierGridPane.add(courierContactInfoField, 1, 1);
        courierGridPane.add(new Label("Курьер:"), 0, 0);
        courierGridPane.add(courierField, 1, 0);
        courierGridPane.add(insertCourierButton, 0, 1);
        courierGridPane.add(retrieveCourierButton, 1, 1);
        courierGridPane.add(new Label("Данные курьеров:"), 0, 2);
        courierGridPane.add(courierDataDisplayArea, 0, 3, 2, 1);

        // Создать вкладки и добавить страницы вкладок
        TabPane tabPane = new TabPane();
        Tab customersTab = new Tab("Клиенты", customerGridPane);
        Tab ordersTab = new Tab("Заказы", orderGridPane);
        Tab productsTab = new Tab("Продукты", productGridPane);
        Tab couriersTab = new Tab("Курьеры", courierGridPane);
        tabPane.getTabs().addAll(customersTab, ordersTab, productsTab, couriersTab);

        // Загрузка значений продуктов из таблицы "Products"
        String productQuery = "SELECT * FROM Products";
        PreparedStatement productStatement = connection.prepareStatement(productQuery);
        ResultSet productResultSet = productStatement.executeQuery();
        while (productResultSet.next()) {
            int productId = productResultSet.getInt("product_id");
            String productName = productResultSet.getString("product_name");
            products.add(new ComboBoxItem(productId, productName));
        }

// Загрузка значений курьеров из таблицы "Couriers"
        String courierQuery = "SELECT * FROM Couriers";
        PreparedStatement courierStatement = connection.prepareStatement(courierQuery);
        ResultSet courierResultSet = courierStatement.executeQuery();
        while (courierResultSet.next()) {
            int courierId = courierResultSet.getInt("courier_id");
            String courierName = courierResultSet.getString("courier_name");
            couriers.add(new ComboBoxItem(courierId, courierName));
        }

        // Создать сцену и установить ее на подмостках
        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Управление данными");
        primaryStage.show();
    }

    private void insertDataIntoCustomersTable() {
        String customerName =customerNameField.getText();
        String contactInfo = contactInfoField.getText();

        try {
            String query = "INSERT INTO Customers (customer_name, contact_info) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customerName);
            statement.setString(2, contactInfo);
            statement.executeUpdate();

            customerNameField.clear();
            contactInfoField.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Данные успешно добавлены в таблицу Customers.");
            alert.showAndWait();
        } catch (SQLException e) {
            showErrorAlert("Ошибка", "Произошла ошибка при добавлении данных:", e.getMessage());
        }
    }

    private void retrieveDataFromCustomersTable() {
        try {
            String query = "SELECT * FROM Customers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder dataBuilder = new StringBuilder();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customerName = resultSet.getString("customer_name");
                String contactInfo = resultSet.getString("contact_info");

                dataBuilder.append("Customer ID: ").append(customerId).append("\n");
                dataBuilder.append("Customer Name: ").append(customerName).append("\n");
                dataBuilder.append("Contact Info: ").append(contactInfo).append("\n\n");
            }

            customerDataDisplayArea.setText(dataBuilder.toString());
        } catch (SQLException e) {
            showErrorAlert("Ошибка", "Произошла ошибка при получении данных:", e.getMessage());
        }
    }

    // Метод для вставки данных в таблицу "Orders"
    private void insertDataIntoOrdersTable() {
        String orderDate = orderDateField.getText();
        ComboBoxItem selectedProduct = productListComboBox.getValue();
        ComboBoxItem selectedCourier = courierListComboBox.getValue();

        if (orderDate.isEmpty() || selectedProduct == null || selectedCourier == null) {
            // Проверка наличия введенных данных
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, заполните все поля заказа.");
            alert.showAndWait();
            return;
        }

        ComboBoxItem productId = selectedProduct;
        ComboBoxItem courierId = selectedCourier;

        try {
            // Создание SQL-запроса для вставки данных заказа
            String insertQuery = "INSERT INTO orders (order_date, product_id, courier_id) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, orderDate);
            preparedStatement.setInt(2, Integer.parseInt(String.valueOf(productId)));
            preparedStatement.setInt(3, Integer.parseInt(String.valueOf(courierId)));

            // Выполнение SQL-запроса
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Успешное выполнение запроса
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Успех");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Заказ успешно добавлен в таблицу заказов.");
                successAlert.showAndWait();

                // Очистка полей ввода
                orderDateField.clear();
                productListComboBox.getSelectionModel().clearSelection();
                courierListComboBox.getSelectionModel().clearSelection();
            } else {
                // Ошибка выполнения запроса
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Ошибка");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Не удалось добавить заказ в таблицу заказов.");
                errorAlert.showAndWait();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveDataFromOrdersTable() {
        try {
            String query = "SELECT * FROM Orders";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder dataBuilder = new StringBuilder();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String orderDate = resultSet.getString("order_date");
                int productId = resultSet.getInt("product_id");
                int courierId = resultSet.getInt("courier_id");

                // Получение имени продукта и курьера по их идентификаторам
                String productName = getProductName(productId);
                String courierName = getCourierName(courierId);

                dataBuilder.append("Order ID: ").append(orderId).append("\n");
                dataBuilder.append("Order Date: ").append(orderDate).append("\n");
                dataBuilder.append("Product: ").append(productName).append("\n");
                dataBuilder.append("Courier: ").append(courierName).append("\n\n");
            }

            orderDataDisplayArea.setText(dataBuilder.toString());
        } catch (SQLException e) {
            showErrorAlert("Ошибка", "Произошла ошибка при получении данных:", e.getMessage());
        }
    }

    private String getProductName(int productId) throws SQLException {
        String query = "SELECT product_name FROM Products WHERE product_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("product_name");
        } else {
            return "";
        }
    }

    private String getCourierName(int courierId) throws SQLException {
        String query = "SELECT courier_name FROM Couriers WHERE courier_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, courierId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("courier_name");
        } else {
            return "";
        }
    }
    private void insertDataIntoProductsTable() {
        try {
            String productName = productField.getText();
            double productPrice = Double.parseDouble(productPriceField.getText());

            // Создание SQL-запроса для вставки данных в таблицу "Products"
            String insertQuery = "INSERT INTO Products (product_name, product_price) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, productName);
            insertStatement.setDouble(2, productPrice);

            // Выполнение SQL-запроса для вставки данных
            insertStatement.executeUpdate();

            // Очистка полей ввода
            productField.clear();
            productPriceField.clear();

            // Оповещение об успешной вставке данных
            System.out.println("Данные продукта успешно добавлены в таблицу.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveDataFromProductsTable() {
        try {
            String query = "SELECT * FROM Products";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder dataBuilder = new StringBuilder();
            while (resultSet.next()) {
                int productId= resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                dataBuilder.append("ID: ").append(productId).append(", Name: ").append(productName).append("\n");
            }

            productDataDisplayArea.setText(dataBuilder.toString());
        } catch (SQLException e) {
            showErrorAlert("Ошибка", "Произошла ошибка при получении данных:", e.getMessage());
        }
    }

    private void insertDataIntoCouriersTable() {
        try {
            String courierName = courierField.getText();
            String courierContactInfo = courierContactInfoField.getText();

            // Создание SQL-запроса для вставки данных в таблицу "Couriers"
            String insertQuery = "INSERT INTO Couriers (courier_name, contact_info) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, courierName);
            insertStatement.setString(2, courierContactInfo);

            // Выполнение SQL-запроса для вставки данных
            insertStatement.executeUpdate();

            // Очистка полей ввода
            courierField.clear();
            courierContactInfoField.clear();

            // Оповещение об успешной вставке данных
            System.out.println("Данные курьера успешно добавлены в таблицу.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveDataFromCouriersTable() {
        try {
            String query = "SELECT * FROM Couriers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder dataBuilder = new StringBuilder();
            while (resultSet.next()) {
                int courierId = resultSet.getInt("courier_id");
                String courierName = resultSet.getString("courier_name");
                dataBuilder.append("ID: ").append(courierId).append(", Name: ").append(courierName).append("\n");
            }

            courierDataDisplayArea.setText(dataBuilder.toString());
        } catch (SQLException e) {
            showErrorAlert("Ошибка", "Произошла ошибка при получении данных:", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        // Закрыть соединение с базой данных при завершении приложения
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}