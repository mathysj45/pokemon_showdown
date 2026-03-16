package com.example.pokemon_showdown.Controller.view;

import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Classes.Team;
import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class TeamBuilderController {

    @FXML private ComboBox<Pokemon> teamBuilder;
    @FXML private ListView<String> teamListView;
    @FXML private Button fightButton;
    @FXML private StatsView statsView;
    @FXML private Team Team = new Team();

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


    @FXML
    public void initialize() {
        this.statsView = new StatsView(
                pokemonName, pokemonType1Data, pokemonType2Data, pokemonHpData, pokemonAttackData,
                pokemonDefenseData, pokemonSpeAttackData, pokemonSpeDefenseData,
                pokemonSpeedData, pokemonMove1Data, pokemonMove2Data,
                pokemonMove3Data, pokemonMove4Data
        );

        DatabaseManager dbManager = new DatabaseManager();
        teamBuilder.setItems(dbManager.getAllPokemons());

        teamBuilder.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.statsView.updateInterface(newValue);
            }
        });

        teamBuilder.setConverter(new StringConverter<Pokemon>() {
            @Override
            public String toString(Pokemon pokemon) {
                return (pokemon == null) ? "" : pokemon.getName();
            }

            @Override
            public Pokemon fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void addPokemonToTeam() {
        Pokemon selectedPokemon = teamBuilder.getSelectionModel().getSelectedItem();
        boolean isSuccess = Team.addMember(selectedPokemon);

        if (isSuccess) {
            teamListView.getItems().add(selectedPokemon.getName());
            fightButton.setDisable(!Team.isValid());
        } else {
            System.out.println("Cannot add: Team full (Max 6) or empty selection.");
        }
    }

    @FXML
    private void removePokemonFromTeam() {
        int selectedIndex = teamListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Team.removeMember(selectedIndex);
            teamListView.getItems().remove(selectedIndex);
            fightButton.setDisable(!Team.isValid());
            System.out.println("Pokémon retiré. Taille équipe : " + teamListView.getItems().size());
        }


    }

}