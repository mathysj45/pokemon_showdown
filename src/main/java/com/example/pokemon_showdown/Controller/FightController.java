package com.example.pokemon_showdown.Controller;

import com.example.pokemon_showdown.Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class FightController {

    @FXML private Text nameP1;
    @FXML private Text nameP2;
    @FXML private ProgressBar healthBarP1;
    @FXML private ProgressBar healthBarP2;
    @FXML private Text hpTextP1;
    @FXML private Text hpTextP2;

    // Nouveaux pointeurs d'injection
    @FXML private Button pokemonMove1Data;
    @FXML private Button pokemonMove2Data;
    @FXML private Button pokemonMove3Data;
    @FXML private Button pokemonMove4Data;

    private BattleEngine engine;
    private Pokemon activeP1;
    private Pokemon activeP2;

    public void initBattle(Team playerTeam, Team opponentTeam) {
        this.activeP1 = playerTeam.getMembers().get(0);
        this.activeP2 = opponentTeam.getMembers().get(0);

        this.activeP1.setCurrentHp(this.activeP1.getHp());
        this.activeP2.setCurrentHp(this.activeP2.getHp());

        this.engine = new BattleEngine(activeP1, activeP2);

        updateUI();
    }

    private void updateUI() {
        nameP1.setText(activeP1.getName());
        nameP2.setText(activeP2.getName());

        double ratioP1 = (double) activeP1.getCurrentHp() / activeP1.getHp();
        double ratioP2 = (double) activeP2.getCurrentHp() / activeP2.getHp();

        healthBarP1.setProgress(Math.max(0, ratioP1));
        healthBarP2.setProgress(Math.max(0, ratioP2));

        hpTextP1.setText(activeP1.getCurrentHp() + " / " + activeP1.getHp());
        hpTextP2.setText(activeP2.getCurrentHp() + " / " + activeP2.getHp());

        // Chargement dynamique des capacités
        int moveCount = activeP1.getMoves().size();
        pokemonMove1Data.setText(moveCount > 0 ? activeP1.getMoves().get(0).getName() : "-");
        pokemonMove2Data.setText(moveCount > 1 ? activeP1.getMoves().get(1).getName() : "-");
        pokemonMove3Data.setText(moveCount > 2 ? activeP1.getMoves().get(2).getName() : "-");
        pokemonMove4Data.setText(moveCount > 3 ? activeP1.getMoves().get(3).getName() : "-");
    }

    @FXML
    private void onAttackClicked() {
        Attack move1 = activeP1.getMoves().get(0);
        Attack move2 = activeP2.getMoves().get(0);

        engine.executeTurn(move1, move2);

        updateUI();
        checkWinCondition();
    }

    private void checkWinCondition() {
        if (activeP1.getCurrentHp() <= 0) {
            System.out.println(activeP1.getName() + " est K.O. !");
        }
        if (activeP2.getCurrentHp() <= 0) {
            System.out.println(activeP2.getName() + " est K.O. !");
        }
    }
}