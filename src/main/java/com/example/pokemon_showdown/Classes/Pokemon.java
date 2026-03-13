package com.example.pokemon_showdown.Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Pokemon {

    private String sprite;
    private String name;
    private String type;
    private String type2;
    private int hp;
    private int attack;
    private int speAttack;
    private int defense;
    private int speDefense;
    private int speed;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/showdown";
    private static final String USER = "root";
    private static final String PASS = "";

    public Pokemon(String sprite, String name, String type, String type2,
                   int hp, int attack, int speAttack, int defense,
                   int speDefense, int speed) {
        this.sprite = sprite;
        this.name = name;
        this.type = type;
        this.type2 = type2;
        this.hp = hp;
        this.attack = attack;
        this.speAttack = speAttack;
        this.defense = defense;
        this.speDefense = speDefense;
        this.speed = speed;
    }

    public static ObservableList<Pokemon> getAllPokemons() {
        ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
        String query = "SELECT * FROM pokemon";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Pokemon pokemonInstance = new Pokemon(
                        rs.getString("sprite"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("type2"),
                        rs.getInt("hp"),
                        rs.getInt("attack"),
                        rs.getInt("speAttack"),
                        rs.getInt("defense"),
                        rs.getInt("speDefense"),
                        rs.getInt("speed")
                );
                pokemonList.add(pokemonInstance);
                System.out.println(pokemonInstance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemonList;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
