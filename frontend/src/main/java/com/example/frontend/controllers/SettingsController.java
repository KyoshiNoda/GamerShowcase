package com.example.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField; // Change import to TextField
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private TextField Name_Box;

    @FXML
    protected void Return_To_Main_Page() {
        loadScene("/com/example/frontend/setting-page.fxml");
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
    public void changeFirstName() {
        loadScene("/com/example/frontend/userSettings/changeFirstName.fxml");
    }

    @FXML
    public void changeLastName() {
        loadScene("/com/example/frontend/userSettings/changeLastName.fxml");
    }

    @FXML
    public void changeEmail() {
        loadScene("/com/example/frontend/userSettings/changeEmail.fxml");
    }

    @FXML
    public void changePassword() {
        loadScene("/com/example/frontend/userSettings/changePassword.fxml");
    }


}
