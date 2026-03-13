package com.example.pokemon_showdown.Controller.view;

import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class TeamBuilderController {

    @FXML private ComboBox<Pokemon> teamBuilder;
    @FXML private Text pokemonName;
    @FXML private Text pokemonType1Data;
    @FXML private Text pokemonType2Data;
    @FXML private Text pokemonHpData;
    @FXML private Text pokemonAttackData;
    @FXML private Text pokemonDefenseData;
    @FXML private Text pokemonSpeAttackData;
    @FXML private Text pokemonSpeDefenseData;
    @FXML private Text pokemonSpeedData;
    @FXML private Text pokemonMove1Data;
    @FXML private Text pokemonMove2Data;
    @FXML private Text pokemonMove3Data;
    @FXML private Text pokemonMove4Data;

    private StatsView statsView;

    @FXML
    public void initialize() {
        this.statsView = new StatsView(
                pokemonName, pokemonType1Data, pokemonType2Data, pokemonHpData, pokemonAttackData,
                pokemonDefenseData, pokemonSpeAttackData, pokemonSpeDefenseData,
                pokemonSpeedData, pokemonMove1Data, pokemonMove2Data,
                pokemonMove3Data, pokemonMove4Data
        );

        DatabaseManager databaseManager = new DatabaseManager();
        teamBuilder.setItems(databaseManager.getAllPokemons());

        teamBuilder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.statsView.updateInterface(newValue);
            }
        });
    }
}