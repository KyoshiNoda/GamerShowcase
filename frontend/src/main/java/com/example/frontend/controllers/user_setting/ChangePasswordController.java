package com.example.frontend.controllers.user_setting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;

public class ChangePasswordController {
    @FXML private PasswordField currentPassword;
    @FXML private PasswordField newPassword;
    @FXML private Button backButton;

    @FXML
    public void savePasswordHandler() throws IOException {
       System.out.println("New Password Saved!");
    }

    @FXML
    public void BackHandler() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/setting-page.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
