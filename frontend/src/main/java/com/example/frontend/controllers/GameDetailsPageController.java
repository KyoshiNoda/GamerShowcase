package com.example.frontend.controllers;

import com.example.frontend.App;
import com.example.frontend.Game;
import com.example.frontend.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static com.example.frontend.RawgAPIConfig.getGameDescription;
import static com.example.frontend.controllers.MainPageController.selectedGame;
import static com.example.frontend.utils.Utils.*;

public class GameDetailsPageController {

    @FXML private Label RatingLabel;
    @FXML private TextArea DescriptionTextArea;
    @FXML private Label ReleaseLabel;
    @FXML private ImageView GameImageView;
    @FXML private Label ESRBLabel;
    @FXML private Label NameLabel;
    @FXML private User currentUser;
    @FXML private void initialize() throws Exception { LoadGameDetails(selectedGame);}

    private void LoadGameDetails(Game game) throws Exception {
        String imageUrl = game.getBackground_image();
        GameImageView.setImage(new Image(imageUrl));
        NameLabel.setText(game.getName());
        RatingLabel.setText(game.getRating());
        DescriptionTextArea.setText(getGameDescription(game));
        ReleaseLabel.setText(game.getReleased());
        ESRBLabel.setText(game.getEsrb());
    }

    @FXML
    protected void HandleaddFavoriteGame() {
        addFavoriteGame(selectedGame);
    }
    @FXML
    protected void addFavoriteGame(Game game) {
        if (!currentUser.gameExists(game)){
            currentUser.getFavGames().add(game);
            DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("favGames", currentUser.getFavGames());
            try {
                ApiFuture<WriteResult> updateFuture = userRef.set(updateData, SetOptions.merge());
                updateFuture.get();
                showAlert("Game added to favorites successfully", Alert.AlertType.INFORMATION);
            } catch (InterruptedException | ExecutionException e) {
                showAlert("Failed to add game to favorites. Please try again.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Already Favorited Game!", Alert.AlertType.ERROR);
        }
    }

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
            Scene scene = new Scene(loader.load(), 1350, 1000);
            MainPageController mainPageController = loader.getController();
            mainPageController.setUserData(currentUser);
            Stage stage = (Stage) NameLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setX(getVisualWidth(1350));
            stage.setY(getVisualHeight(1000));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
