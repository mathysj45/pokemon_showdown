package com.example.pokemon_showdown.Classes;

import java.util.Random;

public class BattleEngine {
    private final Pokemon p1;
    private final Pokemon p2;
    private int turnCount;
    private final Random random = new Random();
    private double lastTypeMultiplier = 1.0;

    public BattleEngine(Pokemon p1, Pokemon p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.turnCount = 1;
    }

    public BattleEngine(Pokemon p1, Pokemon p2, int currentTurn) {
        this.p1 = p1;
        this.p2 = p2;
        this.turnCount = currentTurn;
    }

    public Pokemon determineFaster() {
        int speed1 = p1.getEffectiveStat("speed", p1.getSpeed());
        int speed2 = p2.getEffectiveStat("speed", p2.getSpeed());
        if (speed1 > speed2) return p1;
        if (speed2 > speed1) return p2;
        return random.nextBoolean() ? p1 : p2;
    }

    public String executeTurn(Attack move1, Attack move2) {
        StringBuilder turnLog = new StringBuilder();
        turnLog.append("--- TOUR ").append(turnCount).append(" ---\n");

        Pokemon first = determineFaster();
        Pokemon second = (first == p1) ? p2 : p1;
        Attack firstMove = (first == p1) ? move1 : move2;
        Attack secondMove = (first == p1) ? move2 : move1;

        turnLog.append(performAttack(first, second, firstMove));

        if (second.getCurrentHp() > 0) {
            turnLog.append(performAttack(second, first, secondMove));
        }

        turnLog.append(applyEndOfTurnEffects(p1));
        turnLog.append(applyEndOfTurnEffects(p2));

        turnCount++;
        return turnLog.toString();
    }

    public String executeOpponentAttack(Attack move) {
        StringBuilder turnLog = new StringBuilder();
        turnLog.append("--- TOUR ").append(turnCount).append(" (Changement) ---\n");

        turnLog.append(performAttack(p2, p1, move));
        turnLog.append(applyEndOfTurnEffects(p1));
        turnLog.append(applyEndOfTurnEffects(p2));

        turnCount++;
        return turnLog.toString();
    }

    private String performAttack(Pokemon attacker, Pokemon target, Attack move) {
        StringBuilder attackLog = new StringBuilder();
        int damage = calculateDamage(attacker, target, move);
        target.setCurrentHp(target.getCurrentHp() - damage);

        attackLog.append(attacker.getName()).append(" utilise ").append(move.getName())
                .append(" (").append(damage).append(" dégâts).\n");

        if (lastTypeMultiplier > 1.0) {
            attackLog.append("C'est super efficace !\n");
        } else if (lastTypeMultiplier > 0 && lastTypeMultiplier < 1.0) {
            attackLog.append("Ce n'est pas très efficace...\n");
        } else if (lastTypeMultiplier == 0) {
            attackLog.append("Ça n'affecte pas ").append(target.getName()).append("...\n");
        }

        attackLog.append(move.triggerEffect(attacker, target, damage));

        if (attacker.getHeldItem() != null)
            attackLog.append(attacker.getHeldItem().onAttackLanding(attacker, target, damage));
        if (target.getHeldItem() != null)
            attackLog.append(target.getHeldItem().onDamageTaken(target, attacker, damage));

        return attackLog.toString();
    }

    private int calculateDamage(Pokemon attacker, Pokemon target, Attack move) {
        double damage = move.calculateDamage(attacker, target);

        lastTypeMultiplier = Type.getMultiplier(move.getTypeId(), target.getType(), target.getType2());
        damage *= lastTypeMultiplier;

        if (attacker.getHeldItem() != null && "DAMAGE_BOOST_RECOIL".equals(attacker.getHeldItem().getEffectType())) {
            damage *= attacker.getHeldItem().getModifier();
        }

        return (int) damage;
    }

    private String applyEndOfTurnEffects(Pokemon p) {
        if (p.getCurrentHp() <= 0) return "";

        StringBuilder log = new StringBuilder();

        if (p.getStatus() == StatusType.POISON || p.getStatus() == StatusType.BURN) {
            int statusDamage = Math.max(1, p.getHp() / 8);
            p.setCurrentHp(p.getCurrentHp() - statusDamage);
            log.append(p.getName()).append(" souffre de ").append(p.getStatus().getLabel()).append(".\n");

            if (p.getCurrentHp() <= 0) {
                log.append(p.getName()).append(" succombe à son altération d'état...\n");
                return log.toString();
            }
        }

        Item held = p.getHeldItem();
        int healRestes = p.applyEndOfTurnItems();
        if (healRestes > 0 && held != null) {
            log.append(p.getName()).append(" récupère ").append(healRestes)
                    .append(" PV grâce à : ").append(held.getName()).append(".\n");
        }

        int healBaie = p.checkConsumableItems();
        if (healBaie > 0) {
            log.append(p.getName()).append(" consomme sa Baie et récupère ")
                    .append(healBaie).append(" PV.\n");
        }

        return log.toString();
    }

    public int getTurnCount() { return turnCount; }
}