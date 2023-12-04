package com.example.frontend.controllers;

import com.example.frontend.User;
import com.example.frontend.controllers.user_setting.ChangeEmailController;
import com.example.frontend.controllers.user_setting.ChangeFirstNameController;
import com.example.frontend.controllers.user_setting.ChangeLastNameController;
import com.example.frontend.controllers.user_setting.ChangePasswordController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingPageController {
    @FXML private Label nameBox;
    @FXML private User currentUser;

    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    protected void Return_To_Main_Page() {
        loadScene("/com/example/frontend/main-page.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 600, 400);
            if (loader.getController() instanceof MainPageController) {
                MainPageController mainPageController = loader.getController();
                mainPageController.setUserData(currentUser);
            } else if (loader.getController() instanceof ChangeFirstNameController) {
                ChangeFirstNameController changeFirstNameController = loader.getController();
                changeFirstNameController.setUserData(currentUser);
            } else if (loader.getController() instanceof ChangeLastNameController) {
                ChangeLastNameController changeLastNameController = loader.getController();
                changeLastNameController.setUserData(currentUser);
            } else if (loader.getController() instanceof ChangeEmailController) {
                ChangeEmailController changeEmailController = loader.getController();
                changeEmailController.setUserData(currentUser);
            } else if (loader.getController() instanceof ChangePasswordController) {
                ChangePasswordController changePasswordController = loader.getController();
                changePasswordController.setUserData(currentUser);
            }

            Stage stage = (Stage) nameBox.getScene().getWindow();
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

    public void returnFromPage(User user) {
        setUserData(user);
    }
}
