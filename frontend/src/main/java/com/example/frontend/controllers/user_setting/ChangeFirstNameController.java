package com.example.frontend.controllers.user_setting;

import com.example.frontend.App;
import com.example.frontend.User;
import com.example.frontend.controllers.SettingPageController;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangeFirstNameController {
    @FXML private Label currentFirstName;
    @FXML private TextField newFirstName;
    @FXML private Button backButton;
    User currentUser;

    @FXML
    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
        currentFirstName.setText(currentUser.getFirstName());
    }

    @FXML
    public void saveFirstNameHandler() throws IOException {
        String updatedFirstName = newFirstName.getText();

        if (!updatedFirstName.isEmpty()) {
            currentFirstName.setText(updatedFirstName);
            try {
                DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
                DocumentSnapshot userSnapshot = userRef.get().get();
                if (userSnapshot.exists()) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("firstName", updatedFirstName);
                    userRef.set(updates, com.google.cloud.firestore.SetOptions.merge());
                    System.out.println("First name updated successfully!");
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
