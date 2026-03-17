package com.example.pokemon_showdown.Interfaces;

import com.example.pokemon_showdown.Classes.Pokemon;

public interface MoveEffect {
    void apply(Pokemon user, Pokemon target, int damageDealt);
}
