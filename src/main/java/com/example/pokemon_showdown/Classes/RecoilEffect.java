package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public class RecoilEffect implements MoveEffect {
    @Override
    public String apply(Pokemon user, Pokemon target, int damageDealt) {
        int recoil = (int) (damageDealt * 0.33);
        user.setCurrentHp(user.getCurrentHp() - recoil);
        return user.getName() + " subit le contrecoup !\n";
    }
}