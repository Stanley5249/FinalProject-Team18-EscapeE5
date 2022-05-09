package com.team18.escapeE5;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SlidingPuzzleController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}