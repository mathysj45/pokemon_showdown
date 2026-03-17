package com.example.pokemon_showdown.Controller.view.menu;

import com.example.pokemon_showdown.Controller.SceneManager;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void handleStartClick() {
        SceneManager.switchScene("/com/example/pokemon_showdown/pokemon-view.fxml");
    }
}