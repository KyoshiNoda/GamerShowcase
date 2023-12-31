package com.example.frontend.controllers;

import com.example.frontend.App;
import com.example.frontend.Game;
import com.example.frontend.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
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
import static com.example.frontend.RawgAPIConfig.getGameDetails;
import static com.example.frontend.RawgAPIConfig.getGames;
import static com.example.frontend.controllers.LoginPageController.parseFavGames;
import static com.example.frontend.utils.Utils.*;

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
    @FXML private TextField searchBar;
    @FXML private HBox toggleButtonContainer;

    private ToggleGroup searchToggleGroup;
    private ArrayList<Game> games;
    private User currentUser;
    private int currentPage = 1;
    static Game selectedGame;


    @FXML private void initialize() {
        updateGameCards();
        createToggleGroup();
    }
    @FXML
    public void setUserData(User user) { this.currentUser = user; }

    private void createToggleGroup() {
        searchToggleGroup = new ToggleGroup();
        ToggleButton gamesToggle = new ToggleButton("Games");
        ToggleButton usersToggle = new ToggleButton("Users");
        gamesToggle.setToggleGroup(searchToggleGroup);
        usersToggle.setToggleGroup(searchToggleGroup);
        gamesToggle.setSelected(true);
        gamesToggle.setStyle("-fx-background-color: #6A5ACD; -fx-text-fill: white; -fx-font-family: 'PT Mono Bold'; -fx-font-size: 13;");
        usersToggle.setStyle("-fx-background-color: #6A5ACD; -fx-text-fill: white; -fx-font-family: 'PT Mono Bold'; -fx-font-size: 13;");
        toggleButtonContainer.getChildren().addAll(gamesToggle, usersToggle);
    }

    @FXML
    private void onSearchHandler() throws Exception {
        String searchQuery = searchBar.getText();
        ToggleButton selectedToggle = (ToggleButton) searchToggleGroup.getSelectedToggle();
        if (selectedToggle != null && !searchQuery.isEmpty()) {
            if ("Games".equals(selectedToggle.getText())) {
                onGameSearch(searchQuery);
            } else if ("Users".equals(selectedToggle.getText())) {
                onUserSearch(searchQuery);
            }
        }
    }

    @FXML
    private void onGameSearch(String slugSearch) throws Exception {
        selectedGame = getGameDetails(slugSearch);
        if (selectedGame != null) {
            gameDetailPage();
        } else {
            showAlert("Cannot find Game! Double check its in slug style!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onUserSearch(String userSearch) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/externalUser-page.fxml"));
        Parent root = loader.load();
        ExternalUserPageController externalUserPageController = loader.getController();
        externalUserPageController.setUserData(currentUser,getOtherUser(userSearch));
        Scene scene = new Scene(root,800,500);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setX(getVisualWidth(800));
        stage.setY(getVisualHeight(500));
        stage.setScene(scene);
        stage.show();
    }

    private User getOtherUser(String email) {
        try {
            var querySnapshot = App.db.collection("Users").whereEqualTo("email", email).get().get();
            if (!querySnapshot.isEmpty()){
                DocumentSnapshot userSnapshot = querySnapshot.getDocuments().get(0);
                return new User(
                        userSnapshot.getId(),
                        userSnapshot.getString("firstName"),
                        userSnapshot.getString("lastName"),
                        userSnapshot.getString("email"),
                        userSnapshot.getString("password"),
                        parseFavGames(userSnapshot.get("favGames"))
                );
            } else {
                showAlert("Cannot find the user's email!", Alert.AlertType.ERROR);
            }
        } catch (InterruptedException | ExecutionException e) {
            showAlert("Firebase ERROR", Alert.AlertType.ERROR);
        }
        return null;
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
                favoriteButton.setStyle("-fx-background-color: #6A5ACD; -fx-text-fill: white; -fx-font-family: 'PT Mono Bold'; -fx-font-size: 13;");
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleGameCardClick(Game game) throws IOException {
        selectedGame = game;
        gameDetailPage();
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
        Scene scene = new Scene(root,600,400);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setX(getVisualWidth(600));
        stage.setY(getVisualHeight(400));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void userFavGamesPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/favGames-page.fxml"));
        Parent root = loader.load();
        FavGamesPageController favGamesPageController = loader.getController();
        favGamesPageController.setUserData(currentUser);
        Scene scene = new Scene(root,800,500);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setX(getVisualWidth(800));
        stage.setY(getVisualHeight(500));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void gameDetailPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontend/game-details-page.fxml"));
        Parent root = loader.load();
        GameDetailsPageController gameDetailspageController = loader.getController();
        gameDetailspageController.setUserData(currentUser);
        Scene scene = new Scene(root,600,400);
        Stage stage = (Stage) gameCard1.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setX(getVisualWidth(600));
        stage.setY(getVisualHeight(400));
        stage.setScene(scene);
        stage.show();
    }
}
