package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

public abstract class Attack {
    protected int id;
    protected String name;
    protected int power;
    protected int typeId;
    protected String category;
    protected MoveEffect secondaryEffect;

    public Attack(int id, String name, int typeId, int power, String category) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.power = power;
        this.category = category;
    }

    public abstract int calculateDamage(Pokemon attacker, Pokemon target);

    protected double getBaseFormula(int atk, int def, int power) {
        return ((((2 * 100.0 / 5) + 2) * power * ((double) atk / def)) / 50) + 2;
    }

    public String triggerEffect(Pokemon user, Pokemon target, int damage) {
        if (this.secondaryEffect != null) {
            return this.secondaryEffect.apply(user, target, damage);
        }
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSecondaryEffect(MoveEffect secondaryEffect) {
        this.secondaryEffect = secondaryEffect;
    }


}

