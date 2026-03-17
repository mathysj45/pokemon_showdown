package com.example.pokemon_showdown.Classes;

import com.example.pokemon_showdown.Interfaces.BattleEffect;

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

    public void executeTurn(Attack move1, Attack move2) {
        Pokemon first = determineFaster();
        Pokemon second = (first == p1) ? p2 : p1;
        Attack firstMove = (first == p1)  ? move1 : move2;
        Attack secondMove = (first == p1)   ? move2 : move1;

        performAttack(first, second, firstMove);

        if (second.getCurrentHp() > 0) {
            performAttack(second, first, secondMove);
        }

        applyEndOfTurnEffects(p1);
        applyEndOfTurnEffects(p2);
    }

    private void performAttack(Pokemon attacker, Pokemon target, Attack move) {
        int damage = calculateDamage(attacker, target, move);
        target.setCurrentHp(target.getCurrentHp() - damage);

        System.out.println(attacker.getName() + " inflige " + damage + " dégâts !");

        move.triggerEffect(attacker, target, damage);

        if (attacker.getHeldItem() != null) {
            attacker.getHeldItem().onAttackLanding(attacker, target, damage);
        }

        if (target.getHeldItem() != null) {
            target.getHeldItem().onDamageTaken(target, attacker, damage);
        }
    }

    private void  applyEndOfTurnEffects(Pokemon p) {
        if (p.getHeldItem() != null && p.getHeldItem() instanceof BattleEffect) {
            ((BattleEffect) p.getHeldItem()).onTurnEnd(p);
        }
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

    public void endTurn() {
        p1.getHeldItem().onTurnEnd(p1);
        p2.getHeldItem().onTurnEnd(p2);
    }
}