package com.example.frontend.controllers;

import com.example.frontend.App;
import com.example.frontend.User;
import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.example.frontend.utils.Utils.showAlert;

public class RegisterPageController {
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField emailInput;
    @FXML private PasswordField passwordInput;
    @FXML private PasswordField confirmPasswordInput;
    private User currentUser;
    @FXML
    public void initialize() { new Hyperlink("Log In").setOnAction(this::loginPageHandler); }

    private int sendVerificationEmail(String email) {
        try {
            // HOSTED CUSTOM API from a different REPO
            URL url = new URL("https://schedulefinder-development.up.railway.app/api/auth/newAccount");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String requestBody = "email=" + email + "&otherEmail=" + "gamershowcase.noreply@gmail.com";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            try (InputStream inputStream = connection.getInputStream()) {
                byte[] responseBytes = inputStream.readAllBytes();
                String response = new String(responseBytes, "utf-8");
            } catch (Exception e) {
                System.out.println("Error reading response: " + e.getMessage());
            }
            connection.disconnect();
            return responseCode;
        } catch (Exception e) {
            showAlert("Error sending verification email: " + e.getMessage(), Alert.AlertType.ERROR);
            return -1;
        }
    }

    private boolean verifyCode(String email, String code) {
        try {
            // HOSTED CUSTOM ENDPOINT FROM DIFFERENT REPO
            URL url = new URL("https://schedulefinder-development.up.railway.app/api/auth/verifyResetPasswordCode");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String requestBody = "email=" + email + "&code=" + code;
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            try (InputStream inputStream = connection.getInputStream()) {
                byte[] responseBytes = inputStream.readAllBytes();
                String response = new String(responseBytes, "utf-8");
            } catch (Exception e) {
                System.out.println("Error reading verification response: " + e.getMessage());
            }
            connection.disconnect();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            System.out.println("Error verifying reset password code: " + e.getMessage());
            return false;
        }
    }

    @FXML
        private void submitHandler(ActionEvent event) {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String email = emailInput.getText();
            String password = passwordInput.getText();
            String confirmPassword = confirmPasswordInput.getText();

            if (!password.equals(confirmPassword)) {
                showAlert("Password and Confirm Password do not match", Alert.AlertType.ERROR);
                return;
            }

            if (sendVerificationEmail(email) == HttpURLConnection.HTTP_OK) {
                String verificationCode = askForVerificationCode();

                if (verificationCode != null && !verificationCode.isEmpty()) {

                    if (verifyCode(email, verificationCode)) {
                        try {
                            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                            String uid = createUserInFirebase(email, hashedPassword);
                            assert uid != null;
                            currentUser = new User(
                                    uid,
                                    firstName,
                                    lastName,
                                    email,
                                    hashedPassword,
                                    new ArrayList<>()
                            );
                            DocumentReference userDocRef = App.db.collection("Users").document(uid);
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("firstName", firstName);
                            userData.put("lastName", lastName);
                            userData.put("email", email);
                            userData.put("favGames", new ArrayList<String>());
                            userData.put("password", hashedPassword);
                            userDocRef.set(userData);
                            goToMainPage();
                        } catch (Exception e) {
                            showAlert("Error creating user: " + e.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
                    else {
                        showAlert("Verification code is not valid. Please try again.", Alert.AlertType.ERROR);
                    }
                }
                else {
                    showAlert("Verification code cannot be empty. Please try again.", Alert.AlertType.ERROR);
                }
            }
            else {
                showAlert("Error sending verification email", Alert.AlertType.ERROR);
            }
        }

    private String createUserInFirebase(String email, String hashedPassword) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(hashedPassword);
            UserRecord userRecord = App.auth.createUser(request);
            return userRecord.getUid();
        } catch (Exception e) {
            showAlert("Error creating user: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }

    private String askForVerificationCode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verification Code");
        dialog.setHeaderText("Please enter the 5-digit verification code sent to your email:");
        dialog.setContentText("Code:");
        dialog.showAndWait().ifPresent(code -> System.out.println("Entered code: " + code));
        return dialog.getResult();
    }

    @FXML
    private void loginPageHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/login-page.fxml"));
        Parent root;
        try { root = loader.load(); } catch (IOException e) { throw new RuntimeException(e);}
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToMainPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
        Parent root = loader.load();
        MainPageController mainPageController = loader.getController();
        mainPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Stage) ((Node) emailInput).getScene().getWindow()).close();
    }
}
