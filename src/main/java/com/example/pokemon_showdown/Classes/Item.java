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
        StringBuilder log = new StringBuilder();
        if ("REFLECT".equals(this.effectType)) {
            int recoil = (int) (attacker.getHp() * this.modifier);
            attacker.setCurrentHp(attacker.getCurrentHp() - recoil);
            log.append(attacker.getName()).append(" subit le renvoi de dégâts !\n");
        }
        if ("HEAL_ONCE".equals(this.effectType) && owner.getCurrentHp() <= owner.getHp() / 2) {
            int heal = (int) (owner.getHp() * this.modifier);
            owner.setCurrentHp(owner.getCurrentHp() + heal);
            log.append(owner.getName()).append(" consomme sa ").append(this.name).append(".\n");
        }
        return log.toString();
    }

    @Override
    public String onAttackLanding(Pokemon owner, Pokemon target, int damage) {
        if ("DAMAGE_BOOST_RECOIL".equals(this.effectType)) {
            int recoil = (int) (owner.getHp() * 0.10);
            owner.setCurrentHp(owner.getCurrentHp() - recoil);
            return owner.getName() + " subit le contrecoup de l'Orbe Vie.\n";
        }
        return "";
    }

    @Override
    public String onTurnEnd(Pokemon owner) {
        if ("HEAL_TURN".equals(this.effectType)) {
            int heal = (int) (owner.getHp() * this.modifier);
            owner.setCurrentHp(owner.getCurrentHp() + heal);
            return owner.getName() + " récupère des PV via " + this.name + ".\n";
        }
        return "";
    }

    public String getName() { return name; }
    public String getEffectType() { return effectType; }
    public double getModifier() { return modifier; }
    public String getAffectedStat() { return affectedStat; }
}