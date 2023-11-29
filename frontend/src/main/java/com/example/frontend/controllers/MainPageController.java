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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static com.example.frontend.RawgAPIConfig.getGames;

public class MainPageController {

    @FXML private StackPane gameCard1;
    @FXML private StackPane gameCard2;
    @FXML private StackPane gameCard3;
    @FXML private StackPane gameCard4;
    @FXML private StackPane gameCard5;
    @FXML private StackPane gameCard6;
    @FXML private StackPane gameCard7;
    @FXML private StackPane gameCard8;
    @FXML private StackPane gameCard9;

    private ArrayList<Game> games;
    private User currentUser;
    private int currentPage = 1;

    static Game clickedGame;

    @FXML private void initialize() { updateGameCards(); }
    @FXML
    void setUserData(User user) {
        this.currentUser = user;
    }

    private void initGameCard(StackPane cardPane, Game game) {
        String imageUrl = game.getBackground_image();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(imageUrl));
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);

                Label nameLabel = new Label(game.getName());

                VBox vBox = new VBox(10);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(imageView, nameLabel);

                Button favoriteButton = new Button("Favorite Game");
                favoriteButton.setOnAction(event -> addFavoriteGame(game));

                vBox.getChildren().add(favoriteButton);
                vBox.setPrefSize(200, 300);
                vBox.setBorder(new Border(new javafx.scene.layout.BorderStroke(
                            Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(1)
                        )
                    )
                );
                cardPane.getChildren().add(vBox);
            } catch (Exception e) {
                e.printStackTrace();
                setDefaultCardImage(cardPane, game);
            }
        } else {
            setDefaultCardImage(cardPane,game);
        }
    }

    private void addFavoriteGame(Game game) {
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

    private void updateGameCards() {
        try {
            games = getGames(currentPage);
            for (int i = 0; i < Math.min(games.size(), 9); i++) {
                Game game = games.get(i);
                StackPane cardPane = getCardPaneByIndex(i + 1);
                cardPane.getChildren().clear();
                initGameCard(cardPane, game);
                setGameCardClickHandler(cardPane, game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGameCardClickHandler(StackPane cardPane, Game game) {
        cardPane.setOnMouseClicked(event -> {
            try {
                handleGameCardClick(game);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // This will change view to detailedGameView
    private void handleGameCardClick(Game game) throws IOException {
        System.out.println("Clicked game ID: " + game.getId());
        clickedGame = game;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/game-details-page.fxml"));
        Parent root = loader.load();
        SettingPageController settingPageController = loader.getController();
        settingPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private StackPane getCardPaneByIndex(int index) {
        return switch (index) {
            case 1 -> gameCard1;
            case 2 -> gameCard2;
            case 3 -> gameCard3;
            case 4 -> gameCard4;
            case 5 -> gameCard5;
            case 6 -> gameCard6;
            case 7 -> gameCard7;
            case 8 -> gameCard8;
            case 9 -> gameCard9;
            default -> null;
        };
    }

    private void setDefaultCardImage(StackPane cardPane, Game game) {
        String defaultImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXGl68Y0oCfYlx18OswvBI5QNYjr7bHdCCUvAf8lHeig&s";

        ImageView defaultImageView = new ImageView(new Image(defaultImageUrl));
        defaultImageView.setFitHeight(150.0);
        defaultImageView.setFitWidth(200.0);

        Label nameLabel = new Label(game.getName());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(defaultImageView, nameLabel);

        Button favoriteButton = new Button("Favorite Game");
        favoriteButton.setOnAction(event -> addFavoriteGame(game));
        vBox.getChildren().add(favoriteButton);

        vBox.setPrefSize(200, 300);
        vBox.setBorder(new Border(new javafx.scene.layout.BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1)
                )
            )
        );

        cardPane.getChildren().add(vBox);
    }

    @FXML
    private void handleNextButton() {
        currentPage++;
        updateGameCards();
    }

    @FXML
    private void handlePrevButton() {
        if (currentPage > 1) {
            currentPage--;
            updateGameCards();
        }
    }

    @FXML
    void userSettingPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/setting-page.fxml"));
        Parent root = loader.load();
        SettingPageController settingPageController = loader.getController();
        settingPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void userFavGamesPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/favGames-page.fxml"));
        Parent root = loader.load();
        FavGamesPageController favGamesPageController = loader.getController();
        favGamesPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Favorite Game Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
