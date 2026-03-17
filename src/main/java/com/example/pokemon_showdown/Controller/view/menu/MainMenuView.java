package com.example.pokemon_showdown.Controller.view.menu;

import com.example.pokemon_showdown.Classes.Team;
import com.example.pokemon_showdown.Classes.BattleService;
import com.example.pokemon_showdown.Controller.FightController;
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

    // Injection du contrôleur de combat (correspond à fx:id="battleView")
    @FXML private FightController battleViewController;

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

    // On modifie cette méthode pour qu'elle accepte l'équipe du joueur en paramètre
    public void switchToBattle(Team playerTeam) {
        appContent.setVisible(false);
        battleContent.setVisible(true);

        // 1. Instanciation du service pour créer l'adversaire
        BattleService battleService = new BattleService();
        Team opponentTeam = battleService.generateRandomOpponent(playerTeam.getMembers().size());

        // 2. Initialisation des données dans la vue de combat
        if (battleViewController != null) {
            battleViewController.initBattle(playerTeam, opponentTeam);
        } else {
            System.err.println("Erreur : battleViewController est null. Vérifie l'attribut fx:id dans le FXML.");
        }
    }
}