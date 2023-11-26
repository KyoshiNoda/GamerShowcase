package com.example.frontend.controllers;

import com.example.frontend.Game;
import com.example.frontend.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class FavGamesPageController {

    @FXML private ListView<Game> gamesListView;
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
                            gameImageView.setFitHeight(80);
                            gameImageView.setFitWidth(300);
                            javafx.scene.control.Label gameLabel = new javafx.scene.control.Label(game.getName());
                            gameLabel.setFont(Font.font("Arial", 16));
                            vbox.getChildren().addAll(gameImageView, gameLabel);
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
        gamesListView.setFixedCellSize(100);
        gamesListView.setPrefHeight(600);
        gamesListView.getItems().setAll(currentUser.getFavGames());
    }
}
