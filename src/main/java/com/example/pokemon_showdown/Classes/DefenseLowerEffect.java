package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public class DefenseLowerEffect implements MoveEffect {
    private final double reductionFactor = 0.80; // 20% reduction

    @Override
    public String apply(Pokemon user, Pokemon target, int damageDealt) {
        // Directly altering the target's defense attribute
        int oldDef = target.getDefense();
        int newDef = (int) (oldDef * reductionFactor);

        // Safeguard to prevent defense from reaching zero
        if (newDef < 1) newDef = 1;

        target.setDefense(newDef);

        return target.getName() + " voit sa défense baisser ! (" + oldDef + " -> " + newDef + ")\n";
    }
}