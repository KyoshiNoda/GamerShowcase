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

public class GameDetailsPageController {

    @FXML
    private StackPane GameImageStackPane;

    @FXML
    private StackPane ESRBStackPane;

    private void LoadGameDetails(StackPane GameImage, Game game) {

    }



    @FXML
    protected void Return_To_Main_Page() {
        loadScene("/com/example/frontend/main-page.fxml");
    }




}
