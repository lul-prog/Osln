package com.nanookmod.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class NanookClawAttackGoal extends Goal {

    private static final double ATTACK_RANGE = 5.0D;
    private static final double PROJECTILE_RANGE = 15.0D; // los proyectiles se pueden lanzar desde más lejos
    private static final int ATTACK_COOLDOWN_TICKS = 40;

    private final NanookEntity nanook;
    private int cooldown = 0;

    public NanookClawAttackGoal(NanookEntity nanook) {
        this.nanook = nanook;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.nanook.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.nanook.getTarget();
        return this.nanook.isAttacking() || (target != null && target.isAlive());
    }

    @Override
    public void stop() {
        this.nanook.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.nanook.getTarget();
        if (target == null) {
            return;
        }

        this.nanook.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (this.nanook.isAttacking()) {
            this.nanook.getNavigation().stop();
            return;
        }

        double distance = this.nanook.distanceTo(target);

        if (distance > PROJECTILE_RANGE) {
            // Demasiado lejos incluso para proyectiles: solo se acerca.
            this.nanook.getNavigation().moveTo(target, 1.0D);
            return;
        }

        if (this.cooldown <= 0) {
            chooseAndStartAttack(distance);
            this.cooldown = ATTACK_COOLDOWN_TICKS;
        } else if (distance > ATTACK_RANGE) {
            // Sigue acercándose mientras espera el cooldown, salvo que vaya a usar
            // proyectiles (que no necesitan estar cuerpo a cuerpo).
            this.nanook.getNavigation().moveTo(target, 1.0D);
        } else {
            this.nanook.getNavigation().stop();
        }

        if (this.cooldown > 0) {
            this.cooldown--;
        }
    }

    /**
     * Elige el ataque según la distancia: cuerpo a cuerpo (garra/hielo) si está
     * cerca, o proyectiles si está fuera de rango de golpe pero dentro de rango
     * de disparo. Dentro de rango de golpe, alterna al azar entre los 3 ataques.
     */
    private void chooseAndStartAttack(double distance) {
        if (distance > ATTACK_RANGE) {
            this.nanook.triggerClawProjectileAttack();
            return;
        }

        int choice = this.nanook.getRandom().nextInt(3);
        if (choice == 0) {
            this.nanook.triggerClawAttack();
        } else if (choice == 1) {
            this.nanook.triggerIceFistAttack();
        } else {
            this.nanook.triggerClawProjectileAttack();
        }
    }
}
