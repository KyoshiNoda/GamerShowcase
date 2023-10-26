package com.mycompany.gamershowcase;

import java.io.IOException;

import javafx.fxml.FXML;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    
}
