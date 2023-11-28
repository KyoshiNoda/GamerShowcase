module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires java.net.http;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires com.google.gson;
    requires jbcrypt;

    opens com.example.frontend to javafx.fxml, google.cloud.firestore; // Open to both modules
    exports com.example.frontend;
    exports com.example.frontend.controllers;
    opens com.example.frontend.controllers to javafx.fxml;
    exports com.example.frontend.controllers.user_setting;
    opens com.example.frontend.controllers.user_setting to javafx.fxml;
}
