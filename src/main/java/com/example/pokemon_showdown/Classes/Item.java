package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.BattleEffect;

public class Item implements BattleEffect {
    private String name;
    private String description;
    private String affectedStat;
    private double modifier;
    private String effectType;

    public Item(String name, String description, String affectedStat, double modifier, String effectType) {
        this.name = name;
        this.description = description;
        this.affectedStat = affectedStat;
        this.modifier = modifier;
        this.effectType = effectType;
    }

    @Override
    public String onDamageTaken(Pokemon owner, Pokemon attacker, int damage) {
        if ("REFLECT".equals(this.effectType)) {
            int recoil = (int) (attacker.getHp() * this.modifier);
            attacker.setCurrentHp(attacker.getCurrentHp() - recoil);
            return attacker.getName() + " subit le renvoi de dégâts (Casque Brut) !\n";
        }
        return "";
    }

    @Override
    public String onAttackLanding(Pokemon owner, Pokemon target, int damage) {
        if ("DAMAGE_BOOST_RECOIL".equals(this.effectType)) {
            return owner.getName() + " subit le contrecoup de l'Orbe Vie.\n";
        }
        return "";
    }

    @Override
    public String onTurnEnd(Pokemon owner) {
        return "";
    }

    public String getName() { return name; }
    public String getEffectType() { return effectType; }
    public double getModifier() { return modifier; }
    public String getAffectedStat() { return affectedStat; }
}