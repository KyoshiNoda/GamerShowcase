package com.example.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.frontend.App;

import java.io.IOException;

public class ChangeFirstNameController {

    @FXML
    private Button Back_Button;

    @FXML
    private Button Save_New_First_Name_Button;

    public void Save_New_First_Name() throws IOException {
    }

    public void BackHandler() throws IOException {
        App.setRoot("Settings");
    }


}
