package com.example.pokemon_showdown.Classes;

public class PhysicalAttack extends Attack {
    public PhysicalAttack(int id, String name, int typeId, int power) {
        super(id, name, typeId, power, "Physical");
    }

    @Override
    public int calculateDamage(Pokemon attacker, Pokemon target) {
        int atk = attacker.getEffectiveStat("attack", attacker.getAttack());
        int def = target.getEffectiveStat("defense", target.getDefense());
        return (int) getBaseFormula(atk, def, this.power);
    }
}
