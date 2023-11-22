package com.example.frontend.controllers;

import com.example.frontend.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ChangeUsernameController {

    @FXML
    private Button Back_Button;

    @FXML
    private Button Save_New_Username_Button;

    public void Save_New_Username() throws IOException {
    }

    public void BackHandler() throws IOException {
        App.setRoot("Settings");
    }
}
