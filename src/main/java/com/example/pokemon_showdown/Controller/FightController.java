package com.example.pokemon_showdown.Controller;

import com.example.pokemon_showdown.Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import java.util.Random;

public class FightController {

    @FXML private Text nameP1, nameP2;
    @FXML private ProgressBar healthBarP1, healthBarP2;
    @FXML private Text hpTextP1, hpTextP2;

    @FXML private Button pokemonMove1Data;
    @FXML private Button pokemonMove2Data;
    @FXML private Button pokemonMove3Data;
    @FXML private Button pokemonMove4Data;

    @FXML private TextArea battleLog;

    private BattleEngine engine;
    private Pokemon activeP1;
    private Pokemon activeP2;
    private Random random = new Random();

    /**
     * Initialisation du duel
     */
    public void initBattle(Team playerTeam, Team opponentTeam) {
        // On récupère le premier Pokémon de chaque équipe
        this.activeP1 = playerTeam.getMembers().get(0);
        this.activeP2 = opponentTeam.getMembers().get(0);

        // Reset des PV pour le combat (optionnel si déjà fait avant)
        this.activeP1.setCurrentHp(this.activeP1.getHp());
        this.activeP2.setCurrentHp(this.activeP2.getHp());

        this.engine = new BattleEngine(activeP1, activeP2);

        updateUI();
        log("Le combat commence entre " + activeP1.getName() + " et " + activeP2.getName() + " !");
    }

    private void updateUI() {
        // Textes et noms
        nameP1.setText(activeP1.getName());
        nameP2.setText(activeP2.getName());

        // Calcul des ratios pour les ProgressBars (entre 0.0 et 1.0)
        double ratioP1 = (double) activeP1.getCurrentHp() / activeP1.getHp();
        double ratioP2 = (double) activeP2.getCurrentHp() / activeP2.getHp();

        healthBarP1.setProgress(Math.max(0, ratioP1));
        healthBarP2.setProgress(Math.max(0, ratioP2));

        hpTextP1.setText(activeP1.getCurrentHp() + " / " + activeP1.getHp());
        hpTextP2.setText(activeP2.getCurrentHp() + " / " + activeP2.getHp());

        // Mise à jour des boutons de capacités
        updateMoveButtons();
    }

    private void updateMoveButtons() {
        Button[] buttons = {pokemonMove1Data, pokemonMove2Data, pokemonMove3Data, pokemonMove4Data};
        for (int i = 0; i < buttons.length; i++) {
            if (i < activeP1.getMoves().size()) {
                buttons[i].setText(activeP1.getMoves().get(i).getName());
                buttons[i].setDisable(false);
            } else {
                buttons[i].setText("-");
                buttons[i].setDisable(true);
            }
        }
    }

    // --- Gestion des clics sur les attaques ---

    @FXML private void onMove1Clicked() { handleTurn(0); }
    @FXML private void onMove2Clicked() { handleTurn(1); }
    @FXML private void onMove3Clicked() { handleTurn(2); }
    @FXML private void onMove4Clicked() { handleTurn(3); }

    private void handleTurn(int playerMoveIndex) {
        if (activeP1.getCurrentHp() <= 0 || activeP2.getCurrentHp() <= 0) return;

        // Choix du joueur
        Attack playerMove = activeP1.getMoves().get(playerMoveIndex);

        // Choix de l'IA (aléatoire parmi ses capacités disponibles)
        int aiIndex = random.nextInt(activeP2.getMoves().size());
        Attack opponentMove = activeP2.getMoves().get(aiIndex);

        // Exécution du tour dans le moteur
        engine.executeTurn(playerMove, opponentMove);

        // Rafraîchissement visuel
        updateUI();
        checkWinCondition();
    }

    private void checkWinCondition() {
        if (activeP2.getCurrentHp() <= 0) {
            log(activeP2.getName() + " est K.O. ! Vous avez gagné !");
            disableAllMoves();
        } else if (activeP1.getCurrentHp() <= 0) {
            log(activeP1.getName() + " est K.O. ! Vous avez perdu...");
            disableAllMoves();
        }
    }

    private void disableAllMoves() {
        pokemonMove1Data.setDisable(true);
        pokemonMove2Data.setDisable(true);
        pokemonMove3Data.setDisable(true);
        pokemonMove4Data.setDisable(true);
    }

    private void log(String message) {
        if (battleLog != null) {
            battleLog.appendText(message + "\n");
        }
        System.out.println(message);
    }
}