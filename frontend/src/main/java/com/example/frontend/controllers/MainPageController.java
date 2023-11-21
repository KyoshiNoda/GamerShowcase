package com.example.frontend.controllers;

import com.example.frontend.App;
import com.example.frontend.Game;
import com.example.frontend.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
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

    @FXML
    private void initialize() {
        try {
            games = getGames();
            for (int i = 0; i < Math.min(games.size(), 9); i++) {
                Game game = games.get(i);
                StackPane cardPane = getCardPaneByIndex(i + 1);
                initGameCard(cardPane, game);
                setGameCardClickHandler(cardPane, game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void setUserData(User user) {
        this.currentUser = user;
        System.out.println("ID: " + currentUser.getId());
        System.out.println("Favorite Games:");
        for (Game currentGame : currentUser.getFavGames()) {
            currentGame.print();
        }
    }

    private void setGameCardClickHandler(StackPane cardPane, Game game) {
        cardPane.setOnMouseClicked(event -> {
            handleGameCardClick(game);
        });
    }

    // This will change view to detailedGameView
    private void handleGameCardClick(Game game) {
        System.out.println("Clicked game ID: " + game.getId());
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
                favoriteButton.setOnAction(event -> handleFavoriteButtonClick(game));

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
                setDefaultCardImage(cardPane);
            }
        } else {
            setDefaultCardImage(cardPane);
        }
    }

    // New method to handle button clicks
    private void handleFavoriteButtonClick(Game game) {
        currentUser.getFavGames().add(game);
        DocumentReference userRef = App.db.collection("Users").document(currentUser.getId());
        try {
            ApiFuture<WriteResult> updateFuture = userRef.update("favGames", currentUser.getFavGames());
            updateFuture.get();
            System.out.println("Firestore update successful");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Firestore update failed: " + e.getMessage());
        }
    }

    private void setDefaultCardImage(StackPane cardPane) {
        String defaultImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXGl68Y0oCfYlx18OswvBI5QNYjr7bHdCCUvAf8lHeig&s";

        ImageView defaultImageView = new ImageView(new Image(defaultImageUrl));
        defaultImageView.setFitHeight(150.0);
        defaultImageView.setFitWidth(200.0);

        Label nameLabel = new Label("Default Game");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(defaultImageView, nameLabel);

        // Add Favorite Game button
        Button favoriteButton = new Button("Favorite Game");
//        favoriteButton.setOnAction(event -> handleFavoriteButtonClick(null));
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
}
