package com.example.pokemon_showdown.Controller.view.battle;

import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Classes.Team;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.util.function.Consumer;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class BattleView {

    private final Text nameP1;
    private final Text nameP2;
    private final ProgressBar healthBarP1;
    private final ProgressBar healthBarP2;
    private final Text hpTextP1;
    private final Text hpTextP2;
    private final Button[] moveButtons;
    private final TextArea battleLog;
    private final ImageView spriteP1;
    private final ImageView spriteP2;
    private final HBox teamListContainer;

    public BattleView(Text nameP1, Text nameP2, ProgressBar healthBarP1,
                      ProgressBar healthBarP2,
                      Text hpTextP1, Text hpTextP2, Button move1, Button move2, Button move3, Button move4,
                      TextArea battleLog, ImageView spriteP1,
                      ImageView spriteP2, HBox teamListContainer) {
        this.nameP1 = nameP1;
        this.nameP2 = nameP2;
        this.healthBarP1 = healthBarP1;
        this.healthBarP2 = healthBarP2;
        this.hpTextP1 = hpTextP1;
        this.hpTextP2 = hpTextP2;
        this.moveButtons = new Button[]{move1, move2, move3, move4};
        this.battleLog = battleLog;
        this.spriteP1 = spriteP1;
        this.spriteP2 = spriteP2;
        this.teamListContainer = teamListContainer;
    }

    public void updateUI(Pokemon activeP1, Pokemon activeP2) {
        nameP1.setText(activeP1.getName());
        nameP2.setText(activeP2.getName());

        double ratioP1 = (double) activeP1.getCurrentHp() / activeP1.getHp();
        double ratioP2 = (double) activeP2.getCurrentHp() / activeP2.getHp();

        healthBarP1.setProgress(Math.max(0, ratioP1));
        healthBarP2.setProgress(Math.max(0, ratioP2));

        hpTextP1.setText(activeP1.getCurrentHp() + " / " + activeP1.getHp());
        hpTextP2.setText(activeP2.getCurrentHp() + " / " + activeP2.getHp());

        updateMoveButtons(activeP1);
    }

    private void updateMoveButtons(Pokemon activeP1) {
        for (int index = 0; index < moveButtons.length; index++) {
            if (index < activeP1.getMoves().size()) {
                moveButtons[index].setText(activeP1.getMoves().get(index).getName());
                moveButtons[index].setDisable(false);
            } else {
                moveButtons[index].setText("-");
                moveButtons[index].setDisable(true);
            }
        }
    }

    public void disableAllMoves() {
        for (Button button : moveButtons) {
            button.setDisable(true);
        }
    }

    public void logMessage(String message) {
        if (battleLog != null) {
            battleLog.appendText(message + "\n");
        }
        System.out.println(message);
    }

    public void loadSprites(String pokemonNameP1, String pokemonNameP2) {
        setSprite(spriteP1, pokemonNameP1);
        setSprite(spriteP2, pokemonNameP2);
    }

    private void setSprite(ImageView targetImageView, String pokemonName) {
        String normalizedName = pokemonName.toLowerCase().trim();
        String resourcePath = "/com/example/pokemon_showdown/sprites/" + normalizedName + ".png";

        try {
            URL resourceUrl = getClass().getResource(resourcePath);
            if (resourceUrl != null) {
                targetImageView.setImage(new Image(resourceUrl.toExternalForm()));
            } else {
                targetImageView.setImage(null);
            }
        } catch (Exception exception) {
            targetImageView.setImage(null);
        }
    }

    public void renderTeamList(Team playerTeam) {
        teamListContainer.getChildren().clear();

        for (Pokemon member : playerTeam.getMembers()) {
            HBox row = new HBox(10.0);
            row.setAlignment(Pos.CENTER_LEFT);

            ImageView sprite = new ImageView();
            sprite.setFitHeight(40.0);
            sprite.setFitWidth(40.0);
            setSprite(sprite, member.getName());

            HBox statsBox = new HBox(2.0);
            Text nameText = new Text(member.getName() + " (" + member.getCurrentHp() + "/" + member.getHp() + ")");

            double hpRatio = (double) member.getCurrentHp() / member.getHp();
            ProgressBar hpBar = new ProgressBar(Math.max(0, hpRatio));
            hpBar.setPrefWidth(100.0);

            statsBox.getChildren().addAll(nameText, hpBar);
            row.getChildren().addAll(sprite, statsBox);

            teamListContainer.getChildren().add(row);
        }
    }

    public void renderTeamList(Team playerTeam, Consumer<Pokemon> onSelectionCallback) {
        teamListContainer.getChildren().clear();

        for (Pokemon member : playerTeam.getMembers()) {
            HBox row = new HBox(10.0);
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            row.setStyle("-fx-cursor: hand;"); // Indication visuelle d'interaction

            ImageView sprite = new ImageView();
            sprite.setFitHeight(40.0);
            sprite.setFitWidth(40.0);
            setSprite(sprite, member.getName());

            HBox statsBox = new HBox(2.0);
            Text nameText = new Text(member.getName() + " (" + member.getCurrentHp() + "/" + member.getHp() + ")");

            double hpRatio = (double) member.getCurrentHp() / member.getHp();
            ProgressBar hpBar = new ProgressBar(Math.max(0, hpRatio));
            hpBar.setPrefWidth(100.0);

            statsBox.getChildren().addAll(nameText, hpBar);
            row.getChildren().addAll(sprite, statsBox);

            // Déclenchement conditionnel
            row.setOnMouseClicked((MouseEvent event) -> {
                if (member.getCurrentHp() > 0) {
                    onSelectionCallback.accept(member);
                }
            });

            teamListContainer.getChildren().add(row);
        }
    }
}