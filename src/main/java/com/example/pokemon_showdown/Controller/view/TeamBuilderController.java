package com.example.pokemon_showdown.Controller.view;


import com.example.pokemon_showdown.Classes.Pokemon;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.pokemon_showdown.Database.DatabaseManager;

public class TeamBuilderController {

    @FXML
    private ComboBox<Pokemon> teamBuilder;

    @FXML
    public void initialize() {
        DatabaseManager dbManager = new DatabaseManager();
        teamBuilder.setItems(dbManager.getAllPokemons());

        teamBuilder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected : " + newValue.getName());
            }
        });
    }
}