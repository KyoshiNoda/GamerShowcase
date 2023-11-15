package com.example.frontend.controllers;

import com.example.frontend.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

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

    private ArrayList<Game> games; // List to store the retrieved games

    @FXML
    private void initialize() {
        try {
            // Fetch games using your getGames() method
            games = getGames();

            // Initialize each game card with the corresponding game data
            for (int i = 0; i < Math.min(games.size(), 9); i++) {
                Game game = games.get(i);
                StackPane cardPane = getCardPaneByIndex(i + 1); // Assuming your IDs are numbered from 1
                initGameCard(cardPane, game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                imageView.setFitHeight(150.0);
                imageView.setFitWidth(200.0);

                Label nameLabel = new Label(game.getName());
                nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16.0;");

                cardPane.getChildren().addAll(imageView, nameLabel);
            } catch (Exception e) {
                e.printStackTrace();
                setDefaultCardImage(cardPane);
            }
        } else {
            setDefaultCardImage(cardPane);
        }
    }

    private void setDefaultCardImage(StackPane cardPane) {
        String defaultImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXGl68Y0oCfYlx18OswvBI5QNYjr7bHdCCUvAf8lHeig&s";
        ImageView defaultImageView = new ImageView(new Image(defaultImageUrl));
        defaultImageView.setFitHeight(150.0);
        defaultImageView.setFitWidth(200.0);

        Label nameLabel = new Label("Default Game");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16.0;");

        cardPane.getChildren().addAll(defaultImageView, nameLabel);
    }
}
