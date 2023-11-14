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
    @FXML
    private StackPane gameCard1;
    @FXML
    private StackPane gameCard2;
    @FXML
    private StackPane gameCard3;
    // Repeat for the remaining cards...

    private ArrayList<Game> games; // List to store the retrieved games

    @FXML
    private void initialize() {
        try {
            // Fetch games using your getGames() method
            games = getGames();

            // Initialize each game card with the corresponding game data
            for (int i = 0; i < games.size(); i++) {
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
            // Add cases for the remaining cards...
            default -> null;
        };
    }
    private void initGameCard(StackPane cardPane, Game game) {
        ImageView imageView = new ImageView(new Image(game.getBackground_image()));
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);

        Label nameLabel = new Label(game.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16.0;");

        cardPane.getChildren().addAll(imageView, nameLabel);
    }
}
