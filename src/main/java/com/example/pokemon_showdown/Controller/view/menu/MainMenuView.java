package com.example.pokemon_showdown.Controller.view.menu;

import com.example.pokemon_showdown.Controller.view.team.ActionTeamView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuView {

    @FXML private StackPane menuContent;
    @FXML private StackPane appContent;
    @FXML private VBox battleContent;

    @FXML private ActionTeamView teamBuilderController;

    @FXML
    public void initialize() {
        if (teamBuilderController != null) {
            teamBuilderController.setParentController(this);
        }
    }

    @FXML
    private void launchApplication() {
        menuContent.setVisible(false);
        appContent.setVisible(true);
    }

    public void switchToBattle() {
        appContent.setVisible(false);
        battleContent.setVisible(true);
    }
}