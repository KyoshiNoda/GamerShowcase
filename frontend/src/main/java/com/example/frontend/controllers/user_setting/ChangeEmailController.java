package com.example.frontend.controllers.user_setting;

import com.example.frontend.User;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.example.frontend.App;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;
import static com.example.frontend.utils.Utils.backToSettingHandler;
import static com.example.frontend.utils.Utils.showAlert;

public class ChangeEmailController {
    @FXML private Label currentEmail;
    @FXML private TextField newEmail;
    @FXML private Button backButton;
    private User currentUser;

    @FXML
    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
        currentEmail.setText(currentUser.getEmail());
    }

    @FXML
    public void saveEmailHandler() {
        String updatedEmail = newEmail.getText();

        if (!updatedEmail.isEmpty()) {
            currentEmail.setText(updatedEmail);
            try {
                DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
                DocumentSnapshot userSnapshot = userRef.get().get();
                if (userSnapshot.exists()) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("email", updatedEmail);
                    userRef.set(updates, com.google.cloud.firestore.SetOptions.merge());
                    currentUser.setEmail(updatedEmail);
                    showAlert("Email updated successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("User not found in Firestore.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("An error occurred while updating the last name.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML public void backHandler() { backToSettingHandler(currentUser,backButton); }
}
