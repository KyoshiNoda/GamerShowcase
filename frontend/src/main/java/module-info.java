module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;


    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;
}