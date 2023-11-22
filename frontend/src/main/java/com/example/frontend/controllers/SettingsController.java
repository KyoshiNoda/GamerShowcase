package com.example.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField; // Change import to TextField
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private TextField Name_Box; // Change type to TextField

    @FXML
    protected void Return_To_Main_Page() {
        // Implement your logic here if needed
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) Name_Box.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Go_To_Change_First_Name() {
        loadScene("/com/example/frontend/ChangeFirstName.fxml");
    }

    @FXML
    public void Go_To_Change_Last_Name() {
        loadScene("/com/example/frontend/ChangeLastName.fxml");
    }

    @FXML
    public void Go_To_Change_Username() {
        loadScene("/com/example/frontend/ChangeUsername.fxml");
    }

    @FXML
    public void Go_To_Change_Email() {
        loadScene("/com/example/frontend/ChangeEmail.fxml");
    }
}
