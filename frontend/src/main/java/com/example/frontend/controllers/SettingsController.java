package com.example.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
   //     App.setRoot("ChangeFirstName");
    }
    @FXML
    public void Go_To_Change_Last_Name() throws IOException {
      //  App.setRoot("ChangeLastName");
    }

    @FXML
    public void Go_To_Change_Username() throws IOException {
       // App.setRoot("ChangeUsername");
    }

    @FXML
    public void Go_To_Change_Email() throws IOException {
     //   App.setRoot("ChangeEmail");
    }
}