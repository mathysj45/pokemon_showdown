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
    private StatusType status = StatusType.NONE;

    public Pokemon(int id, String name, int hp, int attack, int defense, int spe_attack, int spe_defense, int speed, int type, Integer type2, List<Attack> moves) {
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

    // Centralized HP management to handle boundary checks and Focus Sash logic
    public void setCurrentHp(int newHp) {
        if (newHp <= 0 && this.currentHp == this.hp && heldItem != null && "ENDURE".equals(heldItem.getEffectType())) {
            this.currentHp = 1;
            this.heldItem = null; // Item is consumed upon use
            System.out.println(this.name + " survit grâce à sa Ceinture Force !");
        } else {
            // Keep HP within [0, Max HP]
            this.currentHp = Math.max(0, Math.min(this.hp, newHp));
        }
    }

    // Logic for passive items like Leftovers
    public int applyEndOfTurnItems() {
        if (heldItem != null && "HEAL_TURN".equals(heldItem.getEffectType())) {
            if (this.currentHp >= this.hp || this.currentHp <= 0) return 0;

            int oldHp = this.currentHp;
            int healAmount = (int) (this.hp * heldItem.getModifier());
            this.setCurrentHp(this.currentHp + healAmount);

            return this.currentHp - oldHp;
        }
        return 0;
    }

    // Logic for triggered consumables like Berries
    public int checkConsumableItems() {
        if (heldItem != null && "HEAL_ONCE".equals(heldItem.getEffectType())) {
            if (this.currentHp > 0 && this.currentHp <= (this.hp / 2)) {
                int oldHp = this.currentHp;
                int healAmount = (int) (this.hp * heldItem.getModifier());
                this.setCurrentHp(this.currentHp + healAmount);
                this.heldItem = null; // Once used, it's gone
                return this.currentHp - oldHp;
            }
        }
        return 0;
    }

    // Stat calculation with status debuffs (Burn reduces Atk, Paralysis reduces Speed)
    public int getEffectiveStat(String statName, int baseValue) {
        int value = baseValue;
        if (heldItem != null && heldItem.getAffectedStat().equalsIgnoreCase(statName)) {
            value = (int) (value * heldItem.getModifier());
        }

        if (statName.equalsIgnoreCase("speed") && status == StatusType.PARALYSIS) value /= 2;
        if (statName.equalsIgnoreCase("attack") && status == StatusType.BURN) value /= 2;

        return value;
    }

    public void applyLifeOrbRecoil() {
        if (heldItem != null && "DAMAGE_BOOST_RECOIL".equals(heldItem.getEffectType())) {
            int recoil = (int) (this.hp * 0.10);
            this.setCurrentHp(this.currentHp - recoil);
            System.out.println(this.name + " perd des PV à cause de l'Orbe Vie !");
        }
    }

    // Standard getters/setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getCurrentHp() { return currentHp; }
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    public int getSpe_attack() { return spe_attack; }
    public int getSpe_defense() { return spe_defense; }
    public int getSpeed() { return speed; }
    public int getType() { return type; }
    public Integer getType2() { return type2; }
    public List<Attack> getMoves() { return moves; }
    public void setHeldItem(Item heldItem) { this.heldItem = heldItem; }
    public Item getHeldItem() { return heldItem; }
    public StatusType getStatus() { return status; }
    public void setStatus(StatusType status) { this.status = status; }
}