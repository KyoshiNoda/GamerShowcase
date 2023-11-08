package com.example.frontend;
import com.google.cloud.firestore.Firestore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    public static Firestore db;
    private final FirebaseConfig firebaseConfig = new FirebaseConfig();
    @Override
    public void start(Stage stage) throws IOException {
       db = FirebaseConfig.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("welcome-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}