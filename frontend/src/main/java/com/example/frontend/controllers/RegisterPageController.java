package com.example.frontend.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterPageController {
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField emailInput;
    @FXML private PasswordField passwordInput;
    @FXML private PasswordField confirmPasswordInput;
    @FXML private Button submitButton;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @FXML
    private void submitHandler(ActionEvent event) {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String email = emailInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        if (!password.equals(confirmPassword)) {
            System.out.println("Password and Confirm Password do not match");
            return;
        }

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);
            UserRecord userRecord = auth.createUser(request);
            System.out.println("Successfully created user with UID: " + userRecord.getUid());
        } catch (FirebaseAuthException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }
}
