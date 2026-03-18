package com.example.pokemon_showdown.Classes;

import java.util.Random;

public class BattleEngine {
    private Pokemon p1;
    private Pokemon p2;
    private Random random =  new Random();

    public BattleEngine(Pokemon p1, Pokemon p2) {
        this.p1 = p1;
        this.p2 = p2;
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

        return turnLog.toString();
    }

    private String performAttack(Pokemon attacker, Pokemon target, Attack move) {
        StringBuilder attackLog = new StringBuilder();
        int damage = calculateDamage(attacker, target, move);
        target.setCurrentHp(target.getCurrentHp() - damage);

        attackLog.append(attacker.getName()).append(" utilise ").append(move.getName())
                .append(" et inflige ").append(damage).append(" dégâts !\n");

        attackLog.append(move.triggerEffect(attacker, target, damage));

        if (attacker.getHeldItem() != null) {
            attackLog.append(attacker.getHeldItem().onAttackLanding(attacker, target, damage));
        }

        if (target.getHeldItem() != null) {
            attackLog.append(target.getHeldItem().onDamageTaken(target, attacker, damage));
        }

        return attackLog.toString();
    }

    private String applyEndOfTurnEffects(Pokemon p) {
        StringBuilder log = new StringBuilder();

        // items
        if (p.getHeldItem() != null) {
            log.append(p.getHeldItem().onTurnEnd(p));
        }

        // Status damage
        if (p.getStatus() == StatusType.POISON || p.getStatus() == StatusType.BURN) {
            int damage = p.getHp() / 8;
            p.setCurrentHp(p.getCurrentHp() - damage);
            log.append(p.getName()).append(" souffre de son statut (").append(p.getStatus()).append(") !\n");
        }

        return log.toString();
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

    public String executeOpponentAttack(Attack move) {
        StringBuilder turnLog = new StringBuilder();

        turnLog.append(performAttack(p2, p1, move));

        turnLog.append(applyEndOfTurnEffects(p1));
        turnLog.append(applyEndOfTurnEffects(p2));

        return turnLog.toString();
    }

    public void endTurn() {
        p1.getHeldItem().onTurnEnd(p1);
        p2.getHeldItem().onTurnEnd(p2);
    }
}