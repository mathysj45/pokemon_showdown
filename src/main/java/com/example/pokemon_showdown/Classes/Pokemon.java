package com.example.pokemon_showdown.Classes;

import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private int hp;
    private int currentHp;
    private int attack;
    private int defense;
    private int spe_attack;
    private int spe_defense;
    private int speed;
    private int type;
    private Integer type2;
    private List<Attack> moves;
    private Item heldItem;

    public Pokemon(int id, String name, int hp,
                   int attack, int defense, int spe_attack,
                   int spe_defense, int speed,
                   int type, Integer type2, List<Attack> moves) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.currentHp = hp;
        this.defense = defense;
        this.attack = attack;
        this.spe_attack = spe_attack;
        this.spe_defense = spe_defense;
        this.speed = speed;
        this.type = type;
        this.type2 = type2;
        this.moves = moves;
    }

    public Pokemon(Pokemon other) {
        this.id = other.id;
        this.name = other.name;
        this.hp = other.hp;
        this.currentHp = other.hp;
        this.attack = other.attack;
        this.defense = other.defense;
        this.spe_attack = other.spe_attack;
        this.spe_defense = other.spe_defense;
        this.speed = other.speed;
        this.type = other.type;
        this.type2 = other.type2;
        this.moves = other.moves;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }

    public void setHp(int hp) { this.hp = hp; }

    public int getCurrentHp() { return currentHp; }

    public void setCurrentHp(int currentHp) {
        this.currentHp = Math.max(0, Math.min(hp, currentHp));
    }

    public int getAttack() { return attack; }

    public void setAttack(int attack) { this.attack = attack; }

    public int getDefense() { return defense; }

    public void setDefense(int defense) { this.defense = defense; }

    public int getSpe_attack() { return spe_attack; }

    public void setSpe_attack(int spe_attack) { this.spe_attack = spe_attack; }

    public int getSpe_defense() { return spe_defense; }

    public void setSpe_defense(int spe_defense) { this.spe_defense =
            spe_defense; }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) { this.speed = speed; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public Integer getType2() { return type2; }

    public void setType2(Integer type2) { this.type2 = type2; }

    public List<Attack> getMoves() { return moves; }

    public void setMoves(List<Attack> moves) { this.moves = moves; }

    public void setHeldItem(Item heldItem) { this.heldItem = heldItem; }

    public Item getHeldItem() { return heldItem; }

    public int getEffectiveStat(String statName, int baseValue) {
        // If no item is held or it doesn't affect this stat, return base value
        if (heldItem == null || !heldItem.getAffectedStat().equalsIgnoreCase(statName)) {
            return baseValue;
        }

        // Apply the modifier (e.g., 1.5 for a 50% boost)
        return (int) (baseValue * heldItem.getModifier());
    }

    public void applyEndOfTurnItems() {
        if (heldItem != null && "HEAL_TURN".equals(heldItem.getEffectType())) {
            int healAmount = (int) (this.hp * heldItem.getModifier());
            this.setCurrentHp(this.currentHp + healAmount);
            System.out.println(this.name + " restored HP using " + heldItem.getName());
        }
    }

    public void checkConsumableItems() {
        if (heldItem != null && "HEAL_ONCE".equals(heldItem.getEffectType())) {
            if (this.currentHp > 0 && this.currentHp <= (this.hp / 2)) {
                int healAmount = (int) (this.hp * heldItem.getModifier());
                this.setCurrentHp(this.currentHp + healAmount);
                System.out.println(this.name + " consumed " + heldItem.getName() + " and restored HP.");
                this.heldItem = null; // Item is consumed and disappears
            }
        }
    }
}
