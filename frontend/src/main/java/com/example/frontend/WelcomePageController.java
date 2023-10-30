package com.example.frontend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void loginPageHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);

            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void registerPageHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register-page.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);

            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}