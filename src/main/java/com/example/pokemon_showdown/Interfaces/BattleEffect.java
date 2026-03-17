package com.example.pokemon_showdown.Interfaces;

import com.example.pokemon_showdown.Classes.Pokemon;

public interface BattleEffect {
    String onDamageTaken(Pokemon owner, Pokemon attacker, int damage);
    String onAttackLanding(Pokemon owner, Pokemon target, int damage);
    String onTurnEnd(Pokemon owner);
}
