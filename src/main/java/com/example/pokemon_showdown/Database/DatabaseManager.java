package com.example.pokemon_showdown.Database;

import com.example.pokemon_showdown.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    /**
     * Fetches all Pokémon from the database and reconstructs them as objects.
     * This method acts as an Object-Relational Mapper (ORM) for the Pokemon class.
     */
    public ObservableList<Pokemon> getAllPokemons() {
        ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
        String query = "SELECT * FROM pokemon";

        try (Connection conn = ConnectionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                // Fetch the move list associated with this specific Pokemon
                List<Attack> moves = getAttacksForPokemon(id);

                pokemonList.add(new Pokemon(
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
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemonList;
    }

    /**
     * Retrieves moves for a specific Pokémon using a JOIN table.
     * It also handles the instantiation of polymorphic effects based on the effect ID.
     */
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
                    Attack attack;
                    String category = rs.getString("category");

                    // Factory logic to determine if the attack is Physical or Special
                    if ("physical".equalsIgnoreCase(category)) {
                        attack = new PhysicalAttack(rs.getInt("id"), rs.getString("name"), rs.getInt("type_id"), rs.getInt("power"));
                    } else {
                        attack = new SpecialAttack(rs.getInt("id"), rs.getString("name"), rs.getInt("type_id"), rs.getInt("power"));
                    }

                    // Assigning the correct secondary effect strategy based on DB ID
                    switch (rs.getInt("secondary_effect_id")) {
                        case 1: attack.setSecondaryEffect(new RecoilEffect()); break;
                        case 2: attack.setSecondaryEffect(new DrainEffect()); break;
                        case 3: attack.setSecondaryEffect(new StatusEffect(StatusType.BURN)); break;
                        case 4: attack.setSecondaryEffect(new StatusEffect(StatusType.POISON)); break;
                        case 5: attack.setSecondaryEffect(new StatusEffect(StatusType.PARALYSIS)); break;
                        case 7: attack.setSecondaryEffect(new RecoilEffect()); break;
                        case 8: attack.setSecondaryEffect(new DefenseLowerEffect()); break;
                        default: attack.setSecondaryEffect(null); break;
                    }

                    attacks.add(attack);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attacks;
    }

    /**
     * Fetches all available battle items.
     * Used by BattleService to randomly equip the AI team.
     */
    public ObservableList<Item> getAllItems() {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        String query = "SELECT * FROM item";

        try (Connection conn = ConnectionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                itemList.add(new Item(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("affected_stat"),
                        rs.getDouble("modifier"),
                        rs.getString("effect_type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}