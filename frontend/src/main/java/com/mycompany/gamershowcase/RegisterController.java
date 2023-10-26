package com.mycompany.gamershowcase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField emailInput;
    @FXML private TextField passwordInput;
    @FXML private TextField confirmPasswordInput;
    @FXML private Button submitButton;
    
    
    public void initialize() {
        passwordInput.textProperty().addListener((observable, oldValue, newValue) -> {
            String maskedPassword = newValue.replaceAll(".", "*");
            passwordInput.setText(maskedPassword);
        });
        confirmPasswordInput.textProperty().addListener((observable, oldValue, newValue) -> {
            String maskedPassword = newValue.replaceAll(".", "*");
            confirmPasswordInput.setText(maskedPassword);
        });
    }

    
    
    @FXML
    private void submitHandler(ActionEvent event) {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String email = emailInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        // Print the values
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirmPassword);
    }
    
    
}
