package com.example.frontend.controllers;
import javafx.scene.control.Button;
import com.example.frontend.App;
import com.example.frontend.Game;
import com.example.frontend.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.frontend.utils.Utils.*;


public class FavGamesPageController {
    @FXML
    private ListView<Game> gamesListView;
    private User currentUser;

    public void setUserData(User user) {
        this.currentUser = user;
        gamesListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Game> call(ListView<Game> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Game game, boolean empty) {
                        super.updateItem(game, empty);
                        if (empty || game == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            VBox vbox = new VBox();
                            vbox.setStyle("-fx-background-size: contain; " +
                                    "-fx-background-position: center center; " +
                                    "-fx-alignment: center; " +
                                    "-fx-text-alignment: center;"
                            );
                            ImageView gameImageView = new ImageView(new Image(game.getBackground_image()));
                            gameImageView.setFitHeight(150);
                            gameImageView.setFitWidth(150);
                            javafx.scene.control.Label gameLabel = new javafx.scene.control.Label(game.getName());
                            gameLabel.setFont(Font.font("Arial", 16));
                            vbox.getChildren().addAll(gameImageView, gameLabel);
                            vbox.setOnMouseClicked(event -> {
                                removeGameHandler(game);
                            });
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
        gamesListView.setFixedCellSize(200);
        gamesListView.setPrefHeight(600);
        gamesListView.getItems().setAll(currentUser.getFavGames());
    }

    private void removeGameHandler(Game game) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Remove " + game.getName());
        confirmationDialog.setContentText("Are you sure you want to remove this game from favorites?");

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentUser.getFavGames().remove(game);
                DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("favGames", currentUser.getFavGames());
                ApiFuture<WriteResult> updateFuture = userRef.update(updateData);
                try {
                    updateFuture.get();
                    showAlert("Game removed from favorites successfully", Alert.AlertType.INFORMATION);
                } catch (InterruptedException | ExecutionException e) {
                    showAlert("Failed to remove game from favorites. Please try again.", Alert.AlertType.ERROR);
                }
                gamesListView.getItems().setAll(currentUser.getFavGames());
            }
        });
    }

    @FXML
    private void goToMainPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
        Parent root = loader.load();
        MainPageController mainPageController = loader.getController();
        mainPageController.setUserData(currentUser);
        Scene scene = new Scene(root,1350,1000);
        Stage stage = (Stage) gamesListView.getScene().getWindow();
        stage.setX(getVisualWidth(1350));
        stage.setY(getVisualHeight(1000));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void userSettingPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/setting-page.fxml"));
        Parent root = loader.load();
        SettingPageController settingPageController = loader.getController();
        settingPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = (Stage) gamesListView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void userToggle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/friends-list.fxml"));
        Parent root = loader.load();
        SettingPageController settingPageController = loader.getController();
        settingPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = (Stage) gamesListView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
