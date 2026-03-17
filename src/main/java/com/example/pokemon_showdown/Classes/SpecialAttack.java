package com.example.pokemon_showdown.Classes;

public class SpecialAttack extends Attack {
    public SpecialAttack(int id, String name, int typeId, int power) {
        super(id, name, typeId, power, "Special");
    }

    @Override
    public int calculateDamage(Pokemon attacker, Pokemon target) {
        int atk = attacker.getEffectiveStat("spe_attack", attacker.getSpe_attack());
        int def = target.getEffectiveStat("spe_defense", target.getSpe_defense());
        return (int) getBaseFormula(atk, def, this.power);
    }
}