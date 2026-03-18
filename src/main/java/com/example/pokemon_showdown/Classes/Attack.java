package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.MoveEffect;

/**
 * Abstract class representing a generic Attack.
 * We use 'abstract' because an attack must be either Physical or Special to exist.
 */
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

    /**
     * Abstract method enforced on subclasses.
     * PhysicalAttack and SpecialAttack will use different stats (Attack vs SpeAttack).
     */
    public abstract int calculateDamage(Pokemon attacker, Pokemon target);

    // Standard damage formula used across all subclasses
    protected double getBaseFormula(int atk, int def, int power) {
        return ((((2 * 100.0 / 5) + 2) * power * ((double) atk / def)) / 50) + 2;
    }

    /**
     * Executes the secondary effect if assigned (Polymorphism in action).
     * Returns the formatted log message for the UI.
     */
    public String triggerEffect(Pokemon user, Pokemon target, int damage) {
        if (this.secondaryEffect != null) {
            return this.secondaryEffect.apply(user, target, damage);
        }
        return "";
    }

    // Getters and Setters...
    public int getId() { return id; }
    public String getName() { return name; }
    public int getTypeId() { return typeId; }
    public void setSecondaryEffect(MoveEffect secondaryEffect) { this.secondaryEffect = secondaryEffect; }
}