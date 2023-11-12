package com.example.frontend.controllers;
import com.example.frontend.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void loginButtonHandler(ActionEvent actionEvent) {
//        String email = emailField.getText();
//        String password = passwordField.getText();
//
//        try {
//            App.auth.signInWithEmailAndPassword(email, password);
//            System.out.println("Login successful!");
//        } catch (Exception e) {
//            showAlert("Authentication failed: " + e.getMessage());
//        }
        System.out.println("hello");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
