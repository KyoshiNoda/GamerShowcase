package com.example.frontend.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void loginButtonHandler(ActionEvent actionEvent) {
        String email = emailField.getText();
        String password = passwordField.getText();
        System.out.println("email: " + email);
        System.out.println("Password: " + password);
    }
}
