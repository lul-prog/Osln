package com.nanookmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Efecto tipo "stun": mientras esté activo, el jugador no puede moverse
 * con el teclado ni mover la cámara. El bloqueo real de la cámara y de las
 * teclas se hace del lado del cliente en ImmobilizationClientHandler.
 */
public class ImmobilizationEffect extends MobEffect {

    public ImmobilizationEffect() {
        super(MobEffectCategory.HARMFUL, 0x9E2BEE); // Color violeta
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // El bloqueo de movimiento/cámara se maneja del lado del cliente.
        // Aquí solo nos aseguramos de que la velocidad quede a cero (por si
        // el jugador ya estaba corriendo/saltando cuando empezó el efecto).
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Se aplica cada tick
    }
}
