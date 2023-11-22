package com.example.frontend.controllers.user_setting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import com.example.frontend.App;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeEmailController {
    @FXML private Label currentEmail;
    @FXML private TextField newEmail;
    @FXML private Button backButton;


    @FXML private void initialize() {
        currentEmail.setText("kyoshisew@gmail.com");
    }

    @FXML
    public void saveEmailHandler() throws IOException {
        currentEmail.setText(newEmail.getText());
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
