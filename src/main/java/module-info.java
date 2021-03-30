module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jdk.jsobject;

    opens org.example to javafx.fxml;
    exports org.example;
}