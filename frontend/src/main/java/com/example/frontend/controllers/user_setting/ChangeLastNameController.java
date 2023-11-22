package com.example.frontend.controllers.user_setting;

import com.example.frontend.User;
import com.example.frontend.controllers.SettingPageController;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
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
import java.util.HashMap;
import java.util.Map;

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
        String updatedFirstName = newLastName.getText();

        if (!updatedFirstName.isEmpty()) {
            currentLastName.setText(updatedFirstName);
            try {
                DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
                DocumentSnapshot userSnapshot = userRef.get().get();
                if (userSnapshot.exists()) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("lastName", updatedFirstName);
                    userRef.set(updates, com.google.cloud.firestore.SetOptions.merge());
                    System.out.println("Last name updated successfully!");
                } else {
                    System.out.println("User not found in Firestore.");
                }
            } catch (Exception e) {
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
