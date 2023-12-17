module com.example.lab_12_test1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.lab_12_test1 to javafx.fxml;
    exports com.example.lab_12_test1;
    exports com.example.lab_12_test1.managers;
    opens com.example.lab_12_test1.managers to javafx.fxml;
    exports com.example.lab_12_test1.managers.Tables;
    opens com.example.lab_12_test1.managers.Tables to javafx.fxml;
}