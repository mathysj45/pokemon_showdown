package com.example.pokemon_showdown.Classes;


public class Attack {
    private int id;
    private String name;
    private int power;
    private int typeId;
    private String category;
    private int secondaryEffectId;

    public Attack(int id, String name, int typeId, int power, String category,
                  int secondaryEffectId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.power = power;
        this.category = category;
        this.secondaryEffectId = secondaryEffectId;
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

    public int getSecondaryEffectId() {
        return secondaryEffectId;
    }

    public void setSecondaryEffectId(int secondaryEffectId) {
        this.secondaryEffectId = secondaryEffectId;
    }
}

