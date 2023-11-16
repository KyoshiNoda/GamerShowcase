package com.example.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import com.example.frontend.App;

import java.io.IOException;


public class SettingsController {
    @FXML
    private Button Change_First_Name_Button;

    @FXML
    private Button Change_Last_Name_Button;

    @FXML
    private Button Change_Username_Button;

    @FXML
    private Button Change_Email_Button;

    @FXML
    protected void Return_To_Main_Page() {

    }
    @FXML
    public void Go_To_Change_First_Name() throws IOException {
        //App.setRoot("ChangeFirstName");
    }
    @FXML
    public void Go_To_Change_Last_Name() throws IOException {
        //App.setRoot("ChangeLastName");
    }

    @FXML
    public void Go_To_Change_Username() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Username.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
    }

    @FXML
    public void Go_To_Change_Email() throws IOException {
        //App.setRoot("ChangeEmail");
    }
}