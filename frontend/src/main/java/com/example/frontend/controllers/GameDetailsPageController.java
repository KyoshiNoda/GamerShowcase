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
import javafx.scene.control.*;
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

import static com.example.frontend.RawgAPIConfig.getGameDescription;
import static com.example.frontend.RawgAPIConfig.getGames;
import static com.example.frontend.controllers.MainPageController.clickedGame;

public class GameDetailsPageController {

    @FXML
    private TextField RatingTextField;
    @FXML
    private TextArea DescriptionTextArea;
    @FXML
    private TextField ReleaseTextField;
    @FXML
    private ImageView GameImageView;
    @FXML
    private TextField ESRBTextField;
    @FXML
    private Button BackButton;
    @FXML
    private User currentUser;
    @FXML
    private void initialize() throws Exception { LoadGameDetails(clickedGame);}

    private void LoadGameDetails(Game game) throws Exception {
        String imageUrl = game.getBackground_image();
        GameImageView.setImage(new Image(imageUrl));
        RatingTextField.setText(game.getRating());
        DescriptionTextArea.setText(getGameDescription(game));
        ReleaseTextField.setText(game.getReleased());
        ESRBTextField.setText(game.getEsrb());
    }

    public void setUserData(User currentUser) {
        this.currentUser = currentUser;
    }



    @FXML
    protected void Return_To_Main_Page() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/main-page.fxml"));
        App.setRoot("main-page");
    }




}
