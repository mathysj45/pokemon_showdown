package com.example.pokemon_showdown.Controller.view;


import com.example.pokemon_showdown.Classes.Pokemon;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.util.StringConverter;

public class TeamBuilderController {

    @FXML
    private ComboBox<Pokemon> teamBuilder;

    @FXML
    public void initialize() {
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
}