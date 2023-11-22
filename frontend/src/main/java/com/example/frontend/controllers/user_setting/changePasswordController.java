package com.example.frontend.controllers.user_setting;

import com.example.frontend.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class changePasswordController {

    @FXML private Button backButton;

    @FXML private Button saveButton;

    public void savePasswordHandler() throws IOException {

    }

    public void BackHandler() throws IOException {
        App.setRoot("Settings");
    }
}
