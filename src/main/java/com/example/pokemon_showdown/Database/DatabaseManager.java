package com.example.pokemon_showdown.Database;

import com.example.pokemon_showdown.Classes.*;
import com.example.pokemon_showdown.Classes.StatusType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                    // 1. Récupération des données brutes de la table 'attack'
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int power = rs.getInt("power");
                    int typeId = rs.getInt("type_id");
                    String category = rs.getString("category");
                    int secondaryEffectId = rs.getInt("secondary_effect_id");

                    Attack attack;

                    if ("physical".equalsIgnoreCase(category)) {
                        attack = new PhysicalAttack(id, name, typeId, power);
                    } else {
                        attack = new SpecialAttack(id, name, typeId, power);
                    }

                    switch (secondaryEffectId) {
                        case 1:
                            attack.setSecondaryEffect(new RecoilEffect());
                            break;
                        case 2:
                            attack.setSecondaryEffect(new DrainEffect());
                            break;
                        case 3:
                            attack.setSecondaryEffect(new StatusEffect(StatusType.BURN));
                            break;
                        case 4:
                            attack.setSecondaryEffect(new StatusEffect(StatusType.POISON));
                            break;
                        case 5:
                            attack.setSecondaryEffect(new StatusEffect(StatusType.PARALYSIS));
                            break;
                        case 7:
                            attack.setSecondaryEffect(new RecoilEffect());
                            break;
                        default:
                            attack.setSecondaryEffect(null);
                            break;
                    }

                    attacks.add(attack);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attacks;
    }


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
                        rs.getString("effect_type") // Matches your screenshot
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}