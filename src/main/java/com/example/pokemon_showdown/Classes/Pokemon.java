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

    public void setCurrentHp(int newHp) {
        if (newHp <= 0 && this.currentHp == this.hp && heldItem != null &&
                "ENDURE".equals(heldItem.getEffectType())) {
            this.currentHp = 1;
            this.heldItem = null; // Item consumed
            System.out.println(this.name + "Il a survécu ! Merci, la ceinture force.");
        }

        else {
            this.currentHp = Math.max(0, Math.min(this.hp, newHp));
        }
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

    public StatusType getStatus() { return status; }

    public void setStatus(StatusType status) { this.status = status; }

    public int getEffectiveStat(String statName, int baseValue) {
        int value = baseValue;
        if (heldItem != null && heldItem.getAffectedStat().equalsIgnoreCase(statName)) {
            value = (int) (value * heldItem.getModifier());
        }

        if (statName.equalsIgnoreCase("speed") && status == StatusType.PARALYSIS) {
            value /= 2;
        }
        if (statName.equalsIgnoreCase("attack") && status == StatusType.BURN) {
            value /= 2;
        }
        return value;
    }

    public void applyEndOfTurnItems() {
        if (heldItem != null && "HEAL_TURN".equals(heldItem.getEffectType())) {
            int healAmount = (int) (this.hp * heldItem.getModifier());
            this.setCurrentHp(this.currentHp + healAmount);
            System.out.println(this.name + " récupère des HP " + heldItem.getName());
        }
    }

    public void checkConsumableItems() {
        if (heldItem != null && "HEAL_ONCE".equals(heldItem.getEffectType())) {
            if (this.currentHp > 0 && this.currentHp <= (this.hp / 2)) {
                int healAmount = (int) (this.hp * heldItem.getModifier());
                this.setCurrentHp(this.currentHp + healAmount);
                System.out.println(this.name + " consomme " + heldItem.getName() + " et restore ses HP.");
                this.heldItem = null; // Item is consumed and disappears
            }
        }
    }

    public void applyRockyHelmet(Pokemon attacker){
        if (heldItem != null && "REFLECT".equals(heldItem.getEffectType())){
            int recoilDamage = (int) (attacker.getHp() * heldItem.getModifier());
            attacker.setCurrentHp(attacker.getCurrentHp() - recoilDamage);
            System.out.println(attacker.getName() + "a été blesser par le casque brut !");
        }
    }

    public void applyLifeOrbRecoil() {
        if (heldItem != null && "DAMAGE_BOOST_RECOIL".equals(heldItem.getEffectType())) {
            // Calculate 10% of MAX HP
            int recoil = (int) (this.hp * 0.10);
            this.setCurrentHp(this.currentHp - recoil);
            System.out.println(this.name + " lost some HP due to its Life Orb!");
        }
    }
}
