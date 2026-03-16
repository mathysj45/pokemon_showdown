package com.example.pokemon_showdown.Database;

import com.example.pokemon_showdown.Classes.Attack;
import com.example.pokemon_showdown.Classes.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public ObservableList<Pokemon> getAllPokemons() {
        ObservableList<Pokemon> pokemonList =
                FXCollections.observableArrayList();
        String query = "SELECT * FROM pokemon";

        // Utilisation de ConnectionMySQL.getConnection()
        try (Connection conn = ConnectionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                List<Attack> moves = getAttacksForPokemon(id);

                Pokemon pokemonInstance = new Pokemon(
                        id,
                        rs.getString("name"),
                        rs.getInt("hp"),
                        rs.getInt("attack"),
                        rs.getInt("defense"),
                        rs.getInt("spe_attack"),
                        rs.getInt("spe_defense"),
                        rs.getInt("speed"),
                        rs.getInt("type"),
                        rs.getInt("type2"),
                        moves
                );
                pokemonList.add(pokemonInstance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemonList;
    }

    public List<Attack> getAttacksForPokemon(int pokemonId) {
        List<Attack> attacks = new ArrayList<>();
        String query = "SELECT a.* FROM attack a " +
                "JOIN pokemon_attacks pa ON a.id = pa.id_attacks " +
                "WHERE pa.id_pokemon = ?";

        try (Connection conn = ConnectionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, pokemonId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attacks.add(new Attack(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("power"),
                            rs.getInt("type_id"),
                            rs.getString("category"),
                            rs.getInt("secondary_effect_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attacks;
    }
}