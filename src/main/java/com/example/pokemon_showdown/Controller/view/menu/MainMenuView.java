package com.example.pokemon_showdown.Controller.view.menu;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuView {

    @FXML private StackPane appContent;
    @FXML private StackPane menuContent;

    @FXML
    private void launchApplication() {
        menuContent.setVisible(false);
        appContent.setVisible(true);
    }
}