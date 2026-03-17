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
    public void onDamageTaken(Pokemon owner, Pokemon attacker, int damage) {
        if ("REFLECT".equals(this.effectType)) {
            int recoil = (int) (attacker.getHp() * this.modifier);
            attacker.setCurrentHp(attacker.getCurrentHp() - recoil);
        }
        if ("HEAL_ONCE".equals(this.effectType) && owner.getCurrentHp() <= owner.getHp() / 2) {
            int heal = (int) (owner.getHp() * this.modifier);
            owner.setCurrentHp(owner.getCurrentHp() + heal);
            System.out.println(owner.getName() + " consomme sa " + this.name);
        }
    }

    @Override
    public void onAttackLanding(Pokemon owner, Pokemon target, int damage) {
        if ("DAMAGE_BOOST_RECOIL".equals(this.effectType)) {
            int recoil = (int) (owner.getHp() * 0.10);
            owner.setCurrentHp(owner.getCurrentHp() - recoil);
            System.out.println(owner.getName() + " subit le contrecoup de l'Orbe Vie.");
        }
    }

    @Override
    public void onTurnEnd(Pokemon owner) {
        if ("HEAL_TURN".equals(this.effectType)) {
            int heal = (int) (owner.getHp() * this.modifier);
            owner.setCurrentHp(owner.getCurrentHp() + heal);
            System.out.println(owner.getName() + " récupère des PV via " + this.name);
        }
    }

    public String getName() { return name; }
    public String getEffectType() { return effectType; }
    public double getModifier() { return modifier; }
    public String getAffectedStat() { return affectedStat; }
}