package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public class DrainEffect implements MoveEffect {
    @Override
    public String apply(Pokemon user, Pokemon target, int damageDealt) {
        int heal = (int) (damageDealt * 0.50);
        user.setCurrentHp(user.getCurrentHp() + heal);
        return user.getName() + " récupère des hp !\n";
    }
}