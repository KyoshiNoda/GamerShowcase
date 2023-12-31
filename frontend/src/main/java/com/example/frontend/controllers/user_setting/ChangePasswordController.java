package com.example.frontend.controllers.user_setting;

import com.example.frontend.App;
import com.example.frontend.User;
import com.google.cloud.firestore.DocumentReference;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import java.util.HashMap;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;
import static com.example.frontend.utils.Utils.backToSettingHandler;
import static com.example.frontend.utils.Utils.showAlert;

public class ChangePasswordController {
    @FXML private PasswordField currentPassword;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField confirmNewPassword;
    @FXML private Button backButton;
    private User currentUser;

    @FXML
    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    public void savePasswordHandler() {
        String enteredCurrentPassword = currentPassword.getText();
        String enteredNewPassword = newPassword.getText();
        String enteredConfirmPassword = confirmNewPassword.getText();

        if (enteredNewPassword.equals(enteredConfirmPassword)) {
            if (BCrypt.checkpw(enteredCurrentPassword, currentUser.getPassword())) {
                String hashedPassword = BCrypt.hashpw(enteredNewPassword, BCrypt.gensalt());
                try {
                    DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("password", hashedPassword);
                    userRef.set(updates, com.google.cloud.firestore.SetOptions.merge());
                    showAlert("Password updated successfully!", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    showAlert("An error occurred while updating the password.", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            } else {
                showAlert("Incorrect current password. Please try again.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("New password and confirm password do not match.", Alert.AlertType.ERROR);
        }
    }

    @FXML public void backHandler() { backToSettingHandler(currentUser,backButton); }

}
