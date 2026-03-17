package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public class RecoilEffect implements MoveEffect {
    @Override
    public void apply(Pokemon user, Pokemon target, int damageDealt) {
        int recoil = (int) (damageDealt * 0.33); // 33% de recul
        user.setCurrentHp(user.getCurrentHp() - recoil);
        System.out.println(user.getName() + " subit le contrecoup !");
    }
}
