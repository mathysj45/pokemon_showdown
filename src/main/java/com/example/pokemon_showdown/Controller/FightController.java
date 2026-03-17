package com.example.pokemon_showdown.Controller;

import com.example.pokemon_showdown.Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class FightController {

    @FXML private Text nameP1, nameP2;
    @FXML private ProgressBar healthBarP1, healthBarP2;
    @FXML private Text hpTextP1, hpTextP2;

    private BattleEngine engine;
    private Pokemon activeP1;
    private Pokemon activeP2;

    /**
     * Initialise le combat avec les deux Pokémon de tête.
     */
    public void initBattle(Team playerTeam, Team opponentTeam) {
        this.activeP1 = playerTeam.getMembers().get(0);
        this.activeP2 = opponentTeam.getMembers().get(0);

        this.engine = new BattleEngine(activeP1, activeP2);

        updateUI();
    }

    private void updateUI() {
        nameP1.setText(activeP1.getName());
        nameP2.setText(activeP2.getName());

        double ratioP1 = (double) activeP1.getCurrentHp() / activeP1.getHp();
        double ratioP2 = (double) activeP2.getCurrentHp() / activeP2.getHp();

        healthBarP1.setProgress(ratioP1);
        healthBarP2.setProgress(ratioP2);

        hpTextP1.setText(activeP1.getCurrentHp() + " / " + activeP1.getHp());
        hpTextP2.setText(activeP2.getCurrentHp() + " / " + activeP2.getHp());
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
