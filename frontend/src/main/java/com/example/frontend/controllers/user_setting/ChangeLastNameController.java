package com.example.frontend.controllers.user_setting;

import com.example.frontend.User;
import com.example.frontend.controllers.SettingPageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import com.example.frontend.App;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeLastNameController {
    @FXML private Label currentLastName;
    @FXML private TextField newLastName;
    @FXML private Button backButton;
    User currentUser;

    @FXML
    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
        currentLastName.setText(currentUser.getLastName());
    }

    @FXML
    public void saveLastNameHandler() throws IOException {
        currentLastName.setText(newLastName.getText());
    }

    @FXML
    public void BackHandler() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/setting-page.fxml"));
            Parent root = loader.load();

            SettingPageController settingPageController = loader.getController();
            settingPageController.returnFromPage(currentUser);

            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
