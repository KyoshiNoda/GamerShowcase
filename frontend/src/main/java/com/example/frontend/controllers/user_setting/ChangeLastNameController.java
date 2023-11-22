package com.example.frontend.controllers.user_setting;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.frontend.App;

import java.io.IOException;

public class ChangeLastNameController {

    @FXML private Button backButton;

    @FXML private Button saveButton;

    public void saveLastNameHandler() throws IOException {

    }

    public void BackHandler() throws IOException {
       App.setRoot("Settings");
    }
}
