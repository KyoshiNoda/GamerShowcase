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

import static com.example.frontend.utils.Utils.getVisualHeight;
import static com.example.frontend.utils.Utils.getVisualWidth;

public class SettingPageController {
    @FXML private Label nameBox;
    @FXML private User currentUser;

    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    protected void Return_To_Main_Page() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
        Scene scene = new Scene(loader.load(), 1350, 1000);
        MainPageController mainPageController = loader.getController();
        mainPageController.setUserData(currentUser);

        Stage stage = (Stage) nameBox.getScene().getWindow();
        stage.setX(getVisualWidth(1350));
        stage.setY(getVisualHeight(1000));
        stage.setScene(scene);
        stage.show();
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 650, 500);
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
            stage.setX(getVisualWidth(650));
            stage.setY(getVisualHeight(500));
            stage.setScene(scene);
            stage.show();
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
