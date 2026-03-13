package com.example.pokemon_showdown.Controller.view;


import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Classes.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;


public class TeamBuilderController {

    @FXML
    private ComboBox<Pokemon> teamBuilder;
    @FXML
    private ListView<String> teamListView;
    @FXML
    private Button fightButton;
    private Team myTeam = new Team();

    @FXML
    public void initialize() {
        DatabaseManager dbManager = new DatabaseManager();
        teamBuilder.setItems(dbManager.getAllPokemons());

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

    private Team Team = new Team();

    @FXML
    private void addPokemonToTeam() {
        Pokemon selected = teamBuilder.getSelectionModel().getSelectedItem();
        boolean success = Team.addMember(selected);

        if (success) {
            teamListView.getItems().add(selected.getName());
            fightButton.setDisable(!Team.isValid());
        } else {
            System.out.println("Impossible d'ajouter : Équipe complète (Max 6) ou sélection vide.");
        }
    }
}