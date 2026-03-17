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
        int atk, def;
        if (move.getCategory().equalsIgnoreCase("Physical")) {
            atk = attacker.getEffectiveStat("attack", attacker.getAttack());
            def = target.getEffectiveStat("defense", target.getDefense());
        } else {
            atk = attacker.getEffectiveStat("spe_attack", attacker.getSpe_attack());
            def = target.getEffectiveStat("spe_defense", target.getSpe_defense());
        }

        double baseDamage = ((((2 * 100.0 / 5) + 2) * move.getPower() * ((double) atk / def)) / 50) + 2;

        double typeMod = Type.getMultiplier(move.getTypeId(), target.getType(), target.getType2());

        if (attacker.getHeldItem() != null && "DAMAGE_BOOST_RECOIL".equals(attacker.getHeldItem().getEffectType())) {
            baseDamage *= attacker.getHeldItem().getModifier();
        }

        return (int) (baseDamage * typeMod);
    }

    public void endTurn() {
        p1.getHeldItem().onTurnEnd(p1);
        p2.getHeldItem().onTurnEnd(p2);
    }
}