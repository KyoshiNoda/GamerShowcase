package com.example.frontend.controllers;

import com.example.frontend.App;
import com.example.frontend.Game;
import com.example.frontend.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static com.example.frontend.utils.Utils.*;

public class LoginPageController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML
    public void initialize() { new Hyperlink("Create Account").setOnAction(this::registerPageHandler); }

    @FXML
    private void loginButtonHandler(ActionEvent actionEvent) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Query query = App.db.collection("Users").whereEqualTo("email", email);
            var querySnapshot = query.get().get();
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot userSnapshot = querySnapshot.getDocuments().get(0);
                String storedHashedPassword = userSnapshot.getString("password");

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    User currentUser = new User(
                            userSnapshot.getId(),
                            userSnapshot.getString("firstName"),
                            userSnapshot.getString("lastName"),
                            userSnapshot.getString("email"),
                            userSnapshot.getString("password"),
                            parseFavGames(userSnapshot.get("favGames"))
                    );

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
                    Parent root = loader.load();
                    MainPageController mainPageController = loader.getController();
                    mainPageController.setUserData(currentUser);
                    Scene scene = new Scene(root, 1350, 1000);
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setX(getVisualWidth(1350));
                    stage.setY(getVisualHeight(1000));
                    stage.setScene(scene);
                    stage.show();
                } else {
                    showAlert("Invalid password",Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Incorrect Information!", Alert.AlertType.ERROR);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            showAlert("Error during login: " + e.getMessage(),Alert.AlertType.ERROR);
        }
    }
    static public ArrayList<Game> parseFavGames(Object favGamesObject) {
        ArrayList<Game> favGames = new ArrayList<>();

        if (favGamesObject instanceof ArrayList) {
            for (Object gameObj : (ArrayList<?>) favGamesObject) {
                if (gameObj instanceof Map) {
                    Map<String, Object> gameMap = (Map<String, Object>) gameObj;
                    Game game = new Game(
                            (String) gameMap.get("name"),
                            (ArrayList<String>) gameMap.get("platforms"),
                            (String) gameMap.get("released"),
                            String.valueOf(gameMap.get("rating")),
                            ((Long) gameMap.get("id")).intValue(),
                            (String) gameMap.get("esrb"),
                            (String) gameMap.get("background_image"),
                            (ArrayList<String>) gameMap.get("screenshots")
                    );

                    favGames.add(game);
                }
            }
        }

        return favGames;
    }

    @FXML
    private void registerPageHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/register-page.fxml"));
        Parent root;
        try { root = loader.load(); } catch (IOException e) { throw new RuntimeException(e);}
        Scene scene = new Scene(root,800,500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
