package com.example.pokemon_showdown.Classes;

import java.util.Random;

public class BattleEngine {
    private final Pokemon p1;
    private final Pokemon p2;
    private int turnCount;
    private final Random random = new Random();

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

        attackLog.append(move.triggerEffect(attacker, target, damage));

        if (attacker.getHeldItem() != null)
            attackLog.append(attacker.getHeldItem().onAttackLanding(attacker, target, damage));
        if (target.getHeldItem() != null)
            attackLog.append(target.getHeldItem().onDamageTaken(target, attacker, damage));

        return attackLog.toString();
    }

    private int calculateDamage(Pokemon attacker, Pokemon target, Attack move) {
        double damage = move.calculateDamage(attacker, target);
        double typeMod = Type.getMultiplier(move.getTypeId(), target.getType(), target.getType2());
        damage *= typeMod;

        if (attacker.getHeldItem() != null && "DAMAGE_BOOST_RECOIL".equals(attacker.getHeldItem().getEffectType())) {
            damage *= attacker.getHeldItem().getModifier();
        }
        return (int) damage;
    }

    private String applyEndOfTurnEffects(Pokemon p) {
        StringBuilder log = new StringBuilder();
        if (p.getStatus() == StatusType.POISON || p.getStatus() == StatusType.BURN) {
            int statusDamage = p.getHp() / 8;
            p.setCurrentHp(p.getCurrentHp() - statusDamage);
            log.append(p.getName()).append(" souffre de ").append(p.getStatus().getLabel()).append(".\n");
        }
        if (p.getHeldItem() != null) log.append(p.getHeldItem().onTurnEnd(p));
        return log.toString();
    }

    public int getTurnCount() { return turnCount; }
}