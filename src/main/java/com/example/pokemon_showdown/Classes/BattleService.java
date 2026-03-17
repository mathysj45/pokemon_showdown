package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Database.DatabaseManager;
import javafx.collections.ObservableList;
import java.util.Random;


public class BattleService {

    private final DatabaseManager dbManager;
    private final Random random;

    public BattleService() {
        this.dbManager = new DatabaseManager();
        this.random = new Random();
    }

    public Team generateRandomOpponent(int teamSize) {
        Team opponentTeam = new Team();
        ObservableList<Pokemon> allPokemons = dbManager.getAllPokemons();
        ObservableList<Item> allItems = dbManager.getAllItems();

        if (allPokemons.isEmpty() || allItems.isEmpty()) {
            throw new IllegalStateException("Base de données vide : impossible de générer un adversaire.");
        }

        for (int i = 0; i < teamSize; i++) {
            Pokemon template = allPokemons.get(random.nextInt(allPokemons.size()));
            Item randomItem = allItems.get(random.nextInt(allItems.size()));

            Pokemon botPokemon = new Pokemon(template);
            botPokemon.setHeldItem(randomItem);

            opponentTeam.addMember(botPokemon);
        }

        return opponentTeam;
    }
}
