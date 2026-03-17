package com.example.pokemon_showdown.Controller.view.team;

import com.example.pokemon_showdown.Classes.Item;
import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Controller.view.menu.MainMenuView;
import javafx.fxml.FXML;

public class ActionTeamView extends TeamBuilderView {

    private MainMenuView parentController;

    public void setParentController(MainMenuView parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void launchBattle() {
        if (parentController != null) {
            parentController.switchToBattle();
        }
    }

    @FXML
    private void addPokemonToTeam() {
        Pokemon selectedPokemon = teamBuilder.getSelectionModel().getSelectedItem();
        Item selectedItem = itemComboBox.getSelectionModel().getSelectedItem();

        if (selectedPokemon != null && selectedItem != null) {
            Pokemon pokemonToAdd = new Pokemon(selectedPokemon);
            pokemonToAdd.setHeldItem(selectedItem);

            boolean isSuccess = team.addMember(pokemonToAdd);

            if (isSuccess) {
                teamListView.getItems().add(pokemonToAdd.getName()
                        + "   (" + selectedItem.getName() + ")");
                fightButton.setDisable(!team.isValid());
            } else {
                System.out.println("Équipe pleine ou séléction invalide.");
            }
        } else {
            System.out.println("Séléctionnez à la fois un Pokémon et un item.");
        }
    }

    @FXML
    private void removePokemonFromTeam() {
        int selectedIndex = teamListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            team.removeMember(selectedIndex);
            teamListView.getItems().remove(selectedIndex);
            fightButton.setDisable(!team.isValid());
            System.out.println("Pokémon retiré. Taille équipe : " +
                    teamListView.getItems().size());
        }
    }
}