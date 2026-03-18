package com.example.pokemon_showdown.Classes;

public enum StatusType {
    NONE(""),
    BURN("BRU"),
    PARALYSIS("PAR"),
    POISON("PSN");

    private final String label;
    StatusType(String label) { this.label = label; }
    public String getLabel() { return label; }
}
