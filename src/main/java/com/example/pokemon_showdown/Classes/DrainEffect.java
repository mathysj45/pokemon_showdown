package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public class DrainEffect implements MoveEffect {
    @Override
    public String apply(Pokemon user, Pokemon target, int damageDealt) {
        int oldHp = user.getCurrentHp();
        int healAmount = (int) (damageDealt * 0.50);
        user.setCurrentHp(user.getCurrentHp() + healAmount);
        int actualHealed = user.getCurrentHp() - oldHp;

        if (actualHealed > 0) {
            return user.getName() + " regagne " + actualHealed + " PV en drainant l'énergie !\n";
        }
        return user.getName() + " est déjà en pleine forme, le drainage n'a pas d'effet.\n";
    }
}