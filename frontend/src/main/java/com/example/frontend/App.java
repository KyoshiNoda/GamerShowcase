package com.example.frontend;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;



public class App extends Application {
    public static Firestore db;
    public static FirebaseAuth auth;
    private final FirebaseConfig firebaseConfig = new FirebaseConfig();

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        db = FirebaseConfig.initialize();
        auth = FirebaseAuth.getInstance();
      
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("GamerShowcase!");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot (String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}