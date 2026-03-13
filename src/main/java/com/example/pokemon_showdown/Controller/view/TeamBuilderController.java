package com.example.pokemon_showdown.Controller.view;


import com.example.pokemon_showdown.Classes.Pokemon;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class TeamBuilderController {

    @FXML
    private ComboBox<Pokemon> teamBuilder;

    @FXML
    public void initialize() {
        teamBuilder.setItems(Pokemon.getAllPokemons());

        teamBuilder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected : " + newValue.getName());
            }
        });
    }
}