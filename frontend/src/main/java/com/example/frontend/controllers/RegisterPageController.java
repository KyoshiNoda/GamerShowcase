package com.example.frontend.controllers;

import com.example.frontend.App;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.api.core.ApiFuture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterPageController {
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField emailInput;
    @FXML private PasswordField passwordInput;
    @FXML private PasswordField confirmPasswordInput;
    @FXML private Button submitButton;

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
            // AUTH
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);
            UserRecord userRecord = App.auth.createUser(request);
            String uid = userRecord.getUid();
            System.out.println("Successfully created user with UID: " + uid);

            // DATABASE
            DocumentReference userDocRef = App.db.collection("Users").document(uid);
            Map<String, Object> userData = new HashMap<>();
            userData.put("firstName", firstName);
            userData.put("lastName", lastName);
            userData.put("email", email);
            userData.put("favGames", new ArrayList<String>());
            ApiFuture<WriteResult> result = userDocRef.set(userData);
            System.out.println("User data added to Firestore for UID: " + uid);

        } catch (FirebaseAuthException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }
}
