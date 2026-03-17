package com.example.pokemon_showdown.Interfaces;

import com.example.pokemon_showdown.Classes.Pokemon;

public interface BattleEffect {
    void onDamageTaken(Pokemon owner, Pokemon attacker, int damage);
    void onAttackLanding(Pokemon owner, Pokemon target, int damage);
    void onTurnEnd(Pokemon owner);
}
