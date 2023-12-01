package com.example.frontend.controllers;

import com.example.frontend.Game;
import com.example.frontend.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class ExternalUserPageController {
    @FXML private ListView<Game> gamesListView;
    private User currentUser;
    @FXML private Label externalUserLabel;

    public void setUserData(User user, User externalUser) {
        this.currentUser = user;
        externalUserLabel.setText(externalUser.getFirstName() + " " + externalUser.getLastName());

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
                            vbox.setOnMouseClicked(event -> {
                                // reroute to viewGameDetails
                                System.out.println(game.getId());
                            });
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
        gamesListView.setFixedCellSize(100);
        gamesListView.setPrefHeight(600);
        gamesListView.getItems().setAll(externalUser.getFavGames());
    }
    @FXML
    private void goToMainPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
        Parent root = loader.load();
        MainPageController mainPageController = loader.getController();
        mainPageController.setUserData(currentUser);
        Scene scene = new Scene(root);
        Stage stage = (Stage) gamesListView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
