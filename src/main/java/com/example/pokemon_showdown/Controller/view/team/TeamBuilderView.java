package com.example.pokemon_showdown.Controller.view.team;

import com.example.pokemon_showdown.Classes.Item;
import com.example.pokemon_showdown.Classes.Pokemon;
import com.example.pokemon_showdown.Classes.Team;
import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class TeamBuilderView {

    @FXML protected ComboBox<Pokemon> teamBuilder;
    @FXML protected ComboBox<Item> itemComboBox;
    @FXML protected ListView<String> teamListView;
    @FXML protected Button fightButton;
    @FXML private ImageView pokemonSprite;

    private StatsView statsView;
    protected Team team = new Team();

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
        setupComboBoxesStats();
        setupComboBoxesNames();
        setupComboBoxesItems();
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

    private void setupComboBoxesStats() {
        teamBuilder.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue,
                 newValue) -> {
                    if (newValue != null) {
                        this.statsView.updateInterface(newValue);
                    }
                });
    }
    private void setupComboBoxesNames() {

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

    private void setupComboBoxesItems() {

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
}