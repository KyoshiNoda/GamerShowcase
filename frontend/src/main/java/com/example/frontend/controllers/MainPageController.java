package com.example.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainPageController {
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    public void setUserData(String firstName, String lastName, String email) {
        firstNameLabel.setText("First Name: " + firstName);
        lastNameLabel.setText("Last Name: " + lastName);
        emailLabel.setText("Email: " + email);
    }
}
