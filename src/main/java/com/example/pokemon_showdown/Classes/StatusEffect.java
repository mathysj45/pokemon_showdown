package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public class StatusEffect implements MoveEffect {
    private final StatusType statusToApply;

    public StatusEffect(StatusType status) {
        this.statusToApply = status;
    }

    @Override
    public String apply(Pokemon user, Pokemon target, int damageDealt) {
        if (target.getStatus() == StatusType.NONE) {
            target.setStatus(this.statusToApply);
            return target.getName() + " est maintenant affecté par : " + statusToApply.name() + " !\n";
        }
        return "";
    }
}