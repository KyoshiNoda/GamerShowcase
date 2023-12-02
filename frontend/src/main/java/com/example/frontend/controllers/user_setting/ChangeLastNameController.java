package com.example.frontend.controllers.user_setting;

import com.example.frontend.User;
import com.example.frontend.controllers.SettingPageController;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.example.frontend.App;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.frontend.utils.Utils.showAlert;

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
        String updatedLastName = newLastName.getText();

        if (!updatedLastName.isEmpty()) {
            currentLastName.setText(updatedLastName);
            try {
                DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
                DocumentSnapshot userSnapshot = userRef.get().get();
                if (userSnapshot.exists()) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("lastName", updatedLastName);
                    userRef.set(updates, com.google.cloud.firestore.SetOptions.merge());
                    currentUser.setLastName(updatedLastName);
                    showAlert("Last name updated successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("User not found in Firestore.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("An error occurred while updating the last name.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
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
