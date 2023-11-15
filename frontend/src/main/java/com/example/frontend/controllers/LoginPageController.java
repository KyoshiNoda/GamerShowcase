package com.example.frontend.controllers;

import com.example.frontend.App;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginPageController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void loginButtonHandler(ActionEvent actionEvent) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            var querySnapshot = App.db.collection("Users").whereEqualTo("email", email).get().get();
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot userSnapshot = querySnapshot.getDocuments().get(0);
                String storedHashedPassword = userSnapshot.getString("password");

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
                    Parent root = loader.load();
//                    MainPageController mainPageController = loader.getController();
//                    mainPageController.setUserData(
//                            userSnapshot.getString("firstName"),
//                            userSnapshot.getString("lastName"),
//                            userSnapshot.getString("email"),
//                    );
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    showAlert("Invalid password");
                }
            } else {
                showAlert("Incorrect Information!");
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            showAlert("Error during login: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
