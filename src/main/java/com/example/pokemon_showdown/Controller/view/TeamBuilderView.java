package com.example.pokemon_showdown.Controller.view;

import com.example.pokemon_showdown.Classes.Item;
import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Classes.Team;
import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class TeamBuilderView {

    @FXML private ComboBox<Pokemon> teamBuilder;
    @FXML private ComboBox<Item> itemComboBox;
    @FXML private ListView<String> teamListView;
    @FXML private Button fightButton;
    @FXML private ImageView pokemonSprite;

    private StatsView statsView;
    private Team team = new Team();

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
        initializeStatsView();
        loadDatabaseData();
        setupComboBoxes();
    }

    private void initializeStatsView() {
        this.statsView = new StatsView(
                pokemonName, pokemonType1Data, pokemonType2Data, pokemonHpData,
                pokemonAttackData, pokemonDefenseData, pokemonSpeAttackData,
                pokemonSpeDefenseData, pokemonSpeedData, pokemonMove1Data,
                pokemonMove2Data, pokemonMove3Data, pokemonMove4Data,
                pokemonSprite
        );
    }

    private void loadDatabaseData() {
        DatabaseManager dbManager = new DatabaseManager();
        teamBuilder.setItems(dbManager.getAllPokemons());
        itemComboBox.setItems(dbManager.getAllItems());
    }

    private void setupComboBoxes() {
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

        itemComboBox.setConverter(new StringConverter<Item>() {
            @Override
            public String toString(Item item) {
                return (item == null) ? "" : item.getName();
            }

            @Override
            public Item fromString(String string) {
                return null;
            }
        });
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
                teamListView.getItems().add(pokemonToAdd.getName() + " (@" + selectedItem.getName() + ")");
                fightButton.setDisable(!team.isValid());
            } else {
                System.out.println("Cannot add: Team full or invalid selection.");
            }
        } else {
            System.out.println("Please select both a Pokémon and an Item.");
        }
    }

    @FXML
    private void removePokemonFromTeam() {
        int selectedIndex = teamListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            team.removeMember(selectedIndex);
            teamListView.getItems().remove(selectedIndex);
            fightButton.setDisable(!team.isValid());
            System.out.println("Pokémon retiré. Taille équipe : " + teamListView.getItems().size());
        }
    }
}