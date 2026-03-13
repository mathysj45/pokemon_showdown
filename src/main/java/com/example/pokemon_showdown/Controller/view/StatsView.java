package com.example.pokemon_showdown.Controller.view;

import com.example.pokemon_showdown.Classes.Pokemon;
import javafx.scene.text.Text;

public class StatsView {

    private Text pokemonName;
    private Text pokemonType1Data;
    private Text pokemonType2Data;
    private Text pokemonHpData;
    private Text pokemonAttackData;
    private Text pokemonDefenseData;
    private Text pokemonSpeAttackData;
    private Text pokemonSpeDefenseData;
    private Text pokemonSpeedData;
    private Text pokemonMove1Data;
    private Text pokemonMove2Data;
    private Text pokemonMove3Data;
    private Text pokemonMove4Data;

    public StatsView(Text pokemonName, Text pokemonType1Data, Text pokemonType2Data, Text pokemonHpData,
                     Text pokemonAttackData, Text pokemonDefenseData, Text pokemonSpeAttackData,
                     Text pokemonSpeDefenseData, Text pokemonSpeedData, Text pokemonMove1Data,
                     Text pokemonMove2Data, Text pokemonMove3Data, Text pokemonMove4Data) {
        this.pokemonName = pokemonName;
        this.pokemonType1Data = pokemonType1Data;
        this.pokemonType2Data = pokemonType2Data;
        this.pokemonHpData = pokemonHpData;
        this.pokemonAttackData = pokemonAttackData;
        this.pokemonDefenseData = pokemonDefenseData;
        this.pokemonSpeAttackData = pokemonSpeAttackData;
        this.pokemonSpeDefenseData = pokemonSpeDefenseData;
        this.pokemonSpeedData = pokemonSpeedData;
        this.pokemonMove1Data = pokemonMove1Data;
        this.pokemonMove2Data = pokemonMove2Data;
        this.pokemonMove3Data = pokemonMove3Data;
        this.pokemonMove4Data = pokemonMove4Data;
    }

    public void updateInterface(Pokemon selectedPokemon) {
        this.pokemonName.setText(selectedPokemon.getName());
        this.pokemonType1Data.setText(String.valueOf(selectedPokemon.getType()));

        if (selectedPokemon.getType2() != null) {
            this.pokemonType2Data.setText(String.valueOf(selectedPokemon.getType2()));
        } else {
            this.pokemonType2Data.setText("");
        }

        this.pokemonHpData.setText(String.valueOf(selectedPokemon.getHp()));
        this.pokemonAttackData.setText(String.valueOf(selectedPokemon.getAttack()));
        this.pokemonDefenseData.setText(String.valueOf(selectedPokemon.getDefense()));
        this.pokemonSpeAttackData.setText(String.valueOf(selectedPokemon.getSpe_attack()));
        this.pokemonSpeDefenseData.setText(String.valueOf(selectedPokemon.getSpe_defense()));
        this.pokemonSpeedData.setText(String.valueOf(selectedPokemon.getSpeed()));

        if (selectedPokemon.getMoves() != null) {
            int moveCount = selectedPokemon.getMoves().size();
            this.pokemonMove1Data.setText(moveCount > 0 ? selectedPokemon.getMoves().get(0).getName() : "");
            this.pokemonMove2Data.setText(moveCount > 1 ? selectedPokemon.getMoves().get(1).getName() : "");
            this.pokemonMove3Data.setText(moveCount > 2 ? selectedPokemon.getMoves().get(2).getName() : "");
            this.pokemonMove4Data.setText(moveCount > 3 ? selectedPokemon.getMoves().get(3).getName() : "");
        } else {
            this.pokemonMove1Data.setText("");
            this.pokemonMove2Data.setText("");
            this.pokemonMove3Data.setText("");
            this.pokemonMove4Data.setText("");
        }
    }
}