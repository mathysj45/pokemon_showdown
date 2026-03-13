package com.example.pokemon_showdown.Classes;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Pokemon> members = new ArrayList<>();

    public boolean addMember(Pokemon p) {
        if (p != null && members.size() < 6) {
            members.add(p);
            return true;
        }
        return false;
    }

    public void removeMember(Pokemon p) {
        members.remove(p);
    }

    public List<Pokemon> getMembers() {
        return members;
    }

    public boolean isValid() {
        return members.size() >= 3; //conditions pour lancer un combat
    }
}
