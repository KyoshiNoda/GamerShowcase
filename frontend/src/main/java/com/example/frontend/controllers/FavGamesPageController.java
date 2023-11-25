package com.example.frontend.controllers;

import com.example.frontend.Game;
import com.example.frontend.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(game.getName());
                        }
                    }
                };
            }
        });
        gamesListView.getItems().setAll(currentUser.getFavGames());
    }
}
