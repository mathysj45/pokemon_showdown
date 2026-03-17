package com.example.pokemon_showdown.Controller;

import com.example.pokemon_showdown.Classes.*;
import com.example.pokemon_showdown.Controller.view.battle.BattleView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    @FXML private ImageView spriteP1;
    @FXML private ImageView spriteP2;
    @FXML private HBox teamListContainer;

    private BattleEngine engine;
    private Pokemon activeP1;
    private Pokemon activeP2;
    private Team playerTeam;
    private Random random = new Random();
    private BattleView view;

    public void initBattle(Team playerTeam, Team opponentTeam) {
        this.playerTeam = playerTeam;

        this.activeP1 = this.playerTeam.getMembers().get(0);
        this.activeP2 = opponentTeam.getMembers().get(0);

        this.activeP1.setCurrentHp(this.activeP1.getHp());
        this.activeP2.setCurrentHp(this.activeP2.getHp());

        this.engine = new BattleEngine(activeP1, activeP2);

        this.view = new BattleView(nameP1, nameP2, healthBarP1, healthBarP2,
                hpTextP1, hpTextP2,
                pokemonMove1Data, pokemonMove2Data, pokemonMove3Data, pokemonMove4Data,
                battleLog, spriteP1, spriteP2, teamListContainer);

        this.view.loadSprites(activeP1.getName(), activeP2.getName());
        this.view.updateUI(activeP1, activeP2);
        this.view.renderTeamList(this.playerTeam, this::switchActivePokemon);
    }

    @FXML private void onMove1Clicked() { handleTurn(0); }
    @FXML private void onMove2Clicked() { handleTurn(1); }
    @FXML private void onMove3Clicked() { handleTurn(2); }
    @FXML private void onMove4Clicked() { handleTurn(3); }

    private void handleTurn(int playerMoveIndex) {
        if (activeP1.getCurrentHp() <= 0 || activeP2.getCurrentHp() <= 0) return;

        Attack playerMove = activeP1.getMoves().get(playerMoveIndex);
        int aiIndex = random.nextInt(activeP2.getMoves().size());
        Attack opponentMove = activeP2.getMoves().get(aiIndex);

        // Récupération du journal du tour
        String logOutput = engine.executeTurn(playerMove, opponentMove);

        view.updateUI(activeP1, activeP2);
        view.renderTeamList(this.playerTeam, this::switchActivePokemon);

        // Transmission à la vue
        view.logMessage(logOutput);

        checkWinCondition();
    }

    private void checkWinCondition() {
        if (activeP2.getCurrentHp() <= 0) {
            view.logMessage(activeP2.getName() + " est K.O. ! Vous avez gagné !");
            view.disableAllMoves();
        } else if (activeP1.getCurrentHp() <= 0) {
            view.logMessage(activeP1.getName() + " est K.O. ! Vous avez perdu...");
            view.disableAllMoves();
        }
    }

    private void switchActivePokemon(Pokemon targetPokemon) {
        if (targetPokemon == this.activeP1 || targetPokemon.getCurrentHp() <= 0) {
            return;
        }

        this.activeP1 = targetPokemon;

        // Actualisation du moteur et du rendu
        this.engine = new BattleEngine(this.activeP1, this.activeP2);
        this.view.loadSprites(this.activeP1.getName(), this.activeP2.getName());
        this.view.updateUI(this.activeP1, this.activeP2);
        this.view.logMessage("Envoi de " + this.activeP1.getName() + " !");
    }
}