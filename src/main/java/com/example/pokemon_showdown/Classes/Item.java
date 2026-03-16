package com.example.pokemon_showdown.Classes;

public class Item {
    private String name;
    private String description;
    private String affectedStat;  // Defines which stat is modified
                                  // (e.g., "attack", "speed")
    private double modifier;  // The multiplier applied to the stat
                              // (e.g., 1.5 for +50%)

    public Item(String name, String description, String affectedStat,
                                                            double modifier) {
        this.name = name;
        this.description = description;
        this.affectedStat = affectedStat;
        this.modifier = modifier;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description =
                                                            description;}

    public String getAffectedStat() {return affectedStat;}

    public void setAffectedStat(String affectedStat) {this.affectedStat =
                                                            affectedStat;}

    public double getModifier() {return modifier;}

    public void setModifier(double modifier) {this.modifier = modifier;}
}
