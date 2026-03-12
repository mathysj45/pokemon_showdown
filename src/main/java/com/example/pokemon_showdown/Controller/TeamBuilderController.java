package com.example.pokemon_showdown.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TeamBuilderController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
